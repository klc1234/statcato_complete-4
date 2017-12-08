/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import org.statcato.utils.HelperFunctions;

/**
 * A hypothesis test for 1 population mean, 
 * population standard deviation known.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class HypothesisTest1MeanSigmaKnown {
    int n;  // sample size
    double mu;  // hypothesized mean
    double sigma;   // population standard deviation
    double confidenceLevel; // confidence level
    int testType;   // type of test 
    
    /**
     * Constructor.
     * 
     * @param n sample size
     * @param mu hypothesized mean
     * @param sigma population standard deviation
     * @param confidenceLevel   confidence level
     * @param testType (as defined in {@link HypothesisTest})
     */
    public HypothesisTest1MeanSigmaKnown(int n, double mu, double sigma,
            double confidenceLevel, int testType) {
        this.n = n;
        this.mu = mu;
        this.sigma = sigma;
        this.confidenceLevel = confidenceLevel;
        this.testType = testType;
    }
    
    /**
     * Returns the test statistics for the given sample mean xBar.
     * <p>
     * z = (xBar - mu) / (sigma / sqrt(n))
     *  
     * @param xBar sample mean
     * @return test statistic z score
     */
    public double testStatistics(double xBar) {
        return (xBar - mu) / (sigma / Math.sqrt(n));
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
     * Returns the p-value corresponding to xBar in a z distribution.
     * 
     * @param xBar x bar value
     * @return p-value corresponding to xBar 
     */
    public double pValue(double xBar) {
        double ts = testStatistics(xBar);
        
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
