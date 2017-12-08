/*
 * StatcatoDialog.java
 *
 * Created on August 28, 2008, 3:24 PM
 */

package org.statcato;

import org.statcato.spreadsheet.*;
import javax.swing.*;
import java.awt.Dialog;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * A generic dialog for Statcato.
 * @author  Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class StatcatoDialog extends javax.swing.JDialog {
    static private final int HELP_MSG_DELAY = 5;
    protected Statcato app;
    protected Spreadsheet ParentSpreadsheet;
    /**
     * Name of help file for this dialog
     */
    protected String helpFileName = Statcato.DEFAULTHELPFILE;
    /**
     * Name of the dialog
     */
    protected String name = "Statcato dialog";
    /**
     * Short description of the dialog
     */
    protected String description = "";
    /**
     * Help message for this dialog
     */
    protected ArrayList<String> helpStrings;

    private JMenuItem helpMenuItem;

    /** Creates new form StatcatoDialog */
    public StatcatoDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        JMenuBar menu = new JMenuBar();
        helpMenuItem = new JMenuItem("Help");
        app = (Statcato) parent;
        menu.add(helpMenuItem);
        helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        setJMenuBar(menu);
        helpStrings = new ArrayList<String>();
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                app.clearStatus();
                app.clearStatusTimerList();
            }
        });
    }

    /**
     * Sets the name of this dialog.
     *
     * @param name string name of the dialog
     */
    public void setDialogName(String name) {
        this.name = name;
    }

    /**
     * Sets the name of the help file to the given name.
     * Enables help to the given name for the help menu.
     *
     * @param name id of help file
     */
    public void setHelpFile(String name) {
        helpFileName = name;
        if (app != null)
            app.addDialogHelp(helpMenuItem, helpFileName);
    }
    
    /**
     * Updates elements on the dialog so that they have the most
     * current values.  Called by {@link #setVisible} to make
     * sure the dialog displays current values when made visible again.
     * 
     * @see #setVisible(boolean)
     */
    public void updateElements() {
         
    }
    
    @Override
    public void setVisible(boolean state) {
        if (state) {
            ParentSpreadsheet = app.getSpreadsheet();
            updateElements();
            app.clearStatusTimerList();
            app.setStatus(name + " Dialog opened. " + description);
            // display help messages in status bar
            
            int i = 1;
            for (int j = 0; j < 5; ++j) {
                Iterator<String> itr = helpStrings.iterator();
                while (itr.hasNext()) {
                  String element = itr.next();
                  app.setStatusTimer(element, HELP_MSG_DELAY*i);
                  i++;
                }
                app.setStatusTimer("", HELP_MSG_DELAY*i);
                i++;
            }
        }
        else {
            app.clearStatus();
            app.clearStatusTimerList();
        }

        super.setVisible(state);
    }

    @Override
    public void dispose() {
        app.clearStatus();
        super.dispose();
    }
    
    /**
     * Updates the given combo box with available columns in the parent
     * spreadsheet while leaving the originally selected items selected.
     * 
     * @param comboBox combo box to be updated
     */
    protected void updateComboBox(JComboBox comboBox) {
        String selected = (String)comboBox.getSelectedItem();
        ParentSpreadsheet.populateComboBox(comboBox); 
        comboBox.setSelectedItem((Object)selected);
    }
    
    /**
     * Removes all the elements in the given mutable list.
     * 
     * @param list list to be cleared
     */
    protected void clearMutableColumnsList(JList list) {
        DefaultListModel listModel = (DefaultListModel)list.getModel();
        listModel.clear();
    }
    
    /**
     * Updates the given list with available columns in the parent
     * spreadsheet while leaving the originally selected items selected.
     * 
     * @param list list to be updated
     */
    protected void updateColumnsList(JList list) {
         Object[] selected = list.getSelectedValues();
         ParentSpreadsheet.populateColumnsList(list);
         if (selected.length > 0) {
             int[] indices = new int[selected.length];             
             for (int i = 0; i < selected.length; ++i) {
                 list.setSelectedValue(selected[i], false);
                 indices[i] = list.getSelectedIndex();
             }
             list.setSelectedIndices(indices);
         }
    }
    
    /**
     * Updates the given list with all columns in the parent spreadsheet
     * while leaving the originally selected items selected.
     * 
     * @param list list to be updated
     */
    protected void updateAllColumnsList(JList list) {
         Object[] selected = list.getSelectedValues();
         ParentSpreadsheet.populateAllColumnsList(list);
         if (selected.length > 0) {
             int[] indices = new int[selected.length];
             for (int i = 0; i < selected.length; ++i) {
                 list.setSelectedValue(selected[i], false);
                 indices[i] = list.getSelectedIndex();
             }
             list.setSelectedIndices(indices);
         }
    }
    
    @Override
    public String toString() {
        return name;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
