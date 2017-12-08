/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.utils;

import java.util.Vector;
import java.util.Collections;

/**
 * Keeps track of a previously unspecified number of categories and
 * the frequency of each category.  Maps each category to a unique number.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CategoryCounter {
    Vector<String> categories;
    Vector<Integer> frequencies;
    
    /**
     * Constructor.
     */
    public CategoryCounter() {
        categories = new Vector<String>();
        frequencies = new Vector<Integer>();
    }
    
    /**
     * Adds the given category to the counter. 
     *  
     * @param cat category string
     */
    public void addCategory(String cat) {
        if (!cat.equals("")) {
            int index = getIndex(cat);
            if (index == -1) {
                categories.addElement(cat);
                frequencies.addElement(new Integer(0));
            }
            else {
                frequencies.setElementAt( 
                        new Integer(frequencies.elementAt(index).intValue() + 1),
                        index);
            }
        }
    }

    /**
     * Returns the category at the given index.
     *
     * @param i index
     * @return category at the given index
     */
    public String getCategory(int i) {
        if (i >= 0 && i < categories.size())
            return categories.elementAt(i);
        return null;
    }

    @SuppressWarnings("unchecked") 
    public Vector<String> getCategories() {
        Vector<String> cat = (Vector<String>)categories.clone();
        Collections.sort(cat);
        return cat;
    }

    /**
     * Returns the index corresponding to the given category, or 
     * returns -1 if the category is not found.
     * 
     * @param cat category
     * @return index of the category, or -1 if it does not exist
     */
    public int getIndex(String cat) {
        for (int i = 0; i < categories.size(); ++i) {
            String x = categories.elementAt(i);
            if (x.equals(cat))
                return i;
        }
        
        return -1;
    }
    
    /**
     * Returns the number of categories found by this counter.
     * 
     * @return number of categories
     */
    public int getSize() {
        return categories.size();
    }
    
    /**
     * Checks if all the frequencies are the same.  If so, return it;
     * otherwise, return -1.
     * 
     * @return the frequency that all the categories have, or -1 if
     * not all frequencies are equal 
     */
    public int getEqualFrequency() {
        if (frequencies.size() == 0)
            return 0;
        int f = frequencies.elementAt(0).intValue();
        for (int i = 1; i < frequencies.size(); ++i) {
            if (frequencies.elementAt(i).intValue() != f)
                return -1;
        }
        return f;            
    }
    
    /**
     * Returns the frequency of the given category.
     * 
     * @param cat category
     * @return frequency of the given category
     */
    public int getFrequency(String cat) {
        int index = getIndex(cat);
        if (index == -1)
            return -1;
        return frequencies.elementAt(index).intValue();
    }
    
    @Override
    public String toString() {
        String text = "Categories: " + HelperFunctions.printVectorToString(categories);
        text = "\nIndices: " + HelperFunctions.printVectorToString(frequencies);
        return text + "\n";
    }
}
