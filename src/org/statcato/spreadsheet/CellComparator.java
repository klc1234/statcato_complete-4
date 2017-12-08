package org.statcato.spreadsheet;

import java.util.Comparator;

/**
 * A comparator for <code>Cell</code> objects (ascending order).
 * If the <code>Cell</code> objects contains numbers, compare them as numbers.
 * If the <code>Cell</code> objects do not contain numbers, compare them 
 * as strings (empty strings are largest).
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @see Cell
 * @since 1.0
 */
public class CellComparator implements Comparator {
    public int compare(Object Cell1, Object Cell2) {
        Cell C1 = (Cell) Cell1;
        Cell C2 = (Cell) Cell2;
        
        
        // numeric type
        if (C1.isNumeric() && C2.isNumeric()) {
            double value1 = C1.getNumValue().doubleValue();
            double value2 = C2.getNumValue().doubleValue();
            
            if (value1 < value2)
                return -1;
            if (value1 > value2)
                return 1;
            // value1 == value2
            int row1 = C1.getRow();
            int row2 = C2.getRow();
            if (row1 == row2)
                return 0;
            if (row1 < row2)
                return -1;
            if (row1 > row2)
                return 1;
        }

        // make empty string larger than any string
        if (C1.getContents().equals("") && !C2.getContents().equals(""))
            return 1;   // C1 is empty string
        if (C2.getContents().equals("") && !C1.getContents().equals(""))
            return -1;  // C2 is empty string

        // otherwise, compare as strings
        int comparison = C1.getContents().compareTo(C2.getContents());
        if (comparison == 0) {
            int row1 = C1.getRow();
            int row2 = C2.getRow();
            if (row1 == row2)
                return 0;
            if (row1 < row2)
                return -1;
            if (row1 > row2)
                return 1;            
        }
        return comparison;
    }
}
