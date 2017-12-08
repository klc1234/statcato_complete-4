/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;

/**
 * Confidence interval class for two population mean,
 * sigma (population standard deviation) unknown.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CI2PopMeanSigmaUnknown extends ConfidenceInterval {
    private double s1, s2;   // sample standard deviations
    private int n1, n2;      // sample sizes
    private double xBar1, xBar2;    // sample means
    private boolean varEqual;
    
    /**
     * Constructor
     * 
     * @param confidenceLevel confidence level, between 0 and 1
     * @param n1 sample size 1
     * @param n2 sample size 2
     * @param xBar1 sample mean 1
     * @param xBar2 sample mean 2
     * @param s1 sample standard deviation 1
     * @param s2 sample standard deviation 2
     * @param varEqual whether the population variances are assumed to be equal
     */
    public CI2PopMeanSigmaUnknown(double confidenceLevel, int n1, int n2, 
            double xBar1, double xBar2, double s1, double s2, boolean varEqual) {
        this.n1 = n1;
        this.n2 = n2;
        this.s1 = s1;
        this.s2 = s2;
        this.xBar1 = xBar1;
        this.xBar2 = xBar2;
        this.center = xBar1 - xBar2;
        this.confidenceLevel = confidenceLevel;
        this.varEqual = varEqual;
    }
    
    /** 
     * Returns the margin of error.  
     * If population variances are assumed equal,
     *  E = t_(alpha/2) * sqrt(sp^2 / n1 + sp^2 / n2), where
     *  sp^2 = [(n1 - 1)*s1^2 + (n2 - 1)*s2^2] / [n1 + n2 - 2]
     * If population variances are not assumed equal,
     *  E = t_(alpha/2) * sqrt(s1^2 / n1 + s2^2 / n2)
     * 
     * @return margin of error
     */
    public double marginOfError() {
        if (varEqual) { // population variances are assumed equal
            double sp2 = ((n1 - 1.0) * s1 * s1 + (n2 - 1.0) * s2 * s2) / 
                    (n1 + n2 - 2.0);
            return criticalValue() * Math.sqrt(sp2 * (1.0 / n1 + 1.0 / n2));
        }
        else {  // population variances are not assumed equal
            return criticalValue() * Math.sqrt(s1 * s1 / n1 + s2 * s2 / n2);
        }
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
        
        StudentTProbabilityDistribution dist = 
                new StudentTProbabilityDistribution(DOF());
        
        return dist.inverseCumulativeProbability(1 - alpha / 2);
    }   
    
    /**
     * Returns the degrees of freedom.
     * 
     * @return degrees of freedom
     */
    public int DOF() {
        if (varEqual) {
            return n1 + n2 - 2;
        }
        double A = s1 * s1 / n1;
        double B = s2 * s2 / n2;
        return (int)Math.floor(Math.pow(A + B, 2) / (A * A / (n1 - 1) + B * B / (n2 - 1)));
    }
}
