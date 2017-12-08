package org.statcato.spreadsheet;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;

/**
 * A table cell renderer for <code>Spreadsheet</code>.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
class SpreadsheetCellRenderer extends DefaultTableCellRenderer
{
    /** 
     * Sets this object to the given object.  If the given object 
     * represents a number, set the horizontal alignment to right.  
     * 
     * @param aValue object to which this object is set
     */
    public void setValue(Object aValue) {
        Object result = aValue;
        if ((aValue != null) && (aValue instanceof Cell)) {
            Cell cell = (Cell) aValue;
            if (cell.isNumeric()) {
                setHorizontalAlignment(SwingConstants.RIGHT);               
            }
        }
        else if ((aValue != null) && (aValue instanceof Number)) {
            setHorizontalAlignment(SwingConstants.RIGHT);
            //result = String.format("%8.3f", aValue);
        }
        super.setValue(result);
    }
}
