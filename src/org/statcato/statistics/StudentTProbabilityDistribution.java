/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

/**
 * Student's T probability distribution class.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class StudentTProbabilityDistribution extends ProbabilityDistribution {
    double v;   // degrees of freedom
    
    /**
     * Constructor given the degrees of freedom.
     * 
     * @param v degrees of freedom
     */
    public StudentTProbabilityDistribution(double v) {
        this.v = v;
    }
    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x 
     * @return probability density at x
     */
    public double density(double x) {
        return Math.pow(1 + x*x/v, -(v+1)/2) / 
                SpecialFunctions.beta(0.5, 0.5*v) / Math.sqrt(v);
    }
    
    /** Returns the cumulative probabililty up to x.
     * 
     * @param x
     * @return cumulative probability P(<=x)
     */
    public double cumulativeProbability(double x) {
        if (x < 0) // negative
            return SpecialFunctions.betai(v/2,0.5,v/(v+x*x))/2;
        else
            return 1-SpecialFunctions.betai(v/2,0.5,v/(v+x*x))/2;
    }
    
    /**
     * Returns the inverse cumulative probability.
     * 
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public double inverseCumulativeProbability(double area) {
        if (area == 0.5)
            return 0.0;
        else if (area < 0.5)
            return super.inverseCumulativeProbability(-1000000, 0, 0.0001, area);
        else
            return super.inverseCumulativeProbability(0, 1000000, 0.0001, area);
    }
}
