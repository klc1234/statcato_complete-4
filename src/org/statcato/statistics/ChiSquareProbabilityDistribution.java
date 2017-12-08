/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

/**
 * Chi Square probability distribution class.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class ChiSquareProbabilityDistribution extends ProbabilityDistribution {
    double v;   // degrees of freedom
    
    /**
     * Constructor, given the degrees of freedom
     * 
     * @param v degrees of freedom
     */
    public ChiSquareProbabilityDistribution(double v) {
        this.v = v;
    }
    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x 
     * @return probability density at x
     */
    public double density(double x) {
        if (x <= 0)
            return 0;
        return Math.pow(0.5, v/2) / Math.exp(SpecialFunctions.gammln(v/2)) *
                Math.pow(x, v/2 - 1) * Math.exp(-x/2);
    }
    
    /** Returns the cumulative probabililty up to x.
     * 
     * @param x
     * @return cumulative probability P(<=x)
     */
    public double cumulativeProbability(double x) {
        return SpecialFunctions.gammp(v/2, x/2); 
    }
    
    /**
     * Returns the inverse cumulative probability.
     * 
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public double inverseCumulativeProbability(double area) {
            return super.inverseCumulativeProbability(0, 1000000, 0.0001, area);
    }    
}
