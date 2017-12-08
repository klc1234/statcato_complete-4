/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import org.statcato.utils.HelperFunctions;

/**
 * A hypothesis test for 1 population proportion.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class HypothesisTest1Prop {
    int n;  // sample size
    double p;  // hypothesized proportion
    double confidenceLevel; // confidence level
    int testType;   // type of test 
    
    /**
     * Constructor.
     * 
     * @param n sample size
     * @param p hypothesized proportion
     * @param confidenceLevel   confidence level
     * @param testType (as defined in {@link HypothesisTest})
     */
    public HypothesisTest1Prop(int n, double p, 
            double confidenceLevel, int testType) {
        this.n = n;
        this.p = p;
        this.confidenceLevel = confidenceLevel;
        this.testType = testType;
    }
    
    /**
     * Returns the test statistics for the given sample proportion pHat.
     * <p>
     * z = (pHat - p) / sqrt(p * (1-p) / n)
     *  
     * @param pHat hypothesized population proportion
     * @return test statistic z score
     */
    public double testStatistics(double pHat) {
        return (pHat - p) / Math.sqrt(p * (1 - p) / n);
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
     * Returns the p-value corresponding to pHat in a z distribution.
     * 
     * @param pHat sample proportion
     * @return p-value
     */
    public double pValue(double pHat) {
        double ts = testStatistics(pHat);
        
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
