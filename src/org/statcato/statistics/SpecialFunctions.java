package org.statcato.statistics;

/**
 * A class of special functions that are used in calculating probability
 * distributions.  
 * 
 * Based on Numerical Reciples in C: The Art of Scientific Computing (1992)
 * http://www.nr.com
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class SpecialFunctions {

    static double gamser; 
    static double gln; 
    static double gammcf;
    static double ITMAX = 100;    // Maximum iterations. 
    static double EPS = 3.0e-7;   // Relative accuracy.
    static double FPMIN=1.0e-30;  // Number near the smallest representable
    static int ntop = 4;
    static double[] fact = new double[33];
    
    public static void nrerror(String s) {
        System.err.println(s);
    }

    /**
     * Returns the value ln[ gamma(xx)] for xx > 0.
     * 
     * @param xx a number
     * @return ln[gamma(xx)]
     */ 
    public static double gammln(double xx) {
        double x,y,tmp,ser;
        double cof[]={76.18009172947146,-86.50532032941677,24.01409824083091,
                         -1.231739572450155,  0.1208650973866179e-2,-.5395239384953e-5};
        int j;
        y=xx;
        x=xx;
        tmp=x+5.5;
        tmp -= (x+0.5)*Math.log(tmp);
        ser=1.000000000190015;
        for (j=0;j<=5;j++) ser += cof[j]/++y;
        return -tmp+Math.log(2.5066282746310005*ser/x);
    }

    /**
     * Returns the factorial of an integer.
     * 
     * @param n
     * @return n!  -1 indicates an error.
     */
    public static double factrl(int n) {
        fact[0] = 1.0; // fill in table only as required
        fact[1] = 1.0;
        fact[2] = 2.0;
        fact[3] = 6.0;
        fact[4] = 24.0;
        
        int j;
        if (n < 0) 
            nrerror("Negative factorial in routine factrl"); // error
        if (n > 32) 
            return Math.exp(gammln(n+1.0));
        // n <= 32
        while (ntop < n) { // fill in table up to desired value
            j = ntop++;
            fact[ntop] = fact[j] * ntop;
        }
        return fact[n];
    }
    
    /**
     * Returns the natural logarithm of the factorial of a number: ln(n!).
     * 
     * @param n
     * @return ln(n!).  -1 is returned for an error.
     */
    public static double factln(int n) {
        double a [] = new double[101];

        if (n < 0) nrerror("Negative factorial in routine factln");
        if (n <= 1) return 0.0;
        if (n <= 100) { // in table
            if (a[n] == 0)
                a[n] = gammln(n+1.0);                
            return a[n];
        }
        else return gammln(n+1.0);
    }
    
    /**
     * Returns the bionomial coefficient (n k) as a floating-point number.
     * 
     * @param n
     * @param k
     * @return binomial coefficient
     */
    public static double binomialCoefficient(int n, int k) {
        return Math.floor(0.5 + Math.exp(factln(n) - factln(k) - factln(n-k)));
    }
    
    /**
     * Returns the beta function evaluated at (z,w).
     * 
     * @param z
     * @param w
     * @return B(z,w)
     */
    public static double beta(double z, double w) {
        return Math.exp(gammln(z) + gammln(w) - gammln(z + w));
    }
    
    /** 
     * Returns the incomplete gamma function P(a, x).
     * 
     * @param a
     * @param x
     * @return P(a, x)
     */
    public static double gammp(double a, double x) {
    // Returns the incomplete gamma function P(a; x).
    // void gcf(double gammcf, double a, double x, double gln);
    // void gser(double gamser, double a, double x, double gln);
    // void nrerror(char error_text[]);
    // double gamser,gammcf,gln;
     if (x < 0.0 && a <= 0.0) nrerror("Invalid arguments in routine gammp");
        if (x < (a+1.0)) {          // Use the series representation.
            gser(a,x);
            return gamser;
        } else {                   // Use the continued fraction representation
            gcf(a,x);   
            return 1.0-gammcf;       // and take its complement.
        }
    }

    /**
     * Returns the incomplete gamm function Q(a, x) = 1 - P(a, x)
     * 
     * @param a
     * @param x
     * @return Q(a, x)
     */
    public static double gammq(double a, double x) {
      // Returns the incomplete gamma function Q(a; x)=1 - P(a; x).
      // void gcf(double gammcf, double a, double x, double gln);
      // void gser(double gamser, double a, double x, double gln);
      // void nrerror(char error_text[]);
      // double gamser,gammcf,gln;
      if (x < 0.0 && a <= 0.0) nrerror("Invalid arguments in routine gammq");
      if (x < (a+1.0)) {           // Use the series representation
        gser(a,x);
        return 1.0-gamser;         // and take its complement.
      } else {                     // Use the continued fraction representation.
        gcf(a,x);
        return gammcf;
      }
    }

    /**
     * Computes the incomplete gamma function P(a, x) evaluated by its series 
     * representation and stores in global variable gamser.
     * Also computes ln _(a) and stores in global variable gln.
     * 
     * @param a
     * @param x
     */
    public static void gser(double a, double x) {
        int n;
        double sum,del,ap;
        gln=gammln(a);
        if (x <= 0.0) {
            if (x < 0.0) nrerror("x less than 0 in routine gser");
            gamser=0.0;
            return;
        } else {
            ap=a;
            del=sum=1.0/a;
            for (n=1;n<=ITMAX;n++) {
                ++ap;
                del *= x/ap;
                sum += del;
                if (Math.abs(del) < Math.abs(sum)*EPS) {
                    gamser=sum*Math.exp(-x+a*Math.log(x)-(gln));
                    return;
                }
            }
            nrerror("a too large, ITMAX too small in routine gser");
            return;
        }
    }


    /**
     * Computes the incomplete gamma function Q(a; x) evaluated by its continued 
     * fraction representation as gammcf. Also computes ln _(a) as gln.
     * 
     * @param a
     * @param x
     */
    public static void gcf(double a, double x) {
    // double gammln(double xx);
     // void nrerror(char error_text[]);
        int i;
        double an,b,c,d,del,h;
        gln=gammln(a);
        b=x+1.0-a; 
        // Set up for evaluating continued fraction 
        // by modified Lentz's method ( x 5.2) with b0 = 0.
        c=1.0/FPMIN;
        d=1.0/b;
        h=d;
        for (i=1;i<=ITMAX;i++) {
            // Iterate to convergence.
            an = -i*(i-a);
            b += 2.0;
            d=an*d+b;
            if (Math.abs(d) < FPMIN) d=FPMIN;      
            c=b+an/c;
            if (Math.abs(c) < FPMIN) c=FPMIN;
            d=1.0/d;
            del=d*c;
            h *= del;
            if (Math.abs(del-1.0) < EPS) break;
        }
        // if (i > ITMAX) nrerror("a too large, ITMAX too small in gcf");
        gammcf=Math.exp(-x+a*Math.log(x)-(gln))*h;           //Put factors in front.
    }

    /**
     * Returns the error function erf(x).
     * 
     * @param x
     * @return erf(x)
     */
    public static double erff(double x) {
    // double gammp(double a, double x);
        return x < 0.0 ? -gammp(0.5,x*x) : gammp(0.5,x*x);
    }

    /**
     * Returns the complementary error function erfc(x).
     * 
     * @param x
     * @return erffc(x)
     */
    public static double erffc(double x) {
    // double gammp(double a, double x);
    // double gammq(double a, double x);
        return x < 0.0 ? 1.0+gammp(0.5,x*x) : gammq(0.5,x*x);
    }

    /**
     * Returns the incomplete beta function Ix(a, b).
     * @param a
     * @param b
     * @param x
     * @return incomplete beta function value
     */
    public static double betai(double a, double b, double x) {
        // double betacf(double a, double b, double x);
        // double gammln(double xx);
        // void nrerror(char error_text[]);
        double bt;
        if (x < 0.0 || x > 1.0) nrerror("Bad x in routine betai");
        if (x == 0.0 || x == 1.0) bt=0.0;
        else                              // Factors in front of the continued fraction.
            bt=Math.exp(gammln(a+b)-gammln(a)-gammln(b)+a*Math.log(x)+b*Math.log(1.0-x));
        if (x < (a+1.0)/(a+b+2.0))        // Use continued fraction directly.
            return bt*betacf(a,b,x)/a;
        else                              // Use continued fraction after making the symmetry transformation. 
            return 1.0-bt*betacf(b,a,1.0-x)/b;
    }

    /**
     * Used by betai: Evaluates continued fraction for incomplete 
     * beta function by modified Lentz's method.
     * @param a
     * @param b
     * @param x
     * @return betacf
     */
    public static double betacf(double a, double b, double x) {
    // void nrerror(char error_text[]);
        int m,m2;
        double aa,c,d,del,h,qab,qam,qap;
        // These q's will be used in factors that occur in the coefficients (6.4.6). qap=a+1.0;
        qab=a+b;
        qap=a+1.0;
        qam=a-1.0;
        c=1.0;                   // First step of Lentz's method.
        d=1.0-qab*x/qap;
        if (Math.abs(d) < FPMIN) d=FPMIN;
        d=1.0/d;
        h=d;
        for (m=1;m<=ITMAX;m++) {
            m2=2*m;
            aa=m*(b-m)*x/((qam+m2)*(a+m2));
            d=1.0+aa*d;            // One step (the even one) of the recurrence.
            if (Math.abs(d) < FPMIN) d=FPMIN;
            c=1.0+aa/c;
            if (Math.abs(c) < FPMIN) c=FPMIN;
             d=1.0/d;
            h *= d*c;
            aa = -(a+m)*(qab+m)*x/((a+m2)*(qap+m2));
            d=1.0+aa*d;            // Next step of the recurrence (the odd one).
            if (Math.abs(d) < FPMIN) d=FPMIN;
            c=1.0+aa/c;
            if (Math.abs(c) < FPMIN) c=FPMIN;
            d=1.0/d;
            del=d*c;
            h *= del;
            if (Math.abs(del-1.0) < EPS) break;    // Are we done?
        }
        if (m > ITMAX) nrerror("a or b too big, or ITMAX too small in betacf");
        return h;
    }
  
    
    // -------------- CUMULATIVE PROBABILITY DENSITY ------------------
    
    // computes the cumulative probability for the Student's t distribution
    // at the given t value and degree of freedom v1
    public static double cumT(double t, double v1) {
        if (t < 0) // negative
            return betai(v1/2,0.5,v1/(v1+t*t))/2;
        else
            return 1-betai(v1/2,0.5,v1/(v1+t*t))/2;
    }

    // computes the cumulative probability for the F distribution 
    // at the given F value and degrees of freedom v1 and v2
    public static double cumF(double F, double v1, double v2) {
        //return betai(v2/2,v1/2,v2/(v2+v1*F));
        return 1-betai(v2/2,v1/2,v2/(v2+v1*F));
    }

    // computes the cumulative probability for the normal distribution
    // at the given z value
    public static double cumNorm(double z) { 
        return (erff(Math.sqrt(2)*z/2)+1)/2; 
    }

    // computes the cumulative probability for the Chi Square distribution
    // at the given value chi2 and degree of freedom v1
    public static double cumChiSquare(double chi2, double v1) { 
        return gammp(v1/2,chi2/2); 
    }    

    /**
     * Returns the cumulative binomial probability given n, k, and p,
     * the probability of less than k successes occurring in n trials
     * if a success occurs with probability p per trial
     * 
     * @param n the number of trials
     * @param k the number of successes 
     * @param p the probability of success
     * @return P(n, k, p)
     */
    public static double cumBinomial(int n, int k, double p) {
        return 1 - betai(k, n - k + 1, p);
    }
    

    /**
     *  Returns the magnitude of a times the sign of b
     * @param a
     * @param b
     * @return the magnitude of a times the sign of b
     */
    public static double sign(double a, double b) {
        return b >= 0? Math.abs(a) : -Math.abs(a);
    }
}
