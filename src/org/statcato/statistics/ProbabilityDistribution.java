/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Abstract base class of different types of
 * continuous probability distributions.  Subclasses must implement density and
 * cumulativeProbability methods.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public abstract class ProbabilityDistribution {

    
    /**
     * Returns the density of the distribution at x.
     * 
     * @param x 
     * @return probability density at x
     */
    public abstract double density(double x);
    
    /** Returns the cumulative probability up to x.
     * 
     * @param x
     * @return cumulative probability P(<=x)
     */
    public abstract double cumulativeProbability(double x);
    
    /**
     * Returns the inverse cumulative probability.
     * Uses the Brent's method to find a root between x1 and x2 that gives
     * a cumulative probability of area with an accuracy within tol tolerance.
     * If the given area is 1, positive infinity is returned.
     * 
     * @param x1 lower bound of root
     * @param x2 upper bound of root
     * @param tol tolerance
     * @param area cumulative probability
     * @return inverse cumulative probability
     */
    public double inverseCumulativeProbability(double x1, double x2, double tol, 
            double area) {
        if (area == 1) {
            return Double.POSITIVE_INFINITY;
        }
        if (area < 0 || area > 1)
            return Double.NaN;
        
        double ITMAX = 100;    // Maximum iterations. 
        double EPS = 3.0e-7;   // Relative accuracy.
        int iter;
        double a=x1,b=x2,c=x2,d=0,e=0,min1=0,min2=0;
        double fa=cumulativeProbability(a)-area;
        double fb=cumulativeProbability(b)-area;
        double fc,p,q,r,s,tol1,xm;
        d=0; // added by Scott.
        if ((fa > 0.0 && fb > 0.0) || (fa < 0.0 && fb < 0.0)) 
            System.err.println("Root must be bracketed in zbrent");
        c=b;
        fc=fb;
        for (iter=1;iter<=ITMAX;iter++) {
            if ((fb > 0.0 && fc > 0.0) || (fb < 0.0 && fc < 0.0)) { // Rename a, b, c and adjust bounding interval d.
                c=a;
                fc=fa; 
                d=b-a;
                e=d; 
            }
            if (Math.abs(fc) < Math.abs(fb)) {
                a=b; 
                b=c; 
                c=a;
                fa=fb;
                fb=fc;
                fc=fa;
            }
            tol1=2.0*EPS*Math.abs(b)+0.5*tol;                           // Convergence check.
            xm=0.5*(c-b);
            if (Math.abs(xm) <= tol1 || fb == 0.0) 
                // round to 4 decimal places
                return (new BigDecimal(b)).setScale(4, RoundingMode.HALF_UP).doubleValue();
            if (Math.abs(e) >= tol1 && Math.abs(fa) > Math.abs(fb)) {
                s=fb/fa;                                              // Attempt inverse quadratic interpolation.
                if (a == c) {
                    p=2.0*xm*s; q=1.0-s;
                } else {
                    q=fa/fc; r=fb/fc; 
                    p=s*(2.0*xm*q*(q-r)-(b-a)*(r-1.0)); 
                    q=(q-1.0)*(r-1.0)*(s-1.0);
                }
                if (p > 0.0) q = -q;                                  // Check whether in bounds.
                p=Math.abs(p);
                min1=3.0*xm*q-Math.abs(tol1*q);
                min2=Math.abs(e*q);
                if (2.0*p < (min1 < min2 ? min1 : min2)) {
                    // Accept interpolation.
                    e=d; d=p/q;
                } else {
                    // Interpolation failed, use bisection.
                    d=xm; e=d;
                }
            } else { 
            // Bounds decreasing too slowly, use bisection.
                d=xm; e=d;
            }   
            a=b;                                                    // Move last best guess to a.
            fa=fb;
            if (Math.abs(d) > tol1) b +=d;                              // Evaluate new trial root.
            else b += SpecialFunctions.sign(tol1,xm); 
            fb=cumulativeProbability(b) - area;
        }
        System.err.println("Maximum number of iterations exceeded in zbrent");
        return 0.0;                                               // Never get here.        
    }
}
