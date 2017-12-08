package org.statcato.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.print.PageFormat;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.geom.Rectangle2D;

import org.jfree.report.ElementAlignment;
import org.jfree.report.ItemBand;
import org.jfree.report.JFreeReport;
import org.jfree.report.JFreeReportBoot;
import org.jfree.report.PageFooter;
import org.jfree.report.PageHeader;
import org.jfree.report.ReportFooter;
import org.jfree.report.ReportHeader;
import org.jfree.report.TextElement;
import org.jfree.report.SimplePageDefinition;
import org.jfree.report.ReportProcessingException;
import org.jfree.report.elementfactory.LabelElementFactory;
import org.jfree.report.elementfactory.StaticShapeElementFactory;
import org.jfree.report.elementfactory.TextFieldElementFactory;
import org.jfree.report.function.PageOfPagesFunction;
import org.jfree.report.modules.gui.base.PreviewDialog;
import org.jfree.report.style.ElementStyleSheet;
import org.jfree.report.style.FontDefinition;
import org.jfree.ui.FloatDimension;
import org.jfree.util.Log;

/**
 * Print utility for JFreeReport.
 * 
 * @version %I%, %G%
 * @since 1.0
 */
public class JFreeReportPrintUtil {

    static {
        JFreeReportBoot.getInstance().start();
    }
    
    /**
     * Prints a table using JFreeReport.
     * 
     * @param tb table to be printed
     * @param reportHeaderText report header text
     * @param reportFooterText report footer text
     * @param pageHeaderText page header text
     * @param pageFooterText page footer text
     */
    public static void PrintControlTable(JTable tb, String reportHeaderText, 
            String reportFooterText, String pageHeaderText, String pageFooterText) {
        TableModel tm;
        float columnWidth[];
        float columnPos[];
        int i, numCols;
        float width;
        TableColumnModel tcm;
        
        tm = tb.getModel();
        tcm = tb.getColumnModel();
        numCols = tm.getColumnCount();
        if ( 0 == tm.getColumnCount()) return;
        final JFreeReport report = new JFreeReport();
        TextElement t;
        report.setName(reportHeaderText);    

        width = report.getPageDefinition().getWidth();

        // multiple page span
        PageFormat ThisPageformat = new PageFormat();
        // have at most 10 columns on one page
        report.setPageDefinition(new SimplePageDefinition(ThisPageformat, 
                (int)Math.ceil(numCols / (width / 50.0)), 1));
        
        //create report header     
        final ReportHeader header = new ReportHeader();
        header.setName("Report-Header");
        header.getStyle().setStyleProperty(ElementStyleSheet.MINIMUMSIZE, new FloatDimension(0, 5));
        header.getStyle().setFontDefinitionProperty
                (new FontDefinition("Serif", 20, true, false, false, false));
        
        final LabelElementFactory factory = new LabelElementFactory();
        factory.setName("Report-Header-Label");
        factory.setAbsolutePosition(new Point2D.Float(0, 0));
        factory.setMinimumSize(new FloatDimension(-400, 24));
        factory.setHorizontalAlignment(ElementAlignment.CENTER);
        factory.setVerticalAlignment(ElementAlignment.MIDDLE);
        factory.setText(reportHeaderText);
        header.addElement(factory.createElement());
        report.setReportHeader(header);
        
        
        //CreateReportFooter();        
        final ReportFooter footer = new ReportFooter();
        footer.setName("Report-Footer");
        footer.getStyle().setStyleProperty(ElementStyleSheet.MINIMUMSIZE, 
                new FloatDimension(0, 4));
        footer.getStyle().setFontDefinitionProperty
                (new FontDefinition("Serif", 12, true, false, false, false));
        
        final LabelElementFactory rfFactory = new LabelElementFactory();
        rfFactory.setName("Report-Footer-Label");
        rfFactory.setAbsolutePosition(new Point2D.Float(0, 0));
        rfFactory.setMinimumSize(new FloatDimension(-100, 24));
        rfFactory.setHorizontalAlignment(ElementAlignment.RIGHT);
        rfFactory.setVerticalAlignment(ElementAlignment.BOTTOM);
        rfFactory.setText(reportFooterText);
        footer.addElement(rfFactory.createElement());
        report.setReportFooter(footer);
        
        //CreatePageHeader();
        final PageHeader pHeader = new PageHeader();
        pHeader.setName("Page-pHeader");
        rfFactory.setVerticalAlignment(ElementAlignment.TOP);
        rfFactory.setText(pageHeaderText);
        pHeader.addElement(rfFactory.createElement());
        pHeader.addElement(StaticShapeElementFactory.createHorizontalLine
                (null, null, new BasicStroke(1), 24));
        pHeader.setDisplayOnFirstPage(true);
        report.setPageHeader(pHeader); 

        //CreatePageFooter();
        final PageFooter pageFooter = new PageFooter();
        pageFooter.getStyle().setStyleProperty(ElementStyleSheet.MINIMUMSIZE, new FloatDimension(0, 24));
        pageFooter.setName("Page-Footer");
        pageFooter.setDisplayOnFirstPage(true);
        pageFooter.setDisplayOnLastPage(true);
        
        final PageOfPagesFunction pageFunction = new PageOfPagesFunction();
        pageFunction.setName("pageXofY");
        pageFunction.setFormat(pageFooterText + "  -  page {0} of {1}");
        report.addExpression(pageFunction);
        
        final TextFieldElementFactory elementFactory = new TextFieldElementFactory();
        elementFactory.setAbsolutePosition(new Point2D.Float(0, 0));
        elementFactory.setMinimumSize(new FloatDimension(-100, 24));
        elementFactory.setVerticalAlignment(ElementAlignment.MIDDLE);
        elementFactory.setHorizontalAlignment(ElementAlignment.RIGHT);
        elementFactory.setFieldname("pageXofY");
        pageFooter.addElement(elementFactory.createElement());
        report.setPageFooter(pageFooter);

        // add data
        for (i = 0; i < numCols; ++i) {
            t = TextFieldElementFactory.createStringElement(
                "T" + i,
                new Rectangle2D.Double(50*i, 0.0, 50.0, 20.0),
                Color.black,
                ElementAlignment.LEFT,
                ElementAlignment.MIDDLE,
                null, // font
                "-", // null string
                tm.getColumnName(i)
                );            
            report.getItemBand().addElement(t);
        } 
        
        report.setData(tm);
        
        try
        {
            final PreviewDialog preview = new PreviewDialog(report);
            preview.pack();
            preview.setVisible(true);
        }
        catch (ReportProcessingException e)
        {
            Log.error("Failed to generate report ", e);
        }
    }
}