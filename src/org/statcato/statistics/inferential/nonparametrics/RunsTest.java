/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential.nonparametrics;

import org.statcato.utils.HelperFunctions;
import org.statcato.statistics.NormalProbabilityDistribution;
import org.statcato.spreadsheet.Cell;

import java.util.Vector;
import java.util.Iterator;

/**
 * Runs test for randomness.  A nonparametic test that the number of runs
 * (sequence of data having the same characteristic) in a sequence of sample
 * data to test for randomness in the order of the data.
 *
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class RunsTest {
    /**
     * Number of elements of category 1
     */
    private int n1;
    /**
     * Number of elements of cateogry 2
     */
    private int n2;
    /**
     * Number of runs
     */
    private int G;
    /**
     * Significance of the test.
     */
    private double significance;

    /**
     * Constructor, given summarized data.
     *
     * @param n1 number of category 1
     * @param n2 number of category 2
     * @param G number of runs
     * @param significance significance level
     */
    public RunsTest(int n1, int n2, int G, double significance) {
        this.significance = significance;
        this.n1 = n1;
        this.n2 = n2;
        this.G = G;
        this.significance = significance;
    }

    /**
     * Constructor, given a vector of Cell containing data of two categories,
     * the two categories, and significance level.
     *
     * @param vector vector of Cell
     * @param cat1 string representing category 1
     * @param cat2 string representing category 2
     * @param significance significance level
     */
    public RunsTest(Vector<Cell> vector, String cat1, String cat2, double significance) {
        String last = "";
        G = 0;
        n1 = 0;
        n2 = 0;
        this.significance = significance;
        Iterator it = vector.iterator();
        while (it.hasNext()) {
            Cell c = (Cell) it.next();
            String data = c.toString();
            if (data.equals(cat1)) {
                n1++;
            }
            else  {
                n2++;
            }
            if (!data.equals(last)) {
                G++;
                last = c.toString();
            }
        }
    }

    /**
     * Constructor, given a vector containing numerical data,
     * a separator value, and the significance.
     * Test for randomness in which the numerical data
     * fluctuates above or below the separator.  Values equal to the separator
     * are ignored.
     *
     * @param vector vector of double values
     * @param separator double value separating the two groups
     * @param significance significance level
     */
    public RunsTest(Vector<Double> vector, double separator, double significance) {
        G = 0;
        n1 = 0;
        n2 = 0;
        this.significance = significance;
        
        Vector<String> catVector = new Vector<String>();
        Iterator it = vector.iterator();
        while (it.hasNext()) {
            double value = ((Double)it.next()).doubleValue();
            if (value > separator)
                catVector.addElement("1");
            else
                catVector.addElement("2");
        }
        String last = "";
        it = catVector.iterator();
        while (it.hasNext()) {
            String data = (String)it.next();
            if (data.equals("1")) {
                n1++;
            }
            else  {
                n2++;
            }
            if (!data.equals(last)) {
                G++;
                last = data;
            }
        }
    }

    /**
     * Returns the test statistic z = (G - mu) / sigma, where G is the
     * number of runs, mu the mean of the distribution of runs,
     * and sigma the standard deviatioin of the distribution of runs.
     *
     * @return test statistic z
     */
    public double testStatistic() {
        return (G - mu()) / sigma();
    }

    /**
     * Returns the mean of the distribution of runs,
     * mu = 2 * n1 * n2 / (n1 + n2) + 1.
     *
     * @return mean
     */
    public double mu() {
        return 2.0 * n1 * n2 / (n1 + n2) + 1;
    }

    /**
     * Returns the standard deviation of the distribution of runs,
     * sigma = [(2 * n1 * n2)(2 * n1 * n2 - n1 - n2) / (n1 + n2)^2 / (n1 + n2 - 1)]^0.5
     *
     * @return standard deviation
     */
    public double sigma() {
        return Math.sqrt(2.0 * n1 * n2 * (2 * n1 * n2 - n1 - n2)
                / Math.pow((n1 + n2), 2) / (n1 + n2 - 1));
    }

    /**
     * Returns the p-value.  
     *
     * @return p-value
     */
    public double pValue() {
        
        NormalProbabilityDistribution dist =
                    new NormalProbabilityDistribution(0, 1);
        
        double p = dist.cumulativeProbability(testStatistic());

        return 2 * (p <= 0.5? p:1-p);
    }

    public double criticalValue() {
        NormalProbabilityDistribution dist =
                new NormalProbabilityDistribution(0, 1);    // z distribution

        return Math.abs(dist.inverseCumulativeProbability(significance / 2));
    }

    @Override
    public String toString() {
        String s = "";
        s += "Runs Test for Randomness: <br>";
        s += "H<sub>0</sub>: The sequence is random.<br>";
        s += "H<sub>1</sub>: The sequence is not random.";
        s += "<table border='1'>";
        s += "<tr>";
        s += "<th>n<sub>1</sub></th><th>n<sub>2</sub></th>" +
                "<th>Runs</th>";
        s += "<th>&mu;</th><th>&sigma;</th>";
        s += "<th>Test Statistics z</th><th>Critical Value</th><th>p-Value</th>";
        s += "</tr>";
        s += "<td>" + n1 + "</td>";
        s += "<td>" + n2 + "</td>";
        s += "<td>" + G + "</td>";
        s += "<td>" + HelperFunctions.formatFloat(mu(), 3) + "</td>";
        s += "<td>" + HelperFunctions.formatFloat(sigma(), 3) + "</td>";
        s += "<td>" + HelperFunctions.formatFloat(testStatistic(), 3) + "</td>";
        s += "<td>&plusmn;" + HelperFunctions.formatFloat(criticalValue(), 3) + "</td>";
        s += "<td>" + HelperFunctions.formatFloat(pValue(), 5) + "</td>";
        s += "</tr>";
        s += "</table><br>";
        if (n1 <= 10 || n2 <= 10)
            s += "* Warning: Normal approximation might not be valid for small samples.";
        return s;
    }

}


