/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import java.util.Vector;

/**
 * A cell in a table for two-way ANOVA.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class TwoANOVATableCell {
    /**
     * Number of observations 
     */
    private int n;
    /**
     * Double vector of observations
     */
    private Vector<Double> obs;
    
    public TwoANOVATableCell() {
        obs = new Vector<Double>();
        n = 0;
    }
    
    public void addObservation(double data) {
        obs.addElement(new Double(data));
        n++;
    }
    
    public double sum() {
        double sum = 0;
        for (int i = 0; i < n; ++i) 
            sum += obs.elementAt(i).doubleValue();
        return sum;
    }
    
    public double sumOfSquares() {
        double sum = 0;
        double data;
        for (int i = 0; i < n; ++i) {
            data = obs.elementAt(i).doubleValue();
            sum += data * data;
        }
        return sum;
    }
    
    public int getSize() {
        return n;
    }
}
