/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;

/**
 * Confidence interval class for matched pairs.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CIMatchedPairs extends ConfidenceInterval{
    private double s;   // sample standard deviation of the differences
    private int n;      // sample size
    
    /**
     * Constructor.
     * 
     * @param confidenceLevel confidence level, between 0 and 1
     * @param n sample size (number of matched pairs)
     * @param mu sample mean of differences
     * @param s sample standard deviation of differences
     */
    public CIMatchedPairs(double confidenceLevel, int n, 
            double mu, double s) {
        this.n = n;
        this.s = s;
        this.center = mu;
        this.confidenceLevel = confidenceLevel;
    }
    
    /** 
     * Returns the margin of error.  E = t_(alpha/2) * s / sqrt(n).
     * 
     * @return margin of error
     */
    public double marginOfError() {
        return criticalValue() * s / Math.sqrt(n);
    }
    
    /**
     * Returns the critical value corresponding to the given confidence level.
     * Uses the t distribution to find t_(alpha/2), where alpha is
     * the significance level (1 - confidence level).
     * 
     * @return critical value
     */
    public double criticalValue() {
        double alpha = 1 - confidenceLevel;
        
        StudentTProbabilityDistribution dist = 
                new StudentTProbabilityDistribution(n - 1);    // z distribution
        
        return dist.inverseCumulativeProbability(1 - alpha / 2);
    }   
}
