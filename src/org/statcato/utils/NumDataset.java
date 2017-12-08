/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.utils;

import java.util.Vector;

/**
 * A class that represents a dataset of double values.  
 * The dataset consists of vectors of double values corresponding to distinct
 * category labels.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class NumDataset {
    private Vector<String> labels;
    private Vector<Vector<Double>> values;
    
    /**
     * Constructor.
     */
    public NumDataset() {
        labels = new Vector<String>();
        values = new Vector<Vector<Double>>();
    }
    
    /**
     * Adds the given data vector with the given label to the dataset.
     * 
     * @param data vector of double values
     * @param label String data label
     */
    public void add(Vector<Double> data, String label) {
        labels.addElement(label);
        values.addElement(data);
    }
    
    /**
     * Add the data values with the corresponding labels.
     * 
     * @param data vector of double values
     * @param byLabels vector of labels corresponding to the double values
     */
    public void addDataVectorWithByVarLabels(Vector<Double> data, Vector byLabels) {
        String label;
        Double value;
        Vector<Double> thisDataVector;
        
        for (int i = 0; i < byLabels.size(); ++i) {
            label = byLabels.elementAt(i).toString();
            value = (Double) data.elementAt(i);
            thisDataVector = get(label);
            // label not already in dataset
            if (thisDataVector == null) {
                labels.addElement(label);
                thisDataVector = new Vector<Double>();
                thisDataVector.addElement(value);
                values.addElement(thisDataVector);
            }
            // label is already in dataset
            else {
                thisDataVector.addElement(value);
            }
        }
    }
    
    /**
     * Returns the data vector corresponding to the given label.
     * 
     * @param label String data label
     * @return vector of double values corresponding to the given label
     */
    public Vector<Double> get(String label) {
        int index = labels.indexOf(label);  // location of label within vector
        if (index == -1)
            return null;
        return (Vector<Double>) values.elementAt(index);
    }
    
    /**
     * Returns the size of the dataset (number of categories).
     * 
     * @return size of the dataset (integer)
     */
    public int size() {
        return labels.size();
    }
    
    /**
     * Returns all the data values as vectors of double-value vector.
     *  
     * @return vectors of double-value vector
     */
    public Vector<Vector<Double>> getValues() {
        return values;
    }
    
    /**
     * Returns all the labels as a vector of string values.
     * 
     * @return vector of string values
     */
    public Vector<String> getLabels() {
        return labels;
    }
    
    /**
     * Returns the labels of the sets of data values.
     * 
     * @return vector of string values
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < labels.size(); ++i) {
            s += "Label = " + labels.elementAt(i) + "\n";
            Vector<Double> data = values.elementAt(i);
            s += "Values = " + HelperFunctions.printDoubleVectorToString(data) + "\n";
        }
        return s;
    }
}
