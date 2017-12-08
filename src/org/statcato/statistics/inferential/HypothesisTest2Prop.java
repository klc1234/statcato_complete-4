/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import org.statcato.utils.HelperFunctions;

/**
 * A hypothesis test for 2 population proportions.
 * 
 * @author Margaret Yau
 */
public class HypothesisTest2Prop {
    double n1, n2;  // number of trials in populations 1 and 2
    double x1, x2;  // number of events in populations 1 and 2
    double diff;    // hypothesized difference
    double confidenceLevel; // confidence level
    int testType;   // type of test 
    boolean pool;   // whether to use pooled estimate
    
    /**
     * Constructor
     * 
     * @param n1 number of trials in population 1
     * @param n2 number of trials in population 2
     * @param x1 number of events in population 1
     * @param x2 number of events in population 2
     * @param diff hypothesized difference
     * @param confidenceLevel confidence level
     * @param testType test type (as defined in {@link HypothesisTest})
     */
    public HypothesisTest2Prop(int n1, int n2, int x1, int x2, double diff,
            double confidenceLevel, int testType, boolean pool) {
        this.n1 = n1;
        this.n2 = n2;
        this.x1 = x1;
        this.x2 = x2;
        this.diff = diff;
        this.confidenceLevel = confidenceLevel;
        this.testType = testType;
        this.pool = pool;
    }
    
    /**
     * Returns the test statistics.
     * 
     * @return test statistic z score
     */
    public double testStatistics() {
        
        if (pool) {
            double pBar = (x1 + x2) / (n1 + n2);
            double qBar = 1 - pBar;
            System.out.println("pBar = " + pBar + " qBar = " + qBar);
            return (x1 / n1 - x2 / n2 - diff) 
                    / Math.sqrt(pBar * qBar / n1 + pBar * qBar / n2);
        }
        else {
            double p1 = x1 / n1;
            double p2 = x2 / n2;
            return (p1 - p2 - diff) 
                    / Math.sqrt(p1 * (1 - p1) / n1 + p2 * (1 - p2) / n2);
        }
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
     * Returns the p-value.
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
