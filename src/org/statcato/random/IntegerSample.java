/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.random;

import java.util.Random;

/**
 * A random sampler for a range of integers, defined by
 * the minimum and maximum of the range.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class IntegerSample extends Random {
    int minimum, maximum;
    
    public IntegerSample(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    public int nextSample() {
        int randInt = nextInt(maximum - minimum + 1);  // 0...maximum - minimum
        return randInt + minimum;
    }
}
