/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.random;

import java.util.Random;

/**
 * A random sampler for a normal distribution, defined by
 * the mean and standard deviation.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class NormalSample extends Random {
    double mean, stdev;
    
    /**
     * Constructor.  
     * 
     * @param mean  mean of the normal distribution
     * @param stdev standard deviation of the normal distribution
     */
    public NormalSample(double mean, double stdev) {
        this.mean = mean;
        this.stdev = stdev;
    }
    
    /**
     * Returns the next random sample 
     * 
     * @return double
     */
    public double nextSample() {
        double z = nextGaussian();  // mean 0, stdev 1

        return z * stdev + mean;
    }
}
