/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;

/**
 * Confidence interval class for two population proportions.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CI2PopProp extends ConfidenceInterval {
    private double p1, p2;   // sample proportions
    private int n1, n2;      // sample sizes
    
    /**
     * Constructor
     * 
     * @param confidenceLevel confidence level, between 0 and 1
     * @param n1 sample size 1
     * @param n2 sample size 2
     * @param p1 sample proportion 1
     * @param p2 sample proportion 2
     */
    public CI2PopProp(double confidenceLevel, int n1, int n2, 
            double p1, double p2) {
        this.n1 = n1;
        this.n2 = n2;
        this.p1 = p1;
        this.p2 = p2;
        this.center = p1 - p2;
        this.confidenceLevel = confidenceLevel;
    }
    
    /** 
     * Returns the margin of error.  
     * E = z_(alpha/2) * sqrt(p1*q1/n1 + p2*q2/n2)
     * 
     * @return margin of error
     */
    public double marginOfError() {
        return criticalValue() * Math.sqrt(p1 * (1 - p1) / n1 
                + p2 * (1 - p2) / n2);
    }
    
    /**
     * Returns the critical value corresponding to the given confidence level.
     * Uses the z distribution to find z_(alpha/2), where alpha is
     * the significance level (1 - confidence level).
     * 
     * @return critical value
     */
    public double criticalValue() {
        double alpha = 1 - confidenceLevel;
        
        NormalProbabilityDistribution dist = new NormalProbabilityDistribution(0, 1);    // z distribution
            return dist.inverseCumulativeProbability(1 - alpha / 2);

    }   
}
