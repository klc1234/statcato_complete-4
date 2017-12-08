/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.statcato.graph;

import java.text.MessageFormat;
import java.text.NumberFormat;

import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;

/**
 * An item label generator for plots that use data from a 
 * {@link BoxAndWhiskerCategoryDataset}.
 * <P>
 * The tooltip text and item label text are composed using a 
 * {@link java.text.MessageFormat} object, that can aggregate some or all of 
 * the following string values into a message.
 * <table>
 * <tr><td>0</td><td>Series Name</td></tr>
 * <tr><td>1</td><td>X (value or date)</td></tr>
 * <tr><td>2</td><td>Mean</td></tr>
 * <tr><td>3</td><td>Median</td></tr>
 * <tr><td>4</td><td>Minimum</td></tr>
 * <tr><td>5</td><td>Maximum</td></tr>
 * <tr><td>6</td><td>Quartile 1</td></tr>
 * <tr><td>7</td><td>Quartile 3</td></tr>
 * </table>
 * If any of the values are null, then a blank string is placed in the message.
 */
public class ExtendedBoxAndWhiskerToolTipGenerator extends
		BoxAndWhiskerToolTipGenerator {

	private static final long serialVersionUID = -7274642882075068152L;

	/**
     * Creates a default tool tip generator.
     */
    public ExtendedBoxAndWhiskerToolTipGenerator() {
        super();
    }
    
    /**
     * Creates a tool tip formatter.
     * 
     * @param format  the tool tip format string.
     * @param formatter  the formatter.
     */
    public ExtendedBoxAndWhiskerToolTipGenerator(String format, 
                                         NumberFormat formatter) {
        super(format, formatter);   
    }
    
    /**
     * Creates the array of items that can be passed to the 
     * {@link MessageFormat} class for creating labels.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The items (never <code>null</code>).
     */
    protected Object[] createItemArray(CategoryDataset dataset, int series, 
                                       int item) {
        Object[] result = new Object[8];
        result[0] = dataset.getRowKey(series);
        Number y = dataset.getValue(series, item);
        NumberFormat formatter = getNumberFormat();
        result[1] = formatter.format(y);
        if (dataset instanceof BoxAndWhiskerCategoryDataset) {
            BoxAndWhiskerCategoryDataset d = (BoxAndWhiskerCategoryDataset) dataset;
            Number meanValue = d.getMeanValue(series, item);
            if( meanValue != null) {
            	result[2] = formatter.format(meanValue);
            } else {
            	result[2] = "";
            }
            Number medianValue = d.getMedianValue(series, item);
            if( medianValue != null ) {
            	result[3] = formatter.format(medianValue);
            } else {
            	result[3] = "";
            }
            Number minValue = d.getMinRegularValue(series, item);
            if( minValue != null ) {
            	result[4] = formatter.format(minValue);
            } else {
            	result[4] = "";
            }
            Number maxValue = d.getMaxRegularValue(series, item);
            if( maxValue != null ){
            	result[5] = formatter.format(maxValue);
            } else {
            	result[5] = "";
            }
            Number q1Value = d.getQ1Value(series, item);
            if( q1Value != null ) {
            	result[6] = formatter.format(q1Value);
            } else {
            	result[6] = "";
            }            
            Number q3Value = d.getQ3Value(series, item);
            if( q3Value != null ){
            	result[7] = formatter.format(q3Value);
            } else {
            	result[7] = "";
            }
        }
        return result;
    }
    
    /**
     * Tests if this object is equal to another.
     *
     * @param obj  the other object.
     *
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ExtendedBoxAndWhiskerToolTipGenerator) {
            return super.equals(obj);
        }
        return false;
    }
}

