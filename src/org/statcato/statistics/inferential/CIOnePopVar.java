/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import org.statcato.utils.HelperFunctions;

/**
 * Confidence interval class for one population variance.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CIOnePopVar {
    private double variance;   // sample variance
    private int n;      // sample size
    private double confidenceLevel;     // sample confidence level
    
    /**
     * Constructor.
     * 
     * @param confidenceLevel confidence level, between 0 and 1
     * @param n sample size
     * @param variance sample variance
     */
    public CIOnePopVar(double confidenceLevel, int n, double variance) {
        this.n = n;
        this.variance = variance;
        this.confidenceLevel = confidenceLevel;
    }
    

    /**
     * Returns the lower limit of the confidence interval.
     * (n-1)*s^2 / XR^2, where XR is the inverse cumulative probability
     * of the chi-square distribution at 1 - alpha/2 (alpha is the signifiance
     * level).
     * 
     * @return lower limit
     */
    public double lowerLimit() {
        double XR2;
        double alpha = 1 - confidenceLevel;

        ChiSquareProbabilityDistribution dist = new
                ChiSquareProbabilityDistribution(n - 1);
        XR2 = dist.inverseCumulativeProbability(1 - alpha / 2);
        return (n - 1) * variance / XR2;
    }
    
    /**
     * Returns the upper limit of the confidence interval.
     * (n-1)*s^2 / XL^2, where XL is the inverse cumulative probability
     * of the chi-square distribution at alpha/2 (alpha is the significance
     * level).
     * 
     * @return upper limit
     */
    public double upperLimit() {
        double XL2;
        double alpha = 1 - confidenceLevel;

        ChiSquareProbabilityDistribution dist = new
                ChiSquareProbabilityDistribution(n - 1);
        XL2 = dist.inverseCumulativeProbability(alpha / 2);
        return (n - 1) * variance / XL2;
    }
    
    /**
     * Returns a string representing the confidence interval of the variance
     * (lower limit, upper limit).
     * 
     * @return string
     */
    public String toString() {
        return "(" + HelperFunctions.formatFloat(lowerLimit(), 4) + ", " + 
                HelperFunctions.formatFloat(upperLimit(), 4) + ")";
    }    
    
    /**
     * Returns a string representing the confidence interval of the
     * standard deviation (lower limit, upper limit);
     * 
     * @return string
     */
    public String stdevCI() {
        return "(" + HelperFunctions.formatFloat(Math.sqrt(lowerLimit()), 4) + 
                ", " + 
                HelperFunctions.formatFloat(Math.sqrt(upperLimit()), 4) + ")";
    }
    
}
