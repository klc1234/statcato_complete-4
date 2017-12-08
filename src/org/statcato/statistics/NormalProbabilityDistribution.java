/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

/**
 * Normal probability distribution class.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class NormalProbabilityDistribution extends ProbabilityDistribution {
    double mu;      // mean
    double sigma;   // standard deviation
    
    /**
     * Constructor a normal probability distribution given the mean
     * and standard deviation.
     * 
     * @param mu mean
     * @param sigma standard deviation
     */
    public NormalProbabilityDistribution(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;
    }
    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x 
     * @return probability density at x
     */
    public double density(double x) {
        return Math.exp(-0.5 * Math.pow((x-mu)/sigma, 2)) / 
                (sigma * Math.sqrt(2*Math.PI));
    }
    
    /** Returns the cumulative probabililty up to x.
     * 
     * @param x
     * @return cumulative probability P(<=x)
     */
    public double cumulativeProbability(double x) {
        double z = (x-mu)/sigma;
        return (SpecialFunctions.erff(Math.sqrt(2)*z/2)+1)/2; 
    }
    
    /**
     * Returns the inverse cumulative probability.
     * 
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public double inverseCumulativeProbability(double area) {
        if (area < 0.5)
            return super.inverseCumulativeProbability(mu-100*sigma, mu, 0.0001, area);
        else if (area == 0.5)
            return mu;
        else
            return super.inverseCumulativeProbability(mu, mu+100*sigma, 0.0001, area);
    }
    
}
