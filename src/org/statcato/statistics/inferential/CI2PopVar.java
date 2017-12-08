/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import org.statcato.utils.HelperFunctions;

/**
 * Confidence interval class for two population mean,
 * sigma (population standard deviation) known.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CI2PopVar {
    private double var1, var2;   // sample variances
    private int n1, n2;      // sample sizes
    private double confidenceLevel; // confidence level    
    
    /**
     * Constructor.
     * 
     * @param confidenceLevel confidence level, between 0 and 1
     * @param n1 sample size 1
     * @param n2 sample size 2
     * @param var1 sample variance 1
     * @param var2 sample variance 2
     */
    public CI2PopVar(double confidenceLevel, int n1, int n2, 
            double var1, double var2) {
        this.n1 = n1;
        this.n2 = n2;
        this.var1 = var1;
        this.var2 = var2;
        this.confidenceLevel = confidenceLevel;
    }
    
    /**
     * Returns the right-tail critical value (F_R).
     * 
     * @return F_R
     */
    public double criticalValueRight() {
        double alpha = 1 - confidenceLevel;
        FProbabilityDistribution dist = new FProbabilityDistribution(n1 - 1, n2 - 1);
        return dist.inverseCumulativeProbability(1 - alpha / 2);
    }

    /**
     * Returns the left-tail critical value (F_L).
     * 
     * @return F_L
     */
    public double criticalValueLeft() {
        double alpha = 1 - confidenceLevel;
        FProbabilityDistribution dist = new FProbabilityDistribution(n1 - 1, n2 - 1);
        return dist.inverseCumulativeProbability(alpha / 2);
    }    
    
    /** 
     * Returns the lower limit of the confidence interval.
     * 
     * @return lower limit
     */
    public double lowerLimit() {
        return var1 / var2 / criticalValueRight();
    }
    
    /** 
     * Returns the upper limit of the confidence interval.
     * 
     * @return upper limit
     */
    public double upperLimit() {
        return var1 / var2 / criticalValueLeft();
    }
    
    /**
     * Returns a string representation of the confidence interval for
     * variance.
     * 
     * @return string
     */
    public String CIVar() {
        return "(" + HelperFunctions.formatFloat(lowerLimit(), 4) + ", " + 
                HelperFunctions.formatFloat(upperLimit(), 4) + ")";
    }
    
    /**
     * Returns a string representation of the confidence interval for
     * standard deviation.
     * 
     * @return string
     */
    public String CIStdev() {
        return "(" + HelperFunctions.formatFloat(Math.sqrt(lowerLimit()), 4) + ", " + 
                HelperFunctions.formatFloat(Math.sqrt(upperLimit()), 4) + ")";
    }
}
