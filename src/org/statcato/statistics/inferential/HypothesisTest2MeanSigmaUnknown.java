/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import org.statcato.utils.HelperFunctions;

/**
 * A hypothesis test for 2 population means
 * (population standard deviations sigmas unknown).
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class HypothesisTest2MeanSigmaUnknown {
    int n1, n2;  // sample sizes of populations 1 and 2
    double xBar1, xBar2;  // sample means of populations 1 and 2
    double s1, s2;  // sample standard deviations of populations 1 and 2
    double diff;    // hypothesized difference in mean
    double confidenceLevel; // confidence level
    int testType;   // type of test 
    boolean varEqual;
    
    /**
     * Constructor.
     * 
     * @param n1 sample size 1
     * @param n2 sample size 2
     * @param xBar1 sample mean 1
     * @param xBar2 sample mean 2
     * @param s1 sample standard deviation 1
     * @param s2 sample standard deviation 2
     * @param diff hypothesized difference in population mean
     * @param confidenceLevel confidence level
     * @param testType type of test (as defined in {@link HypothesisTest})
     * @param varEqual whether population variances are assumed equal
     */
    public HypothesisTest2MeanSigmaUnknown(int n1, int n2, double xBar1, double xBar2, 
            double s1, double s2, double diff,
            double confidenceLevel, int testType, boolean varEqual) {
        this.n1 = n1;
        this.n2 = n2;
        this.xBar1 = xBar1;
        this.xBar2 = xBar2;
        this.s1 = s1;
        this.s2 = s2;
        this.diff = diff;
        this.confidenceLevel = confidenceLevel;
        this.testType = testType;
        this.varEqual = varEqual;
    }
    
    /**
     * Returns the test statistics.
     * 
     * @return test statistic t score
     */
    public double testStatistics() {        
        if (varEqual) {
            double sp2 = ((n1 - 1.0)*s1*s1 + (n2 - 1.0)*s2*s2) / (n1 + n2 - 2.0);
            return (xBar1 - xBar2 - diff) / Math.sqrt(sp2 / n1 + sp2 / n2); 
        }
        else {
            return (xBar1 - xBar2 - diff) / Math.sqrt(s1 * s1 / n1 + s2 * s2 / n2);
        }
    }
    
    /** 
     * Returns the degree of freedom.
     * 
     * @return degree of freedom
     */
    public int DOF() {
        if (varEqual) {
            return n1 + n2 - 2;
        }
        double A = s1 * s1 / n1;
        double B = s2 * s2 / n2;
        return (int)Math.floor(Math.pow(A + B, 2) / (A * A / (n1 - 1) + B * B / (n2 - 1)));
    }
    
    /**
     * Returns the critical value corresponding to the given confidence
     * level and type of test.  Uses the Student's t distribution.
     * 
     * @return critical value
     * @see HypothesisTest
     */
    public String criticalValue() {
        double alpha = 1 - confidenceLevel;
        
        
        StudentTProbabilityDistribution dist = 
                new StudentTProbabilityDistribution(DOF());    // t distribution
        
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
        
        StudentTProbabilityDistribution dist = 
                new StudentTProbabilityDistribution(DOF());    // t distribution
        
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
