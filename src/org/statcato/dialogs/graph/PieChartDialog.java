/*
 * PieChartDialog.java
 *
 * Created on May 5, 2008, 2:08 PM
 */

package org.statcato.dialogs.graph;

import org.statcato.graph.StatcatoChartFrame;
import org.statcato.graph.GraphFactory;
import org.statcato.*;
import org.statcato.spreadsheet.*;
import org.statcato.utils.HelperFunctions;
import javax.swing.*;
import java.util.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.util.SortOrder.*;
import org.jfree.data.general.DefaultPieDataset;

/**
 * A dialog for creating pie charts.  Allows the user to specify the
 * data values to be graphed, title, sort order of slices (by categories or
 * frequencies), and whether a legend and slice labels are displayed.
 * 
 * @author  Margaret Yau
 * @version %I%, %G%
 * @see org.jfree.chart
 * @since 1.0
 */
public class PieChartDialog extends StatcatoDialog {

    /** Creates new form PieChartDialog */
    public PieChartDialog(java.awt.Frame parent, boolean modal, Statcato app) {
        super(parent, modal);
        this.app = app;
        ParentSpreadsheet = app.getSpreadsheet();
        initComponents();
        
        ButtonGroup group1 = new ButtonGroup();
        group1.add(SummaryRadioButton);
        group1.add(ValuesRadioButton);
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(SortCatRadioButton);
        group2.add(SortFreqRadioButton);
        
        ParentSpreadsheet.populateComboBox(CategoryComboBox);
        ParentSpreadsheet.populateComboBox(FrequencyComboBox);
        ParentSpreadsheet.populateComboBox(DataComboBox);
        
        getRootPane().setDefaultButton(OKButton);
        setHelpFile("graph-piechart");
        name = "Pie Chart";
        description = "For creating pie charts, " +
                "circular charts divided into sectors that show relative " +
                "frequencies of different categories represented";
        helpStrings.add("If summary data of the categories and their " +
                "corresponding frequencies are present in the datasheet, " +
                "select Summary Data from Datasheet. Select the column " +
                "containing categories and the column containing " +
                "the respective frequencies.");
        helpStrings.add("If data values are entered in a single " +
                "column in the datasheet, select Data Values from Datasheet. " +
                "Select the column containing data values.");
        helpStrings.add("Select whether to sort the pie slices by categories " +
                "or by frequencies.");
        helpStrings.add("To display legends, select the Show Legends check box.");
        helpStrings.add("To display values/percentages for each pie sector, " +
                "select Show Values/Percentages for each Pie Sector.");
        pack();
    }

    /**
     * Updates elements on the dialog so that they have the most
     * current values.  Called by {@link #setVisible} to make
     * sure the dialog displays current values when made visible again.
     * 
     * @see #setVisible(boolean)
     */
    @Override
    public void updateElements() {
        updateComboBox(CategoryComboBox);
        updateComboBox(FrequencyComboBox);
        updateComboBox(DataComboBox);       
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OKButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        CategoryComboBox = new javax.swing.JComboBox();
        SummaryRadioButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        ValuesRadioButton = new javax.swing.JRadioButton();
        FrequencyComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        DataComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        LabelCheckBox = new javax.swing.JCheckBox();
        LegendCheckBox = new javax.swing.JCheckBox();
        SortCatRadioButton = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        TitleTextField = new javax.swing.JTextField();
        SortFreqRadioButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pie Chart");

        OKButton.setText("OK");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Graph Variables"));

        SummaryRadioButton.setSelected(true);
        SummaryRadioButton.setText("Summary Data from Worksheet");
        SummaryRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SummaryRadioButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Categories:");

        ValuesRadioButton.setText("Data Values from Worksheet");
        ValuesRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValuesRadioButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Data:");
        jLabel3.setEnabled(false);

        DataComboBox.setEnabled(false);

        jLabel2.setText("Frequencies:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SummaryRadioButton)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3))
                    .addComponent(ValuesRadioButton)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(DataComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FrequencyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SummaryRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(CategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(FrequencyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ValuesRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(DataComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Graph Options"));

        LabelCheckBox.setSelected(true);
        LabelCheckBox.setText("Show Values/Percentages for each Pie Sector");

        LegendCheckBox.setSelected(true);
        LegendCheckBox.setText("Show Legends");

        SortCatRadioButton.setSelected(true);
        SortCatRadioButton.setText("Sort by Categories");

        jLabel4.setText("Chart Title:");

        TitleTextField.setText("Pie Chart");

        SortFreqRadioButton.setText("Sort by Frequencies");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TitleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SortCatRadioButton)
                    .addComponent(SortFreqRadioButton)
                    .addComponent(LegendCheckBox)
                    .addComponent(LabelCheckBox))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(TitleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SortCatRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SortFreqRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LegendCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelCheckBox)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(OKButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CancelButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CancelButton, OKButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelButton)
                    .addComponent(OKButton))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SummaryRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SummaryRadioButtonActionPerformed
        // enable combo boxes related to summary data
        CategoryComboBox.setEnabled(true);
        FrequencyComboBox.setEnabled(true);
        // disable components related to using data values
        ValuesRadioButton.setEnabled(false);
        DataComboBox.setEnabled(false);

    }//GEN-LAST:event_SummaryRadioButtonActionPerformed

    private void ValuesRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValuesRadioButtonActionPerformed
        // enabled components related to using data values
        DataComboBox.setEnabled(true);
        // disable components related to using summary data
        CategoryComboBox.setEnabled(false);
        FrequencyComboBox.setEnabled(false);

    }//GEN-LAST:event_ValuesRadioButtonActionPerformed

    @SuppressWarnings("unchecked") 
    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        app.compoundEdit = new DialogEdit("pie chart");
        
        DefaultPieDataset data = new DefaultPieDataset();
        String heading = "";
        String text = "";
        
        // use summary data
        if (SummaryRadioButton.isSelected()) {
            // get frequencies
            String freqColumnLabel = FrequencyComboBox.getSelectedItem().toString();
            if (freqColumnLabel.equals("")) { // no frequency variable
                app.showErrorDialog("Select the column containing frequencies.");
                return;
            }
            int freqColumn = ParentSpreadsheet.parseColumnNumber(freqColumnLabel);
            Vector<Cell> CellColumnVector = ParentSpreadsheet.getColumn(freqColumn);
            Vector<Double> ColumnVector = HelperFunctions.ConvertInputVectorToDoubles(CellColumnVector);
            if (ColumnVector == null) {
                app.showErrorDialog("Invalid frequency column " + freqColumnLabel +
                    ": all data must be numbers.");
                return;
            }             
            
            // get categories
            String catColumnLabel = CategoryComboBox.getSelectedItem().toString();
            if (catColumnLabel.equals("")) { // no input variable
                app.showErrorDialog("Select the column containing categories.");
                return;
            }
            int catColumn = ParentSpreadsheet.parseColumnNumber(catColumnLabel);
            Vector<Cell> CatColumnVector = ParentSpreadsheet.getColumn(catColumn);
            
            Hashtable hash = new Hashtable();
            
            for (int i = 0; i < CatColumnVector.size(); ++i) {
                String cat = CatColumnVector.elementAt(i).getContents();
                if (!cat.equals("")) {
                    Double freq = ColumnVector.elementAt(i);
                    if (freq == null) {
                        app.showErrorDialog("Missing frequency for category " + cat);
                        return;
                    }
                    if (hash.containsKey(cat)) { // category is in hash already
                        double f = ((Double)hash.get(cat)).doubleValue() 
                                + freq.doubleValue(); // update count
                        hash.put(cat, new Double(f));
                    }
                    else { // put num in hash
                        hash.put(cat, freq);
                    }
                }
            }
            
            // create pie chart data
            for (Enumeration e = hash.keys(); e.hasMoreElements();) {
                String key = (String)e.nextElement();
                data.setValue(key, (Double)hash.get(key));
            }            
            heading += "Pie Chart";
            text += "Categories in " + catColumnLabel + ", frequencies in "
                    + freqColumnLabel;
        }
        // use data values from Datasheet
        else if (ValuesRadioButton.isSelected()) {
            // get categories
            String catColumnLabel = DataComboBox.getSelectedItem().toString();
            if (catColumnLabel.equals("")) { // no input variable
                app.showErrorDialog("Select the column containing data values.");
                return;
            }
            int catColumn = ParentSpreadsheet.parseColumnNumber(catColumnLabel);
            Vector<Cell> CatColumnVector = ParentSpreadsheet.getColumn(catColumn);
            
            Hashtable hash = new Hashtable();
            
            for (int i = 0; i < CatColumnVector.size(); ++i) {
                String cat = CatColumnVector.elementAt(i).getContents();
                if (!cat.equals("")) {

                    if (hash.containsKey(cat)) { // category is in hash already
                        int f = ((Integer)hash.get(cat)).intValue() + 1; // update count
                        hash.put(cat, new Integer(f));
                    }
                    else { // put num in hash
                        hash.put(cat, new Integer(1));
                    }
                }
            }
            
            // create pie chart data
            for (Enumeration e = hash.keys(); e.hasMoreElements();) {
                String key = (String)e.nextElement();
                data.setValue(key, (Integer)hash.get(key));
            }       
            
            heading += "Pie Chart";
            text += "Data values in " + catColumnLabel;
        }
        
        if (SortCatRadioButton.isSelected())
            data.sortByKeys(org.jfree.util.SortOrder.ASCENDING);
        else
            data.sortByValues(org.jfree.util.SortOrder.ASCENDING);
        
       
        app.addLogParagraph(heading, text);

        app.compoundEdit.end();
        app.addCompoundEdit(app.compoundEdit);
        
        StatcatoChartFrame frame = 
                new StatcatoChartFrame(
                TitleTextField.getText(),
                GraphFactory.createPieChart(
                TitleTextField.getText(),
                data,
                LegendCheckBox.isSelected(),
                LabelCheckBox.isSelected()),
                app);
        frame.pack();
        frame.setVisible(true);
        
        setVisible(false);
    }//GEN-LAST:event_OKButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_CancelButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JComboBox CategoryComboBox;
    private javax.swing.JComboBox DataComboBox;
    private javax.swing.JComboBox FrequencyComboBox;
    private javax.swing.JCheckBox LabelCheckBox;
    private javax.swing.JCheckBox LegendCheckBox;
    private javax.swing.JButton OKButton;
    private javax.swing.JRadioButton SortCatRadioButton;
    private javax.swing.JRadioButton SortFreqRadioButton;
    private javax.swing.JRadioButton SummaryRadioButton;
    private javax.swing.JTextField TitleTextField;
    private javax.swing.JRadioButton ValuesRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
    
}
