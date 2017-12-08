package org.statcato.spreadsheet;

/**
 * A cell in a <code>Spreadsheet</code> object.
 * 
 * @author myau
 * @version %I%, %G%
 * @see Spreadsheet
 * @since 1.0
 */
public class Cell {
    private String contents;
    private int row, column;
    private Double value;       // numeric value of cell contents
                                // null if not a number
    
    /**
     * Constructor, given the contents as a string and the row and column of 
     * the cell.
     * 
     * @param contents string representation of the cell contents
     * @param row row number (integer)
     * @param column column number (integer)
     */
    public Cell(String contents, int row, int column) {
        this.contents = contents.trim();
        this.row = row;
        this.column = column;
        try {
            double num = Double.parseDouble(contents);            
            value = new Double(num);
        }
        catch (NumberFormatException e) {
            value = null;
        }        
    }
    
    /**
     * Returns the unformatted string representation the contents of this cell.
     * 
     * @return string representation of the contents
     */
    public String getContents() { return contents; }
    
    /**
     * Returns the row number of this cell.
     * 
     * @return row number (integer)
     */
    public int getRow() { return row; }
    
    /**
     * Returns the column number of this cell
     * 
     * @return column number (integer)
     */
    public int getColumn() { return column; }
    
    /**
     * Returns the string representation of this cell.
     * If the string represents an integer, simply converts it to string.
     * If the string represents a non-integer number, converts it to string
     * such that there are at most six decimal places.
     * Otherwise, simply returns the unformatted string representation of 
     * the contents.
     * 
     * @return string representation
     */
    @Override
    public String toString() {       
        if (isNumeric()) {
            double num = value.doubleValue();
                // limit to 6 decimal places

                // find the number of decimal places (no more than 6)
                // needed to represent the number
                int places = 0;

                int sixPlaces = (int) Math.round((num - Math.floor(num)) * 1000000);
                while (sixPlaces > 0 && places < 6) {
                    sixPlaces %= Math.pow(10, 5-places);
                    places++;
                }
                
                return String.format("%." + places + "f", num);
        }
        return contents; 
    }
    
    /**
     * Returns the numerical value of the cell contents, or null
     * if not a number.
     * 
     * @return double value of the cell contents, or null if not a number
     */
    public Double getNumValue() { return value; }
    
    /**
     * Sets the contents to the cell to the specified string.
     * 
     * @param svalue string used to set the cell contents
     */
    public void setContents(String svalue) {
        contents = svalue.trim();
        try {
            double num = Double.parseDouble(contents);            
            value = new Double(num);
        }
        catch (NumberFormatException e) {
            value = null;
        }         
    }
    
    /**
     * Clears the cell contents by setting to the empty string.
     */
    public void clear() {
        setContents("");
    }
    
    /**
     * Sets the row number of the cell.
     * 
     * @param row row number
     */
    public void setRow(int row) {
        this.row = row;
    }
    
    /**
     * Sets the column number of the cell.
     * 
     * @param col column number
     */
    public void setColumn(int col) {
        this.column = col;
    }
    
    /** 
     * Returns true iff the given cell object is equal to this object.
     * Two cells are equal if their contents, row numbers, and column
     * numbers are equal.
     * 
     * @param cell the <code>Cell</code> object to be compared to this object
     * @return true iff objects are equal
     */
    public boolean equals(Cell cell) {
        return ((contents.equals(cell.getContents())) && (row == cell.getRow()
                && (column == cell.getColumn())));
    }
    
    /**
     * Returns true if the cell contents represent a number.
     * 
     * @return true iff the cell contains a number
     */
    public boolean isNumeric() {
        return (value != null);
    }
    
    
    /**
     * Returns true iff this cell is non-empty (the contents is not equal 
     * to an empty string).
     * 
     * @return true iff not empty
     */
    public boolean hasData() {
        return (!contents.equals(""));
    }
} 
