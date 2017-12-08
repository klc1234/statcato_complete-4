/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.random;

import java.util.Vector;
import java.util.Random;
import org.statcato.spreadsheet.Cell;

/**
 * A random sampler for a given vector, with
 * or without replacement.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class SampleFromVector extends Random {
    Vector<Cell> vector;
    boolean replace;

    @SuppressWarnings("unchecked") 
    public SampleFromVector(Vector<Cell> vector, boolean replace) {
        this.vector = (Vector<Cell>) vector.clone();
        this.replace = replace;
    }
    
    public Cell nextSample() {
        int n = vector.size();
        int randInt = nextInt(n);  // 0...n-1
        Cell cell = (Cell) vector.elementAt(randInt);
        if (!replace) {
            vector.removeElementAt(randInt);
        }
        return cell;
    }
}
