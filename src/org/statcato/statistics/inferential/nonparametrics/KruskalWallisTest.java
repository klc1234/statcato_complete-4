/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential.nonparametrics;

import org.statcato.utils.HelperFunctions;
import org.statcato.utils.OrderingFunctions;
import org.statcato.statistics.ChiSquareProbabilityDistribution;

import java.util.Vector;

/**
 * Kruskal-Wallis test.  A nonparametic test that uses ranks of
 * samples from three or more independent populations to
 * to test the null hypothesis that the independent samples come from population
 * with the same distribution.
 *
 * The independent samples are combined into one sample, are sorted in
 * ascending order and are ranked based on its place in the one sample.
 * The sum of the ranks corresponding to each of the samples are computed
 * and is used in calculating the test statistics H.  The Chi-square
 * distribution is used for approximation.
 *
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class KruskalWallisTest {
    /**
     * Sample size of the samples
     */
    private int n[];
    /**
     * Rank sum of the samples
     */
    private double R[];
    /**
     * Total number of observations in all samples combined
     */
    private int N;
    /**
     * Number of independent samples
     */
    private int k;
    /**
     * Significance of the test
     */
    public double significance;

    /**
     * Constructor, given samples of data values.
     *
     * @param data      a vector of vector of double values, with each
     * vector of double values representing an independent sample
     * @param significance  significance level (between 0 and 1)
     */
    public KruskalWallisTest(Vector<Vector<Double>> data,
            double significance) {
        this.significance = significance;

        k = data.size();
        n = new int[k];
        R = new double[k];
        
        // calculate rank sum
        computeRankSum(data);

        // calculate sample size
        N = 0;
        for (int i = 0; i < data.size(); ++i) {
            n[i] = data.elementAt(i).size();
            N += n[i];
        }
    }

    /**
     * Returns the test statistic H = 12 / [N(N+1)] Sum_i (R_i^2/n_i) - 3(N+1).
     *
     * @return test statistic H
     */
    public double testStatistic() {
        double sum = 0;
        // sum R_i^2 / n_i
        for (int i = 0; i < k; ++i) {
            sum += R[i] * R[i] / n[i];
        }
        return sum * 12.0 / N / (N + 1) - 3 * (N + 1);
    }

    /**
     * Returns the degrees of freedom.
     * 
     * @return degrees of freedom
     */
    public int DOF() {
        return k - 1;
    }

    /**
     * Returns the p-value.  
     *
     * @return p-value
     */
    public double pValue() {
        ChiSquareProbabilityDistribution dist =
                new ChiSquareProbabilityDistribution(DOF());
        double ts = testStatistic();
        return 1 - dist.cumulativeProbability(ts);
    }

    /**
     * Returns the one-tailed critical value.
     *
     * @return critical value
     */
    public double criticalValue() {
        ChiSquareProbabilityDistribution dist =
                new ChiSquareProbabilityDistribution(DOF());
        return dist.inverseCumulativeProbability(1 - significance);
    }

    @Override
    public String toString() {
        String s = "";
        boolean hasSmallSample = false;
        double totalRanks = 0;
        s += "Kruskal-Wallis Test: <br>";
        s += "<table border='1'>";
        s += "<tr><th>Sample</th>";
        for (int i = 0; i < k; ++i) {
            s += "<th>" + (i+1) + "</th>";
        }
        s += "<th>Total</th></tr>";
        s += "<tr><th>Sample Size</th>";
        for (int i = 0; i < k; ++i) {
            s += "<td>" + n[i];
            if (n[i] < 5) {
                s += "*";
                hasSmallSample = true;
            }
            s += "</td>";
        }
        s += "<td>" + N + "</td>";
        s += "</tr><tr><th>Rank Sum</th>";
        for (int i = 0; i < k; ++i) {
            s += "<td>" + R[i] + "</td>";
            totalRanks += R[i];
        }
        s += "<td>" + totalRanks + "</td></tr>";
        s += "</table><br>";

        s += "<table border='1'>";
        s += "<tr>";
        s += "<th>DOF</th><th>Test Statistics H</th><th>Critical Value</th><th>p-Value</th>";
        s += "</tr><tr>";
        s += "<td>" + DOF() + "</td>";
        s += "<td>" + HelperFunctions.formatFloat(testStatistic(), 3) + "</td>";
        s += "<td>" + HelperFunctions.formatFloat(criticalValue(), 3) + "</td>";
        s += "<td>" + HelperFunctions.formatFloat(pValue(), 5) + "</td>";
        s += "</tr>";
        s += "</table>";
        if (hasSmallSample)
            s += "* - small samples (&le; 5)<br>";

        return s;
    }

    /**
     * Computes the sum of ranks for each sample.  Assigns the appropriate
     * rank sum to the private array R[].
     * Ranks are computed after all samples are combined and sorted in
     * ascending order.
     * 
     * @param data vector of Double values
     * @return rank sum
     */
    public void computeRankSum(Vector<Vector<Double>> data) {
        Vector<Double> dataVector = new Vector<Double>();

        // fill DataValue vector with values from the samples
        for (int i = 0; i < data.size(); ++i) {
            Vector<Double> sample = data.elementAt(i);
            for (int j = 0; j < sample.size(); ++j) {
                Double value = sample.elementAt(j);
                if (value != null)
                    dataVector.add(value);
            }
        }

        // compute ranks
        double[] ranks = OrderingFunctions.rankDoubleVector(dataVector);
        
        // compute the sums of ranks for sample 1
        double rankSum;
        for (int i = 0; i < data.size(); ++i) {
            rankSum = 0;
            Vector<Double> sample = data.elementAt(i);
            n[i] = sample.size();
            for (int j = 0; j < sample.size(); ++j) {
                Double value = sample.elementAt(j);
                if (value != null) {
                    rankSum += ranks[dataVector.indexOf(value)];
                }
            }
            R[i] = rankSum;
        }
    }
}


