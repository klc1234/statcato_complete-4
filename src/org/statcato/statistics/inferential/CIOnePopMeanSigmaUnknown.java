/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;

/**
 * Confidence interval class for one population mean,
 * sigma (population standard deviation) unknown.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CIOnePopMeanSigmaUnknown extends ConfidenceInterval{
    private double s;   // sample standard deviation
    private int n;      // sample size
    
    /**
     * Constructor.
     * 
     * @param confidenceLevel confidence level, between 0 and 1
     * @param n sample size
     * @param mean sample mean
     * @param s sample standard deviation
     */
    public CIOnePopMeanSigmaUnknown(double confidenceLevel, int n, 
            double mean, double s) {
        this.n = n;
        this.s = s;
        this.center = mean;
        this.confidenceLevel = confidenceLevel;
    }
    
    /** 
     * Returns the margin of error.  E = t_(alpha/2) * s / sqrt(n).
     * 
     * @return margin of error
     */
    @Override
    public double marginOfError() {
        return criticalValue() * s / Math.sqrt(n);
    }
    
    /**
     * Returns the critical value corresponding to the given confidence level.
     * Uses Student's t distribution to find t_(alpha/2), where alpha is
     * the significance level (1 - confidence level).
     * 
     * @return critical value
     */
    @Override
    public double criticalValue() {
        double alpha = 1 - confidenceLevel;
        
        StudentTProbabilityDistribution dist = 
                new StudentTProbabilityDistribution(n - 1);
        
        return dist.inverseCumulativeProbability(1 - alpha / 2);
    }

    /**
     * Returns the sample standard deviation.
     *
     * @return sample standard deviation
     */
    public double getStdev() {
        return s;
    }

    /**
     * Returns the sample size.
     * @return sample size
     */
    public double getN() {
        return n;
    }
}
