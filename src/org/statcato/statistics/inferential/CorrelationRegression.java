/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.StudentTProbabilityDistribution;
import org.statcato.statistics.BasicStatistics;
import org.statcato.utils.HelperFunctions;
import java.util.Vector;
import java.util.ArrayList;

/**
 * Linear correlation and regression
 * between for two variables.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class CorrelationRegression {
    private Vector<Double> x, y;
    int n;  // number of pairs of data values
    double significance;
    
    /**
     * Constructor.  The x and y variables are provided as vectors
     * of double type of the same length. The values in the two vectors
     * at the same position are considered to be a data point.
     * 
     * @param x independent variable
     * @param y dependent variable
     * @param sig significance
     */
    public CorrelationRegression(Vector<Double> x, Vector<Double> y, double sig) {
        this.x = x;
        this.y = y;
        this.n = x.size();
        this.significance = sig;
    }
    
    /**
     * Returns the number of data pairs
     * 
     * @return n
     */
    public int N() {
        return n;
    }
    
    /**
     * Returns the degrees of freedom.
     * 
     * @return DOF
     */
    public int DOF() {
        return n - 2;
    }
    
    /**
     * Returns the linear correlation coefficient.
     * 
     * @return r
     */
    public double LinearCorrelationCoeff() {
        double sumX = SumX();
        double sumY = SumY();
        return (n * SumXY() - sumX * sumY) / 
                Math.sqrt(n * SumXSq() - Math.pow(sumX, 2)) / 
                Math.sqrt(n * SumYSq() - Math.pow(sumY, 2));
    }
    
    /**
     * Returns the test statistic t using the linear correlation correlation r.
     * t = r / sqrt((1 - r^2)/(n - 2)).
     * 
     * @return test statistic t
     */
    public double TestStatistic_t() {
        double r = LinearCorrelationCoeff();
        return r / Math.sqrt((1 - r * r) / (n - 2));
    }
    
    /**
     * Returns the slope of the regression equation b1.
     * 
     * @return b1 slope
     */
    public double RegressionEqSlope() {
        double sumX = SumX();
        double sumY = SumY();
        return (n * SumXY() - sumX * sumY) / 
                (n * SumXSq() - sumX * sumX);
    }
    
    /**
     * Returns the y-intercept of the regression equation b0.
     * 
     * @return b0 y-intercept
     */
    public double RegressionEqYInt() {
        double sumX = SumX();
        double sumY = SumY();
        double sumXSq = SumXSq();
        return (sumY * sumXSq - sumX * SumXY()) / 
                (n * SumXSq() - sumX * sumX);
    }
    
    /**
     * Returns the sum of all the x values.
     * 
     * @return sum of x
     */
    private double SumX() {
        return BasicStatistics.sum(x);
    }
    
    /**
     * Returns the sum of all the y values.
     * 
     * @return sum of y
     */
    private double SumY() {
        return BasicStatistics.sum(y);
    }
    
    /**
     * Returns the sum of pairwise products between the x and y values.
     * 
     * @return sum of x * y 
     */
    private double SumXY() {
        double sum = 0;
        for (int i = 0; i < x.size(); ++i) {
            sum += ((Double)x.elementAt(i)).doubleValue() * 
                    ((Double)y.elementAt(i)).doubleValue();
        }
        return sum;
    }
    
    /**
     * Returns the sum of sqaures of the x values.
     * 
     * @return sum of x squares
     */
    private double SumXSq() {
        double sum = 0;
        for (int i = 0; i < x.size(); ++i) {
            sum += ((Double)x.elementAt(i)).doubleValue() * 
                    ((Double)x.elementAt(i)).doubleValue();
        }
        return sum;
    }
    
    /**
     * Returns the sum of sqaures of the y values.
     * 
     * @return sum of y squares
     */
    private double SumYSq() {
        double sum = 0;
        for (int i = 0; i < y.size(); ++i) {
            sum += ((Double)y.elementAt(i)).doubleValue() * 
                    ((Double)y.elementAt(i)).doubleValue();
        }
        return sum;
    }
    
    /**
     * Returns the total variation (sum of differences between the y value
     * and the mean).
     * 
     * @return total variation
     */
    public double TotalVariation() {
        double total = 0;
        double mean = BasicStatistics.mean(y).doubleValue();
        for (int i = 0; i < y.size(); ++i) {
            total += Math.pow((Double)y.elementAt(i).doubleValue() - mean, 2);
        }
        return total;
    }
    
    /**
     * Returns the explained variation (sum of differences between the 
     * predicted y value and the mean).
     * 
     * @return explained variation
     */
    public double ExplainedVariation() {
        double total = 0;
        double mean = BasicStatistics.mean(y).doubleValue();
        double xValue, yValue, yR;
        double b0 = RegressionEqYInt();
        double b1 = RegressionEqSlope();
        for (int i = 0; i < y.size(); ++i) {
            yValue = (Double)y.elementAt(i).doubleValue();
            xValue = (Double)x.elementAt(i).doubleValue();
            yR = b0 + b1 * xValue;
            total += Math.pow(yR - mean, 2);
        }
        return total;
    }

    /**
     * Returns the unexplained variation (sum of differences between the 
     * y value and the predicted y value).
     * 
     * @return unexplained variation
     */
    public double UnexplainedVariation() {
        double total = 0;
        double mean = BasicStatistics.mean(y).doubleValue();
        double xValue, yValue, yR;
        double b0 = RegressionEqYInt();
        double b1 = RegressionEqSlope();
        for (int i = 0; i < y.size(); ++i) {
            yValue = (Double)y.elementAt(i).doubleValue();
            xValue = (Double)x.elementAt(i).doubleValue();
            yR = b0 + b1 * xValue;
            total += Math.pow(yValue - yR, 2);
        }
        return total;
    }
    
    /**
     * Returns a list of residual values in order.
     * 
     * @return  ArrayList object containing the residuals 
     */
    public ArrayList<Double> Residuals() {
        ArrayList<Double> residualList = new ArrayList<Double>();
        ArrayList<Double> predictedList = PredictedValues();
        double yValue, yR;

        for (int i = 0; i < y.size(); ++i) {
            yR = predictedList.get(i);
            yValue = (Double)y.elementAt(i).doubleValue();
            residualList.add(new Double(yValue - yR));
        }
        return residualList;
    }
    
    /**
     * Returns a list of predicated values in order.
     * 
     * @return  ArrayList object containing the predicted values 
     */
    public ArrayList<Double> PredictedValues() {
        ArrayList<Double> valueList = new ArrayList<Double>();
        double xValue, yValue, yR;
        double b0 = RegressionEqYInt();
        double b1 = RegressionEqSlope();
        for (int i = 0; i < y.size(); ++i) {
            yValue = (Double)y.elementAt(i).doubleValue();
            xValue = (Double)x.elementAt(i).doubleValue();
            yR = b0 + b1 * xValue;
            valueList.add(new Double(yR));
        }
        return valueList;
    }
    
    /**
     * Returns the coefficient of determination r^2, the amount of the variation 
     * in y that is explained by the regression line.
     * 
     * @return r^2 coefficient of determination
     */
    public double CoefficientOfDetermination() {
        return ExplainedVariation() / TotalVariation();
    }
    
    public double StandardError() {
        double b0 = RegressionEqYInt();
        double b1 = RegressionEqSlope();
        return Math.sqrt((SumYSq() - b0 * SumY() - b1 * SumXY()) / (n - 2));
    }
    
    /**
     * Returns the critical value for r corresponding to the given significance.
     * 
     * @param significance significance
     * @return critical value (positive)
     */
    public double CriticalValue_r(double significance) {
        double t = CriticalValue_t(significance);
        double tSq = t * t;
        return Math.sqrt(tSq / (n - 2 + tSq));
    }

    public double CriticalValue_t(double significance) {
        StudentTProbabilityDistribution dist =
                new StudentTProbabilityDistribution(DOF());
        return dist.inverseCumulativeProbability(1 - significance / 2);
    }

    /**
     * Returns the p-Value corresponding to the test statistic.
     * 
     * @return p-Value
     */
    public double PValue() {
        double ts = TestStatistic_t();
        
        StudentTProbabilityDistribution dist = 
                new StudentTProbabilityDistribution(DOF());    // t distribution
        
        double area = dist.cumulativeProbability(ts);
        if (area > 0.5)
            area = 1 - area;
        return 2 * area;
    }        
    
    @Override
    public String toString() {
        String text = "";
        text += "Sample size n = " + N() + "<br>";
        text += "Degrees of freedom = " + DOF() + "<br><br>";
        text += "<u>Correlation</u>:<br>";
        text += "H<sub>0</sub>: &rho; = 0 (no linear correlation)<br>";
        text += "H<sub>1</sub>: &rho; &ne; 0 (linear correlation)<br>";
        text += "<table border=1>";
        text += "<tr><th>&nbsp;</th><th>Test Statistic</th><th>Critical Value</th></tr>";
        text += "<tr><th>r</th>";
        text += "<td>" +
                HelperFunctions.formatFloat(LinearCorrelationCoeff(), 4) + "</td>";
        text += "<td>&plusmn;" +
                HelperFunctions.formatFloat(CriticalValue_r(significance), 4) + "</td>";
        text += "</tr>";
        text += "<tr><th>t</th>";
        text += "<td>" +
                HelperFunctions.formatFloat(TestStatistic_t(), 4) + "</td>";
        text += "<td>&plusmn;" +
                HelperFunctions.formatFloat(CriticalValue_t(significance), 4) + "</td>";
        text += "</tr></table>";

        text += "p-Value = " + 
                HelperFunctions.formatFloat(PValue(), 4) + "<br><br>";
        text += "<u>Regression</u>:<br>";
        text += "Regression equation Y = b<sub>0</sub> + b<sub>1</sub>x<br>";
        text += "b<sub>0</sub> = " + 
                HelperFunctions.formatFloat(RegressionEqYInt(), 4) + "<br>";
        text += "b<sub>1</sub> = " + 
                HelperFunctions.formatFloat(RegressionEqSlope(), 4) + "<br><br>";
        text += "<u>Variation</u>:<br>";
        text += "Explained variation = " +
                HelperFunctions.formatFloat(ExplainedVariation(), 4) + "<br>";
        text += "Unexplained variation = " +
                HelperFunctions.formatFloat(UnexplainedVariation(), 4) + "<br>";
        text += "Total variation = " + 
                HelperFunctions.formatFloat(TotalVariation(), 4) + "<br>";
        text += "Coefficient of determination r<sup>2</sup> = " + 
                HelperFunctions.formatFloat(CoefficientOfDetermination(), 4)
                + "<br>";
        text += "Standard error of estimate = " + 
                HelperFunctions.formatFloat(StandardError(), 4) + "<br><br>";
        
        return text;
    }
    
}
