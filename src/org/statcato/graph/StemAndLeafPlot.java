/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.graph;

import java.util.Vector;
import java.util.Collections;

/**
 * A stem-and-leaf plot.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class StemAndLeafPlot {
    private Vector<Double> Stems;
    private Vector<Vector<Integer>> Leaves;
    /**
     * Debug flag.
     */
    public static boolean DEBUG = false;
    private double leafUnits;
    /**
     * A boolean variable indicating whether stems with no leaves should
     * be displayed.
     */
    public boolean displayEmptyStem = true; 
        
    
    /**
     * Constructor.  The data values and the leaf unit are given.
     * 
     * @param values vector of double values
     * @param u leaf unit (10^x where x is an integer, -1 if the leaf unit is
     * not pre-determined)
     */
    public StemAndLeafPlot(Vector<Double> values, double u) {
        Stems = new Vector<Double>();
        Leaves = new Vector<Vector<Integer>>();
                
        if (u <= 0 || Math.log10(u) - Math.floor(Math.log10(u)) != 0) {
            // invalid leaf unit -> compute leaf unit from input data values
            Vector<Double> LeafUnits = new Vector<Double>();
            for (int i = 0; i < values.size(); ++i) {
                double value = values.elementAt(i).doubleValue();
                double leafUnit = computeLeafUnit(value);
                printDebug("value = " + value + " leafunit = " + leafUnit);
                LeafUnits.addElement(new Double(leafUnit));
            }
            leafUnits = computeCommonLeafUnit(LeafUnits);
        }
        else
            leafUnits = u;
        
        for (int i = 0; i < values.size(); ++i) {
            double value = values.elementAt(i).doubleValue() + leafUnits / 2;
            Integer leaf = new Integer(getLeaf(value, leafUnits));            
            Double stem = new Double(getStem(value, leafUnits));
            printDebug("stem = " + stem + ", leaf = " + leaf);
            if (Stems.contains(new Double(stem))) { // stem already exists
                // add leaf to the vector corresponding to the existing stem
                int loc = Stems.indexOf(stem);
                Leaves.elementAt(loc).addElement(leaf);
            }
            else {  // new stem
                Stems.addElement(stem);
                Collections.sort(Stems);
                int loc = Stems.indexOf(stem);
                Leaves.add(loc, new Vector<Integer>());
                Leaves.elementAt(loc).addElement(leaf);
            }
        }
        
        for (int i = 0; i < Stems.size(); ++i) {
            printDebug("Stem: " + Stems.elementAt(i));
            for (int j = 0; j < Leaves.elementAt(i).size(); ++j) {
                printDebug("leaf " + Leaves.elementAt(i).elementAt(j));
            }
        }
    }
    
    /**
     * Returns an html formatted string of a table containing the stems 
     * and leaves.
     * 
     * @return string of html code
     */
    @Override
    public String toString() {
        if (Stems.size() == 0)
            return "";
        
        String s = "";
        s += "<table border='1'>";
        s += "<tr><td align='right'>Stem</td><td>Leaves</td></tr>";
        double lastStem = Stems.elementAt(0).doubleValue();
        s += rowToString(lastStem, Leaves.elementAt(0));
        
        double thisStem;
        double stemUnits = getStemUnits();
        for (int i = 1; i < Stems.size(); ++i) {
            thisStem = Stems.elementAt(i).doubleValue();
            for (double r = lastStem +  stemUnits; r < thisStem - stemUnits / 2; 
            r = r + stemUnits) {
                s += rowToString(r, null);
            }
            s += rowToString(thisStem, Leaves.elementAt(i));
            lastStem = thisStem;
        }
        s += "</table>";
        return s;
    }
    
    /**
     * Convert the given stem and leaf to a string formatting an html table row.
     * @param stem stem double value
     * @param Leaf vector of leaves (integer values)
     * @return html-formatted string
     */
    private String rowToString(double stem, Vector<Integer> Leaf) {
        String s = "";
        if (displayEmptyStem || Leaf != null) {        
            s = "<tr>";
            if (leafUnits >= 0.1)
                s += "<td align='right'>" +
                        String.format("%d", (int)stem) + "</td>";
            else {
                int places = -(int)Math.log10(leafUnits) - 1;
                s += "<td align='right'>" +
                        String.format("%." + places + "f", stem) + "</td>";
            }
            s += "<td>";
            if (Leaf == null) {
                    s += "&nbsp;";
            }
            else {
                Collections.sort(Leaf);
                for (int j = 0; j < Leaf.size(); ++j) {
                    s += Leaf.elementAt(j);
                }
            }
            s += "</td>";
            s += "</tr>";
        }
        return s;
    }
    
    /**
     * Returns the leaf unit used in this stem-and-leaf plot.
     * 
     * @return leaf unit (double)
     */
    public double getLeafUnits() {
        return leafUnits;
    }
    
    private double getStemUnits() {
        if (leafUnits >= 0.1)
            return 1;
        else
            return leafUnits * 10;
    }
    
    /**
     * Returns the stem for the given data value given the leaf unit.
     * 
     * @param value double value 
     * @param place leaf unit (double)
     * @return stem (double)
     */
    public static double getStem(double value, double place) {
        String s = String.format("%f", value);
        int decimalLoc = s.indexOf('.');    // index of decimal point
        int leafLoc = getLeafIndex(value, place);   // index of leaf 
        if (leafLoc == -1)  
            return 0;
        if (decimalLoc == -1) { // no decimal point
            return Integer.parseInt(s.substring(0, leafLoc));
        }
        else {
            printDebug("value = " + value + " place = " + place);
            printDebug("decimal loc " + decimalLoc);
            if (leafLoc == 0)   // no stem
                return 0;
            // return what is to the left of leaf
            return Double.parseDouble(s.substring(0, leafLoc));
        }
    }
    
    /**
     * Returns the index of the starting location of the leaf in given data value.
     * 
     * @param value double value
     * @param place leaf unit
     * @return integer index
     */
    public static int getLeafIndex(double value, double place) {
        String s = String.format("%f", value);
        int decimalLoc = s.indexOf('.');
        int offset = (int)Math.log10(place);
        int loc = 0;
        if (decimalLoc == -1) { // no decimal point
            if (offset < 0) {
                return -1;  // invalid place unit for value
            }
            loc = s.length() - offset - 1;
        }
        else {
            loc = decimalLoc - offset;

            if (offset >= 0)
                loc--;
        }
        if (loc < 0 || loc >= s.length())
            return -1;
        return loc;
    }
    
    /**
     * Returns the leaf of the given double value at the given leaf unit.
     * 
     * @param value double value
     * @param place leaf unit
     * @return leaf (integer [0,9])
     */
    public static int getLeaf(double value, double place) {
        int loc = getLeafIndex(value, place);
        String s = String.format("%f", value);
        if (loc == -1)
            return 0;
        return Character.getNumericValue(s.charAt(loc));
    }
    
    /**
     * Computes and returns the proper leaf unit for the given number.
     * The leaf unit for a number is the place to the right of the leftmost
     * non-zero digit.
     * 
     * @param x a double value
     * @return the proper leaf unit for x
     */
    public static double computeLeafUnit(double x) {
        double i;
        x = Math.abs(x);
        if (x < 1) {
            i = 10;
            while (x * i < 1) {
                i *= 10;
            }
            i = 1 / i / 10;
        }
        else {
            i = 10;
            while (x / i >= 1) {
                i *= 10;
            }
            i /= 100;
        }
        return i;
    }
    
    /**
     * Returns the common leaf unit that should be used for the given set of leaf units.
     * The maximum of the given leaf units is the common leaf unit.
     * 
     * @param units vector of leaf units (double values)
     * @return common leaf unit that should be used for the given set of leaf units 
     */
    public static double computeCommonLeafUnit(Vector<Double> units) {
        double max = Collections.max(units);
        return max;
    }
    
    /**
     * Prints the given debug message to the standard output.
     * 
     * @param msg string
     */
    public static void printDebug(String msg) {
        if (DEBUG)
            System.out.println(msg);
    }
}
