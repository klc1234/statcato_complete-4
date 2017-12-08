/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

/**
 * Uniform probability distribution class.  A continuous probability
 * distribution defined by a lower and upper bound.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class UniformProbabilityDistribution extends ProbabilityDistribution {
    double lower, upper;   // degrees of freedom
    double uniformDensity;
    
    /**
     * Constructor, given the lower and upper bound.
     * 
     * @param lower lower bound
     * @param upper upper bound
     */
    public UniformProbabilityDistribution(double lower, double upper) {
        this.lower = lower;
        this.upper = upper;
        this.uniformDensity = 1.0 / (upper - lower);
    }
    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x 
     * @return probability density at x
     */
    public double density(double x) {
        if (x < lower || x > upper)
            return 0;
        return uniformDensity;
    }
    
    /** Returns the cumulative probabililty up to x.
     * 
     * @param x
     * @return cumulative probability P(<=x)
     */
    public double cumulativeProbability(double x) {
        if (x <= lower)
            return 0;
        if (x >= upper)
            return 1;
        return uniformDensity * (x - lower); 
    }
    
    /**
     * Returns the inverse cumulative probability.
     * 
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public double inverseCumulativeProbability(double area) {
            return area / uniformDensity + lower;
    }    
}
