package org.statcato.statistics;

import java.util.*;

/**
 * A collection of basic statistical functions.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class BasicStatistics {

    /**
     * Standardizes a vector of numbers of subtracting one number and then 
     * dividing by another number for each number in the vector.
     * 
     * @param numbers   vector of doubles
     * @param num1      double value to subtract from each number in the vector
     * @param num2      double value by which each number in the vector is divided.
     * Assumed to be non-zero.
     * @return a vector of standardized double values
     */
    public static Vector<Double> customStandardize(Vector<Double> numbers,
            double num1, double num2) {
        Vector<Double> vec = new Vector<Double>(0);
        double num; 
        for (int i = 0; i < numbers.size(); ++i) {
            if (numbers.elementAt(i) != null) {
                num = ((Double)numbers.elementAt(i)).doubleValue();
                vec.addElement((num - num1) / num2);
            }
            else
                vec.addElement(null);
        }
        return vec;
    } 

    /**
     * Standardizes a vector of numbers by subtracting the mean and then
     * dividing by the standard deviation for each number in the vector.
     * 
     * @param numbers a vector of double values
     * @return vector of standardized double values
     */
    public static Vector<Double> standardize(Vector<Double> numbers) {
        Vector<Double> vec = new Vector<Double>(0);
        if (numbers.size() < 2)
            return null;
        double num;
        double mean = mean(numbers);
        double stdev = stdev(numbers).doubleValue();
        if (stdev == 0)
            return null;
        
        for (int i = 0; i < numbers.size(); ++i) {
            if (numbers.elementAt(i) != null) {
                num = ((Double)numbers.elementAt(i)).doubleValue();
                vec.addElement((num - mean) / stdev);
            }
            else
                vec.addElement(null);
        }
        return vec;
    }    
    
    /**
     * Adjusts a vector of numbers to fit in a specific range [a, b].
     * A number n is adjusted as a + (n - min)(b - a) / (max - min).
     * 
     * @param numbers   a vector of double values
     * @param a         minimum value of the range
     * @param b         maximum value of the range.  Assumed greater than a.
     * @return vector of double values with a range [a, b]
     */
    public static Vector<Double> changeRange(Vector<Double> numbers,
            double a, double b) {
        double min = min(numbers);
        double max = max(numbers);
        Vector<Double> vec = new Vector<Double>(0);
        double num; 

        if (stdev(numbers).doubleValue() == 0)
            return null;
        
        for (int i = 0; i < numbers.size(); ++i) {
            if (numbers.elementAt(i) != null) {
                num = ((Double)numbers.elementAt(i)).doubleValue();
                vec.addElement(a + (num - min)*(b - a)/(max - min));
            }
            else
                vec.addElement(null);
        }
        return vec;        
    }
    
    /**
     * Returns a copy of the given vector of doubles in which
     * all the null elements are removed.
     * 
     * @param numbers vector of doubles
     * @return vector of doubles with no null elements
     */
    private static Vector<Double> removeNullValues(Vector<Double> numbers) {
        Vector<Double> vec = new Vector<Double>(0);
        for (int i = 0; i < numbers.size(); ++i) {
            if (numbers.elementAt(i) != null)
                vec.addElement(numbers.elementAt(i));
        }            
        return vec;
    }

    /**
     * Returns the number of nonmissing numbers in the input vector.
     * 
     * @param numbers vector of doubles
     * @return number of nonmissing numbers
     */
    public static int Nnonmissing(Vector<Double> numbers) {
        int num = 0;
        for (Enumeration e = numbers.elements(); e.hasMoreElements();) {
            if (e.nextElement() != null)
                num++;
        }    
        return num;        
    }
    
    /**
     * Returns the number of missing numbers in the input vector.
     * 
     * @param numbers vector of doubles
     * @return number of missing numbers
     */
    public static int Nmissing(Vector<Double> numbers) {
        return Ntotal(numbers) - Nnonmissing(numbers);
    }
    
    /**
     * Returns the total number of numbers in the input vector.
     * Stops counting after the last nonmissing number in the vector.
     * 
     * @param numbers   vector of doubles
     * @return  total number of numbers in the vector
     */
    public static int Ntotal(Vector<Double> numbers) {
        int lastNonMissingIndex = 0;
        for (int i = 0; i < numbers.size(); ++i) {
            if (numbers.elementAt(i) != null)
                lastNonMissingIndex = i;
        }    
        return lastNonMissingIndex + 1;         
    }
    
    /**
     * Returns the mean of a vector of numbers.
     * 
     * @param numbers vector of doubles
     * @return mean of the numbers, or null if the vector is empty
     */
    public static Double mean(Vector<Double> numbers) {
        int nnonmissing = Nnonmissing(numbers);
        if (nnonmissing == 0)
            return null;
        return sum(numbers) / Nnonmissing(numbers);
    }
    
    /**
     * Returns the sum of a vector of numbers.
     * 
     * @param numbers vector of doubles
     * @return sum 
     */
    public static double sum(Vector<Double> numbers) {
        double sum = 0;
        for (Enumeration e = numbers.elements(); e.hasMoreElements();) {
            Double next = (Double) e.nextElement();
            if (next != null)
                sum += next.doubleValue();
        }    
        return sum;
    }
    
    /**
     * Returns the standard deviation of a vector of numbers.
     * 
     * @param numbers vector of doubles
     * @return standard deviation, or null if the vector has less than two 
     * numbers
     */
    public static Double stdev(Vector<Double> numbers) {        
        if (Nnonmissing(numbers) < 2)
            return null;
        return Math.sqrt(variance(numbers));
    }
    
    /**
     * Returns the variance of a vector of numbers.
     * 
     * @param numbers
     * @return variance, or null if the vector has less than two numbers
     */
    public static Double variance(Vector<Double> numbers) {   
        if (Nnonmissing(numbers) < 2)
            return null;
        return moment(numbers, 2) / (Nnonmissing(numbers) - 1);
    }    

    /**
     * Returns the kth moment about the mean = sum[(x - mean)^k].
     * 
     * @param numbers vector of doubles
     * @param k order
     * @return kth moment
     */
    public static Double moment(Vector<Double> numbers, int k) {
        double mean = mean(numbers);
        double sumOfDiff = 0;
        for (Enumeration e = numbers.elements(); e.hasMoreElements();) {
            Double next = (Double) e.nextElement();
            if (next != null)
                sumOfDiff += Math.pow(next.doubleValue() - mean, k);            
        }         
        return sumOfDiff;        
    }
    
    /**
     * Returns the minimum of a vector of numbers.
     * 
     * @param numbers vector of doubles
     * @return minimum value, or null if the vector is empty
     */
    public static Double min(Vector<Double> numbers) {
        if (numbers.size() == 0)
            return null;
        double min = Double.POSITIVE_INFINITY;
        for (Enumeration e = numbers.elements(); e.hasMoreElements();) {
            Double Next = (Double) e.nextElement();
            if (Next != null) {
                double next = Next.doubleValue();
                if (next < min)
                    min = next;
            }
        }
        if (min == Double.POSITIVE_INFINITY)
            return null;
        return min;
    }
    
    /**
     * Returns the minimum of an ArrayList of numbers.
     * 
     * @param numbers ArrayList of doubles
     * @return minimum value, or null if numbers is empty
     */
    public static Double min(ArrayList<Double> numbers) {
        if (numbers.isEmpty())
            return null;
        double min = Double.POSITIVE_INFINITY;
        for (Iterator e = numbers.iterator(); e.hasNext();) {
            Double Next = (Double) e.next();
            if (Next != null) {
                double next = Next.doubleValue();
                if (next < min)
                    min = next;
            }
        }
        if (min == Double.POSITIVE_INFINITY)
            return null;
        return min;
    }
    
    /**
     * Returns the minimum of an array of integers (assume array length > 0).
     * 
     * @param numbers array of integers, assumed non-empty
     * @return minimum
     */
    public static int min(int[] numbers) {
        int min = numbers[0];
        for (int i = 1; i < numbers.length; ++i)
            if (numbers[i] < min)
                min = numbers[i];
        return min;
    }
    
    /**
     * Returns the maximum of a vector of doubles
     * 
     * @param numbers vector of doubles
     * @return maximum value, or null if the vector is empty
     */
    public static Double max(Vector<Double> numbers) {
        if (numbers.size() == 0)
            return null;
        double max = Double.NEGATIVE_INFINITY;
        for (Enumeration e = numbers.elements(); e.hasMoreElements();) {
            Double Next = (Double) e.nextElement();
            if (Next != null) {
                double next = Next.doubleValue();
                if (next > max)
                    max = next;
            }
        }
        if (max == Double.NEGATIVE_INFINITY)
            return null;
        return max;        
    }

    /**
     * Returns the maximum of an ArrayList of numbers.
     * 
     * @param numbers ArrayList of doubles
     * @return maximum value, or null if numbers is empty
     */
    public static Double max(ArrayList<Double> numbers) {
        if (numbers.isEmpty())
            return null;
        double max = Double.NEGATIVE_INFINITY;
        for (Iterator e = numbers.iterator(); e.hasNext();) {
            Double Next = (Double) e.next();
            if (Next != null) {
                double next = Next.doubleValue();
                if (next > max)
                    max = next;
            }
        }
        if (max == Double.NEGATIVE_INFINITY)
            return null;
        return max;
    }
    
    /**
     * Returns the maximum of an array of integers (assume array length > 0).
     * 
     * @param numbers array of integers, assumed non-empty
     * @return maximum
     */
    public static int max(int[] numbers) {
        int max = numbers[0];
        for (int i = 1; i < numbers.length; ++i)
            if (numbers[i] > max)
                max = numbers[i];
        return max;
    }
    
    /**
     * Returns the range of a vector of numbers.
     * 
     * @param numbers vector of doubles
     * @return range, or null if the vector is empty
     */
    public static Double range(Vector<Double> numbers) {
        if (numbers.size() == 0)
            return null;
        Double max = max(numbers);
        Double min = min(numbers);
        if (max == null || min == null)
            return null;
        return max(numbers) - min(numbers);
    }
    
    /**
     * Returns the median of a vector of numbers.
     * 
     * @param numbers vector of doubles
     * @return median, or null if the vector is empty
     */
    public static Double median(Vector<Double> numbers) {
        Vector<Double> vec = removeNullValues(numbers);
        if (vec.size() == 0)
            return null;
        Collections.sort(vec);

        if (vec.size() % 2 == 0) {   // even length
            int middle = vec.size() / 2;
            return (Double)(vec.elementAt(middle).doubleValue() 
                    + (Double)vec.elementAt(middle - 1).doubleValue())/2;
        }
        else { // odd length
            return (Double) vec.elementAt((vec.size() - 1)/2).doubleValue();
        }        
    } 
    
    /**
     * Returns the sum of squares of a vector of numbers.
     * 
     * @param numbers vector of number
     * @return sum of squares
     */
    public static double sumOfSquares(Vector<Double> numbers) {
        double sum = 0;
        for (Enumeration e = numbers.elements(); e.hasMoreElements();) {
            Double next = (Double) e.nextElement();
            if (next != null)
                sum += Math.pow(next.doubleValue(),2);
        }    
        return sum;
    }
    
    /**
     * Return the standard error of the mean (s/sqrt(n)) of a vector of numbers.
     * 
     * @param numbers vector of doubles
     * @return standard error of the mean, or null if the vector has less than 
     * two numbers
     */
    public static Double SEmean(Vector<Double> numbers) {
        if (Nnonmissing(numbers) < 2)
            return null;
        double std = stdev(numbers).doubleValue();
        return std / Math.sqrt(Nnonmissing(numbers));
    }
    
    /**
     * Returns the coefficient of variance (standard deviation / mean * 100).
     * 
     * @param numbers vector of number
     * @return coefficient of variance, or null if the vector has less than two 
     * numbers
     */
    public static Double coefficientOfVariance(Vector<Double> numbers) {
        if (numbers.size() < 2)
            return null;
        Double std = stdev(numbers);
        if (std == null)
            return null;
        double mean = mean(numbers);
        return std.doubleValue() / mean * 100;
    }
    
    /**
     * Returns the first quartile of a vector of numbers as a Double object.
     * Returns null if the first quartile is undefined
     * @param numbers vector of numbers
     * @return first quartile, or null if it is undefined
     */
    public static Double firstQuartile(Vector<Double> numbers) {
        Vector<Double> vec = removeNullValues(numbers);
        Collections.sort(vec);
        int N = vec.size();

        if (N == 0) // empty list
            return null;

        double w = ((double) N + 1) / 4; // w = (n + 1)/4
        double y = Math.floor(w);   // y = the truncated integer value of w
        double z = w - y;           // z = the fraction component of w that was truncated away

        if (z == 0) // no fractional component
            return ((Double)vec.elementAt((int)y - 1)).doubleValue();
        if (y < 1 || y > N - 1)
            return null;
        // interpolate using fractional component
        double num1 = ((Double)vec.elementAt((int)y-1)).doubleValue();
        double num2 = ((Double)vec.elementAt((int)y)).doubleValue();
        return  new Double(num1 + z*(num2 - num1)); // Q1 = x_y + z*(x_y+1 - x_y)
    }
    
    /**
     * Returns the third quartile of a vector of numbers as a Double object.
     * Returns null if the third quartile is undefined
     * @param numbers vector of numbers
     * @return third quartile, or null if it is undefined
     */
    public static Double thirdQuartile(Vector<Double> numbers) {
        Vector<Double> vec = removeNullValues(numbers);
        Collections.sort(vec);
        int N = vec.size();

        if (N == 0) // empty list
            return null;

        double w = ((double) N + 1) * 3 / 4; // w = 3(n + 1)/4
        double y = Math.floor(w);   // y = the truncated integer value of w
        double z = w - y;           // z = the fraction component of w that was truncated away

        if (z == 0) // no fractional component
            return ((Double)vec.elementAt((int)y - 1)).doubleValue();
        if (y < 1 || y > N - 1)
            return null;
        // interpolate using fractional component
        double num1 = ((Double)vec.elementAt((int)y-1)).doubleValue();
        double num2 = ((Double)vec.elementAt((int)y)).doubleValue();
        return  new Double(num1 + z*(num2 - num1)); // x_y + z*(x_y+1 - x_y)
    }

    /**
     * Returns the i'th percentile of a vector of numbers as a Double object.
     * Returns null if the i'th percentile is undefined
     * @param numbers vector of numbers
     * @return i'th percentile, or null if it is undefined
     */
    public static Double percentile(Vector<Double> numbers, double i) {
        Vector<Double> vec = removeNullValues(numbers);
        Collections.sort(vec);
        int N = vec.size();

        if (N == 0) // empty list
            return null;

        double w = i * (N + 1) / 100; // w = i(n + 1)/100
        double y = Math.floor(w);   // y = the truncated integer value of w
        double z = w - y;           // z = the fraction component of w that was truncated away
        
        if (y <= 0)
            return ((Double)vec.elementAt(0)).doubleValue();
        if (y >= N)
            return ((Double)vec.elementAt(N - 1)).doubleValue();
        if (z == 0) // no fractional component
            return ((Double)vec.elementAt((int)y - 1)).doubleValue();

        // interpolate using fractional component
        double num1 = ((Double)vec.elementAt((int)y-1)).doubleValue();
        double num2 = ((Double)vec.elementAt((int)y)).doubleValue();
        return  new Double(num1 + z*(num2 - num1)); //  x_y + z*(x_y+1 - x_y)
    }

    /**
     * Returns the interquartile range (third quartile - first quartile).
     * 
     * @param numbers vector of numbers
     * @return interquartile range, or null if it is undefined
     */
    public static Double interquartileRange(Vector<Double> numbers) {
        Double q1 = firstQuartile(numbers);
        Double q3 = thirdQuartile(numbers);
        
        if (q1 == null || q3 == null)
            return null;
        return new Double(q3.doubleValue() - q1.doubleValue());                    
    }
   
    /**
     * Finds the mode of a vector of numbers and returns a vector of the mode(s)
     * along with the number of occurences for the mode(s).
     * 
     * @param numbers vector of double values
     * @return an array of Object, where the first element is 
     * a vector of double values representing the mode(s) and the second
     * element is an Integer representing the number of occurences of the mode(s)
     */
    @SuppressWarnings("unchecked") 
    public static Object[] mode(Vector<Double> numbers) {
        Vector<Double> vec = removeNullValues(numbers);
        
        // create a hash table
        Hashtable hash = new Hashtable();
        int maxCount = 0;
        
        for (Enumeration e = vec.elements(); e.hasMoreElements();) {
            Double num = (Double)e.nextElement();
            if (hash.containsKey(num)) { // num is in hash already
                int count = ((Integer)hash.get(num)).intValue() + 1; // update count
                hash.put(num, new Integer(count));
                if (count > maxCount)
                    maxCount = count;
            }
            else { // put num in hash
                hash.put(num, new Integer(1));
            }
        }
        
        Vector<Double> modes = new Vector<Double>(0);
        
        for (Enumeration e = hash.keys(); e.hasMoreElements();) {
            Double num = (Double)e.nextElement();
            int count = ((Integer)hash.get(num)).intValue();
            if (count == maxCount)
                modes.addElement(num);
        }
        
        Object[] returnValues = new Object[2];
        returnValues[0] = modes;
        returnValues[1] = new Integer(maxCount);
        return returnValues; 
    }

    /**
     * Returns the trimmed mean of a vector doubles.  The trimmed mean
     * is found by removing the smallest and largest given percent of the 
     * values and then computing the mean of the remaining values.
     * 
     * @param numbers vector of double values
     * @param percent percent of smallest and largest values to remove 
     * @return trimmed mean, or null is undefined
     */
    public static Double trimmedMean(Vector<Double> numbers, double percent) {
        Vector<Double> vec = removeNullValues(numbers);
        int n = vec.size();
        int r = (int)Math.round(n * percent / 100); // number of values to trim 
        
        // remove r values from the smallest and largest ends of sorted values
        Collections.sort(vec);
        for (int i = 0; i < r; ++i) {   
            if (vec.size() > 0)
                vec.removeElementAt(0);
            if (vec.size() > 0)
                vec.removeElementAt(vec.size() - 1);
        }
        if (vec.size() > 0)
            return mean(vec);
        else
            return null;
    }
    
    /**
     * Returns the unbiased skewness of a vector of numbers.
     * 
     * @param numbers vector of double values
     * @return  skewness, or null if the vector has less than three elements
     */
    public static Double skewness(Vector<Double> numbers) {
        if (Nnonmissing(numbers) < 3)
            return null;
        int n = Nnonmissing(numbers);
        double stdev = stdev(numbers).doubleValue();
        double moment3 = moment(numbers, 3);
        return moment3 / Math.pow(stdev, 3) * n / (n-1) / (n-2);
    }
    
    /**
     * Returns the biased skewness of a vector of numbers.
     * 
     * @param numbers vector of double values
     * @return  skewness, or null if the vector has less than two elements
     */
    public static Double skewnessBiased(Vector<Double> numbers) {
        if (Nnonmissing(numbers) < 2)
            return null;
        int n = Nnonmissing(numbers);
        double moment2 = moment(numbers, 2);
        double moment3 = moment(numbers, 3);
        return Math.sqrt(n) * moment3 / Math.pow(moment2, 1.5);
    }
    
    /**
     * Returns the unbiased kurtosis of a vector of numbers (centered at 0).
     * 
     * @param numbers vector of double values
     * @return kurtosis, or null if the vector has less than four elements
     */
    public static Double kurtosis(Vector<Double> numbers) {
        if (Nnonmissing(numbers) < 4)
            return null;
        int n = Nnonmissing(numbers);
        double stdev = stdev(numbers).doubleValue();
        double moment4 = moment(numbers, 4);
        return moment4 / Math.pow(stdev, 4) * n * (n+1) / (n-1) / (n-2) / (n-3) 
                - 3 * Math.pow(n-1, 2) / (n-2) / (n-3);
    }
    
    /**
     * Returns the kurtosis of a vector of numbers (centered at 3)
     * 
     * @param numbers vector of double values
     * @return kurtosis, or null if the vector has less than four elements
     */
    public static Double kurtosisCentered3(Vector<Double> numbers) {
        if (Nnonmissing(numbers) < 4)
            return null;
        return kurtosis(numbers) + 3;
    }
    
    /**
     * Returns the biased kurtosis of a vector of numbers (centered at 0).
     * 
     * @param numbers vector of double values
     * @return kurtosis, or null if the vector has less than four elements
     */
    public static Double kurtosisBiased(Vector<Double> numbers) {
        if (Nnonmissing(numbers) < 2)
            return null;
        int n = Nnonmissing(numbers);
        double moment2 = moment(numbers, 2);
        double moment4 = moment(numbers, 4);
        return n * moment4 / Math.pow(moment2, 2) - 3;
    }
    
    /**
     * Returns the biased kurtosis of a vector of numbers (centered at 3).
     * 
     * @param numbers vector of double values
     * @return kurtosis, or null if the vector has less than four elements
     */
    public static Double kurtosisBiasedCentered3(Vector<Double> numbers) {
        if (Nnonmissing(numbers) < 2)
            return null;
        return kurtosisBiased(numbers) + 3;
    }
    
    /**
     * Returns the mean of successive squared differences (MSSD).
     * 
     * @param numbers vector of double values
     * @return MSSD, or null if the vector has less than two numbers
     */
    public static Double MSSD(Vector<Double> numbers) {
        if (Nnonmissing(numbers) < 2) 
            return null;
        
        Vector<Double> vec = removeNullValues(numbers);
        double sum = 0;
        double diff;
        
        for (int i = 1; i < vec.size(); ++i) {
            diff = ((Double)vec.elementAt(i)).doubleValue() - 
                    ((Double)vec.elementAt(i - 1)).doubleValue();
            sum += Math.pow(diff, 2);
        }
        
        return new Double(sum / 2 / (vec.size() - 1));
    }
    
    /**
     * Returns the critical value used in the Ryan-Joiner normality test.
     * 
     * @param n sample size
     * @param significance significance level
     * @return critical value
     */
    public static double RyanJoinerNormalityCV(int n, double significance) {
        if (significance == 0.1) {
            return 1.0071 - 0.1371 / Math.sqrt(n) - 0.3682 / n + 0.7780 / (n * n);
        }
        if (significance == 0.05) {
            return 1.0063 - 0.1288 / Math.sqrt(n) - 0.6118 / n + 1.3505 / (n * n);
        }
        if (significance == 0.01) {
            return 0.9963 - 0.0211 / Math.sqrt(n) - 1.4106 / n + 3.1791 / (n * n);
        }
        return -1;
    }

    /**
     * Removes the outliers from a vector of double values and returns a
     * vector of the outliers.
     * 
     * @param values a vector of double values
     * @return a vector of outliers
     */
    public static Vector<Double> removeOutliers(Vector<Double> values) {
        double iqr = interquartileRange(values);
        double q1 = firstQuartile(values);
        double q3 = thirdQuartile(values);
        double min = q1 - 1.5 * iqr; // minimum normal
        double max = q3 + 1.5 * iqr; // maximum normal
        Vector<Double> outliers = new Vector<Double>();
        for (int i = 0; i < values.size(); ++i) {
            double value = values.elementAt(i).doubleValue();
            if (value < min || value > max)
                outliers.addElement(values.elementAt(i));
        }
        for (int i = 0; i < outliers.size(); ++i) {
            values.remove(outliers.elementAt(i));
        }
        return outliers;
    }

    /**
     * Returns the number of the values in the given vector that are less than
     * the given median.
     *
     * @param values vector of double values
     * @param k median
     * @return number of negative signs
     */
    public static int numberOfNegativeSigns(Vector<Double> values, double k) {
        int n = 0;
        for (int i = 0; i < values.size(); ++i) {
            double value = values.elementAt(i).doubleValue();
            if (value < k)
                n++;
        }
        return n;
    }

    /**
     * Returns the number of the values in the given vector that are greater than
     * the given median.
     *
     * @param values vector of double values
     * @param k median
     * @return number of positive signs
     */
    public static int numberOfPositiveSigns(Vector<Double> values, double k) {
        int n = 0;
        for (int i = 0; i < values.size(); ++i) {
            double value = values.elementAt(i).doubleValue();
            if (value > k)
                n++;
        }
        return n;
    }
}
