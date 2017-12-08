/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;

/**
 * Confidence interval class for one population proportion.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CIOnePopProp extends ConfidenceInterval {
    private double p;   // sample proportion
    private int n;      // sample size
    private int distType; // distribution type
    public static int NORMAL = 0;   // normal distribution
    public static int BINOMIAL = 1; // binomial distribution
    
    /**
     * Constructor
     * 
     * @param confidenceLevel confidence level, between 0 and 1
     * @param n sample size
     * @param p sample proportion
     * @param type type of distribution for calculating critical value
     */
    public CIOnePopProp(double confidenceLevel, int n, 
            double p, int type) {
        this.n = n;
        this.p = p;
        this.center = p;
        this.confidenceLevel = confidenceLevel;
        this.distType = type;
    }
    
    /** 
     * Returns the margin of error.  E = z_(alpha/2) * sqrt(p*q/n).
     * 
     * @return margin of error
     */
    public double marginOfError() {
        return criticalValue() * Math.sqrt(p*(1-p)/n);
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

        if (distType == NORMAL) {
            NormalProbabilityDistribution dist = new NormalProbabilityDistribution(0, 1);    // z distribution
            return dist.inverseCumulativeProbability(1 - alpha / 2);
        }
        else {
            BinomialProbabilityDistribution dist = new BinomialProbabilityDistribution(n, p);   
            return dist.inverseCumulativeProbability(1 - alpha / 2);
        }
    }   
}
