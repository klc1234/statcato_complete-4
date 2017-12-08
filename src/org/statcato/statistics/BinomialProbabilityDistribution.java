/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

/**
 * Binomial probability distribution class.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class BinomialProbabilityDistribution extends DiscreteProbabilityDistribution {
    int n;
    double p, q;   
    
    /**
     * Constructor, given the number of trials and event probability.
     * 
     * @param n number of trials
     * @param p event probability
     */
    public BinomialProbabilityDistribution(int n, double p) {
        this.n = n;
        this.p = p;
        this.q = 1-p;
    }
    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x 
     * @return probability density at x
     */
    @Override
    public double density(double x) {
        if (x < 0 || x > n)
            return 0;
        int k = (int) x;
        return (double) SpecialFunctions.binomialCoefficient(n, k) *
                Math.pow(p, k) * Math.pow(q, n - k);
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
        if (x >= n)
            return 1;
        return 1 - SpecialFunctions.betai(x + 1, n - x, p);
    }
    
    /**
     * Returns the inverse cumulative probability.
     * 
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public Double inverseCumulativeProbability(double area) {
        return super.inverseCumulativeProbability(0, n, 0.0001, area);
    }    
}
