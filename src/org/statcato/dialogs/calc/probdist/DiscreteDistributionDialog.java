/*
 * DiscreteDistributionDialog.java
 *
 * Created on May 19, 2008, 4:26 PM
 */

package org.statcato.dialogs.calc.probdist;

import org.statcato.*;
import org.statcato.spreadsheet.*;
import org.statcato.utils.HelperFunctions;
import org.statcato.statistics.*;
import javax.swing.*;
import java.util.*;


/**
 * A dialog for discrete probability distribution,
 * where probabilites of discrete values are specified.
 * Provides options to compute the probability density, cumulative
 * probability, and inverse cumulative probability.
 * 
 * @author  Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class DiscreteDistributionDialog extends StatcatoDialog {

    /** Creates new form DiscreteDistributionDialog */
    public DiscreteDistributionDialog(java.awt.Frame parent, boolean modal,
            Statcato app) {
        super(parent, modal);
        this.app = app;
        this.ParentSpreadsheet = app.getSpreadsheet();    
        initComponents();
        
        ButtonGroup group1 = new ButtonGroup();
        group1.add(ProbDensityRadioButton);
        group1.add(CumProbRadioButton);
        group1.add(InvCumProbRadioButton);
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(ConstRadioButton);
        group2.add(ColumnRadioButton);
        
        ParentSpreadsheet.populateComboBox(ColumnComboBox); 
        ParentSpreadsheet.populateComboBox(ValuesComboBox);
        ParentSpreadsheet.populateComboBox(ProbabilitiesComboBox);
        
        getRootPane().setDefaultButton(OKButton);
        setHelpFile("calc-discrete");
        name = "Discrete Probability Distribution";
        description = "For calculations on discrete probability distribution.";
        helpStrings.add("Select the columns containing discrete values and " +
                "their corresponding probabilities.");
        helpStrings.add("Choose probability density for P(x), cumulative " +
                "probability for (P(<= x), and inverse cumulative probability " +
                "for finding x given a such that P(<= x) = a.");
        helpStrings.add("Inputs can be numbers within a column or a single " +
                "constant.");
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
        updateComboBox(ColumnComboBox);
        updateComboBox(ValuesComboBox);
        updateComboBox(ProbabilitiesComboBox);
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
        ProbDensityRadioButton = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        CumProbRadioButton = new javax.swing.JRadioButton();
        ProbabilitiesComboBox = new javax.swing.JComboBox();
        ValuesComboBox = new javax.swing.JComboBox();
        InvCumProbRadioButton = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        ColumnComboBox = new javax.swing.JComboBox();
        ConstRadioButton = new javax.swing.JRadioButton();
        StoreTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ConstTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ColumnRadioButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Discrete Probability Distribution");

        OKButton.setText("Compute");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("Close");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Distribution"));

        ProbDensityRadioButton.setSelected(true);
        ProbDensityRadioButton.setText("Probability density");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Compute:");

        jLabel3.setText("Probabilities in column:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Distribution Parameters:");

        CumProbRadioButton.setText("Cumulative probability");

        InvCumProbRadioButton.setText("Inverse cumulative probability");

        jLabel2.setText("Values in column:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(InvCumProbRadioButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(ProbDensityRadioButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(CumProbRadioButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ProbabilitiesComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ValuesComboBox, 0, 111, Short.MAX_VALUE))))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ValuesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ProbabilitiesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProbDensityRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CumProbRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InvCumProbRadioButton)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Inputs and Outputs"));

        ConstRadioButton.setText("Constant:");
        ConstRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ConstRadioButtonStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Input(s):");

        ConstTextField.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Store Results in: (optional)");

        jLabel7.setText("(e.g. C1 for column label, or variable name)");

        ColumnRadioButton.setSelected(true);
        ColumnRadioButton.setText("Column:");
        ColumnRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ColumnRadioButtonStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ConstRadioButton)
                            .addComponent(ColumnRadioButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ConstTextField)
                            .addComponent(ColumnComboBox, 0, 111, Short.MAX_VALUE)))
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(StoreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ColumnRadioButton)
                    .addComponent(ColumnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ConstRadioButton)
                    .addComponent(ConstTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StoreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(OKButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OKButton)
                    .addComponent(CancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ConstRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ConstRadioButtonStateChanged
        if (ConstRadioButton.isSelected()) {
            ColumnComboBox.setEnabled(false);
            ConstTextField.setEnabled(true);
        } else {
            ColumnComboBox.setEnabled(true);
            ConstTextField.setEnabled(false);
        }
    }//GEN-LAST:event_ConstRadioButtonStateChanged

    private void ColumnRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ColumnRadioButtonStateChanged
        if (ColumnRadioButton.isSelected()) {
            ColumnComboBox.setEnabled(true);
            ConstTextField.setEnabled(false);
        } else {
            ColumnComboBox.setEnabled(false);
            ConstTextField.setEnabled(true);
        }
    }//GEN-LAST:event_ColumnRadioButtonStateChanged

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        app.compoundEdit = new DialogEdit("discrete pdf");
        
        int min = 0, max = 0;
        
        String heading = "Discrete Distribution: ";
        String text = "";

        // read values and probabilities from selected columns
        String valuesLabel = ValuesComboBox.getSelectedItem().toString();
        if (valuesLabel.equals("")) { // no values column
                app.showErrorDialog("Select the column containing values.");
                return;
            }
        int valueCol = ParentSpreadsheet.parseColumnNumber(valuesLabel);
        Vector<Cell> CellVector = ParentSpreadsheet.getColumn(valueCol);
        Vector<Double> ValuesVector = HelperFunctions.ConvertInputVectorToDoubles(CellVector);
        if (ValuesVector == null) {
            app.showErrorDialog("Invalid values column " + valuesLabel +
                        ": all data must be numbers.");
            return;
        }
        ValuesVector = HelperFunctions.removeNullValues(ValuesVector);
        text += "Values in : " + valuesLabel + "<br>";

        String probsLabel = ProbabilitiesComboBox.getSelectedItem().toString();
        if (probsLabel.equals("")) { // no probabilities column
                app.showErrorDialog("Select the column containing probabilities.");
                return;
        }
        int probCol = ParentSpreadsheet.parseColumnNumber(probsLabel);
        CellVector = ParentSpreadsheet.getColumn(probCol);
        Vector<Double> ProbsVector = HelperFunctions.ConvertInputVectorToDoubles(CellVector);
        if (ProbsVector == null) {
            app.showErrorDialog("Invalid probabilities column " + probsLabel +
                        ": all data must be numbers.");
            return;
        }
        // check that probabilities add up to one
        if (BasicStatistics.sum(ProbsVector) != 1) {
            app.showErrorDialog("Invalid probabilities column " + probsLabel +
                        ": probabilities must add up to 1.");
            return;
        }
        ProbsVector = HelperFunctions.removeNullValues(ProbsVector);    
        text += "Probabilities in : " + probsLabel + "<br>";
        
        if (ValuesVector.size() != ProbsVector.size()) {
            app.showErrorDialog("The number of values must be the same as" +
                    " the number of probabilities provided.");
            return;
        }
        
        Vector<Double> ColumnVector = new Vector<Double>();
        
        // get input
        if (ColumnRadioButton.isSelected()) {
            // get data for selected input column
            String columnLabel = ColumnComboBox.getSelectedItem().toString();
            if (columnLabel.equals("")) { // no input variable
                app.showErrorDialog("Select the input column.");
                return;
            }
            int column = ParentSpreadsheet.parseColumnNumber(columnLabel);
            Vector<Cell> StrColumnVector = ParentSpreadsheet.getColumn(column);
            ColumnVector = HelperFunctions.ConvertInputVectorToDoubles(StrColumnVector);
            if (ColumnVector == null) {
                app.showErrorDialog("Invalid input column " + columnLabel +
                        ": all data must be numbers.");
                return;
            }
            text += "Input: " + columnLabel + "<br>";
        } else {
            // get input from constant text field
            try {
                double num = Double.parseDouble(ConstTextField.getText());
                ColumnVector.addElement(num);
                text += "Input: " + num + "<br>";
                
            } catch (NumberFormatException e) {
                app.showErrorDialog("Invalid constant input.  Enter a valid number.");
                return;
            }
        }
        
        // compute results
        DiscreteCustomProbabilityDistribution npd = new DiscreteCustomProbabilityDistribution(
                ValuesVector, ProbsVector);
        Vector<String> ResultVector = new Vector<String>();
        if (ProbDensityRadioButton.isSelected()) {
            text += "Type: Probability density<br>";
            text += "<table>";
            text += "<tr><td>X</td><td>P(X)</td></tr>";
            for (int i = 0; i < ColumnVector.size(); ++i) {
                Double elm = ColumnVector.elementAt(i);
                if (elm != null) {
                    double x = elm.doubleValue();
                    double density = npd.density(x);
                    text += "<tr><td>" + x + "</td><td>" +
                            HelperFunctions.formatFloat(density, 6) + "</td></tr>";
                    ResultVector.addElement(density+"");
                }
            }
            text += "</table>";
        } else if (CumProbRadioButton.isSelected()) {
            text += "Type: Cumulative probability<br>";
            text += "<table>";
            text += "<tr><td>X</td><td>P(<&lt;=X)</td></tr>";
            for (int i = 0; i < ColumnVector.size(); ++i) {
                Double elm = ColumnVector.elementAt(i);
                if (elm != null) {
                    double x = elm.doubleValue();
                    double density = npd.cumulativeProbability(x);
                    text += "<tr><td>" + x + "</td><td>" +
                            HelperFunctions.formatFloat(density, 6) + "</td></tr>";
                    ResultVector.addElement(density+"");
                }
            }
            text += "</table>";
        } else if (InvCumProbRadioButton.isSelected()) {
            text += "Type: Inverse cumulative probability<br>";
            text += "<table>";
            text += "<tr><td>P(<&lt;=X)</td><td>X</td></tr>";
            for (int i = 0; i < ColumnVector.size(); ++i) {
                Double elm = ColumnVector.elementAt(i);
                if (elm != null) {
                    double area = elm.doubleValue();
                    Double x = npd.inverseCumulativeProbability(area);
                    if (x == null) {
                        text += "<tr><td>" + area + "</td><td>" +
                                "*" + "</td></tr>";
                        ResultVector.addElement("*");
                    } else {
                        text += "<tr><td>" + area + "</td><td>" +
                                x.toString() + "</td></tr>";
                        ResultVector.addElement(x.doubleValue() + "");
                    }
                }
            }
            text += "</table>";
        }
        
        String storeColumn = StoreTextField.getText();
        if (!storeColumn.equals("")) {
            // check if Column is valid
            int storeColumnNum = ParentSpreadsheet.getColumnNumber(storeColumn);
            if (storeColumnNum == -1) { // invalid column
                app.showErrorDialog("Enter a valid column name (e.g. C1) or a valid variable name to store the results.");
                return;
            }
            ParentSpreadsheet.setColumn(storeColumnNum, ResultVector);
        }
        
        app.addLogParagraph(heading, text);
        app.compoundEdit.end();
        app.addCompoundEdit(app.compoundEdit);
        
        setVisible(false);
    }//GEN-LAST:event_OKButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_CancelButtonActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JComboBox ColumnComboBox;
    private javax.swing.JRadioButton ColumnRadioButton;
    private javax.swing.JRadioButton ConstRadioButton;
    private javax.swing.JTextField ConstTextField;
    private javax.swing.JRadioButton CumProbRadioButton;
    private javax.swing.JRadioButton InvCumProbRadioButton;
    private javax.swing.JButton OKButton;
    private javax.swing.JRadioButton ProbDensityRadioButton;
    private javax.swing.JComboBox ProbabilitiesComboBox;
    private javax.swing.JTextField StoreTextField;
    private javax.swing.JComboBox ValuesComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
    
}
