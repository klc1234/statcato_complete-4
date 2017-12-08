/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

/**
 * F probability distribution class.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class FProbabilityDistribution extends ProbabilityDistribution {
    double v1, v2;   // degrees of freedom
    
    /**
     * Constructor, given the numerator and denominator degrees of freedom.
     * 
     * @param v1 numerator degrees of freedom
     * @param v2 denominator degrees of freedom
     */
    public FProbabilityDistribution(double v1, double v2) {
        this.v1 = v1;
        this.v2 = v2;
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
        return Math.sqrt(Math.pow(v1*x, v1) * Math.pow(v2, v2) /
                Math.pow(v1*x + v2, v1 + v2)) / (x * SpecialFunctions.beta(v1/2, v2/2));
    }
    
    /** Returns the cumulative probabililty up to x.
     * 
     * @param x
     * @return cumulative probability P(<=x)
     */
    public double cumulativeProbability(double x) {
        if (x <= 0)
            return 0;
        return 1-SpecialFunctions.betai(v2/2,v1/2,v2/(v2+v1*x)); 
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
