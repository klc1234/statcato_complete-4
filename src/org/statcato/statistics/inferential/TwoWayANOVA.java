/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import org.statcato.utils.HelperFunctions;

/**
 * Two-way analysis of variance (two-factor ANOVA).  
 * Performs computations for two-way ANOVA given all the observations
 * and their corresponding row and column indices.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class TwoWayANOVA {
    private int a;  // number of levels in factor A
    private int b;  // number of levels in factor B
    private int N;  // total of all observations
    private int n;  // number of observations per each level combination
    private double CM;  // correction of the mean
    private TwoANOVATableCell y[][];    // table of observations,
                                        // observations grouped in TwoANOVATableCell
    
    /**
     * Constructor, given the number of levels in factor A and B.  
     * 
     * @param a number of levels in factor A
     * @param b number of levels in factor B
     */
    public TwoWayANOVA(int a, int b) {
        this.a = a;
        this.b = b;
        y = new TwoANOVATableCell[a][b];
        for (int i = 0; i < a; ++i) 
            for (int j = 0; j < b; ++j)
                y[i][j] = new TwoANOVATableCell();
        
    }
    
    /**
     * Adds the given data to row i and column j.
     * 
     * @param i     row index
     * @param j     column index
     * @param data  double array
     */
    public void addObservation(int i, int j, double data) {
        y[i][j].addObservation(data);
    }
    
    /**
     * Checks if the data is valid for two-way ANOVA.  Sets the 
     * treatment group size and the total sample size.  Computes
     * the correction of the mean (CM).
     * 
     * @return boolean value of whether the data is valid
     */
    public boolean isValidData() {
        int size = y[0][0].getSize();
        
        for (int i = 0; i < a; ++i) 
            for (int j = 0; j < b; ++j)
                if (y[i][j].getSize() != size)
                    return false;
        
        n = size;
        N = a * b * n;
        findCM();
        return true;
    }
    
    /**
     * Returns the row sum A_i = Sum_{j=1...b} {cell sum of y[i][j]}.
     * 
     * @return row sum
     */
    public double rowSum(int i) {
        double sum = 0;
        for (int j = 0; j < b; ++j) {
            sum += y[i][j].sum();
        }
        return sum;
    }
    
    /**
     * Returns the column sum B_j = Sum_{i=1...a} {cell sum of y[i][j]}.
     * 
     * @return column sum
     */
    public double colSum(int j) {
        double sum = 0;
        for (int i = 0; i < a; ++i) {
            sum += y[i][j].sum();
        }
        return sum;
    }
    
    /**
     * Computes the correction of the mean, (sum of all observations)^2 / N.
     * Should be called before calls to compute sum of squares.
     */
    public void findCM() {
        double sum = 0; // (sum of all observations)^2
        for (int i = 0; i < a; ++i) 
            for (int j = 0; j < b; ++j)
                sum += y[i][j].sum();
        CM = sum * sum / N;
    }
    
    /**
     * Returns the sum of squares for factor A, 
     * (Sum_{i=1..a} A_i^2) / (b*n) - CM.
     * 
     * @return sum of squares for factor A
     */
    public double SSA() {
        double sum = 0;
        for (int i = 0; i < a; ++i) {
            sum += Math.pow(rowSum(i), 2);
        }
        return sum / (b * n) - CM;
    }
    
    /**
     * Returns the sum of squares for factor B,
     * (Sum_{j=1..b} B_j^2) / (a*n) - CM.
     * 
     * @return sum of squares for factor B
     */
    public double SSB() {
        double sum = 0;
        for (int j = 0; j < b; ++j) {
            sum += Math.pow(colSum(j), 2);
        }
        return sum / (a * n) - CM;
    }
    
    /**
     * Returns the sum of squares for interaction AB,
     * (Sum (cell sum)^2) / n - CM - SSA - SSB.
     * 
     * @return sum of squares for interaction AB
     */
    public double SSAB() {
        if (n == 1)
            return 0;
        double sum = 0; 
        for (int i = 0; i < a; ++i) {
            for (int j = 0; j < b; ++j) {
                sum += Math.pow(y[i][j].sum(), 2);
            }
        }
        return sum / n - CM - SSA() - SSB();
    }
    
    /**
     * Returns the total sum of squares, 
     * Sum (observation)^2 - CM.
     * 
     * @return total sum of squares
     */
    public double SSTotal() {
        double sum = 0; 
        for (int i = 0; i < a; ++i) {
            for (int j = 0; j < b; ++j) {
                sum += y[i][j].sumOfSquares();
            }
        }
        return sum - CM;
    }
    
    /**
     * Returns sum of squares of error,
     * SSTotal - SSA - SSB - SSAB.
     * 
     * @return sum of squares of error
     */
    public double SSE() {
        return SSTotal() - SSA() - SSB() - SSAB();
    }
    
    /**
     * Returns the mean of squares of factor A = SSA / DOFA.
     * 
     * @return mean of squares of factor A
     */
    public double MSA() {
        return SSA() / DOFA();
    }
    
    /**
     * Returns the mean of squares of factor B = SSB / DOFB.
     * 
     * @return mean of squares of factor B
     */
    public double MSB() {
        return SSB() / DOFB();
    }
    
    /**
     * Returns the mean of squares of interaction AB = SSAB / DOFAB.
     * 
     * @return mean of squares of interaction AB
     */
    public double MSAB() {
        return SSAB() / DOFAB();
    }
    
    /**
     * Returns the mean of squares of error = SSE / DOFE.
     * 
     * @return mean of squares of error
     */
    public double MSE() {
        return SSE() / DOFE();
    }
    
    
    /**
     * Returns the degrees of freedom  for factor A = a - 1.
     * 
     * @return degrees of freedom for A
     */
    public int DOFA() {
        return a - 1;
    }
    
    /**
     * Returns the degrees of freedom for factor B = b - 1.
     * 
     * @return degrees of freedom for B
     */
    public int DOFB() {
        return b - 1;
    }
    
    /**
     * Returns the degrees of freedom for interaction AB.
     * 
     * @return degrees of freedom for AB
     */
    public int DOFAB() {
        return (a - 1) * (b - 1);
    }
    
    /**
     * Returns the degrees of freedom for the error.
     * 
     * @return degrees of freedom for error
     */
    public int DOFE() {
        if (n == 1)
            return (N - 1) - (a - 1) - (b - 1);
        return N - a * b;
    }
    
    /**
     * Returns the total degrees of freedom.
     * 
     * @return total degrees of freedom
     */
    public int DOFTotal() {
        return a * b * n - 1;
    }

    /**
     * Returns the F test statistics for factor A.
     * 
     * @return F for factor A
     */
    public double FA() {
        return MSA() / MSE();
    }
    
    /**
     * Returns the F test statistics for factor B.
     * 
     * @return F for factor B
     */
    public double FB() {
        return MSB() / MSE();
    }
    
    /**
     * Returns the F test statistics for interaction AB.
     * 
     * @return F for interaction AB
     */
    public double FAB() {
        return MSAB() / MSE();
    }
    
    /**
     * Returns the p-Value corresponding to the F test statistic for factor A.
     * 
     * @return p-Value
     */
    public double PValueA() {
        double ts = FA();
        
        FProbabilityDistribution dist = 
                new FProbabilityDistribution(DOFA(), DOFE());
        
        double area = dist.cumulativeProbability(ts);
        return 1 - area;
    }
    
    /**
     * Returns the p-Value corresponding to the F test statistic for factor B.
     * 
     * @return p-Value
     */
    public double PValueB() {
        double ts = FB();
        
        FProbabilityDistribution dist = 
                new FProbabilityDistribution(DOFB(), DOFE());
        
        double area = dist.cumulativeProbability(ts);
        return 1 - area;
    }
    
    /**
     * Returns the p-Value corresponding to the F test statistic for interaction
     * AB.
     * 
     * @return p-Value
     */
    public double PValueAB() {
        double ts = FAB();
        
        FProbabilityDistribution dist = 
                new FProbabilityDistribution(DOFAB(), DOFE());
        
        double area = dist.cumulativeProbability(ts);
        return 1 - area;
    }
    
    @Override
    public String toString() {
        String text = "Treatment group size = " + n + "<br>";
        text += "Total sample size = " + N + "<br>";
        text += "<table border='1'>";
        text += "<tr align='right'><th>Source of Variation</th><th>DOF</th><th>SS</th><th>MS</th>" +
                "<th>F</th>" +
                "<th>p-Value</th></tr>";
        text += "<tr align='right'><th>Row Factor</th><td>" + DOFA() + "</td>"
                + "<td>" + HelperFunctions.formatFloat(SSA(), 5) + "</td>" 
                + "<td>" + HelperFunctions.formatFloat(MSA(), 5) + "</td>"
                + "<td>" + HelperFunctions.formatFloat(FA(), 5) + "</td>"
                + "<td>" + HelperFunctions.formatFloat(PValueA(), 5) + "</td></tr>";
        text += "<tr align='right'><th>Column Factor</th><td>" + DOFB() + "</td>"
                + "<td>" + HelperFunctions.formatFloat(SSB(), 5) + "</td>" 
                + "<td>" + HelperFunctions.formatFloat(MSB(), 5) + "</td>"
                + "<td>" + HelperFunctions.formatFloat(FB(), 5) + "</td>"
                + "<td>" + HelperFunctions.formatFloat(PValueB(), 5) + "</td></tr>";
        if (n > 1) {
            text += "<tr align='right'><th>Interaction</th><td>" + DOFAB() + "</td>"
                + "<td>" + HelperFunctions.formatFloat(SSAB(), 5) + "</td>" 
                + "<td>" + HelperFunctions.formatFloat(MSAB(), 5) + "</td>"
                + "<td>" + HelperFunctions.formatFloat(FAB(), 5) + "</td>"
                + "<td>" + HelperFunctions.formatFloat(PValueAB(), 5) + "</td></tr>";
        }
        text += "<tr align='right'><th>Error</th><td>" + DOFE() + "</td>"
                + "<td>" + HelperFunctions.formatFloat(SSE(), 5) + "</td>"
                + "<td>" + HelperFunctions.formatFloat(MSE(), 5) + "</td>"
                + "<td>&nbsp;</td><td>&nbsp;</td></tr>";
        text += "<tr align='right'><th>Total</th><td>" + DOFTotal() + "</td>"
                + "<td>" + HelperFunctions.formatFloat(SSTotal(), 5) + "</td>"
                + "<td>&nbsp;</td>"
                + "<td>&nbsp;</td><td>&nbsp;</td></tr>";
        text += "</table>";        
        return text;
    }
}
