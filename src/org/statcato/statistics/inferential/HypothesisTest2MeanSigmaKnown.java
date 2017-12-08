/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import org.statcato.utils.HelperFunctions;

/**
 * A hypothesis test for 2 population means
 * (population standard deviations sigmas known).
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class HypothesisTest2MeanSigmaKnown {
    int n1, n2;  // sample sizes of populations 1 and 2
    double xBar1, xBar2;  // sample means of populations 1 and 2
    double sigma1, sigma2;  // population standard deviations of populations 1 and 2
    double diff;    // hypothesized difference in mean
    double confidenceLevel; // confidence level
    int testType;   // type of test 
    boolean varEqual;
    
    /**
     * Constructor
     * 
     * @param n1 sample size 1
     * @param n2 sample size 2
     * @param xBar1 sample mean 1
     * @param xBar2 sample mean 2
     * @param sigma1 population standard deviation 1
     * @param sigma2 population standard deviation 2
     * @param diff hypothesized difference in population mean
     * @param confidenceLevel confidence level
     * @param testType type of test (as defined in {@link HypothesisTest})
     */
    public HypothesisTest2MeanSigmaKnown(int n1, int n2, double xBar1, double xBar2, 
            double sigma1, double sigma2, double diff,
            double confidenceLevel, int testType) {
        this.n1 = n1;
        this.n2 = n2;
        this.xBar1 = xBar1;
        this.xBar2 = xBar2;
        this.sigma1 = sigma1;
        this.sigma2 = sigma2;
        this.diff = diff;
        this.confidenceLevel = confidenceLevel;
        this.testType = testType;
    }
    
    /**
     * Returns the test statistics.
     * 
     * @return test statistic t score
     */
    public double testStatistics() {        
        return (xBar1 - xBar2 - diff) / Math.sqrt(sigma1 * sigma1 / n1 +
                sigma2 * sigma2 / n2);
    }
    
    
    /**
     * Returns the critical value corresponding to the given confidence
     * level and type of test.  Uses the z distribution.
     * 
     * @return critical value
     * @see HypothesisTest
     */
    public String criticalValue() {
        double alpha = 1 - confidenceLevel;
        
        
        NormalProbabilityDistribution dist = 
                new NormalProbabilityDistribution(0, 1);    // z distribution
        
        if (testType == HypothesisTest.RIGHT_TAIL)
            return HelperFunctions.formatFloat(
                    dist.inverseCumulativeProbability(1 - alpha), 3);
        else if (testType == HypothesisTest.LEFT_TAIL)
            return HelperFunctions.formatFloat(
                    dist.inverseCumulativeProbability(alpha), 3);
        else    // two tail
            return 
                HelperFunctions.formatFloat(
                dist.inverseCumulativeProbability(alpha / 2), 3) + ", " +
                HelperFunctions.formatFloat(
                dist.inverseCumulativeProbability(1 - alpha / 2), 3);
    }    

    /**
     * Returns the p-value in a z distribution.
     * 
     * @return p-value
     */
    public double pValue() {
        double ts = testStatistics();
        
        NormalProbabilityDistribution dist = 
                new NormalProbabilityDistribution(0, 1);    // z distribution
        
        if (testType == HypothesisTest.LEFT_TAIL)
            return dist.cumulativeProbability(ts);
        else if (testType == HypothesisTest.RIGHT_TAIL)
            return 1 - dist.cumulativeProbability(ts);
        else {    // two tail
            double area = dist.cumulativeProbability(ts);
            if (area > 0.5)
                area = 1 - area;
            return 2 * area;
        }
    }
}
