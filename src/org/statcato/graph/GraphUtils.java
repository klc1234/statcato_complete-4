/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.graph;

import java.awt.Color;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import org.jfree.chart.ChartPanel;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;

/**
 * Utilities for graph generation and manipulation.
 * 
 * @author  Margaret Yau
 * @version %I%, %G%
 * @see org.jfree.chart
 * @since 1.0
 */
public class GraphUtils {
    public static Icon createGraphImageLabel(JFreeChart plot) {
        BufferedImage image = plot.createBufferedImage(400, 300);
        return new ImageIcon(image);
    } 
    
    /**
     * Returns a BufferedImage that is the result
     * of joining a list of charts 
     * @param charts an ArrayList of JFreeChart objects
     * @return BufferedImage
     */
    public static BufferedImage joinBufferedChartImages(ArrayList<JFreeChart> charts) {
        if (charts.isEmpty()) {
            return null;
        }
        
        int height = ChartPanel.DEFAULT_HEIGHT;
        int width = ChartPanel.DEFAULT_WIDTH;
        int totalHeight =  height * (int) Math.ceil(charts.size() / 2.0);
        int totalWidth = width * 2;
        
        BufferedImage newImage = new BufferedImage(
                totalWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        drawCharts(g, charts, 0, 0, totalWidth, totalHeight);
        g.dispose();
        return newImage;
    }
    
    /**
     * Draws the charts on the given Java@d graphics device, 
     * with two charts per row at the given position.
     * @param g graphics device
     * @param charts list of charts
     * @param x x-position
     * @param y y-position
     * @param width width
     * @param height height
     */
    public static void drawCharts(Graphics2D g, ArrayList<JFreeChart> charts,
            int x, int y, int width, int height) {   
        int aHeight =  height / (int) Math.ceil(charts.size() / 2.0);
        int aWidth = width / 2;
        g.setBackground(Color.WHITE);  
        g.clearRect(x, y, width + x, height + y);  
        for (int i = 0; i < charts.size(); ++i) {
            charts.get(i).draw(g, new Rectangle2D.Double(x + i % 2 * aWidth,
                   y + i / 2 * aHeight, aWidth, aHeight));
        }
    }
    
    /**
     * Writes a chart to an output stream in PNG format.  
     *
     * @param out  the output stream (<code>null</code> not permitted).
     * @param image the BufferedImage object
     * @param width  the image width.
     * @param height  the image height.
     *
     * @throws IOException if there are any I/O errors.
     */
    public static void writeBufferedImageAsPNG(OutputStream out, 
            BufferedImage image,
            int width, int height)
            throws IOException {

        EncoderUtil.writeBufferedImage(image, ImageFormat.PNG, out);
    }
}
