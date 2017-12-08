/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

/**
 * Integer probability distribution class.
 * Discrete uniform probability distribution, where each integer in a specific
 * interval has the same probability of occurring.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class IntegerProbabilityDistribution {
    int min, max;
    double p;
    
    /**
     * Constructs an Integer probability distribution with the given
     * minimum and maximum values.
     * 
     * @param min minimum
     * @param max maximum
     */
    public IntegerProbabilityDistribution(int min, int max) {
        this.min = min;
        this.max = max;
        this.p = 1 / (double)(max - min + 1);
    }
    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x an integer
     * @return probability density at x
     */
    public double density(double x) {        
        if (x >= min && x <= max)
            return p;
        else return 0;
    }
    
    /** Returns the cumulative probabililty up to x.
     * 
     * @param x an integer
     * @return cumulative probability P(<=x)
     */
    public double cumulativeProbability(double x) {
        if (x < min)
            return 0;
        else if (x >= max)
            return 1;
        else 
            return (Math.floor(x) - min + 1) * p;    
    }
    
    /**
     * Returns the inverse cumulative probability.
     * 
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public Integer inverseCumulativeProbability(double area) {
        if (area < 0 || area > 1)
            return null;
        if (area == 1)
            return new Integer(max);
        if (area == 0)
            return new Integer(min);
        
        int i = (int)Math.round(area / p);
        if (i < 1)
            return new Integer(min);

        return new Integer(i + min - 1);
    }    
}
