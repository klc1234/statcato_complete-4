/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

import java.util.Vector;
import java.util.Set;
import java.util.HashSet;

import org.statcato.utils.MultidimensionalHash;
import org.statcato.utils.MultidimensionalHashKey;
import org.statcato.spreadsheet.Cell;

/**
 * Cross Tabulation Class.  Computes the frequency of each 
 * multidimensional category.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CrossTabulation {
    /**
     * A multidimensional hash that keeps track of the frequency of 
     * each multidimensional category.
     */
    private MultidimensionalHash hash;
    /**
     * A vector of the dimensions present.
     */
    private Vector<String> dimensions;
    /**
     * Vector of vector of cell values.
     */
    private Vector<Vector<Cell>> allValues;
    /**
     * Number of dimensions.
     */
    private int numDimensions;
    
    /**
     * Constructor.
     * 
     * @param values a vector containing a set of Cell-value
     * vectors, each of which represent a dimension (the order of the dimensions
     * are the same as the given vector of dimension names 
     * @param dimensions a vector of the names of the dimensions
     */
    public CrossTabulation(Vector<Vector<Cell>> values, 
            Vector<String> dimensions) {
            numDimensions = dimensions.size();
            hash = new MultidimensionalHash(numDimensions);
            allValues = values;
            
            // populate hash
            for (int j = 0; j < values.elementAt(0).size(); ++j) {
                Vector<Cell> obs = new Vector<Cell>();
                for (int i = 0; i < values.size(); ++i) {
                    obs.addElement(values.elementAt(i).elementAt(j));
                }
                MultidimensionalHashKey key = 
                        new MultidimensionalHashKey(obs);
                hash.put(key);
            }
    }
    
    /**
     * Returns a HTML-formatted string representing a cross-tabulation to the 
     * given row dimensions and column dimensions.
     * 
     * @param rows      a vector of row dimension names
     * @param columns   a vector of column dimension names
     * @return HTML-formatted string representating the cross tabulation 
     */
    public String getTable(Vector<String> rows, Vector<String> columns) {
        String s = "";
        
        // convert dimension names into indices into the hash
        int[] rowDimensions = new int[rows.size()];
        int[] colDimensions = new int[columns.size()];
        
        for (int i = 0; i < rows.size(); ++i) 
            rowDimensions[i] = dimensions.indexOf(rows.elementAt(i));
        for (int i = 0; i < columns.size(); ++i)
            colDimensions[i] = dimensions.indexOf(columns.elementAt(i));
        
        s += "<table>";
                
        for (int i = 0; i < rows.size(); ++i) {
            for (int j = 0; j < columns.size(); ++j) {
                Set<String> cati = getCategories(rowDimensions[i]);
                Set<String> catj = getCategories(colDimensions[j]);
                
            }
        }
        s += "</table>";
        
        return s;
    }   

    @SuppressWarnings("unchecked") 
    private Set<String> getCategories(int index) {
        Set<String> categories = new HashSet();
        Vector<Cell> values = allValues.elementAt(index);
        for (int i = 0; i < values.size(); ++i) {
            categories.add(values.elementAt(i).toString());
        }
        return categories;
    }
    
    
}
