/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential.nonparametrics;

import org.statcato.utils.HelperFunctions;
import org.statcato.utils.OrderingFunctions;
import org.statcato.statistics.inferential.CorrelationRegression;

import java.util.Vector;

/**
 * Spearman's rank correlation between two variables.  Computes
 * the correlation between two variables using their ranks.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @see org.statcato.statistics.inferential.CorrelationRegression
 * @since 1.0
 */
public class RankCorrelation {
    CorrelationRegression cr;
    double significance;

    /**
     * Constructor.  The x and y variables are provided as vectors
     * of double type of the same length. The values in the two vectors
     * at the same position are considered to be a data point.
     * The signifiance level of the test is also provided.
     * 
     * @param x variable 1
     * @param y variable 2
     * @param sig significance
     */
    @SuppressWarnings("unchecked") 
    public RankCorrelation(Vector<Double> x, Vector<Double> y, double sig) {
        // compute ranks
        double[] ranks1 = OrderingFunctions.rankDoubleVector(x);
        double[] ranks2 = OrderingFunctions.rankDoubleVector(y);
        Vector<Double> v1 = new Vector<Double>();
        Vector<Double> v2 = new Vector<Double>();
        for (int i = 0; i < ranks1.length; ++i) {
            v1.addElement(new Double(ranks1[i]));
            v2.addElement(new Double(ranks2[i]));
        }

        this.significance = sig;
        cr = new CorrelationRegression(
                new Vector(v1),
                new Vector(v2),
                sig);
    }
    
    /**
     * Returns the number of data pairs
     * 
     * @return n
     */
    public int N() {
        return cr.N();
    }
    
    /**
     * Returns the rank correlation coefficient.
     * 
     * @return rs
     */
    public double RankCorrelationCoeff() {
        return cr.LinearCorrelationCoeff();
    }
   
    /**
     * Returns the critical value for r corresponding to the given significance.
     * 
     * @param significance significance
     * @return critical value (positive)
     */
    public double CriticalValue() {
        return cr.CriticalValue_r(significance);
    }

    /**
     * Returns the p-Value corresponding to the test statistic.
     * 
     * @return p-Value
     */
    public double PValue() {
        return cr.PValue();
    }        
    
    @Override
    public String toString() {
        String text = "";
        
        text += "H<sub>0</sub>: &rho;<sub>s</sub> = 0 (There is no correlation between the two variables.)<br>";
        text += "H<sub>1</sub>: &rho;<sub>s</sub> &ne; 0 (There is a correlation between the two variables.)<br><br>";
        text += "Sample size n = " + N() + "<br>";
        text += "Test statistic (rank correlation coefficient) = " +
                HelperFunctions.formatFloat(RankCorrelationCoeff(), 4) + "<br>";
        text += "Critical value = &plusmn;" +
                HelperFunctions.formatFloat(CriticalValue(), 4) + "<br>";
        text += "p-Value = " + 
                HelperFunctions.formatFloat(PValue(), 4) + "<br><br>";
        
        return text;
    }
    
}
