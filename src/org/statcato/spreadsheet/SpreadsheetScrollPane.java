package org.statcato.spreadsheet;

import org.statcato.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * A scroll pane containing a spreadsheet object.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @see Spreadsheet
 * @see RowHeaderTable
 * @since 1.0
 */
public class SpreadsheetScrollPane extends JScrollPane {
    private Spreadsheet table;
    private RowHeaderTable header;
    private boolean dragged = false;
    private int startDraggedRow = -1;
    private int lastSelectedRow = -1;
    
    /**
     * Constructor, given parent frame.
     * 
     * @param mTab parent frame
     */
    public SpreadsheetScrollPane(Statcato mTab) {
        super(new Spreadsheet(mTab));
        table = (Spreadsheet)getViewport().getView();
        header = new RowHeaderTable(table);
        setRowHeaderView(header);
        addRowHeaderMouseListeners();
    }
    
    /**
     * Constructor, given the parent frame and the spreadsheet to be contained.
     * 
     * @param mTab parent frame
     * @param s spreadsheet
     */
    public SpreadsheetScrollPane(Statcato mTab, Spreadsheet s) {
        super(s);
        table = s;
        header = new RowHeaderTable(table);
        setRowHeaderView(header);
        addRowHeaderMouseListeners();     
    }
    
    /**
     * Returns the internal spreadsheet object.
     * 
     * @return spreadsheet
     */
    public Spreadsheet getSpreadsheet() {
        return table;
    }
    
    /**
     * Returns the row header table.
     * 
     * @return row header table
     */
    public RowHeaderTable getRowHeaderTable() {
        return header;
    }
    
    /**
     * Returns the changed status of the internal spreadsheet.
     * 
     * @return changed status of spreadsheet
     */
    public boolean getChangedStatus() {
        return table.getChangedStatus();
    }
    
    /**
     * Adds mouse listeners to the row header table.
     */
    private void addRowHeaderMouseListeners() {
        MouseMotionAdapter motionListener = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {            
                if (!dragged)
                    return;

                // dragging on row headers in motion
                int row = header.rowAtPoint(e.getPoint());
                ListSelectionModel headerSelection = header.getSelectionModel();
                ListSelectionModel tableSelection = table.getSelectionModel(); 
                
                if (row == -1) { // invalid dragging motion on row headers
                    tableSelection.clearSelection();
                    headerSelection.clearSelection();
                    return;
                }

                int count = table.getColumnCount();

                if (count != 0)
                    table.setColumnSelectionInterval(0, count - 1);

                if (row < startDraggedRow) {
                    tableSelection.setSelectionInterval(row, startDraggedRow);       
                }
                else {
                    tableSelection.setSelectionInterval(startDraggedRow, row);
                }                            
            }
        };        
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                dragged = false;
                startDraggedRow = -1;
            }

            @Override
            public void mousePressed(MouseEvent e) {  
                int row = header.rowAtPoint(e.getPoint());
                ListSelectionModel headerSelection = header.getSelectionModel();
                ListSelectionModel tableSelection = table.getSelectionModel();

                if (dragged) {
                    tableSelection.clearSelection();
                    headerSelection.clearSelection();
                    dragged = false;
                    startDraggedRow = -1;
                }

                if (row == -1)
                    return;

                dragged = true; // start dragging on a row
                int count = table.getColumnCount();

                if (count != 0)
                    table.setColumnSelectionInterval(0, count - 1);       

                tableSelection.addSelectionInterval(row, row);
                startDraggedRow = row;
            }                        

            @Override
            public void mouseClicked(MouseEvent e) {
                ListSelectionModel headerSelection = header.getSelectionModel();
                ListSelectionModel tableSelection = table.getSelectionModel();
                int row = header.rowAtPoint(e.getPoint());

                if (row == -1)
                    return;

                int count = table.getColumnCount();

                if (count != 0)
                    table.setColumnSelectionInterval(0, count - 1);

                if (e.isShiftDown()) {
                    if (lastSelectedRow != -1) {
                        if (lastSelectedRow <= row) {
                            headerSelection.setSelectionInterval(lastSelectedRow, row);
                            tableSelection.setSelectionInterval(lastSelectedRow, row);
                        }
                        else {
                            headerSelection.setSelectionInterval(row, lastSelectedRow);
                            tableSelection.setSelectionInterval(row, lastSelectedRow);                            
                        }
                    }                    
                    /*
                    int anchor = headerSelection.getAnchorSelectionIndex();
                    int lead = headerSelection.getLeadSelectionIndex();

                    if (anchor != -1) {
                        boolean old = headerSelection.getValueIsAdjusting();
                        headerSelection.setValueIsAdjusting(true);
                        boolean anchorSelected = headerSelection.isSelectedIndex(anchor);

                        if (lead != -1) {
                            if (anchorSelected) {
                                headerSelection.removeSelectionInterval(anchor, lead);
                            }
                            else {
                                headerSelection.addSelectionInterval(anchor, lead);
                            }
                        }
                        if (anchorSelected) {
                            headerSelection.addSelectionInterval(anchor, row);
                        }
                        else {
                            headerSelection.removeSelectionInterval(anchor, row);
                        }
                        tableSelection.setSelectionInterval(headerSelection.getMinSelectionIndex(), headerSelection.getMaxSelectionIndex());
                        headerSelection.setValueIsAdjusting(old);
                    }
                    else {
                        headerSelection.setSelectionInterval(row, row);
                        tableSelection.setSelectionInterval(row, row);
                    }
                     * */
                }
                else if (e.isControlDown()) {
                    boolean old = headerSelection.getValueIsAdjusting();
                    headerSelection.setValueIsAdjusting(true);
                    int anchor = headerSelection.getAnchorSelectionIndex();
                    int lead = headerSelection.getLeadSelectionIndex();                   
                    
                    if (!headerSelection.isSelectedIndex(row))
                        tableSelection.removeSelectionInterval(row, row);
                    else
                        tableSelection.addSelectionInterval(row, row);

                    headerSelection.setValueIsAdjusting(old);
                }
                else {
                    headerSelection.setSelectionInterval(row, row);  
                    tableSelection.setSelectionInterval(row, row);
                }
                
                lastSelectedRow = row;
            }           
        };
        header.addMouseListener(mouseAdapter);
        header.addMouseMotionListener(motionListener);
    }

}
