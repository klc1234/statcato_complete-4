/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

/**
 * Geometric probability distribution class.
 * <p>
 * Type I: models the number of trials needed to produce the first success<br>
 * Type II: model the number of failures before the first success<br>
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class GeometricProbabilityDistribution extends DiscreteProbabilityDistribution {
    double p;   // success probability
    int type;   // distribution type
    
    /**
     * models the number of trials needed to produce the first success
     */
    public static int TYPEI = 1;    
    
    /**
     * models the number of failures before the first success
     */
    public static int TYPEII = 2;   
    
    /**
     * Constructs a geometric probability distribution with the given 
     * success probability and type.
     * 
     * @param p     success probability
     * @param type  TYPEI (models the number of trials needed to produce the first success)
     * or TYPEII (model the number of failures before the first success)
     */
    public GeometricProbabilityDistribution(double p, int type) {
        this.p = p;
        if (type == TYPEI || type == TYPEII)
            this.type = type;
        else
            this.type = TYPEI;  // default to type 1 for unacceptable type
    }
    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x an integer
     * @return probability density at x
     */
    public double density(double x) {        
        int k = (int) x;
        
        if (k <= 0 && type == TYPEI)
            return 0;
        if (k < 0 && type == TYPEII)
            return 0;
        
        if (type == TYPEI)
            return Math.pow(1-p, k-1) * p;  // (1-p)^(k-1) * p
        else
            return Math.pow(1-p, k) * p;    // (1-p)^k * p
    }
    
    /** Returns the cumulative probabililty up to x.
     * 
     * @param x
     * @return cumulative probability P(<=x)
     */
    public double cumulativeProbability(double x) {
        if (x <= 0 && type == TYPEI)
            return 0;
        if (x < 0 && type == TYPEII)
            return 0;
        
        if (type == TYPEI)
            return 1 - Math.pow(1-p, x);
        else
            return 1 - Math.pow(1-p, x+1);
    }
    
    /**
     * Returns the inverse cumulative probability.
     * 
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public Double inverseCumulativeProbability(double area) {
        if (type == TYPEI)
            return super.inverseCumulativeProbability(1, 1000000, 0.0001, area);
        else
            return super.inverseCumulativeProbability(0, 1000000, 0.0001, area);
    }    
}
