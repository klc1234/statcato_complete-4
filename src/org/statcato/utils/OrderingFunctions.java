package org.statcato.utils;

import org.statcato.spreadsheet.*;
import java.util.*;

/**
 * Functions for ordering column cells.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class OrderingFunctions {

    /**
     * Given a vector of Cells and the sort order, return a vector of Integers
     * that are the ranks of the Cells. 
     * For ascending order, the smallest number has rank 0, 
     * and cells with higher order have increasing ranks.
     * For descending order, the largest number has rank 0, 
     * and cells with lower order have increasing ranks.  
     * Empty cells have no rank.
     * 
     * @param vec vector of Cell to be ranked
     * @param ascending sort order
     * @return a vector of ranks
     */
    @SuppressWarnings("unchecked") 
    public static Vector<Integer> rankColumnVector(Vector<Cell> vec,
            boolean ascending) {
        Vector<Cell> Cells = (Vector<Cell>) vec.clone();
        
        // sort the vector column cells
        if (ascending) {
            Collections.sort(Cells, new CellComparator());
        }
        else {
            Collections.sort(Cells, new CellComparatorDescending());
        }
        
        // compute ranks based on sorted order
        Vector<Integer> rankVector = new Vector<Integer>(Cells.size());
        for (int i = 0; i < Cells.size(); ++i) {
            rankVector.addElement(null);
        }
        
        // construct ranks in the order of the sorted column cells
        for (int i = 0; i < Cells.size(); ++i) {
            Cell cell = Cells.elementAt(i);
            rankVector.setElementAt(new Integer(i+1), cell.getRow()-1);
        }        

        return rankVector;
    }
    
    /**
     * Sorts a vector of Cell based on another vector of Cell in the given order.
     * 
     * @param vec vector to be sorted
     * @param byVec vector to sort by
     * @param ascending sort order
     * @return a sorted vector of Cell
     */
    @SuppressWarnings("unchecked") 
    public static Vector<Cell> sortColumnVector(Vector<Cell> vec, 
            Vector<Cell> byVec, boolean ascending) {
        if (byVec == null) {
            Vector<Cell> copy = (Vector<Cell>)vec.clone();
            if (ascending)
                Collections.sort(copy, new CellComparator());
            else
                Collections.sort(copy, new CellComparatorDescending());
            return copy;
        }
        
        // sort by byVec
        Vector<Cell> Cells = new Vector<Cell>(); // a vector empty cells
        for (int i = 0; i < vec.size(); ++i) {
            Cells.addElement(new Cell("", i, 0));
        }
        Vector<Integer> ranks = rankColumnVector(byVec, ascending); // ranks of byVec

        for (int i = 0; i < vec.size(); ++i) {
            Integer rank = ranks.elementAt(i);
            if (rank != null) {
                Cells.setElementAt(vec.elementAt(i), rank);
            }            
        }
        
        return Cells;
    }
    
    /**
     * Sorts a set of vectors in place, from the last vector in the set to the 
     * first.  
     * 
     * @param vecs vector of vectors of cells
     * @param orders vector of boolean values indicating the sort orders.
     * Must have the same length as <code>vec</code>.
     * @return vector of vectors of cells that are sorted
     */
    @SuppressWarnings("unchecked") 
    public static Vector<Vector<Cell>> sortVectors(Vector<Vector<Cell>> vecs, 
            Vector<Boolean> orders) {
        Vector<Vector<Cell>> vectors = (Vector<Vector<Cell>>)vecs.clone();
        for (int col = vectors.size()-1; col > 0; --col) {
            // for each vector
            // find the ranks
            Vector<Integer> ranks = rankColumnVector(
                    (Vector<Cell>)vectors.elementAt(col),
                    ((Boolean)orders.elementAt(col)).booleanValue());
                    HelperFunctions.printVector(ranks);
            int i, j;
            
            for (i = 0; i  < col; ++i) {
                Vector<Cell> vector = (Vector<Cell>) vectors.elementAt(i);
                HelperFunctions.printVector(vector);
                Vector<Cell> vectorp = new Vector<Cell>();
                for (j = 0; j < vector.size(); ++j) {
                    vectorp.addElement(new Cell("", j+1, 0));
                }
                for (j = 0; j < vector.size(); ++j) {
                    Integer rank = ranks.elementAt(j);
                    if (rank != null) {
                        vectorp.setElementAt(
                                new Cell(vector.elementAt(j).toString(), rank, 0), 
                                rank-1);
                    }
                }
                HelperFunctions.printVector(vectorp);
                vectors.setElementAt(vectorp, i);
            }
        }
        
        return vectors;
    }

    /**
     * Returns the ranks of the given DataValue objects.
     * Ranks are based on absolute values, and signs of data values are
     * attached to their corresponding ranks.
     * Mean ranks are assigned to tied data values.
     *
     * @param data vector of DataValue objects
     * @return array of ranks corresponding to the given data values (in the
     * same order)
     */
    @SuppressWarnings("unchecked") 
    public static double[] rank(Vector<DataValue> data) {
        // sort data
        Vector<DataValue> sorted = (Vector<DataValue>) data.clone();
        Collections.sort(sorted, new DataValueComparator());

        // compute ranks
        int size = sorted.size();
        double[] ranks = new double[size];
        double rank = 1;
        int i = 0, start, end, sum;
        while (i < size) {
            start = i;
            end = i + 1;
            // loop over repeated numbers
            while (end < size &&
                    (sorted.elementAt(start).getAbsoluteValue() ==
                    sorted.elementAt(end).getAbsoluteValue())) {
                end++;
            }

            // start = index of first repeated value, end - 1 = index of last repeated value
            sum = 0;
            for (int j = start; j < end; ++j) {
                sum += j + 1;
            }
            rank = (double)sum / (double)(end - start);
            for (int j = start; j < end; ++j) {
                ranks[j] = sorted.get(j).getSign() * rank; // replace sign
            }

            i = end;
        }
        return ranks;
    }

    /**
     * Returns the ranks of the given DataValue objects.
     * Ranks are based on absolute values, and signs of data values are
     * attached to their corresponding ranks.
     * Mean ranks are assigned to tied data values.
     *
     * @param data vector of DataValue objects
     * @return array of ranks corresponding to the given data values (in the
     * same order)
     */
    @SuppressWarnings("unchecked") 
    public static double[] rankDoubleVector(Vector<Double> data) {
        // sort data
        Vector<Double> sorted = (Vector<Double>) data.clone();
        Collections.sort(sorted);

        // compute ranks for sorted values
        int size = sorted.size();
        double[] ranks = new double[size];
        double rank = 1;
        int i = 0, start, end, sum;
        while (i < size) {
            start = i;
            end = i + 1;
            // loop over repeated numbers
            while (end < size &&
                    (sorted.elementAt(start).doubleValue() ==
                    sorted.elementAt(end).doubleValue())) {
                end++;
            }

            // start = index of first repeated value, end - 1 = index of last repeated value
            sum = 0;
            for (int j = start; j < end; ++j) {
                sum += j + 1;
            }
            rank = (double)sum / (double)(end - start);
            for (int j = start; j < end; ++j) {
                ranks[j] = rank; 
            }
            i = end;
        }

        // compute ranks for original values
        double[] ranks2 = new double[size];
        for (i = 0; i < data.size(); ++i) {
            int index = sorted.indexOf(data.elementAt(i));
            ranks2[i] = ranks[index];
        }

        return ranks2;
    }  
}
