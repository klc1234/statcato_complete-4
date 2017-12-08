/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

/**
 * Poisson probability distribution class.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class PoissonProbabilityDistribution extends DiscreteProbabilityDistribution {
    double lambda;   
    
    /**
     * Constructs a poisson probability distribution with the given mean.
     * 
     * @param lambda mean
     */
    public PoissonProbabilityDistribution(double lambda) {
        this.lambda = lambda;
    }
    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x 
     * @return probability density at x
     */
    @Override
    public double density(double x) {
        if (x < 0)
            return 0;
        double ee = x * Math.log(lambda) - lambda - SpecialFunctions.gammln(x+1);
        return Math.exp(ee);
    }
    
    /** Returns the cumulative probabililty up to x.
     * 
     * @param x
     * @return cumulative probability P(<=x)
     */
    @Override
    public double cumulativeProbability(double x) {
        if (x < 0)
            return 0;

        return SpecialFunctions.gammq(x + 1, lambda);
    }
    
    /**
     * Returns the inverse cumulative probability.
     * 
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public Double inverseCumulativeProbability(double area) {
        return super.inverseCumulativeProbability(0, 20*lambda, 0.0001, area);
    }    
}
