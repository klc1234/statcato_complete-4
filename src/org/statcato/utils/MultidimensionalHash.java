/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.utils;

import java.util.HashMap;

/**
 * A multidimensional hash that contains the frequencies of 
 * multidimensional keys.
 *   
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class MultidimensionalHash {
    HashMap hash;
    int dimensions;
    
    /**
     * Constructor.
     * 
     * @param dimensions the number of dimensions
     */
    public MultidimensionalHash(int dimensions) {
        hash = new HashMap();
        this.dimensions = dimensions;
    }
    
    /**
     * Increases the frequency of the given key value by one.
     * 
     * @param key   a multidimensional hashkey
     */
    @SuppressWarnings("unchecked") 
    public void put(MultidimensionalHashKey key) {
        if (hash.containsKey(key)) {
            int f = ((Integer)hash.get(key)).intValue();
            f += 1;
            hash.put(key, new Integer(f));
        }
        else {
            hash.put(key, new Integer(1));
        }
    }
    
    /**
     * Returns the frequency of the given key value.
     * 
     * @param key a multidimensional hashkey
     * @return frequency of the given key
     */
    public int get(MultidimensionalHashKey key) {
        Object o = hash.get(key);
        if (o == null)
            return 0;
        return ((Integer)hash.get(key)).intValue();
    }
    
    /**
     * Returns the number of dimensions in the hash.
     * 
     * @return number of dimensions
     */
    public int getDimensions() {
        return dimensions;
    }
            
}
