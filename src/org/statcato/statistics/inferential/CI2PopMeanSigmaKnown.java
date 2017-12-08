/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;

/**
 * Confidence interval class for two population mean,
 * sigma (population standard deviation) known.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CI2PopMeanSigmaKnown extends ConfidenceInterval {
    private double sigma1, sigma2;   // population standard deviations
    private int n1, n2;      // sample sizes
    private double xBar1, xBar2;    // sample means
    
    /**
     * Constructor
     * 
     * @param confidenceLevel confidence level, between 0 and 1
     * @param n1 sample size 1
     * @param n2 sample size 2
     * @param xBar1 sample mean 1
     * @param xBar2 sample mean 2
     * @param sigma1 population standard deviation 1
     * @param sigma2 population standard deviation 2
     */
    public CI2PopMeanSigmaKnown(double confidenceLevel, int n1, int n2, 
            double xBar1, double xBar2, double sigma1, double sigma2) {
        this.n1 = n1;
        this.n2 = n2;
        this.sigma1 = sigma1;
        this.sigma2 = sigma2;
        this.xBar1 = xBar1;
        this.xBar2 = xBar2;
        this.center = xBar1 - xBar2;
        this.confidenceLevel = confidenceLevel;
    }
    
    /** 
     * Returns the margin of error.  
     *  E = z_(alpha/2) * sqrt(sigma1^2 / n1 + sigma2^2 / n2)
     * 
     * @return margin of error
     */
    public double marginOfError() {
        return criticalValue() * Math.sqrt(sigma1 * sigma1 / n1 
                + sigma2 * sigma2 / n2);
    }
    
    /**
     * Returns the critical value corresponding to the given confidence level.
     * Uses Student's t distribution to find t_(alpha/2), where alpha is
     * the significance level (1 - confidence level).
     * 
     * @return critical value
     */
    public double criticalValue() {
        double alpha = 1 - confidenceLevel;    
        
        NormalProbabilityDistribution dist = 
                new NormalProbabilityDistribution(0, 1);
        
        return dist.inverseCumulativeProbability(1 - alpha / 2);
    }   
    
}
