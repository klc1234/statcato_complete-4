package org.statcato.spreadsheet;

import java.util.Vector;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.Enumeration;
import org.statcato.Statcato;

/**
 * A table model for <code>Spreadsheet</code>.
 * <p>
 * The table data is represented by vectors of vectors of Cell.
 * The first row of the table contains the column/variable names.
 * The row and column numbers of table Cells are not guaranteed to be 
 * accurate.  Their correctness is ensured when a row/column is retrieved.
 *  
 * @author Margaret Yau
 * @version %I%, %G%
 * @see Spreadsheet
 * @since 1.0
 */

public class SpreadsheetModel extends AbstractTableModel implements TableModelListener{
    private boolean DEBUG = false;
    
    private int numColumns = 50; // initial number of columns
    private int numRows = 301;   // inital number of rows
    private Vector<Vector<Cell>> data; // vectors of vectors of cells
    private Vector<String> columnNames;
    private Statcato app;
    
    /**
     * Constructor given the parent frame.
     * 
     * @param app parent frame
     */
    public SpreadsheetModel(Statcato app) {
        this.app = app;
        initialize();
    }
    
    /**
     * Constructor given the parent frame, number of rows, and number of columns.
     * 
     * @param app parent frame
     * @param rows number of rows
     * @param columns number of columns
     */
    public SpreadsheetModel(Statcato app, int rows, int columns) {
        numRows = rows;
        numColumns = columns;
        this.app = app;
        initialize();
    }
    
    /**
     * Initializes the table model by putting column names into its
     * corresponding data structor and cells into the underlying table structure.
     * 
     */
    private void initialize() {
        data = new Vector<Vector<Cell>>(numRows);
        columnNames = new Vector<String>(numColumns);
        int row, col;
        
        // add column names
        for (col = 0; col < numColumns; ++col) {
            columnNames.addElement(getColumnLabel(col));
        }
        
        // add variable names row
        data.addElement(new Vector<Cell>(numColumns));
        for (col = 0; col < numColumns; ++col) {
            data.elementAt(0).addElement(new Cell("", 0, col));
        }
        // add the rest of the rows
        for (row = 1; row < numRows; ++row) {
            data.addElement(new Vector<Cell>(numColumns));
            for (col = 0; col < numColumns; ++col) {
                data.elementAt(row).addElement(new Cell("", row, col));
            }
        }
        
        addTableModelListener(this);        
    }
    
    /**
     * Returns the vector of cells in the given column number.
     * 
     * @param col column number
     * @return vector of cells in the given column
     */
    public Vector<Cell> getColumn(int col) {
        if (col < 0 || col >= numColumns)
            return null;
        Vector<Cell> column = new Vector<Cell>(getRowCount());
        for (int row = 1; row < getRowCount(); ++row) {
            Cell cell = data.elementAt(row).elementAt(col);
            cell.setRow(row);
            cell.setColumn(col);
            column.addElement(cell);
        }
        return column;
    } 
    
    /**
     * Returns the vector of cells in the given row number.
     * 
     * @param row row number
     * @return vector of cells in the given row
     */
    public Vector<Cell> getRow(int row) {
        Vector<Cell> rowVec = (Vector<Cell>) data.elementAt(row);
        for (int col = 0; col < rowVec.size(); ++col) {
            rowVec.elementAt(col).setColumn(col);
            rowVec.elementAt(col).setRow(row);
        }
        return rowVec;
    }
    
    /**
     * Returns the column label for the given column number.
     * 
     * @param column number
     * @return column label
     */
    public static String getColumnLabel(int column) {
        return "C"+(column+1);
    }

    
    
    /**
     * Returns the column number embedded in the given column label.
     * 
     * @param label column label
     * @return column number
     */
    public int getColumnNumber(String label) {
        if (label.equals(""))
            return -1;
        else if ((!label.startsWith("C") && !label.startsWith("c"))) {
            // check if label is a variable name
            label = label.trim();   // remove whitespaces at beginning and end
            // remove double quotes at beginning and end
            if (label.startsWith("\"") && label.length() > 1)
                label = label.substring(1);
            if (label.endsWith("\"") && label.length() > 1)
                label = label.substring(0, label.length() - 1);
            int loc = getVariableIndex(label);
            return loc;
        }
        else {  // Cx
            try {
                int col = Integer.parseInt(label.substring(1)) - 1;
                if (col >= 0 && col < getColumnCount())
                    return col;
                return -1;
            }   
            catch (NumberFormatException e) { // variable name starts with c/C
                // check if label is a variable name
                int loc = getVariableIndex(label.trim());
                return loc;
            }
        }
    }
    
    /**
     * Returns the index of the given variable name or -1 if not found.
     * 
     * @param label column label
     * @return index into the variable name vector
     */
    private int getVariableIndex(String label) {
        Vector<Cell> variables = data.elementAt(0);
        for (int i = 0; i < variables.size(); ++i) {
            if (variables.elementAt(i).getContents().trim().equals(label))
                return i;
        }
        return -1;
    }
    
    /**
     * Returns the row number from the given row label.
     * 
     * @param label row label
     * @return row number
     */
    public int getRowNumber(String label) {
        try {
            int row = Integer.parseInt(label);
            if (row >= 1 && row < getRowCount()) 
                return row;
            return -1;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Returns the row number of the last non-empty row.
     * 
     * @return row number
     */
    public int getLastNonEmptyRow() {
        int lastNonEmptyRow = -1;
        for (int r = 0; r < getRowCount(); ++r) {
            for (int c = 0; c < getColumnCount(); ++c) {
                Cell cdata = (Cell) getValueAt(r, c);
                if (SpreadsheetModel.hasData(cdata)) {
                    lastNonEmptyRow = r;
                    break;
                }
            }
        }
        return lastNonEmptyRow;
    }
    
    /**
     * returns the column number of the last non-empty column in the given row.
     * 
     * @param row number in which the last non-empty column is found
     * @return column number
     */
    public int getLastNonEmptyColumn(int row) {
        int lastNonEmptyCol = -1;
        
        for (int c = 0; c < getColumnCount(); ++c) {
            Cell cdata = (Cell) getValueAt(row, c);
                if (SpreadsheetModel.hasData(cdata)) {
                    lastNonEmptyCol = c;                    
                }
            }
        
        return lastNonEmptyCol;
    }
    
    /**
     * Returns the column number of the last column with data. 
     * 
     * @return column number
     */
    public int getLastNonEmptyColumn() {
        int lastCol = 0;
        int col;
        for (int i = 0; i < getLastNonEmptyRow(); ++i) {
            col = getLastNonEmptyColumn(i);
            if (col > lastCol) 
                lastCol = col;
        }
        return lastCol;
    }
    
    public void tableChanged(TableModelEvent e) {
        
        /*
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        
        if (column == 0 && row > 0) {  // row label
            
        }
         * */
    }
    
    public int getColumnCount() {
        return data.elementAt(0).size();
    }
    
    public int getRowCount() {
        return data.size();
    }
    
    /**
     * Returns the column name at the given column.
     * 
     * @param col column number
     * @return column name
     */
    @Override
    public String getColumnName(int col) {
        return columnNames.elementAt(col);
    }
    
    /**
     * Returns the variable name at the given column.
     * 
     * @param col column number
     * @return variable name
     */
    public String getVariableName(int col) {
        return data.elementAt(0).elementAt(col).getContents();
    }
    
    public Object getValueAt(int row, int col) {
        return data.elementAt(row).elementAt(col);
    }
    
    @Override
    public Class getColumnClass(int c) {
        return Object.class;
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
            return true;
    }

    /**
     * Returns the column number of columns that contain data.
     * 
     * @return vector of Integers representing column numbers
     */
    public Vector getColumnsWithData() {
        Vector<Integer> columns = new Vector<Integer>();
        for (int i=0; i < getColumnCount(); i++) {
            for (int j=1; j < getRowCount(); j++) {
                if (hasData(getValueAt(j,i))) {
                    columns.addElement(new Integer(i));
                    break;
                }
            }
        }                
        return columns;
    }

    /**
     * Gets all the column numbers.
     * 
     * @return a vector of integers (column numbers)
     */
    public Vector getAllColumnNumbers() {
        Vector<Integer> columns = new Vector<Integer>();
        
        for (int i = 0; i < getColumnCount(); ++i)
            columns.addElement(new Integer(i));
        
        return columns;
    }
    
    /**
     * Returns true if and only if the given cell has data.
     * A cell has data if its content is not an empty string.
     * 
     * @param cell cell object to check if it contains data
     * @return true iff the given cell has data
     */
    public static boolean hasData(Object cell) {
        return !((Cell)cell).getContents().equals("");
    }
    
    /**
     * Sets the value at the given row and column to the given value.
     * If the target row is the variable row, checks if the given variable 
     * name already exists.
     * 
     * @param value Object to set
     * @param row row number
     * @param col column number
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (row == 0) {
            String var = ((String) value).trim();
            // check for invalid variabl ename
            if (var.indexOf('\'') != -1) {
                app.showErrorDialog("Variable name cannot contain the character '");
                        return;
            }
            if (var.indexOf('\"') != -1) {
                app.showErrorDialog("Variable name cannot contain the character \"");
                        return;
            }
            // check if the given variable name already exists           
            if (!var.equals("")) {
                for (int i = 0; i < getColumnCount(); ++i) {
                    if (var.equals(data.elementAt(0).elementAt(i).getContents())) {
                        app.showErrorDialog("Variable name " + var + " already exists!");
                        return;
                    }
                }
            }
        }
        boolean empty = false;
        
        if (value.getClass() == String.class) {
            data.elementAt(row).set(col, new Cell((String)value, row, col));
            if (((String)value).equals(""))
                empty = true;
        }
        else if (value.getClass() == Cell.class) {
            data.elementAt(row).set(col, (Cell)value);
            if (((Cell)value).getContents().equals(""))
                empty = true;
        }
        else
            data.elementAt(row).set(col, new Cell(value.toString(), row, col));
        fireTableCellUpdated(row, col);
    }
    
    /**
     * Set the string value at the specified row and column.
     * 
     * @param value string value
     * @param row   row
     * @param col   column
     */
    public void setStringValueAt(String value, int row, int col) {
        Cell cell = (Cell) getValueAt(row, col);
        cell.setContents(value);
        fireTableCellUpdated(row, col);
    }
    
    /**
     * Sets the contents of the given column to the given data vector.
     * 
     * @param column    column number
     * @param data      vector of strings
     */
    public void setColumn(int column, Vector<String> data) {
        int row = 1;
        for (Enumeration e = data.elements(); e.hasMoreElements();) {
            setValueAt(new Cell((String)e.nextElement(), row, column), row, column);
            row++;
        }  
    } 

    /**
     * Sets the contents of the given column to the given cell vector.
     * 
     * @param column    column number
     * @param data      vector of cells
     */
    public void setCellColumn(int column, Vector<Cell> data) {
        int row = 1;
        for (Enumeration e = data.elements(); e.hasMoreElements();) {
            setValueAt(new Cell(((Cell)e.nextElement()).getContents(), row, column), 
                    row, column);
            row++;
        }  
    }
    
    /**
     * Sets the contents of the given row to the given data vector.
     * 
     * @param row row number
     * @param data vector of strings
     */
    public void setRow(int row, Vector<String> data) {
        int col = 0;
        for (Enumeration e = data.elements(); e.hasMoreElements();) {
            //setValueAt(new Cell((String)e.nextElement(), row, col), row, col);
            setValueAt((String)e.nextElement(), row, col);
            col++;
        }  
    } 
    
    /**
     * Sets the underlying table structure to the given data matrix.
     * 
     * @param data vector of vectors of strings
     */
    @SuppressWarnings("unchecked") 
    public void setData(Vector<Vector<String>> data) {
        int r = 1, c = 0;
        Vector<String> Row;
        for (Enumeration e = data.elements(); e.hasMoreElements();) {
            Row = (Vector<String>) e.nextElement();
            c = 0;
            for (Enumeration f = Row.elements(); f.hasMoreElements();) {
                setValueAt((String)f.nextElement(), r, c);
                c++;
            }
            r++;
        }
    }
    
    /**
     * Returns the table data as a string of tab-dlimited values.
     * 
     * @return string containing table data values separated by tab
     */
    public String getTabDelimitedValues() {
        String s = "";
        int lastRow = getLastNonEmptyRow();
        for (int i = 0; i <= lastRow; i++) {
            for (int j = 0; j <= getLastNonEmptyColumn(i); j++) {
                s += (String)data.elementAt(i).elementAt(j).getContents();
                s += "\t";
            }
            s += "\n";
        }        
        return s;
    }

    /**
     * Returns the table data as a string of comma-separated values.
     * 
     * @return string containing table data values separated by comma
     */
    public String getCommaSeparatedValues() {
        String s = "";
        int lastRow = getLastNonEmptyRow();
        for (int i = 0; i <= lastRow; i++) {
            for (int j = 0; j <= getLastNonEmptyColumn(i); j++) {
                s += (String)data.elementAt(i).elementAt(j).getContents();
                s += ",";
            }
            s += "\n";
        }        
        return s;
    }
    
    /**
     * Clears the contents in the cell at the specified row and column.
     * 
     * @param row row number
     * @param col column number
     */
    public void clearCell(int row, int col) {
        data.elementAt(row).elementAt(col).clear();
        fireTableCellUpdated(row, col);
    }

    /**
     * Deletes the entire row at the specified row number.  Shifts all
     * rows below upward by one row.
     * 
     * @param row row number 
     */
    public void deleteRow(int row) {
        if (row <= 0) {// don't delete variable row
            for (int col = 0; col < getColumnCount(); ++col) {
                data.elementAt(0).elementAt(col).clear();
            }
        }        
        else { // cannot delete the first row (variables)
            data.removeElementAt(row);
            Vector<Cell> rowVec = new Vector<Cell>(getColumnCount());
            for (int col = 0; col < getColumnCount(); ++col) {
                rowVec.addElement(new Cell("", row, col));
            }
            data.addElement(rowVec);
            fireTableDataChanged();
        }
        fireTableDataChanged();
    }

    /**
     * Deletes the entire column at the specified column number.
     * Shifts all columns on the right to the left by one column.
     * 
     * @param column column number
     */
    public void deleteColumn(int column) {
        Vector<Cell> rowVec;
        for (int row = 0; row < getRowCount(); row++) {
            rowVec = data.elementAt(row);
            rowVec.removeElementAt(column);
            rowVec.addElement(new Cell("", row, getColumnCount()-1));
        }
        fireTableDataChanged();
    }

    /**
     * Deletes the cell at the specified row and column.  Shifts data
     * up to replace the removed cell.
     * 
     * @param row row number
     * @param column column number
     */
    public void deleteCell(int row, int column) {
        if (row > 0) { // cannot delete the first row (variables)
            // clear cell at row, column
            data.elementAt(row).elementAt(column).clear();

            // copy the cells below the deleted cell to one cell up
            for (int i = row + 1; i < getRowCount(); ++i) {
                Cell cell2 = data.elementAt(i).elementAt(column);
                Cell cell1 = data.elementAt(i-1).elementAt(column);
                
                cell1.setContents(cell2.getContents());
            }            
            data.elementAt(getRowCount() - 1).elementAt(column).clear();
            fireTableDataChanged();
        }
    }

    /**
     * Deletes the cells within the specified range.
     * 
     * @param minRow minimum row
     * @param maxRow maximum row
     * @param minCol minimum col
     * @param maxCol maximum col
     */
    public void deleteCells(int minRow, int maxRow, int minCol, int maxCol) {
        if (minRow == 0 && maxRow == 0) {// don't delete variable row
            for (int col = minCol; col <= maxCol; ++col) {
                data.elementAt(0).elementAt(col).clear();
            }
            fireTableDataChanged();
            return;
        }

        if (minRow <= 0)
            minRow = 1;     
        
        for (int col = minCol; col <= maxCol; ++col) {
            // clear cells within [minRow, maxRow]
            for (int row = minRow; row <= maxRow; ++row)
                data.elementAt(row).elementAt(col).clear();
            // copy the cells below the deleted cells to one cell up
            for (int row = maxRow + 1; row < getRowCount(); ++row) {
                Cell cell2 = data.elementAt(row).elementAt(col);
                Cell cell1 = data.elementAt(row-(maxRow-minRow+1)).elementAt(col);
                
                cell1.setContents(cell2.getContents());
                cell2.clear();
            }                      
        }
        fireTableDataChanged();
    }

    /**
     * Inserts a blank row at the specified row number.
     * 
     * @param row row number
     */
    public void insertRow(int row) {
        Vector<Cell> rowVec = new Vector<Cell>(getColumnCount());
        for (int col = 0; col < getColumnCount(); ++col) {
            rowVec.addElement(new Cell("", row, col));
        }
        if (row == getRowCount()) {
            data.addElement(rowVec);
        }        
        else {
            data.insertElementAt(rowVec, row);
        }
        numRows++;
        fireTableStructureChanged();
        fireTableDataChanged();
    }
    
    /**
     * Inserts a blank column at the specified column number.
     * @param col column number
     */
    public void insertColumn(int col) {
        Vector<Cell> rowVec;
        for (int row = 0; row < getRowCount(); row++) {
            rowVec = data.elementAt(row);
            rowVec.insertElementAt(new Cell("", row, col), col);
        }
        columnNames.insertElementAt(getColumnLabel(col), col);
        for (int i = col + 1; i < getColumnCount(); i++) {
            columnNames.setElementAt(getColumnLabel(i), i);
        }
        numColumns++;
        fireTableStructureChanged();
        fireTableDataChanged();        
    }
    
    /**
     * Inserts a blank cell at the specified row and column.
     * 
     * @param row row number
     * @param col column number
     */
    public void insertCell(int row, int col) {
        // copy the cells to one cell dowm at the insertion point
        insertRow(getRowCount());
        for (int i = getRowCount() - 2; i >= row; --i) {
            Cell cell2 = data.elementAt(i).elementAt(col);

            Cell cell1 = data.elementAt(i+1).elementAt(col);
            
            cell1.setContents(cell2.getContents());
            cell2.clear();
        }          
        fireTableStructureChanged();
        fireTableDataChanged();
    }
    
    /**
     * Prints debug data.
     */
    public void printDebugData() {       

        for (int i=0; i < getRowCount(); i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < getColumnCount(); j++) {
                System.out.print("  " + data.elementAt(i).elementAt(j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }    

}