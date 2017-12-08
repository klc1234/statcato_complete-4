/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;

/**
 * Confidence interval class for one population mean,
 * sigma (population standard deviation) known.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CIOnePopMeanSigmaKnown extends ConfidenceInterval{
    private double sigma;   // population standard deviation
    private int n;      // sample size
    
    /**
     * Constructor.
     * 
     * @param confidenceLevel confidence level, between 0 and 1
     * @param n sample size
     * @param mean sample mean
     * @param sigma sample standard deviation
     */
    public CIOnePopMeanSigmaKnown(double confidenceLevel, int n, 
            double mean, double sigma) {
        this.n = n;
        this.sigma = sigma;
        this.center = mean;
        this.confidenceLevel = confidenceLevel;
    }
    
    /** 
     * Returns the margin of error.  E = z_(alpha/2) * sigma / sqrt(n).
     * 
     * @return margin of error
     */
    @Override
    public double marginOfError() {
        return criticalValue() * sigma / Math.sqrt(n);
    }
    
    /**
     * Returns the critical value corresponding to the given confidence level.
     * Uses the z distribution to find z_(alpha/2), where alpha is
     * the significance level (1 - confidence level).
     * 
     * @return critical value
     */
    @Override
    public double criticalValue() {
        double alpha = 1 - confidenceLevel;
        
        NormalProbabilityDistribution dist = 
                new NormalProbabilityDistribution(0, 1);    // z distribution
        
        return dist.inverseCumulativeProbability(1 - alpha / 2);
    }

    /**
     * Returns the standard deviation.
     *
     * @return standard deviation
     */
    public double getStdev() {
        return sigma;
    }

    /**
     * Returns the sample size
     *
     * @return sample size
     */
    public int getN() {
        return n;
    }
}
