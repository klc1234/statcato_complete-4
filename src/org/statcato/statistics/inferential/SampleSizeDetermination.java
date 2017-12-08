/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;

/**
 * Functions for determining the sample size estimating a population statistic.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class SampleSizeDetermination {
    
    /**
     * Returns the sample size for estimating a population proportion
     * given the significance level, proportion estimate, and
     * margin of error.  
     * 
     * n = z^2 * p * q / E^2  if proportion estimate p is known
     * (z is the critical value based on the significance level,
     * E is the margin of error, and q is 1 - p.)
     * 
     * n = z^2 * 0.25 / E^2 if proportion estimate is unknown
     * 
     * @param alpha significance level
     * @param estimate proportiion estimate (-1 if unknown)
     * @param error margin of error
     * @return sample size 
     */
    public static int proportion(double alpha, double estimate, double error) {
        NormalProbabilityDistribution dist = 
                new NormalProbabilityDistribution(0, 1);    // z distribution
        
        double z = dist.inverseCumulativeProbability(1 - alpha / 2);
        
        if (estimate == -1) { // proportion estimate is unknown
            return (int)Math.ceil(z * z * 0.25 / (error * error));
        }
        else {
            return (int)Math.ceil(z * z * estimate * (1 - estimate) / (error * error));
        }
    }
    
    /**
     * Returns the sample size for estimating a population mean
     * given the significance level, standard deviation, and margin of error.
     * 
     * n = [z * sigma / E]^2
     * where z is the critical value given the significance level,
     * sigma is the population standard deviation, and E is the margin of error.
     * 
     * @param alpha significance level
     * @param stdev population standard deviation
     * @param error margin of error
     * @return sample size
     */
    public static int mean(double alpha, double stdev, double error) {
        NormalProbabilityDistribution dist = 
                new NormalProbabilityDistribution(0, 1);    // z distribution
        
        double z = dist.inverseCumulativeProbability(1 - alpha / 2);
        
        return (int)Math.ceil(Math.pow(z * stdev / error, 2));                
    }
}
