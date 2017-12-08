/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

import java.util.*;
import org.statcato.statistics.inferential.CorrelationRegression;
import org.statcato.utils.HelperFunctions;

/**
 * A class representing the Ryan-Joiner normality test.
 * 
 * @author  Margaret Yau
 * @version %I%, %G%
 * @see org.jfree.chart
 * @since 1.0
 */
public class NormalityTest {
    Vector<Double> dataVector, zscoresVector;
    double significance;
    int n;
    public NormalityTest(Vector<Double> data, double sig) {
        dataVector = data;
        significance = sig;
        // find z scores
        zscoresVector = new Vector<Double>();
        Collections.sort(dataVector);
        n = dataVector.size();
        
        for (int i = 0; i < n; ++i) {
             double a = (i + 1.0) / (n + 1.0);
             NormalProbabilityDistribution dist = new 
                     NormalProbabilityDistribution(0, 1);
             double z = dist.inverseCumulativeProbability(a);
             zscoresVector.addElement(new Double(z));
        }
    }
            
    @Override
    public String toString() {
        // normality test results
        String text = "";
        text += "<u>Normality Test (Ryan-Joiner):</u><br>";
        CorrelationRegression c = new CorrelationRegression(dataVector, 
                zscoresVector, significance);
        text += "&nbsp;&nbsp;Sample Size: n = " + n + "<br>";
        text += "&nbsp;&nbsp;Significance: " + significance + "<br>";
        text += "&nbsp;&nbsp;Correlation Coefficient: r = " + 
                HelperFunctions.formatFloat(c.LinearCorrelationCoeff(), 5) + "<br>";
        text += "&nbsp;&nbsp;Critical Value: " + 
                HelperFunctions.formatFloat(
                BasicStatistics.RyanJoinerNormalityCV(n, significance), 5);
        if (n > 75) {
            text += "<br><i>Warning</i>: large sample size n > 75";
        }
        return text;
    }
}
