/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import java.util.Vector;

/**
 * Cross tabulation and Chi-square test.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @see org.statcato.statistics.inferential.ContingencyTable
 * @since 1.0
 */
public class CrossTabulation {
    private int a;  // number of rows
    private int b;  // number of columns
    private double y[][];    // table of tabulations
    private ContingencyTable contingencyTable;
    
    /**
     * Constructor, given the number of rows and columns.
     * 
     * @param a number of rows
     * @param b number of columns
     */
    public CrossTabulation(int a, int b) {
        this.a = a;
        this.b = b;
        y = new double[a][b];
        for (int i = 0; i < a; ++i) 
            for (int j = 0; j < b; ++j)
                y[i][j] = 0;
        
    }
    
    /**
     * Adds the given frequency to the location at row i and column j.
     * 
     * @param i     row index
     * @param j     column index
     * @param data  double array
     */
    public void addObservation(int i, int j, double frequency) {
        y[i][j] += frequency;
    }

    /**
     * Returns the frequency at row i and column j.
     *
     * @param i     row index
     * @param j     column index
     * @return  frequency
     */
    public double getFrequency(int i, int j) {
        return y[i][j];
    }

    /**
     * Returns the percent at row i and column j.
     * 
     * @param i
     * @param j
     * @return percent
     */
    public double getPercent(int i, int j) {
        return getFrequency(i, j) / getTotalSum();
    }

    /**
     * Return the sum of the given  row.
     *
     * @param i row index
     * @return row sum
     */
    public double getRowSum(int i) {
        double sum = 0;
        for (int j = 0; j < b; ++j)
            sum += y[i][j];
        return sum;
    }

    /**
     * Return the percent of the sum of the given row out of the total sum.
     *
     * @param i row index
     * @return row sum percent
     */
    public double getRowSumPercent(int i) {
        return getRowSum(i) / getTotalSum();
    }

    /**
     * Return the sum of the given column.
     *
     * @param j column index
     * @return column sum
     */
    public double getColumnSum(int j) {
        double sum = 0;
        for (int i = 0; i < a; ++i)
            sum += y[i][j];
        return sum;
    }

    /**
     * Return the percent of the sum of the given column out of the total sum.
     *
     * @param i column index
     * @return column sum percent
     */
    public double getColumnSumPercent(int i) {
        return getColumnSum(i) / getTotalSum();
    }

    /**
     * Return the total sum.
     *
     * @return total sum
     */
    public double getTotalSum() {
        double sum = 0;
        for (int i = 0; i < a; ++i)
            for (int j = 0; j < b; ++j)
                sum += y[i][j];
        return sum;
    }

    /**
     * Creates a contingency table object using the cross tabulation data.
     */
    public void computeChiSquare() {
        // form vector of column vectors
        Vector<Vector<Double>> table = new Vector<Vector<Double>>();
        for (int j = 0; j < b; ++j) {
            Vector<Double> vec = new Vector<Double>();
            for (int i = 0; i < a; ++i) {
                vec.addElement(new Double(y[i][j]));
            }
            table.addElement(vec);
        }
        contingencyTable = new ContingencyTable(table);
    }

    /**
     * Returns the contingency table.
     *
     * @return ContingencyTable object
     */
    public ContingencyTable getContingencyTable() {
        return contingencyTable;
    }
}
