/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential.nonparametrics;

import org.statcato.statistics.BasicStatistics;
import org.statcato.statistics.BinomialProbabilityDistribution;
import org.statcato.utils.HelperFunctions;
import java.util.Vector;
import org.statcato.statistics.NormalProbabilityDistribution;

/**
 * Sign test.  Determines the likelihood of observing a specified number of
 * observations above and below the hypothesized median.
 *
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class SignTest {
    /**
     * Sample size
     */
    private int sampleSize;
    /**
     * Number of positive signs
     */
    private int numPositive;
    /**
     * Number of negative signs
     */
    private int numNegative;
    /**
     * Hypothesized median
     */
    private double testMedian;
    /**
     * Sample median
     */
    private double sampleMedian;
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
     * Sample size threshold for for normal approximation to binomial
     */
    public static int THRESHOLD = 50;

    /**
     * Constructor, given summary data.
     *
     * @param numPositive number of positive signs
     * @param numNegative number of negative signs
     * @param testMedian hypothesized median
     * @param testType type of alternative hypothesis
     */
    public SignTest(int numPositive, int numNegative, 
            double testMedian, int testType) {
        this.numPositive = numPositive;
        this.numNegative = numNegative;
        this.testMedian = testMedian;
        this.testType = testType;
        this.sampleSize = numPositive + numNegative;
        this.sampleMedian = Double.NaN;
    }

    /**
     * Constructor, given data values.
     *
     * @param data vector of double data values
     * @param testMedian hypothesized median
     * @param testType type of alternative hypothesis
     */
    public SignTest(Vector<Double> data, double testMedian, int testType) {
        this.testMedian = testMedian;
        this.testType = testType;
        numPositive = BasicStatistics.numberOfPositiveSigns(data, testMedian);
        numNegative = BasicStatistics.numberOfNegativeSigns(data, testMedian);
        this.sampleSize = numPositive + numNegative;
        sampleMedian = BasicStatistics.median(data);
    }

    /**
     * Returns the p-value.  Uses the exact binomial probabilities if if
     * the sample size (excluding sample equal to the hypothesized median)
     * is less than or equal to the sample size threshold; otherwise, uses
     * normal approximation to binomial.
     *
     * @see #THRESHOLD
     * @return p-value
     */
    public double pValue() {
        if (sampleSize <= THRESHOLD) {
            // exact method using binomial probabilities
            BinomialProbabilityDistribution dist =
                    new BinomialProbabilityDistribution(numPositive + numNegative,
                            0.5);    // binomial distribution

            if (testType == LESS)
                return dist.cumulativeProbability(numPositive);
            else if (testType == GREATER)
                return dist.cumulativeProbability(numNegative);
            else {    // two tail
                int[] nums = {numPositive, numNegative};
                double area = dist.cumulativeProbability(BasicStatistics.min(nums));
                if (area > 0.5)
                    area = 1 - area;
                return 2 * area;
            }
        }
        else {
            // normal approximation
            int n = numPositive + numNegative;
            NormalProbabilityDistribution dist =
                    new NormalProbabilityDistribution(0, 1);
            double s;
            if (testType == LESS)
                s = numPositive;
            else if (testType == GREATER)
                s = numNegative;
            else {    // two tail
                int[] nums = {numPositive, numNegative};
                s = BasicStatistics.min(nums);
            }
            
            s = (s + 0.5 - n / 2) / (Math.sqrt(n) / 2);
            double p = dist.cumulativeProbability(s);

            if (testType == LESS || testType == GREATER)
                return p;
            else
                return 2 * p;
        }
    }

    @Override
    public String toString() {
        String s = "";
        s += "Sign Test: <br>";
        /*
        s += "H<sub>0</sub>: median = " + testMedian + "<br>";
        s += "H<sub>1</sub>: median ";
        if (testType == LESS)
            s += " &lt; ";
        else if (testType == GREATER)
            s += " &gt; ";
        else
            s += " &ne; ";
        s += testMedian + "<br>";
         */
        if (numPositive + numNegative > 50)
            s += "Using normal approximation<br>";
        s += "<table border='1'>";
        s += "<tr>";
        s += "<th>Sample size</th><th># Negatives</th><th># Positives</th>";
        s += "<th>Sample Median</th><th>p-Value</th>";
        s += "</tr>";
        s += "<td>" + sampleSize + "</td>";
        s += "<td>" + numNegative + "</td>";
        s += "<td>" + numPositive + "</td>";
        s += "<td>";
        if (Double.isNaN(sampleMedian))
            s += "NA";
        else 
            s += HelperFunctions.formatFloat(sampleMedian, 5);
        s += "</td>";
        s += "<td>" + HelperFunctions.formatFloat(pValue(), 5) + "</td>";
        s += "</tr>";
        s += "</table>";

        return s;
    }
}
