/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.graph;

import org.statcato.statistics.NormalProbabilityDistribution;
import org.statcato.statistics.BasicStatistics;
import org.statcato.statistics.inferential.MultipleRegression;
import org.statcato.utils.HelperFunctions;
import org.statcato.graph.ExtendedBoxAndWhiskerToolTipGenerator;
import org.statcato.graph.ExtendedBoxAndWhiskerRenderer;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.Regression;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;

import Jama.Matrix;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.geom.Line2D;

/**
 * Functions for generating graphs.
 * 
 * @author  Margaret Yau
 * @version %I%, %G%
 * @see org.jfree.chart
 * @since 1.0
 */
public class GraphFactory {
    /**
     * No regression
     */
    public static final int REG_NONE = 0;
    /**
     * Linear regression
     */
    public static final int REG_LIN = 1;
    /**
     * Quadratic regression
     */
    public static final int REG_QUAD = 2;
    /**
     * Cubic regression
     */
    public static final int REG_CUBIC = 3;
    /**
     * Polynomial regression
     */
    public static final int REG_POLY = 4;
    /**
     * Logarithmic regression
     */
    public static final int REG_LOG = 5;
    /**
     * Power regression
     */
    public static final int REG_POW = 6;
    /**
     * Fixed power regression
     */
    public static final int REG_FIXEDPOW = 7;
    /**
     * Exponential regression
     */
    public static final int REG_EXP = 8;

    public static final Color[] COLORS =
    {Color.BLUE, Color.CYAN, Color.MAGENTA, Color.GREEN, Color.ORANGE,
     Color.RED, Color.PINK, Color.YELLOW, Color.GRAY, Color.LIGHT_GRAY};
     /**
      * Number of subintervals for plotting non-linear functions
      */
    private static final int NUM_SUBINT = 1000;
    /**
     * A constant for shifting used in log transformation
     */
    private static final double SHIFT = 0;

    /**
     * Creates a normal quantile plot.
     * 
     * @param YColumnVector vector of data values
     * @param title         chart title
     * @param xLabel        x axis label
     * @param yLabel        y axis label
     * @param isDataOnXAxis whether the data is on the x axis
     * @param showRegressionLine    whether the regression line should be shown
     * @return JFreeChart plot object
     */
    public static JFreeChart createNormalQuantilePlot(Vector<Double> YColumnVector,
            String title, String xLabel, String yLabel,
            boolean isDataOnXAxis, boolean showRegressionLine) {
        Vector<Double> ZScores = new Vector<Double>();
        Collections.sort(YColumnVector);
        int n = YColumnVector.size();
        XYSeries series = new XYSeries("Points"); // create data series
        for (int i = 0; i < n; ++i) {
             // find quartile
             double a = (2.0 * i + 1) / (2.0 * n);
             NormalProbabilityDistribution dist = new 
                     NormalProbabilityDistribution(0, 1);
             double z = dist.inverseCumulativeProbability(a);
             ZScores.addElement(new Double(z));
             if (isDataOnXAxis)
                 series.add(((Double)YColumnVector.elementAt(i)).doubleValue(), z);
             else
                 series.add(z, ((Double)YColumnVector.elementAt(i)).doubleValue());
        }
        
         // create scatterplot
        XYSeriesCollection data = new XYSeriesCollection(series); 
        XYDataset data1 = data;
        XYDotRenderer renderer1 = new XYDotRenderer();
        JFreeChart plot = ChartFactory.createScatterPlot(
                title,
                xLabel,
                yLabel,
                data1,
                PlotOrientation.VERTICAL,
                false,
                true,
                false); 
        
        if (showRegressionLine) {
            // add regression line
            if (!isDataOnXAxis)
                YColumnVector = ZScores;
            XYPlot xYplot = plot.getXYPlot();
            double max = BasicStatistics.max(YColumnVector).doubleValue() + 0.5;
            double min = BasicStatistics.min(YColumnVector).doubleValue() - 0.5;
            double[] coeff = Regression.getOLSRegression(data1, 0);
            XYLineAnnotation regressionLine = new XYLineAnnotation(
                    min, coeff[0] + coeff[1] * min,
                    max, coeff[0] + coeff[1] * max,
                    new BasicStroke(), Color.BLUE);
            xYplot.addAnnotation( (XYAnnotation) regressionLine);
            ValueAxis rangeAxis = xYplot.getRangeAxis();
            ValueAxis domainAxis = xYplot.getDomainAxis();
            xYplot.setDomainAxis(domainAxis);
            xYplot.setRangeAxis(rangeAxis);
        }
        
        return plot;
    }
    
    /**
     * Creates a normal quantile plot.
     * 
     * @param YColumnArray  ArrayList of data values
     * @param title         chart title
     * @param xLabel        x axis label
     * @param yLabel        y axis label
     * @param isDataOnXAxis whether the data is on the x axis
     * @param showRegressionLine    whether the regression line should be shown
     * @return JFreeChart plot object
     */
    public static JFreeChart createNormalQuantilePlot(ArrayList<Double> YColumnArray,
            String title, String xLabel, String yLabel,
            boolean isDataOnXAxis, boolean showRegressionLine) {
        return createNormalQuantilePlot(new Vector(YColumnArray), title,
                xLabel, yLabel, isDataOnXAxis, showRegressionLine);
    }
    
    /**
     * Creates a box plot.
     * 
     * @param dataset   DefaultBoxAndWhisterCategoryDataset object
     * @param title     plot title
     * @param xLabel    x-axis label
     * @param yLabel    y-axis label
     * @param showLegend    whether the legend should be shown
     * @param horizontal    whether the plot is horizontal
     * @return JFreeChart plot object
     */
    public static JFreeChart createBoxPlot(
            DefaultBoxAndWhiskerCategoryDataset dataset,
            String title, String xLabel, String yLabel, boolean showLegend,
            boolean horizontal) {
        CategoryAxis xAxis = new CategoryAxis(xLabel);
        NumberAxis yAxis = new NumberAxis(yLabel);
        yAxis.setAutoRangeIncludesZero(false);
        ExtendedBoxAndWhiskerRenderer renderer = new ExtendedBoxAndWhiskerRenderer();
        renderer.setFillBox(true);
        renderer.setBaseToolTipGenerator(new ExtendedBoxAndWhiskerToolTipGenerator());
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        if (horizontal)
            plot.setOrientation(PlotOrientation.HORIZONTAL);
        else
            plot.setOrientation(PlotOrientation.VERTICAL);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0.15);
        domainAxis.setUpperMargin(0.15);
        domainAxis.setCategoryMargin(0.25);
        
        JFreeChart chart = new JFreeChart(
            title,
            new Font("SansSerif", Font.BOLD, 14),
            plot,
            showLegend
        );
        
        return chart;
    }
    
    /**
     * Creates a scatterplot.
     * 
     * @param seriesCollection  XYSeriesCollection containing the data series
     * @param title             chart title
     * @param xLabel            x-axis label
     * @param yLabel            y-axis label
     * @param showLegend        whether the legend should be shown
     * @param showRegression    whether a regression line should be shown
     * @param regressionType    type of regression
     * @param regressionArg     regression argument
     * @param min               minimum x value
     * @param max               maximum x value
     * @return JFreeChart object representing a scatterplot
     */
    public static JFreeChart createScatterplot(XYSeriesCollection seriesCollection, 
            String title, String xLabel, String yLabel,
            boolean showLegend, int regressionType, double regressionArg,
            double min, double max) {
        
        // create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
            title,
            xLabel, yLabel, 
            seriesCollection, 
            PlotOrientation.VERTICAL,
            showLegend, 
            true, 
            false
        );
        
        XYPlot plot = chart.getXYPlot();
        XYDotRenderer renderer = new XYDotRenderer();
        renderer.setDotWidth(4);
        renderer.setDotHeight(4);
        plot.setRenderer(renderer);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        // regression line
        if (regressionType == REG_LIN) {
            // linear regression
            for (int i = 0; i < seriesCollection.getSeriesCount(); ++i) {
                double[] coeff = Regression.getOLSRegression(seriesCollection, i);
                XYLineAnnotation regressionLine = new XYLineAnnotation(
                        min, coeff[0] + coeff[1] * min,
                        max, coeff[0] + coeff[1] * max,
                        new BasicStroke(), COLORS[i % COLORS.length]);
                plot.addAnnotation( (XYAnnotation) regressionLine);
                LegendItemCollection c = plot.getLegendItems();
                c.add(
                        new LegendItem(
                        "y = " + HelperFunctions.formatFloat2(coeff[0])
                        + " + " + HelperFunctions.formatFloat2(coeff[1])
                        + "x" ,
                        "linear regression",
                        "linear regression",
                        null,
                        new Line2D.Double(0, 0, 12, 0),
                        new BasicStroke(),
                        COLORS[i % COLORS.length]));
                plot.setFixedLegendItems(c);
            }
        }
        else if (regressionType != REG_NONE) {
            for (int i = 0; i < seriesCollection.getSeriesCount(); ++i) {
                XYSeries series = seriesCollection.getSeries(i);
                Vector<Double> xVector = new Vector<Double>();
                Vector<Double> yVector = new Vector<Double>();
                for (int j = 0; j < series.getItemCount(); ++j) {
                    double x = series.getX(j).doubleValue();
                    double y = series.getY(j).doubleValue();
                    xVector.addElement(new Double(x));
                    yVector.addElement(new Double(y));                    
                }
                // compute regression coefficients
                Vector<Double> coeff = computeRegressionCoeff(
                        xVector, yVector, regressionType, regressionArg);
                if (coeff == null || coeff.size() == 0)
                    return null;
                
                if (regressionType == REG_QUAD || regressionType == REG_CUBIC
                        || regressionType == REG_POLY) {
                    for (int k = 0; k < NUM_SUBINT; ++k) {
                        double x1 = min + k * (max - min) / NUM_SUBINT;
                        double y1 = 0;
                        for (int n = 0; n < coeff.size(); ++n) {
                            y1 += coeff.elementAt(n).doubleValue() *
                                        Math.pow(x1, n);
                        }
                        double x2 = min + (k + 1) * (max - min) / NUM_SUBINT;
                        double y2 = 0;
                        for (int n = 0; n < coeff.size(); ++n) {
                            y2 += coeff.elementAt(n).doubleValue() *
                                        Math.pow(x2, n);
                        }
                        XYLineAnnotation line = new XYLineAnnotation(
                                x1, y1, x2, y2, new BasicStroke(), 
                                COLORS[i % COLORS.length]);
                        plot.addAnnotation( (XYAnnotation) line);                        
                    }
                    LegendItemCollection c = plot.getLegendItems();
                    String text = "y = ";
                    text += HelperFunctions.formatFloat2(coeff.elementAt(0).doubleValue());
                    text += " + " +
                            HelperFunctions.formatFloat2(coeff.elementAt(1).doubleValue())
                            + "x";
                    for (int n = 2; n < coeff.size(); ++n) {
                        text += " + " +
                                HelperFunctions.formatFloat2(coeff.elementAt(n).doubleValue());
                        text += "x^" + n;
                    }
                    c.add(
                        new LegendItem(
                        text,
                        "regression equation",
                        "regression equation",
                        null,
                        new Line2D.Double(0, 0, 12, 0),
                        new BasicStroke(),
                        COLORS[i % COLORS.length]));
                    plot.setFixedLegendItems(c);
                }
                else if (regressionType == REG_LOG) {
                    for (int k = 0; k < NUM_SUBINT; ++k) {
                        double x1 = min + k * (max - min) / NUM_SUBINT;
                        double y1 = coeff.elementAt(0).doubleValue() +
                                coeff.elementAt(1).doubleValue() * Math.log(x1);

                        double x2 = min + (k + 1) * (max - min) / NUM_SUBINT;
                        double y2 = 0;
                        y2 = coeff.elementAt(0).doubleValue() +
                                    coeff.elementAt(1).doubleValue() * Math.log(x2);

                        XYLineAnnotation line = new XYLineAnnotation(
                                x1, y1, x2, y2, new BasicStroke(),
                                COLORS[i % COLORS.length]);
                        plot.addAnnotation( (XYAnnotation) line);
                    }
                    LegendItemCollection c = plot.getLegendItems();
                    String text = "y = ";
                    text += HelperFunctions.formatFloat2(coeff.elementAt(0).doubleValue());
                    text += " + " +
                            HelperFunctions.formatFloat2(coeff.elementAt(1).doubleValue())
                            + "ln(x)";
                    c.add(
                        new LegendItem(
                        text,
                        "regression equation",
                        "regression equation",
                        null,
                        new Line2D.Double(0, 0, 12, 0),
                        new BasicStroke(),
                        COLORS[i % COLORS.length]));
                    plot.setFixedLegendItems(c);
                }
                else if (regressionType == REG_POW) {
                    double a = coeff.elementAt(0).doubleValue();
                    double b = coeff.elementAt(1).doubleValue();

                    for (int k = 0; k < NUM_SUBINT; ++k) {
                        double x1 = min + k * (max - min) / NUM_SUBINT - SHIFT;
                        double y1 = a * Math.pow(x1, b);

                        double x2 = min + (k + 1) * (max - min) / NUM_SUBINT - SHIFT;
                        double y2 = a * Math.pow(x2, b);

                        XYLineAnnotation line = new XYLineAnnotation(
                                x1, y1, x2, y2, new BasicStroke(),
                                COLORS[i % COLORS.length]);
                        plot.addAnnotation( (XYAnnotation) line);
                    }
                    LegendItemCollection c = plot.getLegendItems();
                    String text = "y = ";
                    text += HelperFunctions.formatFloat2(coeff.elementAt(0).doubleValue());
                    text += " * x^" +
                            HelperFunctions.formatFloat2(coeff.elementAt(1).doubleValue());
                    c.add(
                        new LegendItem(
                        text,
                        "regression equation",
                        "regression equation",
                        null,
                        new Line2D.Double(0, 0, 12, 0),
                        new BasicStroke(),
                        COLORS[i % COLORS.length]));
                    plot.setFixedLegendItems(c);
                }
                else if (regressionType == REG_FIXEDPOW) {
                    double a = coeff.elementAt(0).doubleValue();
                    for (int k = 0; k < NUM_SUBINT; ++k) {
                        double x1 = min + k * (max - min) / NUM_SUBINT;
                        double y1 = a * Math.pow(x1, regressionArg);

                        double x2 = min + (k + 1) * (max - min) / NUM_SUBINT;
                        double y2 = a * Math.pow(x2, regressionArg);

                        XYLineAnnotation line = new XYLineAnnotation(
                                x1, y1, x2, y2, new BasicStroke(),
                                COLORS[i % COLORS.length]);
                        plot.addAnnotation( (XYAnnotation) line);
                    }
                    LegendItemCollection c = plot.getLegendItems();
                    String text = "y = ";
                    text += HelperFunctions.formatFloat2(a);
                    text += " * x^" +
                            HelperFunctions.formatFloat2(regressionArg);
                    c.add(
                        new LegendItem(
                        text,
                        "regression equation",
                        "regression equation",
                        null,
                        new Line2D.Double(0, 0, 12, 0),
                        new BasicStroke(),
                        COLORS[i % COLORS.length]));
                    plot.setFixedLegendItems(c); 
                }
                else if (regressionType == REG_EXP) {
                    double a = coeff.elementAt(0).doubleValue();
                    double b = coeff.elementAt(1).doubleValue();
                    for (int k = 0; k < NUM_SUBINT; ++k) {
                        double x1 = min + k * (max - min) / NUM_SUBINT;
                        double y1 = a * Math.pow(b, x1);

                        double x2 = min + (k + 1) * (max - min) / NUM_SUBINT;
                        double y2 = a * Math.pow(b, x2);

                        XYLineAnnotation line = new XYLineAnnotation(
                                x1, y1, x2, y2, new BasicStroke(),
                                COLORS[i % COLORS.length]);
                        plot.addAnnotation( (XYAnnotation) line);
                    }
                    LegendItemCollection c = plot.getLegendItems();
                    String text = "y = ";
                    text += HelperFunctions.formatFloat2(a);
                    text += " * " +
                            HelperFunctions.formatFloat2(b) + "^x";
                    c.add(
                        new LegendItem(
                        text,
                        "regression equation",
                        "regression equation",
                        null,
                        new Line2D.Double(0, 0, 12, 0),
                        new BasicStroke(),
                        COLORS[i % COLORS.length]));
                    plot.setFixedLegendItems(c);
                }
            }
        }


        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        
        return chart;
    }

    /**
     * Computes the regression coefficients for the given vectors and
     * regression type.
     *
     * @param xVector   vector of x (independent)values
     * @param yVector   vector of y (dependent) values
     * @param regressionType    type of regression
     * @param regressionArg     arguments for specific types of regression
     * @return vector of regression coefficients
     */
    public static Vector<Double> computeRegressionCoeff(Vector<Double> xVector,
            Vector<Double> yVector, int regressionType, double regressionArg) {
        Vector<Vector<Double>> vectors = new Vector<Vector<Double>>();
        Vector<Double> coeff = new Vector<Double>();
        double k = 0;  // number of coefficients
        boolean logTransform = false;
        if (regressionType == REG_QUAD) {
            vectors.addElement(xVector);
            vectors.addElement(HelperFunctions.powerVector(xVector, 2));
            k = 3;
        } else if (regressionType == REG_CUBIC) {
            vectors.addElement(xVector);
            vectors.addElement(HelperFunctions.powerVector(xVector, 2));
            vectors.addElement(HelperFunctions.powerVector(xVector, 3));
            k = 4;
        } else if (regressionType == REG_POLY) {
                vectors.addElement(xVector);
                for (int i = 2; i <= regressionArg; ++i)
                    vectors.addElement(HelperFunctions.powerVector(xVector, i));
                k = regressionArg + 1;
        } else if (regressionType == REG_LOG) {
            vectors.addElement(HelperFunctions.logVector(xVector));
            k = 2;
        } else if (regressionType == REG_POW) {
            vectors.addElement(HelperFunctions.logVector(
                    HelperFunctions.addConstantVector(xVector, SHIFT)));
            yVector = HelperFunctions.logVector(
                    HelperFunctions.addConstantVector(yVector, SHIFT));
            k = 2;
            logTransform = true;
        } else if (regressionType == REG_EXP) {
            vectors.addElement(xVector);
            yVector = HelperFunctions.logVector(
                    HelperFunctions.addConstantVector(yVector, SHIFT));
            k = 2;
            logTransform = true;
        } else if (regressionType == REG_FIXEDPOW) {
            Vector<Double> xn = HelperFunctions.powerVector(xVector, regressionArg);
            vectors.addElement(xn);
            k = 1;
        }

        // compute
        try {
            MultipleRegression mr = new MultipleRegression(vectors, yVector,
                    regressionType != REG_FIXEDPOW);

            Matrix RegCoeff = mr.RegressionEqCoefficients();

            if (regressionType == REG_FIXEDPOW) {
                if (Double.isNaN(RegCoeff.get(0,0)) || Double.isInfinite(RegCoeff.get(0,0)))
                    return null;
                coeff.addElement(RegCoeff.get(0, 0));
                /*
                int n = xVector.size();
                double[][] x = new double[n][1];
                double[][] y = new double[n][1];
                for (int i = 0; i < n; ++i) {
                    x[i][0] = Math.pow(xVector.elementAt(i), regressionArg);
                    y[i][0] = yVector.elementAt(i);
                }
                Matrix xMatrix = new Matrix(x);
                Matrix yMatrix = new Matrix(y);

                Matrix kMatrix = xMatrix.solve(yMatrix);
                System.out.println("fixed power " + kMatrix.get(0,0));
                coeff.addElement(kMatrix.get(0,0));*/
            }
            else {
                if (!logTransform) {
                        for (int i = 0; i < k; ++i) {
                            if (Double.isNaN(RegCoeff.get(i,0)) || Double.isInfinite(RegCoeff.get(i,0)))
                                return null;
                            coeff.addElement(RegCoeff.get(i, 0));
                        }
                } else {
                    if (regressionType == REG_POW) {
                        if (Double.isNaN(RegCoeff.get(0,0)) || Double.isInfinite(RegCoeff.get(0,0)))
                            return null;
                        if (Double.isNaN(RegCoeff.get(1,0)) || Double.isInfinite(RegCoeff.get(1,0)))
                            return null;
                        coeff.addElement(Math.exp(RegCoeff.get(0,0)));
                        coeff.addElement(RegCoeff.get(1, 0));
                    }
                    else if (regressionType == REG_EXP) {
                        if (Double.isNaN(RegCoeff.get(0,0)) || Double.isInfinite(RegCoeff.get(0,0)))
                            return null;
                        if (Double.isNaN(RegCoeff.get(1,0)) || Double.isInfinite(RegCoeff.get(1,0)))
                            return null;
                        coeff.addElement(Math.exp(RegCoeff.get(0,0)));
                        coeff.addElement(Math.exp(RegCoeff.get(1,0)));
                    }
                }
            }
        }
        catch (RuntimeException e) {
            System.out.println("exception in computing regression coefficients: " + e);
        }
        return coeff;
    }

    /**
     * Creates a scatterplot.
     *
     * @param seriesCollection  XYSeriesCollection containing the data series
     * @param title             chart title
     * @param xLabel            x-axis label
     * @param yLabel            y-axis label
     * @param showLegend        whether the legend should be shown
     * @param showRegression    whether a regression line should be shown
     * @param min               minimum x value
     * @param max               maximum x value
     * @return
     */
    public static JFreeChart createScatterplot(XYSeriesCollection seriesCollection,
            String title, String xLabel, String yLabel,
            boolean showLegend, boolean showRegression,
            double min, double max) {

        if (showRegression)
            return createScatterplot(seriesCollection, title, xLabel, yLabel,
                showLegend, REG_LIN, 0, min, max);
        else
            return createScatterplot(seriesCollection, title, xLabel, yLabel,
                showLegend, REG_NONE, 0, min, max);
    }
    
    /**
     * Creates a residual plot.
     *
     * @param seriesCollection  XYSeriesCollection containing the data series
     *                          Y series contains residuals
     * @param title             chart title
     * @param xLabel            x-axis label
     * @param yLabel            y-axis label
     * @param showLegend        whether the legend should be shown
     * @param showZeroHorizon   whether a horizontal line Y = 0 should be shown
     * @param min               minimum x value
     * @param max               maximum x value
     * @return                  JFreeChart residual plot
     */
    public static JFreeChart createResidualPlot(XYSeriesCollection seriesCollection,
            String title, String xLabel, String yLabel,
            boolean showLegend, boolean showZeroHorizon,
            double min, double max) {

        JFreeChart chart = createScatterplot(seriesCollection, title, xLabel, yLabel,
                showLegend, REG_NONE, 0, min, max);
        
       if (showZeroHorizon) {
           // add Y = 0 line to chart
           XYLineAnnotation line = new XYLineAnnotation(
                        min, 0,
                        max, 0,
                        new BasicStroke(), Color.GRAY);
                chart.getXYPlot().addAnnotation( (XYAnnotation) line);
       }
        
       return chart;
    }

    /**
     * Creates a bar chart.
     *
     * @param title     chart title
     * @param xTitle    x-axis title
     * @param yTitle    y-axis title
     * @param dataset   bar chart dataset
     * @param orientation   chart orientation
     * @param hasLegend whether a legend is included
     * @return JFreeChart object representing a bar chart
     */
    public static JFreeChart createBarChart(String title, String xTitle, String yTitle,
            DefaultCategoryDataset dataset,
            PlotOrientation orientation, boolean hasLegend) {
            JFreeChart chart = ChartFactory.createBarChart(
                    title,      // chart title
                    xTitle,     // domain axis label
                    yTitle,     // range axis label
                    dataset,    // data
                    orientation,// orientation
                    hasLegend,  // include legend
                    true,       // tooltips?
                    false       // URLs?
                    );
            
            CategoryPlot categoryplot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer)categoryplot.getRenderer();
            renderer.setItemMargin(0); // no space within category
            return chart;
    }

    /**
     * Creates a pie chart.
     *
     * @param title     chart title
     * @param data      pie chart dataset
     * @param hasLegend whether a legend is included
     * @param hasLabel  whether labels for pie slices are included
     * @return  JFreeChart object representing a pie chart
     */
    public static JFreeChart createPieChart(String title, DefaultPieDataset data,
            boolean hasLegend, boolean hasLabel) {
        JFreeChart chart = ChartFactory.createPieChart(
                title,
                data,
                hasLegend, // legend?
                true, // tooltips?
                false // URLs?
                );

        PiePlot plot = (PiePlot) chart.getPlot();
        if (hasLabel) {
            StandardPieSectionLabelGenerator gen =
                    new StandardPieSectionLabelGenerator("{0} = {1} ({2})");
            plot.setLabelGenerator(gen);
        }
        else
            plot.setLabelGenerator(null);
        
        return chart;
    }
                
    /**
     * Creates a histogram.
     *
     * @param title     title of the histogram
     * @param xTitle    x-axis label
     * @param yTitle    y-axis label
     * @param dataset   HistogramDataset object
     * @param hasLegend whether a legend should be displayed
     * @param units     tick units object of the x-axis
     * @param minBin    bin size
     * @param minBinStart   the start of the first bin
     * @param center    whether the tick marks should be displayed in the middle of bins
     * @param yTicks    tick mark units of the y-axis
     * @return JFreeChart object representing a histogram
     */
    public static JFreeChart createHistogram(
            String title, String xTitle, String yTitle,
            HistogramDataset dataset, boolean hasLegend,
            TickUnits units, double minBin, double minBinStart,
            boolean center, double yTicks) {
        
        JFreeChart chart = ChartFactory.createHistogram(
            title,
            xTitle,
            yTitle,
            dataset,
            PlotOrientation.VERTICAL,
            hasLegend,
            false,
            false
        );

        XYPlot xyplot = chart.getXYPlot();
        xyplot.setForegroundAlpha(0.75f);

        // format x axis
        final NumberAxis numberaxis = (NumberAxis)xyplot.getDomainAxis();
        numberaxis.setStandardTickUnits(units);
        numberaxis.setLowerBound(minBinStart);
        numberaxis.setLowerMargin(0);

        double firstTick;
        if (center) {   // center of bar
            firstTick = minBinStart + minBin / 2;
        }
        else {  // between bars
            firstTick = minBinStart;
        }
        final double xmin = firstTick;
        NumberAxis domainAxis_x = new NumberAxis("X Values"){
           @Override
           protected double calculateLowestVisibleTickValue() {
               return xmin;
           }
       };

       domainAxis_x.setStandardTickUnits(units);
       domainAxis_x.setRange(numberaxis.getRange());
       domainAxis_x.setAutoRangeIncludesZero(false);
       domainAxis_x.setLabel(xTitle);
       xyplot.setDomainAxis(domainAxis_x);

       // format y axis
       if (yTicks > 0) {
           NumberAxis yaxis = (NumberAxis)xyplot.getRangeAxis();
           yaxis.setTickUnit(new NumberTickUnit(yTicks));
       }

       return chart;
    }
}
