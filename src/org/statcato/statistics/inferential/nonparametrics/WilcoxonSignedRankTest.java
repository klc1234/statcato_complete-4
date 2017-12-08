/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential.nonparametrics;

import org.statcato.utils.HelperFunctions;
import org.statcato.utils.OrderingFunctions;
import org.statcato.utils.DataValue;

import java.util.Vector;
import java.util.Iterator;

/**
 * Wilcoxon Signed Rank test.  A nonparametic test for claims regarding the
 * median of a single population.
 *
 * Samples are ranked by their absolute distances from the hypothesized median.
 * The ranks of sample values that are above the hypothesized median are given
 * positive signs; those of values below the hypothesized median are given
 * negative signs.  The sums of the ranks corresponding to positive and negative
 * signs are computed, respectively.  The rank sums are used as test statistics.
 *
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class WilcoxonSignedRankTest {
    /**
     * Sample size
     */
    private int sampleSize;
    /**
     * rank sum (test statistic)
     */
    private double rankSum;
    /**
     * Positive rank sum
     */
    private double posRankSum;
    /**
     * Negative rank sum
     */
    private double negRankSum;
    /**
     * Hypothesized median
     */
    private double testMedian;
    /**
     * The type of test
     */
    private int testType;
    /**
     * Alternative hypothesis type: less than
     */
    public static int LESS = 0;
    /**
     * Alternative hypothesis type: greater than
     */
    public static int GREATER = 2;
    /**
     * Alternative hypothesis type: not equal to
     */
    public static int NOTEQUAL = 1;
    /**
     * Significance of the test.
     */
    public double significance;

    /**
     * Constructor, given data values.
     *
     * @param data vector of double values
     * @param testMedian hypothesized median
     * @param testType type of alternative hypothesis
     */
    public WilcoxonSignedRankTest(Vector<Double> data,
            double testMedian, int testType, double significance) {
        this.testMedian = testMedian;
        this.testType = testType;
        this.significance = significance;

        // find the number of non-zero values
        this.sampleSize = 0;
        Iterator it = data.iterator();
        while (it.hasNext()) {
            Double next = (Double) it.next();
            if (next != null) {
                double value = next.doubleValue() - testMedian;
                if (value != 0)
                    this.sampleSize += 1;
                }
        }
        
        // calculates rank sum
        this.rankSum = getRankSum(data);
        
    }

    /**
     * Returns the p-value.  
     *
     * @return p-value
     */
    public double pValue() {
        
        double p = WilcoxonSignedRankPValue.getPValue((int)Math.round(rankSum),
                    sampleSize);

        if (testType == LESS || testType == GREATER)
                return p;
            else
                return 2 * p;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Wilcoxon Signed Rank Test: <br>";

        s += "<table border='1'>";
        s += "<tr>";
        s += "<th>Sample size<br>(excludes values =median)</th><th>Rank Sums (+, -)</th>";
        s += "<th>Test Statistics</th><th>Critical Value</th><th>p-Value</th>";
        s += "</tr>";
        s += "<td>" + sampleSize + "</td>";
        s += "<td>" + posRankSum + ", " + negRankSum + "</td>";
        s += "<td>" + rankSum + "</td>";
        s += "<td>" +  
                HelperFunctions.formatFloat(WilcoxonSignedRankPValue.getCriticalValue(
                (testType == NOTEQUAL?significance/2:significance),
                sampleSize), 2) + "</td>";
        s += "<td>" + HelperFunctions.formatFloat(pValue(), 5) + "</td>";
        s += "</tr>";
        s += "</table>";

        return s;
    }

    /**
     * Returns the rank sum of the given data (negative rank sum for
     * right-tailed test, positive rank sum for left-tailed, and
     * minimum of the two rank sums for two-tailed).
     * 
     * @param data vector of Double values
     * @return rank sum
     */
    public double getRankSum(Vector<Double> data) {
        Vector<DataValue> dataVector = new Vector<DataValue>();

        // fill DataValue vector with the differences between the 
        // non-zero values from the input data vector and the test median
        Iterator it = data.iterator();
        while (it.hasNext()) {
            Double next = (Double)it.next();
            if (next != null) {
                double value = next.doubleValue() - testMedian;
                if (value != 0)
                    dataVector.add(new DataValue(value));
            }
        }
        
        // compute ranks
        double[] ranks = OrderingFunctions.rank(dataVector);

        // compute the sums of the positive and negative ranks
        posRankSum = 0;
        negRankSum = 0;
        for (int i = 0; i < dataVector.size(); ++i) {
            if (ranks[i] > 0)
                posRankSum += ranks[i];
            else
                negRankSum += ranks[i];
        }

        negRankSum = Math.abs(negRankSum);
        
        // return the smaller of the two sums
        if (testType == LESS)
            return posRankSum;
        else if (testType == GREATER)
            return negRankSum;
        else
            return Math.min(posRankSum, negRankSum);
    }

    
}


