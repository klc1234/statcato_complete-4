/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.utils;

import java.util.Vector;
import java.util.Iterator;

/**
 * An object that represents a key into a hash of multiple dimensions.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class MultidimensionalHashKey {
    private Vector keyValues;
    
    /**
     * Constructor.
     * 
     * @param values a list of key values
     */
    public MultidimensionalHashKey(Vector values) {
        keyValues = values;
    }
    
    /**
     * Returns the number of dimensions in this hash key.
     * 
     * @return number of dimensions
     */
    public int getNumDimensions() {
        return keyValues.size();
    }
    
    /**
     * Returns the key value of the given dimension.
     * 
     * @param d the dimension
     * 
     * @return
     */
    public Object getKey(int d) {
        return keyValues.get(d);
    }
    
    @Override
    public int hashCode() {
        int code = 0;
        for (Iterator it = keyValues.iterator(); it.hasNext(); ) {
            Object s = it.next();
            code += s.hashCode();
        }
        return code;
    }
    
    @Override
    public boolean equals(Object o) {
        MultidimensionalHashKey key = (MultidimensionalHashKey) o;
        if (key.getNumDimensions() != getNumDimensions())
            return false;
        for (int i = 0; i < keyValues.size(); ++i) {
            if (!getKey(i).equals(key.getKey(i)))
                return false;
        }
        return true;
    }
}
