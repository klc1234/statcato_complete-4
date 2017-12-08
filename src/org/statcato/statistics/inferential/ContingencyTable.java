/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import java.util.Vector;
import org.statcato.statistics.ChiSquareProbabilityDistribution;
import org.statcato.utils.HelperFunctions;

/**
 * A contingency table.  
 * Provides methods that perform computations
 * relevant to a test of independence in a contingency table.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class ContingencyTable {
    private double[][] observed;       // observed frequencies
    private double[][] expected;    // expected frequencies
    private double[][] tsCont;      // test statistic contribution
    private double rowTotal[];
    private double columnTotal[];
    private int numRows, numColumns;
    private double grandTotal;
    
    /**
     * Constructor.  A vector of vector of Double representing
     * the table of observed frequencies is provided.  
     * The row and column totals are calculated.
     * The expected frequencies are also computed.
     * 
     * @param table vector of vector of Double representing
     * the table of observed frequencies
     */
    public ContingencyTable(Vector<Vector<Double>> table) {
        numColumns = table.size();
        numRows = table.elementAt(0).size();       
        rowTotal = new double[numRows];
        columnTotal = new double[numColumns];
        observed = new double[numRows][numColumns];
        expected = new double[numRows][numColumns];
        tsCont = new double[numRows][numColumns];
        grandTotal = 0;
        
        for (int i = 0; i < numColumns; ++i) {
            for (int j = 0; j < numRows; ++j) {
                observed[j][i] = ((Double)table.elementAt(i).elementAt(j)).doubleValue(); 
                grandTotal += observed[j][i];
            }
        }
        
        // calculate column totals
        for (int i = 0; i < numColumns; ++i) {
            double sum = 0; 
            for (int j = 0; j < numRows; ++j)
                sum += observed[j][i];
            columnTotal[i] = sum;
        }
        
        // calcalate row totals
        for (int j = 0; j < numRows; ++j) {
            double sum = 0;
            for (int i = 0; i < numColumns; ++i)
                sum += observed[j][i];
            rowTotal[j] = sum;
        }
        
        // calculate expected frequencies
        for (int i = 0; i < numColumns; ++i) {
            for (int j = 0; j < numRows; ++j) {
                expected[j][i] = rowTotal[j]*columnTotal[i]/grandTotal;
                tsCont[j][i] = Math.pow(observed[j][i] - expected[j][i], 2) /
                        expected[j][i];
            }
        }
    }
    
    /**
     * Returns the test statistic for a test of independence between
     * the row and column variable in the contingency table.
     * chi^2 = Sum((O-E)^2 / E).
     * 
     * @return chi^2 test statistics
     */
    public double testStatistics() {
        double chi2 = 0;
        
        for (int i = 0; i < numColumns; ++i) {
            for (int j = 0; j < numRows; ++j) {
                chi2 += tsCont[j][i];
            }
        }
        
        return chi2;
    }
    
    /**
     * Returns the degrees of freedom of a contingency table.
     * 
     * @return degrees of freedom
     */
    public int DOF() {
        return (numRows - 1) * (numColumns - 1);
    }

    /**
     * Returns the critical value corresponding to the given confidence level
     * and degrees of freedom of the contingency table.
     * 
     * @return critical value
     */
    public double criticalValue(double confidenceLevel) {
        ChiSquareProbabilityDistribution dist = 
                new ChiSquareProbabilityDistribution(DOF());
        return dist.inverseCumulativeProbability(confidenceLevel);
    }
    
    /**
     * Returns the p-Value corresponding to the test statistic
     * in a chi-square distribution.
     * 
     * @return p-value
     */
    public double pValue() {
        ChiSquareProbabilityDistribution dist = 
                new ChiSquareProbabilityDistribution(DOF());
        double ts = testStatistics();
        return 1 - dist.cumulativeProbability(ts);
    }
    
    public String toString() {
        String str = "";
        for (int i = 0; i < numRows; ++i) {
            str += "<tr align='right'><th>" + (i+1) + "</th>";
            for (int j = 0; j < numColumns; ++j) {
                str += "<td>" + 
                        HelperFunctions.formatFloat(observed[i][j], 2) + 
                        "<br>(" + 
                        HelperFunctions.formatFloat(expected[i][j], 2) +
                        ")<br>[" +
                        HelperFunctions.formatFloat(tsCont[i][j], 2) +
                        "]</td>";
            }
            str += "<td>" + 
                    HelperFunctions.formatFloat(rowTotal[i], 2) + "</td>";
            str += "</tr>";
        }
        str += "<tr align='right'><th>Total</th>";
        for (int j = 0; j < numColumns; ++j) {
            str += "<td>" + 
                    HelperFunctions.formatFloat(columnTotal[j], 2) + "</td>";
        }
        str += "<td>" + HelperFunctions.formatFloat(grandTotal, 2) 
                + "</td></tr>";
        return str;
    }
}
