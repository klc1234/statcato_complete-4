/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.random;

import java.util.Random;

/**
 * A random sampler for a range of uniformly distributed
 * continuous double values.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class UniformSample extends Random {
    double minimum, maximum;
    
    /**
     * Constructor.  The range of uniformly distributed values is
     * [minimum, maximum).
     * 
     * @param minimum inclusive
     * @param maximum exclusive
     */
    public UniformSample(double minimum, double maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    /**
     * Returns the next random sample within [minimum, maximum)
     * 
     * @return double
     */
    public double nextSample() {
        double rand = nextDouble();  // [0, 1)
        return rand * (maximum - minimum) + minimum;    // [minimum, maximum)
    }
}
