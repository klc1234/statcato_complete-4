/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import org.statcato.utils.HelperFunctions;

/**
 * A hypothesis test for matched pairs, 
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class HypothesisTestMatchedPairs {
    int n;  // sample size
    double mu;  // hypothesized mean of differences between matched pairs
    double s;   // sample standard deviation
    double confidenceLevel; // confidence level
    int testType;   // type of test 
    
    /**
     * Constructor.
     * 
     * @param n sample size of differences between matched pairs
     * @param mu hypothesized mean of differences between matched pairs
     * @param s sample standard deviation of differences between matched pairs
     * @param confidenceLevel   confidence level
     * @param testType (as defined in {@link HypothesisTest})
     */
    public HypothesisTestMatchedPairs(int n, double mu, double s,
            double confidenceLevel, int testType) {
        this.n = n;
        this.mu = mu;
        this.s = s;
        this.confidenceLevel = confidenceLevel;
        this.testType = testType;
    }
    
    /**
     * Returns the test statistics for the given sample mean of
     * differences between matched pairs, dBar.
     * <p>
     * t = (dBar - mu) / (s / sqrt(n))
     *  
     * @param dBar sample mean
     * @return test statistic t
     */
    public double testStatistics(double dBar) {
        return (dBar - mu) / (s / Math.sqrt(n));
    }
    
    /**
     * Returns the critical value corresponding to the given confidence
     * level and type of test.  Uses the t distribution.
     * 
     * @return critical value
     * @see HypothesisTest
     */
    public String criticalValue() {
        double alpha = 1 - confidenceLevel;
        
        StudentTProbabilityDistribution dist = 
                new StudentTProbabilityDistribution(n-1);    // t distribution
        
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
     * Returns the p-value corresponding to dBar in a t distribution.
     * 
     * @param dBar
     * @return p-value
     */
    public double pValue(double dBar) {
        double ts = testStatistics(dBar);
        
        StudentTProbabilityDistribution dist = 
                new StudentTProbabilityDistribution(n-1);    // t distribution
        
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
