/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.file;

import org.statcato.Statcato;
import org.statcato.LogWindow;
import org.statcato.DialogEdit;
import org.statcato.utils.HelperFunctions;
import org.statcato.spreadsheet.Spreadsheet;

import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JOptionPane;
import java.util.Vector;
import java.util.Enumeration;

/**
 * A collection of file operations.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class FileOperations {
    private static File recentDatasheet = null;
    private static File recentProject = null;
    private static ExtensionFileFilter excelFilter = 
            new ExtensionFileFilter("Excel (*.xls)", "xls");
    private static ExtensionFileFilter csvFilter = 
            new ExtensionFileFilter("Comma-separated values(*.csv)", "csv");
    private static ExtensionFileFilter txtFilter = 
            new ExtensionFileFilter("Tab-delimited values (*.txt)", "txt");
    private static ExtensionFileFilter stcFilter =
            new ExtensionFileFilter("Statcato Project File (.stc)", Project.extension);

    /**
     * Opens a log.
     *
     * @param app parent application
     */
    public static void openLogFile(Statcato app) {
        // create a file chooser
        JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new ExtensionFileFilter("HTML", "html"));
        fc.setAcceptAllFileFilterUsed(false);
        LogWindow LogTextPane = app.getLogTextPane();
        
        int returnValue = fc.showOpenDialog(app);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            String contents = HelperFunctions.getFileContents(file);
            // check if append to or overwrite current log window
            Object[] options = {"Overwrite", "append"};
            int choice = JOptionPane.showOptionDialog(app, 
                    "Would you like to overwrite the current log window with the file you are opening, or append file contents to current log window",
                    "Load Log Window", JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (choice == 0) { // overwrite                
                app.compoundEdit = new DialogEdit("overwrite log");
                LogTextPane.addHeading("Open saved log " +
                        "window (overwrite): " + file.getName());
                LogTextPane.overwrite(contents);
                app.compoundEdit.end();
                app.addCompoundEdit(app.compoundEdit);
            }
            else {// append                
                contents = "<br><hr><br>" + contents;
                app.compoundEdit = new DialogEdit("append log");
                LogTextPane.addHeading("Open saved log window " +
                        "(append): " + file.getName());
                LogTextPane.addText(contents);
                app.compoundEdit.end();
                app.addCompoundEdit(app.compoundEdit);
            }          
        }            
    }

    /**
     * Opens a datasheet
     * 
     * @param app parent application
     */
    public static void openDatasheet(Statcato app) {
        // create a file chooser
        JFileChooser fc = new JFileChooser(
                recentDatasheet==null?null:recentDatasheet.getParentFile());
        fc.addChoosableFileFilter(excelFilter);
        fc.addChoosableFileFilter(csvFilter);
        fc.addChoosableFileFilter(txtFilter);
        fc.setAcceptAllFileFilterUsed(false);
        
        // set selected file filter
        if (recentDatasheet != null) {
            if (FileChooserUtils.getExtension(recentDatasheet).equals("xls")) {
                fc.setFileFilter(excelFilter);
            }
            else if (FileChooserUtils.getExtension(recentDatasheet).equals("csv")) {
                fc.setFileFilter(csvFilter);
            }
            else {
                fc.setFileFilter(txtFilter);
            }
        }

        int returnValue = fc.showOpenDialog(app);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (file == null || !file.isFile()) {
                app.showErrorDialog("Error opening datasheet");
            }
            else {
                // open excel file
                if (FileChooserUtils.getExtension(file).equals("xls")) {
                    Vector<String> sheets = HelperFunctions.readExcelFile(file);
                    if (sheets != null) {
                        for (Enumeration e = sheets.elements(); e.hasMoreElements(); ) {
                            String str = (String) e.nextElement();
                            if (!str.trim().equals(""))
                                app.getDatasheetTabbedPane().addDatasheet(str, null);
                        }
                    }
                    else {
                        app.showErrorDialog("Error reading " + "excel file.");
                        return;
                    }
                }
                else {
                    String contents = HelperFunctions.getFileContents(file);
                    if (FileChooserUtils.getExtension(file).equals("csv")) {
                        contents = HelperFunctions.parseCSV(contents);
                    }
                    app.getDatasheetTabbedPane().addDatasheet(contents, file);
                }
                recentDatasheet = file;
            }
        }               
    }

    /**
     * Opens a project.
     *
     * @param app parent application
     */
    public static void openProjectFile(Statcato app) {
        // create a file chooser
        JFileChooser fc = new JFileChooser(
                recentProject==null?null:recentProject.getParentFile());
        fc.addChoosableFileFilter(stcFilter);
        fc.setAcceptAllFileFilterUsed(false);
        Project project = app.getProject();
        
        int returnValue = fc.showOpenDialog(app);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (file == null || !file.isFile())
                app.showErrorDialog("Error opening project file");
            else {
                if (!project.readFile(file.getPath())) {
                    app.showErrorDialog("Error opening project file");
                }
                recentProject = file;
            }
        }            
    }

    /**
     * Saves the current datasheet of the application to a file.
     *
     * @param app Statcato application
     * @param isSaveAs whether to save as
     */
    public static void saveDatasheet(Statcato app, boolean isSaveAs) {
        Spreadsheet table = app.getSpreadsheet();
        File file = table.writeToFile(app, isSaveAs);
        if (file != null) {
            app.getDatasheetTabbedPane().setCurrentTabTitle(file.getName());
            app.setStatus("Datasheet saved to " + file.getName() + ".");
            app.setClearStatusTimer(3);
            recentDatasheet = file;
        }
    }

    /**
     * Saves the current project of the application to a file.
     *
     * @param app Statcato application
     * @param isSaveAs whether to save as
     */
    public static void saveProject(Statcato app, boolean isSaveAs) {
        if (app.getProject().writeToFile(isSaveAs)) {
            app.setTitle("Statcato - " + app.getProject().getName());
            app.setStatus("Project saved to " + app.getProject().getName() + ".");
            app.setClearStatusTimer(3);
            recentProject = app.getProject().getFile();
        }
        else {
            app.showErrorDialog("Failed to save project file");
        }
    }

    /**
     * Returns the recent datasheet file.
     *
     * @return recent datasheet file
     */
    public static File getRecentDatasheet() {
        return recentDatasheet;
    }

    /**
     * Returns the recent project file.
     *
     * @return recent project file
     */
    public static File getRecentProject() {
        return recentProject;
    }
}
