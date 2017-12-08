/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.calculator;

import java.util.Vector;

/**
 * A mathematical function object defined by a type.  
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 * @see MathFunctions
 */
public class MathFunction {
    private int type;
    private boolean isRadian = true;
    
    public MathFunction(int type, boolean isRadian) {
        this.type = type;
        this.isRadian = isRadian;
    }
    
    /** 
     * Performs a function call on the given computation unit.
     * 
     * @param c computational unit
     * @return the result of calling the function on the argument
     */       
    public CompUnit call(CompUnit c) {
        int ctype = c.getType();
        if (ctype == Token.NUMBER)  {
            return new CompUnit(Token.NUMBER, call1(c.getNumber()));
        }
        else {
            Vector<Double> v = c.getColumn();
            for (int i = 0; i < v.size(); ++i) {
                Double value = v.elementAt(i);
                if (value != null)
                    v.setElementAt(new Double(call1(value.doubleValue())), i);
            }
            return new CompUnit(Token.COLUMN, v);
        }
    }
    
    /**
     * Performs a function call on the given double value.
     * 
     * @param argument double value
     * @return the result of calling the function on the given double value
     */
    public double call1(double argument) {
        switch (type) {
            case MathFunctions.ABS:
                return Math.abs(argument);
            case MathFunctions.ACOS:
                if (isRadian)
                    return Math.acos(argument);
                else
                    return Math.toDegrees(Math.acos(argument));
            case MathFunctions.ASIN:
                if (isRadian)
                    return Math.asin(argument);
                else
                    return Math.toDegrees(Math.asin(argument));
            case MathFunctions.ATAN:
                if (isRadian)
                    return Math.atan(argument);
                else
                    return Math.toDegrees(Math.atan(argument));
            case MathFunctions.CEIL:
                return Math.ceil(argument);
            case MathFunctions.COS:
                if (!isRadian)
                    argument = Math.toRadians(argument);
                return Math.cos(argument);
            case MathFunctions.EXP:
                return Math.exp(argument);
            case MathFunctions.FLOOR:
                return Math.floor(argument);
            case MathFunctions.LN:
                return Math.log(argument);
            case MathFunctions.LOG:
                return Math.log(argument) / Math.log(10);
            case MathFunctions.ROUND:
                return Math.round(argument);
            case MathFunctions.SIN:
                if (!isRadian)
                    argument = Math.toRadians(argument);
                return Math.sin(argument);
            case MathFunctions.SQRT:
                return Math.sqrt(argument);
            case MathFunctions.TAN:
                if (!isRadian)
                    argument = Math.toRadians(argument);
                return Math.tan(argument);
            case MathFunctions.FACTORIAL:
                int argInt = (int) argument;
                // make sure the argument is a nonnegative integer
                if (argument - argInt != 0 || argument < 0)
                    return Double.NaN;
                return MathOperations.factorial(argument);
            default:
                return Double.NaN;
        }
    }
}
