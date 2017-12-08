/*
 * DatasheetTabbedPane.java
 */

package org.statcato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import org.statcato.spreadsheet.*;

/**
 * A tabbed pane of SpreadsheetScrollPane objects.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class DatasheetTabbedPane extends JTabbedPane implements MouseListener, Serializable {
    transient private Statcato app;
    transient private MouseListener popupListener;
    transient private int[] KeyMneumonics = {KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4,
    KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9};
    
    /**
     * Constructor, given the parent frame and popup listener for this object.
     * 
     * @param mTab parent frame
     * @param popupListener popup mouse listener
     */
    public DatasheetTabbedPane(Statcato mTab, MouseListener popupListener) {
        super();
        app = mTab;
        this.popupListener = popupListener;
        initialize();
        addMouseListener(this);
    }
    
    /**
     * Adds the first spreadsheet scrollpane object to this pane.
     * Sets its mouse listener and mnemonic.
     */
    private void initialize() {
        SpreadsheetScrollPane Pane = new SpreadsheetScrollPane(app);
        Pane.getSpreadsheet().addMouseListener(popupListener);
        addTab("Datasheet 1", Pane);
        setMnemonicAt(0, KeyEvent.VK_1);
    }
    
    /**
     * Adds a tab containing the given component and title to this tabbed pane.
     * 
     * @param title string to be displayed at the top of the tab
     * @param component component contained in the tab
     */
    @Override
    public void addTab(String title, Component component) {
        this.addTab(title, component, null);
    }
    
    /**
     * Adds a tab containing the given component, title, and icon.
     * 
     * @param title string to be displayed at the top of the tab
     * @param component component to be contained in the tab
     * @param extraIcon icon to be displayed at the top of the tab
     */
    public void addTab(String title, Component component, Icon extraIcon) {
        super.addTab(title, new CloseTabIcon(extraIcon), component);
    }
    
    /**
     * Adds a blank spreadsheet to this tabbed pane.
     */
    public void addDatasheet() {
        // add a blank Datasheet
        SpreadsheetScrollPane Pane = new SpreadsheetScrollPane(app);    
        Pane.getSpreadsheet().addMouseListener(popupListener);
        int tabCount = getTabCount() + 1;
        addTab("Datasheet " + tabCount, Pane);
        if (tabCount <= KeyMneumonics.length)
            setMnemonicAt(tabCount - 1, KeyMneumonics[tabCount -1]);
        setSelectedIndex(indexOfComponent(Pane));         
        app.addUndoListenerToDatasheet();
    }
    
    /**
     * Add the given tab-delimited string to a new Datasheet.
     * If file is not null, the given string represents contents of the 
     * given file
     * 
     * @param contents a tab-delimited string
     * @param file file containing contents; null if none
     */
    public void addDatasheet(String contents, File file) {
        // create a new spreadsheet in a new tab
        SpreadsheetScrollPane Pane = new SpreadsheetScrollPane(app);  
        Pane.getSpreadsheet().addMouseListener(popupListener);
        int tabCount = getTabCount() + 1;
        if (file == null)
            addTab("Datasheet " + tabCount, Pane);
        else
            addTab(file.getName(), Pane);
        if (tabCount <= KeyMneumonics.length)
            setMnemonicAt(tabCount - 1, KeyMneumonics[tabCount -1]);
        setSelectedIndex(indexOfComponent(Pane));  
        Spreadsheet ThisSpreadsheet = Pane.getSpreadsheet();
        //if (file == null)
        //    ThisSpreadsheet.setData(contents);
        //else
            ThisSpreadsheet.setDataUnchangedStatus(contents);
        ThisSpreadsheet.setFile(file);        
        app.addUndoListenerToDatasheet();
    }    
    
    /**
     * Adds the given spreadsheet object to this tabbed pane.
     * 
     * @param ThisSpreadsheet spreadsheet to be added
     */
    public void addDatasheet(Spreadsheet ThisSpreadsheet) {
        // add the given spreadsheet in a new tab
        SpreadsheetScrollPane Pane = new SpreadsheetScrollPane(app, ThisSpreadsheet);
        Pane.getSpreadsheet().addMouseListener(popupListener);
        int tabCount = getTabCount() + 1;
        addTab("Datasheet " + tabCount, Pane);
        if (tabCount <= KeyMneumonics.length)
            setMnemonicAt(tabCount - 1, KeyMneumonics[tabCount -1]);
        setSelectedIndex(indexOfComponent(Pane));         
        app.addUndoListenerToDatasheet();
    }

    public void addDatasheets(DatasheetTabbedPane pane) {
        for (int i = 0; i < pane.getTabCount(); ++i) {
            SpreadsheetScrollPane tab = (SpreadsheetScrollPane) 
                    getComponentAt(i);
            Spreadsheet sheet = tab.getSpreadsheet();
            addDatasheet(sheet);
        }
    }
    
    /**
     * Prompts to save the current Datasheet if it has been changed 
     * and then closes it.  
     * 
     * @return 0 if the current Datasheet is closed successfully and -1 if
     * it is not closed
     */
    public int closeCurrentDatasheet() {
        // close current Datasheet
        int i = getSelectedIndex();
        Object[] options = {"Yes, close Datasheet anyway", "Cancel"};
        if (i != -1) {
            SpreadsheetScrollPane tab = (SpreadsheetScrollPane) 
                    getComponentAt(i);
            if (tab.getChangedStatus()) {
                int choice = JOptionPane.showOptionDialog(this, 
                    app.getCurrentTabTitle() + " is not yet saved.  "+
                    "Do you want to close it anyway?",
                    "Unsaved Datasheet...",
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, null,
                    options, options[1]);
                if (choice == 1 || choice == JOptionPane.CLOSED_OPTION) {
                    setSelectedIndex(i);
                    return -1;       
                }
            }            
            remove(i);
        }
        if (getTabCount() == 0)
            addDatasheet();
        app.clearUndoManager();
        return 0;
    }    

    
    /**
     * Checks if there is any changed tab and if so prompts the user to save file.
     * 
     * @return 0 if the pane can be closed and 1 if the pane cannot be closed
     */
    public int closePane() {
        Object[] options = {"Yes, close anyway", "Cancel"};
        for (int i = 0; i < getTabCount(); ++i) {
            SpreadsheetScrollPane tab = (SpreadsheetScrollPane) 
                    getComponentAt(i);
            if (tab.getChangedStatus()) {
                int choice = JOptionPane.showOptionDialog(this, 
                    "There is a Datasheet that is not saved.  "+
                    "Do you want to close anyway?",
                    "Unsaved Datasheet...",
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, null,
                    options, options[1]);
                if (choice == 1 || choice == JOptionPane.CLOSED_OPTION) {
                    setSelectedIndex(i);
                    return -1;       
                }
                else
                    return 0;
            }             
        }
        return 0;
    }
    
    /**
     * Returns true iff at least one of the datasheets is modified.
     * 
     * @return true if at least one of the datasheets is modified or 
     * false otherwise.
     */
    public boolean getChangedStatus() {
        for (int i = 0; i < getTabCount(); ++i) {
            SpreadsheetScrollPane tab = (SpreadsheetScrollPane) 
                    getComponentAt(i);
            if (tab.getChangedStatus()) {
                return true;
            }             
        }
        return false;
    }

    /**
     * Set the modified status of the current datasheet to changed.
     */
    public void setChangedStatus() {
        ((SpreadsheetScrollPane)getSelectedComponent()).getSpreadsheet().setChangedStatus();
    }

    /**
     * Sets the title of the selected tab.
     * 
     * @param title string to be displayed on the selected tab
     */
    public void setCurrentTabTitle(String title) {
        setTitleAt(getSelectedIndex(), title);
    }
    
    /**
     * Resets the tab titles.
     */
    public void resetTabTitles() {
        for (int i = 0; i < getTabCount(); ++i) {
            setTitleAt(i, "Datasheet " + (i + 1));
            //String title = getTitleAt(i);
            //if (title.endsWith("*"))
            //    setTitleAt(i, title.substring(0, title.length() - 1));
        }
    }
    
    /**
     * Returns the title at the selected tab.
     * 
     * @return string displayed as the selected tab title
     */
    public String getCurrentTabTitle() {
        return getTitleAt(getSelectedIndex());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int tabNumber=getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (tabNumber < 0) return;
        Rectangle rect=((CloseTabIcon)getIconAt(tabNumber)).getBounds();
        if (rect.contains(e.getX(), e.getY())) {
            //the tab is being closed
            //this.removeTabAt(tabNumber);
            closeCurrentDatasheet();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    /**
     * The class which generates the 'X' icon for the tabs. The constructor
     * accepts an icon which is extra to the 'X' icon. 
     * This value is null if no extra icon is required.
     */
    class CloseTabIcon implements Icon {
      private int x_pos;
      private int y_pos;
      private int width;
      private int height;
      private Icon fileIcon;

      public CloseTabIcon(Icon fileIcon) {
        this.fileIcon=fileIcon;
        width=16;
        height=16;
        //setToolTipText("Close this tab");
      }

      @Override
      public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x_pos=x;
        this.y_pos=y;

        Color col=g.getColor();

        g.setColor(Color.gray);
        int y_p=y+2;
        g.drawLine(x+1, y_p, x+12, y_p);
        g.drawLine(x+1, y_p+13, x+12, y_p+13);
        g.drawLine(x, y_p+1, x, y_p+12);
        g.drawLine(x+13, y_p+1, x+13, y_p+12);
        g.drawLine(x+3, y_p+3, x+10, y_p+10);
        g.drawLine(x+3, y_p+4, x+9, y_p+10);
        g.drawLine(x+4, y_p+3, x+10, y_p+9);
        g.drawLine(x+10, y_p+3, x+3, y_p+10);
        g.drawLine(x+10, y_p+4, x+4, y_p+10);
        g.drawLine(x+9, y_p+3, x+3, y_p+9);
        g.setColor(col);
        if (fileIcon != null) {
          fileIcon.paintIcon(c, g, x+width, y_p);
        }
      }

      @Override
      public int getIconWidth() {
        return width + (fileIcon != null? fileIcon.getIconWidth() : 0);
      }

      @Override
      public int getIconHeight() {
        return height;
      }

      public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, width, height);
      }
    }    
}
