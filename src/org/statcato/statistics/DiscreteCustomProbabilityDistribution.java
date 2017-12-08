/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

import java.util.*;


/**
 * Discrete probability distribution class with user-specified
 * probabilities for discrete values.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class DiscreteCustomProbabilityDistribution {
    TreeMap map; // map of values vs. probabilities
    TreeMap cumMap;  // map of values vs. cumulative probabilities

    /**
     * Construct a discrete probability distribution with custom values
     * and probabilities.
     * 
     * @param values a vector of values
     * @param probabilities a vector of probabilities corresponding
     * to the values
     */
    @SuppressWarnings("unchecked") 
    public DiscreteCustomProbabilityDistribution(
            Vector<Double> values, Vector<Double> probabilities) {
        // create hash table of values vs. probabilities
        map = new TreeMap();
        cumMap = new TreeMap();
        double total = 0;

        for (int i = 0; i < values.size(); ++i) {
            map.put(values.elementAt(i), probabilities.elementAt(i));
            total += probabilities.elementAt(i).doubleValue();
            cumMap.put(values.elementAt(i), new Double(total));
        }
    }
    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x an integer
     * @return probability density at x
     */
    public double density(double x) {        
        Double p = (Double) map.get(new Double(x));
        if (p == null)
            return 0;
        return p.doubleValue();
    }
    
    /** Returns the cumulative probabililty up to x.
     * 
     * @param x
     * @return cumulative probability P(<=x)
     */
    public double cumulativeProbability(double x) {
        Set keys = cumMap.keySet();
        double p = 0;
  
        for (Iterator it = keys.iterator(); it.hasNext(); ) {
            Double value = (Double)it.next();
            if (x < value)    // when current key value is less than x
                return p;   // return largest cumulative probability                            
                
            p = (Double)cumMap.get(value);
            
            if (x == value) // exact cumulative probability exists
                return p;
        }
        
        return 1.0;
    }
    
    /**
     * Returns the inverse cumulative probability.
     * 
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public Double inverseCumulativeProbability(double area) {
        if (area < 0 || area > 1)
            return null;
        
        Set keys = cumMap.keySet();
        Double p;
  
        for (Iterator it = keys.iterator(); it.hasNext(); ) {
            Double value = (Double)it.next();
            p = (Double)cumMap.get(value);
            
            if (area <= p.doubleValue())    
                return value;   // return value corresponding to the smallest
                        // probability >= area
        }
        
        return null;   // never gets here     
    }    
}
