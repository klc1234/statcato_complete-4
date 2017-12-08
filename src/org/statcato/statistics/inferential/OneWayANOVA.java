/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics.inferential;

import org.statcato.statistics.*;
import java.util.Vector;

/**
 * One-way analysis of variance (single factor ANOVA).  
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class OneWayANOVA {
    Vector<Vector<Double>> SamplesVectors;
    int k;  // number of treatments (levels)
    int n[];    // sample sizes of treatment samples
    int N;  // total of all sample sizes
    double means[]; // sample means of treatment samples
    double stdevs[];    // sample standard deviations of treatment samples
    
    /**
     * Constructor.  The samples are given as a vector of vector of double,
     * each vector contains values of a treatment sample.
     * 
     * @param Samples
     */
    public OneWayANOVA(Vector<Vector<Double>> Samples) {
        this.SamplesVectors = Samples;
        k = Samples.size();
        n = new int[k];
        means = new double[k];
        stdevs = new double[k];
        N = 0;
        
        // put the sample size of each treatment sample in array n
        for (int i = 0; i < k; ++i) {
            Vector<Double> sample = (Vector<Double>)Samples.elementAt(i);
            n[i] = sample.size();
            N += n[i];
            means[i] = BasicStatistics.mean(sample).doubleValue();
            stdevs[i] = BasicStatistics.stdev(sample).doubleValue();
            
            //System.out.println("i=" + i + ", n=" + n[i] + ", mean=" + means[i]
            //        + ", stdev=" + stdevs[i]);
        }
        
        //System.out.println("N = " + N + ", k = " + k);
    }
    
    /**
     * Returns the grand mean (mean of all the treatment sample means).
     * 
     * @return grand mean
     */
    public double GrandMean() {
        double total = 0;
        for (int i = 0; i < k; ++i) {
            total += ((double)n[i])*means[i];
        }
        
        return total / (double)N;
    }
    
    /**
     * Returns SS(treatment), sum of squares of treatments.
     * 
     * @return SS(treatment)
     */
    public double SSTreatment() {
        double sum = 0;
        double grandMean = GrandMean();
        double diff;
        
        for (int i = 0; i < k; ++i) {
            diff = means[i] - grandMean;
            sum += n[i] * diff * diff;
        }
        
        return sum;
    }
    
    /**
     * Returns MS(treatment), the mean of squares of treatment.
     * 
     * @return MS(treatment)
     */
    public double MSTreatment() {
        return SSTreatment() / (k - 1);
    }
    
    /**
     * Returns SS(error), the sum of squares of means.
     * 
     * @return SS(error)
     */
    public double SSError() {
        double sum = 0;
        
        for (int i = 0; i < k; ++i) {
            sum += (n[i] - 1) * Math.pow(stdevs[i], 2);
        }
        
        return sum;
    }
    
    /**
     * Returns MS(error), the mean of squares of errors.
     * @return MS(error)
     */
    public double MSError() {
        return SSError() / (N - k);
    }
    
    /**
     * Returns the total sum of squares SS(treatment) + SS(error).
     * 
     * @return SS(treatment) + SS(error)
     */
    public double SSTotal() {
        return SSTreatment() + SSError();
    }
    
    /**
     * Returns the test statistics F = MS(treatment) / MS(error).
     * 
     * @return F
     */
    public double TestStatisticsF() {
        return MSTreatment() / MSError();
    }
    
    /**
     * Returns the numerator degrees of freedom (k - 1).
     * 
     * @return degrees of freedom 1
     */
    public int DOFTreatment() {
        return k - 1;
    }
    
    /**
     * Returns the denominator degrees of freedom (N - k).
     * 
     * @return degrees of freedom 2
     */
    public int DOFError() {
        return N - k;
    }
    
    /**
     * Returns the total degrees of freedom.
     * 
     * @return DOF1 + DOF2
     */
    public int DOFTotal() {
        return DOFTreatment() + DOFError();
    }
    
    /**
     * Returns the critical value corresponding to the given significance.
     * 
     * @param significance
     * @return critical value (positive)
     */
    public double CriticalValue(double significance) {
        FProbabilityDistribution dist = 
                new FProbabilityDistribution(DOFTreatment(), DOFError()); // F(k-1, N-k)
        
        double F = dist.inverseCumulativeProbability(1 - significance);  
        return F;
    }
    
    /**
     * Returns the p-Value corresponding to the test statistic.
     * 
     * @return p-Value
     */
    public double PValue() {
        double ts = TestStatisticsF();
        
        FProbabilityDistribution dist = 
                new FProbabilityDistribution(DOFTreatment(), DOFError()); // F(k-1, N-k)
        
        double area = dist.cumulativeProbability(ts);
        return 1 - area;
    }
}
