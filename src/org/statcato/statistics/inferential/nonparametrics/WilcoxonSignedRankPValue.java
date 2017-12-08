/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential.nonparametrics;

import org.statcato.statistics.NormalProbabilityDistribution;

/**
 * A class that computes p-values and critical values for
 * Wilcoxon Signed Rank Test using exact and normal approximation methods.
 *
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class WilcoxonSignedRankPValue {
    /**
     * The maximum sample size calculated using exact method.  Normal approximation
     * is used for sample size greater than this number.
     */
    public static final int MAX_N = 30;
    /**
     * One-dimensional array of boolean values indicating whether the p-values
     * for each sample size are calculated.
     */
    public static boolean[] calculated = new boolean[MAX_N + 1];
    /**
     * The maximum rank sum given the maximum sample size.
     */
    public static final int MAX_SUM = MAX_N * (MAX_N + 1) / 2;
    /**
     * Two-dimensional array of integers representing the number of occurrences
     * of each rank sum corresponding to the sample size.  sum[i][j]
     * = number of occurrences of rank sum j for sample size i.
     */
    public static int[][] sum = new int[MAX_N + 1][MAX_SUM + 1];
    /**
     * Two-dimensional array of double values representing the cumulative p-values
     * of each rank sum corresponding to the sample size.  pvalue[i][j]
     * = cumulative p-value of rank sum j for sample size i.
     */
    public static double[][] pvalue = new double[MAX_N + 1][MAX_SUM + 1];

    /**
     * Constructor.  
     */
    public WilcoxonSignedRankPValue() {
        for (int i = 0; i <= MAX_N; ++i) {
            calculated[i] = false;
            for (int j = 0; j <= MAX_SUM; ++j) {
                sum[i][j] = 0;
                pvalue[i][j] = 0;
            }
        }
        // computes sum and pvalue for n = 1
        sum[1][0] = 1;
        sum[1][1] = 1;
        pvalue[1][0] = 0.5;
        pvalue[1][1] = 1;
        calculated[1] = true;
    }

    /**
     * Returns the p-value corresponding to the given rank sum and sample size.
     * The exact method is used for sample size <= MAX_N, where as normal
     * approximation is used for sample size > MAX_N.
     * 
     * @param s rank sum
     * @param n sample size
     * @return p-value
     */
    public static double getPValue(double s, int n) {
        if (n <= MAX_N) {
            // using exact method
            if (!calculated[n]) {
               for (int i = 2; i <= n; ++i) {
                    if (!calculated[i]) {
                        // add calculated number of occurrences to rank sum array
                        // (dynamic programming) and compute p-value
                        for (int j = 0; j <= i * (i + 1) / 2; ++j) {
                            if (j < i)
                                sum[i][j] = sum[i-1][j];
                            else
                                sum[i][j] = sum[i-1][j] + sum[i-1][j - i];
                            pvalue[i][j] = (double)sum[i][j] / (double)Math.pow(2, i)
                                    + (j>0?pvalue[i][j-1]:0);
                        }
                        calculated[i] = true;
                    }
               }
            }
            return pvalue[n][(int)Math.round(s)];
        }
        else {
            // using normal approximation
             NormalProbabilityDistribution dist =
                    new NormalProbabilityDistribution(0, 1);
            double ts = (s - n * (n + 1) / 4) /
                    Math.sqrt(n * (n + 1) *
                    (2 * n + 1) / 24);

            return dist.cumulativeProbability(ts);
        }
    }

    /**
     * Returns the critical value corresponding to the given significance and
     * sample size.
     *
     * @param alpha significance
     * @param n sample size
     * @return critical value
     */
    public static double getCriticalValue(double alpha, int n) {
        if (n <= MAX_N) {
            // using exact probabilities
            int max = n * (n + 1) / 2;
            for (int ranksum = 0; ranksum <= max; ++ranksum) {
                if (getPValue(ranksum, n) >= alpha)
                    return ranksum;
            }
            return max;
        }
        else {
            // using normal approximation
            NormalProbabilityDistribution dist =
                    new NormalProbabilityDistribution(0, 1);
            double z = dist.inverseCumulativeProbability(alpha);
            return z * Math.sqrt(n * (n + 1) * (2 * n + 1) / 24) + n * (n + 1) / 4;
        }
    }
}
