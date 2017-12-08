/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.calculator;

import java.util.Vector;
import org.statcato.statistics.SpecialFunctions;

/**
 * Mathematical operations used in the calculator application.  
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class MathOperations {
    /**
     * Adds the numbers in the two given vectors in a pairwise fashion.
     * The sum of a pair containing a missing number is simply the nonmissing
     * number.
     * 
     * @param v1 vector 1
     * @param v2 vector 2
     * @return vector containing pairwise sums
     */
    @SuppressWarnings("unchecked") 
    public static Vector<Double> add(Vector<Double> v1, Vector<Double> v2) {
        // ensure the number of elements in v1 <= the number elements in v2
        if (v1.size() > v2.size()) {
            Vector<Double> temp = (Vector<Double>)v1.clone();
            v1 = (Vector<Double>)v2.clone();
            v2 = temp;
        }
        Vector<Double> v = new Vector<Double>();
        int i,j;
        for (i = 0; i < v1.size(); ++i) {
            double n1 = 0, n2 = 0;
            if (v1.elementAt(i) == null && v2.elementAt(i) == null)
                v.addElement(null);
            else {
                if (v1.elementAt(i) != null)
                    n1 = v1.elementAt(i).doubleValue();
                if (v2.elementAt(i) != null)
                    n2 = v2.elementAt(i).doubleValue();
                v.addElement(new Double(n1 + n2));
            }
        }
        for (j = i; j < v2.size(); ++j) {
            v.addElement(v2.elementAt(j));
        }
        return v;
    }
    
    /**
     * Adds two computational units.  If the two units are numbers, simply
     * adds them.  If the two units are columns, adds each pair of numbers in the 
     * corresponding positions in the columns.  If one unit is number and 
     * the other is a column, add the number to each of the numbers in the 
     * column.
     * 
     * @param c1 CompUnit 1
     * @param c2 CompUnit 2
     * @return a CompUnit containing the sum of the two inputs
     */
    public static CompUnit add(CompUnit c1, CompUnit c2) {
        int type1 = c1.getType(), type2 = c2.getType();
        if (type1 == Token.NUMBER && type2 == Token.NUMBER) 
            // add two numbers
            return new CompUnit(Token.NUMBER, c1.getNumber() + c2.getNumber());
        else if (type1 == Token.COLUMN && type2 == Token.COLUMN) {
            // add two columns
            return new CompUnit(Token.COLUMN, 
                    add(c1.getColumn(), c2.getColumn()));
        }
        else {
            // add a column and a number
            Vector<Double> v1 = null, v2 = null;
            if (type1 == Token.NUMBER) {
                v2 = c2.getColumn();
                v1 = new Vector<Double>();
                double value = c1.getNumber();
                for (int i = 0; i < v2.size(); ++i)
                    v1.add(new Double(value));
            }
            else  {
                v1 = c1.getColumn();
                v2 = new Vector<Double>();
                double value = c2.getNumber();
                for (int i = 0; i < v1.size(); ++i)
                    v2.add(new Double(value));
            }
            
            return new CompUnit(Token.COLUMN, add(v1, v2));
        }
    }
    
    /**
     * Returns the negation of a computation unit.  If the unit is a number,
     * simply negates it.  If the unit is a column, negates each number in 
     * the column.
     * 
     * @param c computation unit
     * @return a CompUnit containing the negation of the argument
     */
    public static CompUnit negate(CompUnit c) {
        int type = c.getType();
        if (type == Token.NUMBER) {
            return new CompUnit(Token.NUMBER, -1 * c.getNumber());
        }
        else if (type == Token.COLUMN) {
            Vector<Double> v1 = c.getColumn();
            Vector<Double> v2 = new Vector<Double>();
            for (int i = 0; i < v1.size(); ++i) {
                if (v1.elementAt(i) == null)
                    v2.addElement(null);
                else {
                    double n = -1 * v1.elementAt(i).doubleValue();
                    v2.addElement(new Double(n));
                }
            }
            return new CompUnit(Token.COLUMN, v2);
        }
        return null;
    }
    
    /**
     * Multiplies the numbers in the two given vectors in a pairwise fashion.
     * The product of a pair containing a missing number is null.
     * 
     * @param v1 vector 1
     * @param v2 vector 2
     * @return vector containing pairwise products
     */
    @SuppressWarnings("unchecked") 
    public static Vector<Double> multiply(Vector<Double> v1, Vector<Double> v2) {
        // ensure the number of elements in v1 <= the number elements in v2
        if (v1.size() > v2.size()) {
            Vector<Double> temp = (Vector<Double>)v1.clone();
            v1 = (Vector<Double>)v2.clone();
            v2 = temp;
        }
        Vector<Double> v = new Vector<Double>();
        int i,j;
        for (i = 0; i < v1.size(); ++i) {
            double n1 = 0, n2 = 0;
            if (v1.elementAt(i) == null || v2.elementAt(i) == null)
                v.addElement(null);
            else {
                n1 = v1.elementAt(i).doubleValue();
                n2 = v2.elementAt(i).doubleValue();
                v.addElement(new Double(n1 * n2));
            }
        }

        return v;
    }
    
    /**
     * Returns the difference between two computational units.
     * If the two units are numbers, simply
     * subtracts the two.  If the two units are columns, finds the 
     * difference of each pair of numbers in the 
     * corresponding positions in the columns.  If the first unit is 
     * a number and the second is a column, adds the number to the negation
     * of each number in the column.  If the first is a column and the second 
     * is a number, adds the negation of the number to each number in the column.
     * 
     * @param c1 CompUnit 1
     * @param c2 CompUnit 2
     * @return a CompUnit containing the difference of the two inputs
     */
    public static CompUnit subtract(CompUnit c1, CompUnit c2) {
        CompUnit nc2 = negate(c2);
        return add(c1, nc2);
    }
    
    /**
     * Multiplies two computational units.  If the two units are numbers, simply
     * multiplies them.  If the two units are columns, multiplies each pair of numbers in the 
     * corresponding positions in the columns.  If one unit is number and 
     * the other is a column, multiplies the number to each of the numbers in the 
     * column.
     * 
     * @param c1 CompUnit 1
     * @param c2 CompUnit 2
     * @return a CompUnit containing the product of the two inputs
     */
    public static CompUnit multiply(CompUnit c1, CompUnit c2) {
        int type1 = c1.getType(), type2 = c2.getType();
        if (type1 == Token.NUMBER && type2 == Token.NUMBER) 
            // add two numbers
            return new CompUnit(Token.NUMBER, c1.getNumber() * c2.getNumber());
        else if (type1 == Token.COLUMN && type2 == Token.COLUMN) {
            // add two columns
            return new CompUnit(Token.COLUMN, 
                    multiply(c1.getColumn(), c2.getColumn()));
        }
        else {
            // multiply a column and a number
            Vector<Double> v1 = null, v2 = null;
            if (type1 == Token.NUMBER) {
                v2 = c2.getColumn();
                v1 = new Vector<Double>();
                double value = c1.getNumber();
                for (int i = 0; i < v2.size(); ++i)
                    v1.add(new Double(value));
            }
            else  {
                v1 = c1.getColumn();
                v2 = new Vector<Double>();
                double value = c2.getNumber();
                for (int i = 0; i < v1.size(); ++i)
                    v2.add(new Double(value));
            }
            
            return new CompUnit(Token.COLUMN, multiply(v1, v2));
        }
    }
    
    /**
     * Returns the reciprocal of a computational unit.  
     * 
     * @param c computational unit
     * @return reciprocal of the given computational unit.
     */
    public static CompUnit reciprocal(CompUnit c) {
        int type = c.getType();
        if (type == Token.NUMBER)
            return new CompUnit(Token.NUMBER, 1/c.getNumber());
        Vector<Double> v = c.getColumn();
        for (int i = 0; i < v.size(); ++i) {
            Double value = v.elementAt(i);
            if (value == null)
                v.setElementAt(null, i);
            else
                v.setElementAt(new Double(1 / value.doubleValue()), i);
        }
        return new CompUnit(Token.COLUMN, v);
    }
    
    /**
     * Divides two computational units.  If the two units are numbers, simply
     * divides them.  If the two units are columns, divides each pair of numbers in the 
     * corresponding positions in the columns.  If the first is a number
     * and the second is a column, multiplies the number with the reciprocal
     * of each number in the column.  If the first is a column and the second
     * is a number, mutiplies the reciprocal of the number with each number
     * in the column.
     * 
     * @param c1 CompUnit 1
     * @param c2 CompUnit 2
     * @return a CompUnit containing the product of the two inputs
     */
    public static CompUnit divide(CompUnit c1, CompUnit c2) {
        return multiply(c1, reciprocal(c2));
    }
    
    /**
     * Returns the value of the first computation unit raised to the 
     * second computational unit.  If the two units are columns, 
     * performs the computation pairwise.  If one of the arguments 
     * is a number, uses the number to fill a column and performs the computation
     * with the other argument pairwise.
     * 
     * @param c1 CompUnit 1
     * @param c2 CompUnit 2
     * @return a CompUnit containing the value of the first unit raised to
     * the power of the second unit
     */
    public static CompUnit power(CompUnit c1, CompUnit c2) {
        int type1 = c1.getType(), type2 = c2.getType();
        if (type1 == Token.NUMBER && type2 == Token.NUMBER) 
            // add two numbers
            return new CompUnit(Token.NUMBER, Math.pow(c1.getNumber(), c2.getNumber()));
        else if (type1 == Token.COLUMN && type2 == Token.COLUMN) {
            // add two columns
            return new CompUnit(Token.COLUMN, 
                    power(c1.getColumn(), c2.getColumn()));
        }
        else {
            // multiply a column and a number
            Vector<Double> v1 = null, v2 = null;
            if (type1 == Token.NUMBER) {
                v2 = c2.getColumn();
                v1 = new Vector<Double>();
                double value = c1.getNumber();
                for (int i = 0; i < v2.size(); ++i)
                    v1.add(new Double(value));
            }
            else  {
                v1 = c1.getColumn();
                v2 = new Vector<Double>();
                double value = c2.getNumber();
                for (int i = 0; i < v1.size(); ++i)
                    v2.add(new Double(value));
            }
            
            return new CompUnit(Token.COLUMN, power(v1, v2));
        }
    }
    
    /**
     * For each pair of numbers in the corresponding positions in the two
     * vectors, raises the first number to the power specified by the 
     * second number.
     * The result is null if the pair contains a missing number.
     * 
     * @param v1 vector 1
     * @param v2 vector 2
     * @return vector containing pairwise products
     */
    public static Vector<Double> power(Vector<Double> v1, Vector<Double> v2) {
        Vector<Double> v = new Vector<Double>();
        int i,j;
        for (i = 0; i < v1.size(); ++i) {
            if (i < v2.size()) {
                double n1 = 0, n2 = 0;
                if (v1.elementAt(i) == null || v2.elementAt(i) == null)
                    v.addElement(null);
                else {
                    n1 = v1.elementAt(i).doubleValue();
                    n2 = v2.elementAt(i).doubleValue();
                    v.addElement(new Double(Math.pow(n1, n2)));
                }
            }
            else
                break;
        }

        return v;
    }
    
    public static CompUnit functionCall(CompUnit c, MathFunction f) {
        return f.call(c);
        
    }
    
    /**
     * Returns the factorial of x.  Assumes that x is a nonnegative integer.
     * 
     * @param x nonnegative integer
     * @return factorial of x
     */
    public static double factorial(double x) {
        return SpecialFunctions.factrl((int)x);
    }
}
