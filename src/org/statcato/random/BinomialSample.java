/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.random;

import java.util.Random;

/**
 * A random sampler for a binomial distribution defined by 
 * the number of trials and event probability.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class BinomialSample extends Random {
    double probability;
    int trials;
    
    /**
     * Constructor.  
     * 
     * @param trials number of trials
     * @param probability event probability
     */
    public BinomialSample(int trials, double probability) {
        this.trials = trials;
        this.probability = probability;
    }
    
    /**
     * Returns the next random sample 
     * 
     * @return int number of successes
     */
    public int nextSample() {
        int total = 0;
        for (int i = 0; i < trials; ++i) {
            double rand = nextDouble();
            if (rand < probability)
                total++;
        }
        return total;
    }
}
