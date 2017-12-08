/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import java.util.Vector;
import org.statcato.statistics.ChiSquareProbabilityDistribution;

/**
 * A Chi-square goodness-of-fit test.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class GoodnessOfFitTest {
    int k;  // number of categories
    double confidenceLevel;    // confidence level
    
    /**
     * Constructor.
     * 
     * @param k number of categories
     * @param confidence confidence level
     */
    public GoodnessOfFitTest(int k, double confidence) {
        this.k = k;
        this.confidenceLevel = confidence;
    }
    
    /**
     * Returns a vector of each observed frequency's contribution to the chi-square
     * test statistic.
     * 
     * @param observedFreq vector of observed frequencies
     * @param expectedFreq vector of expected frequencies
     * @return a vector of Double (each observed frequency's contribution to 
     * test statistic)
     */
    public Vector<Double> testStatisticContribution(Vector<Double> observedFreq,
            Vector<Double> expectedFreq) {
        Vector<Double> cont = new Vector<Double>();
        for (int i = 0; i < observedFreq.size(); ++i) {
            double chi2 = Math.pow(observedFreq.elementAt(i) - 
                    expectedFreq.elementAt(i), 2) 
                    / expectedFreq.elementAt(i);
            cont.addElement(new Double(chi2));
        }
        return cont;
    }
    
    /**
     * Computes the chi-square test statistic for goodness-of-fit 
     * test in multinomial experiment, given the observed and 
     * expected frequencies.  
     * Chi^2 = Sum_{(O - E)^2 / E}.
     * 
     * @param observedFreq a vector of observed frequencies
     * @param expectedFreq a vector of expected frequencies
     * @return test statistic
     */
    public double testStatistic(Vector<Double> observedFreq, 
            Vector<Double> expectedFreq) {
        double chi2 = 0;    
        for (int i = 0; i < observedFreq.size(); ++i) {
            chi2 += Math.pow(observedFreq.elementAt(i) - 
                    expectedFreq.elementAt(i), 2) 
                    / expectedFreq.elementAt(i);
        }
        return chi2;
    }
    
    /**
     * Returns the degrees of freedom.
     * 
     * @return degrees of freedom
     */
    public int DOF() {
        return k-1;
    }
    
    /**
     * Returns the critical value corresponding to the significance level
     * and degrees of freedom.
     * 
     * @return critical value
     */
    public double criticalValue() {
        ChiSquareProbabilityDistribution dist = 
                new ChiSquareProbabilityDistribution(DOF());
        return dist.inverseCumulativeProbability(confidenceLevel);
    }
    
    /**
     * Returns the p-Value corresponding to the given test statistic
     * in a chi-square distribution.
     * 
     * @param ts
     * @return p-value
     */
    public double pValue(double ts) {
        ChiSquareProbabilityDistribution dist = 
                new ChiSquareProbabilityDistribution(DOF());
        return 1 - dist.cumulativeProbability(ts);
    }
}
