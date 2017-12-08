/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.calculator;

import java.util.Vector;
import org.statcato.utils.HelperFunctions;

/**
 * A computational unit.  Contains the type of the computational object
 * and the value (a double value or a vector of doubles).
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 * @see Token
 */
public class CompUnit {
    int type;
    Vector<Double> column;
    double number;
    
    /**
     * Constructor, given the type of the unit and the value.
     * 
     * @param type type of unit (Token.COLUMN or Token.NUMBER)
     * @param value Double for a number or Vector&lt;Double&gt; for a column
     * @see Token
     */
    @SuppressWarnings("unchecked") 
    public CompUnit(int type, Object value) {
        this.type = type;
        if (type == Token.COLUMN) 
            column = (Vector<Double>) value;
        else
            number = ((Double)value).doubleValue();
    }
    
    /**
     * Returns the number value represented by this unit.
     * 
     * @return number
     */
    public double getNumber() {
        return number;
    }
    
    /**
     * Returns the column represented by this unit
     * @return column (vector of doubles)
     */
    public Vector<Double> getColumn() {
        return column;
    }
    
    /**
     * Returns the type of this unit
     * @return type
     */
    public int getType() {
        return type;
    }
    
    @Override
    public String toString() {
        if (type == Token.NUMBER)
            return "" + HelperFunctions.formatFloat(number, 8);
        else
            return "[ " + HelperFunctions.printDoubleVectorToString(column) + " ]";
    }
}
