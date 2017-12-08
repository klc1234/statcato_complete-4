/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.calculator;

import java.util.Arrays;

/**
 * Defines functions used in the calculator.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class MathFunctions {
    public static final int SIN = 0;
    public static final int COS = 1;
    public static final int EXP = 2;
    public static final int ABS = 3;
    public static final int TAN = 4;
    public static final int ACOS = 5;
    public static final int ASIN = 6;
    public static final int ATAN = 7;
    public static final int CEIL = 8;
    public static final int FLOOR = 9;
    public static final int LOG = 10;
    public static final int ROUND = 11;
    public static final int SQRT = 12;
    public static final int LN = 13;
    public static final int FACTORIAL = 14;
    public static final double PI = Math.PI;
    public static final double UNDEFINED = Double.NEGATIVE_INFINITY;
    
    public static final String[] FUNNAMES = 
     {"sin", "cos", "exp", "abs", "tan", "arccos", "arcsin", 
      "arctan", "ceil", "floor", "log", "round", "sqrt", "ln", "factorial"};
    
    /**
     * Returns the function number corresponding to the given function name.
     * 
     * @param s function name
     * @return function number
     */
    public int getFunctionNumber(String s) {
        for (int i = 0; i < FUNNAMES.length; ++i) {
            if (s.equals(FUNNAMES[i]))
                return i;
        }
        return -1;
    }
    
    /**
     * Returns the array of sorted function name.
     * 
     * @return array of sorted function name
     */
    public static String[] getSortedFunNames() {
        String[] temp = FUNNAMES.clone();
        Arrays.sort(temp);
        return temp;
    }
}