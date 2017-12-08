/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.utils.HelperFunctions;

/**
 * Confidence interval class.  This abstract class is the base
 * class of all confidence interval objects.  All classes 
 * extending the ConfidenceInterval class must implement the
 * marginOfError and criticalValue methods.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public abstract class ConfidenceInterval {
    protected double center;  // center of the confidence interval
    protected double confidenceLevel; // confidence level, between 0 and 1
    
    /** 
     * Returns the margin of error.
     * 
     * @return margin of error
     */
    public abstract double marginOfError();
    
    /**
     * Returns the critical value corresponding to the given confidence level.
     * 
     * @return critical value
     */
    public abstract double criticalValue();
    
    /**
     * Returns the lower limit of the confidence interval.
     * 
     * @return lower limit
     */
    public double lowerLimit() {
        return center - marginOfError();
    }
    
    /**
     * Returns the upper limit of the confidence interval.
     * 
     * @return upper limit
     */
    public double upperLimit() {
        return center + marginOfError();
    }

    /**
     * Returns the center of the interval.
     * @return center
     */
    public double center() {
        return center;
    }
    
    /**
     * Returns a string representing the confidence interval
     * (lower limit, upper limit).
     * 
     * @return string
     */
    @Override
    public String toString() {
        return "(" + HelperFunctions.formatFloat(lowerLimit(), 4) + ", " + 
                HelperFunctions.formatFloat(upperLimit(), 4) + ")";
    }
}
