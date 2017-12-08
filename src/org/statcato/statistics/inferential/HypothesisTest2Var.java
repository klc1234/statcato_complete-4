/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import org.statcato.utils.HelperFunctions;

/**
 * A hypothesis test for 2 population variances.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class HypothesisTest2Var {
    int n1, n2;  // sample sizes of populations 1 and 2
    double var1, var2;  // sample variances of populations 1 and 2
    double confidenceLevel; // confidence level
    int testType;   // type of test 
    
    /**
     * Constructor
     * 
     * @param n1 sample size 1
     * @param n2 sample size 2
     * @param var1 sample variance 1
     * @param var2 sample variance 2
     * @param confidenceLevel confidence level
     * @param testType type of test (as defined in {@link HypothesisTest})
     */
    public HypothesisTest2Var(int n1, int n2, double var1, double var2,
            double confidenceLevel, int testType) {
        this.n1 = n1;
        this.n2 = n2;
        this.var1 = var1;
        this.var2 = var2;
        this.confidenceLevel = confidenceLevel;
        this.testType = testType;
    }
    
    /**
     * Returns the test statistics F = var1 / var2.
     * 
     * @return test statistic F
     */
    public double testStatistics() {        
        return var1 / var2; 
    }
    
    /** 
     * Returns the degree of freedom 1.
     * 
     * @return degree of freedom 1
     */
    public int DOF1() {
        return n1 - 1;
    }
    
    /** 
     * Returns the degree of freedom 2.
     * 
     * @return degree of freedom 2
     */
    public int DOF2() {
        return n2 - 1;
    }
    
    /**
     * Returns the critical value corresponding to the given confidence
     * level and type of test.  Uses the F distribution.
     * 
     * @return critical value
     * @see HypothesisTest
     */
    public String criticalValue() {
        double alpha = 1 - confidenceLevel;
        
        
        FProbabilityDistribution dist = 
                new FProbabilityDistribution(DOF1(), DOF2());    // F distribution
        
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
     * Returns the p-value in a F distribution.
     * 
     * @return p-value
     */
    public double pValue() {
        double ts = testStatistics();
        
        FProbabilityDistribution dist = 
                new FProbabilityDistribution(DOF1(), DOF2());    // F distribution
        
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
