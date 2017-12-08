/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

/**
 * Exponential probability distribution class.  A continuous probability
 * distribution defined by the exponential function.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class ExponentialProbabilityDistribution extends ProbabilityDistribution {
    /**
     * rate parameter
     */
    private double lambda;
    
    
    /**
     * Constructor, given the rate parameter.
     * 
     * @param rate parameter
     */
    public ExponentialProbabilityDistribution(double lambda) {
        this.lambda = lambda;
    }
    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x 
     * @return probability density at x
     */
    public double density(double x) {
        if (x < 0)
            return 0;
        return lambda * Math.exp(-lambda * x);
    }
    
    /** Returns the cumulative probabililty up to x.
     * 
     * @param x
     * @return cumulative probability P(<=x)
     */
    public double cumulativeProbability(double x) {
        if (x < 0)
            return 0;
        return 1 - Math.exp(-lambda * x);
    }
    
    /**
     * Returns the inverse cumulative probability.
     * 
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public double inverseCumulativeProbability(double area) {
            return Math.log(1 - area) / (-lambda);
    }    
}
