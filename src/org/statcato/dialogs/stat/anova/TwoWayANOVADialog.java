/*
 * TwoWayANOVADialog.java
 *
 * Created on October 27, 2008, 12:02 PM
 */

package org.statcato.dialogs.stat.anova;

import org.statcato.*;
import org.statcato.spreadsheet.Cell;
import org.statcato.utils.HelperFunctions;
import org.statcato.utils.CategoryCounter;
import org.statcato.statistics.inferential.*;
import java.util.Vector;

/**
 * A dialog for two-way ANOVA (analysis of variance).
 *
 * @author  Margaret Yau
 * @version %I%, %G%
 * @see org.statcato.statistics.inferential.TwoWayANOVA
 * @since 1.0
 */
public class TwoWayANOVADialog extends StatcatoDialog {
    
    /** Creates new form TwoWayANOVADialog */
    public TwoWayANOVADialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.app = (Statcato)parent;
        this.ParentSpreadsheet = app.getSpreadsheet(); 
        initComponents();
        getRootPane().setDefaultButton(OKButton);
        
        ParentSpreadsheet.populateComboBox(ResponseComboBox);
        ParentSpreadsheet.populateComboBox(RowComboBox);
        ParentSpreadsheet.populateComboBox(ColComboBox);
        
        setHelpFile("stat-anova2");
        name = "Two-Way ANOVA";
        description = "For performing two-way ANOVA.";
        helpStrings.add("In the Response drop-down menu, select the column " +
                "contains the observations.");
        helpStrings.add("Select the column containing the row factor labels " +
                "corresponding to the observations.");
        helpStrings.add("Select the column containing the column factor" +
                " labels corresponding to the observations.");
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
        updateComboBox(ResponseComboBox); 
        updateComboBox(RowComboBox); 
        updateComboBox(ColComboBox); 
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ResponseComboBox = new javax.swing.JComboBox();
        RowComboBox = new javax.swing.JComboBox();
        ColComboBox = new javax.swing.JComboBox();
        OKButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Two-Way ANOVA");

        jLabel1.setText("Response:");

        jLabel2.setText("Row Factor:");

        jLabel3.setText("Column Factor:");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(OKButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CancelButton)
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ColComboBox, 0, 122, Short.MAX_VALUE)
                            .addComponent(ResponseComboBox, 0, 122, Short.MAX_VALUE)
                            .addComponent(RowComboBox, 0, 122, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ResponseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(RowComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ColComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OKButton)
                    .addComponent(CancelButton))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        app.compoundEdit = new DialogEdit("two-way ANOVA");
        
        String heading = "Two-way ANOVA";
        String text = "";
        
        // response column
        String columnLabel = ResponseComboBox.getSelectedItem().toString();
        text += "responses in " + columnLabel;
        if (columnLabel.equals("")) { // no input variable
            app.showErrorDialog("Select the input variable.");
            return;
        }
        int selectedXColumn = ParentSpreadsheet.parseColumnNumber(columnLabel);
        Vector<Cell> StrColumnVector = 
                ParentSpreadsheet.getColumn(selectedXColumn);
        StrColumnVector = HelperFunctions.removeEndingEmptyCells(StrColumnVector);
        // get response column of doubles
        Vector<Double> ResponseVector =
                HelperFunctions.ConvertInputVectorToDoubles(StrColumnVector);
        if (ResponseVector == null) {
            app.showErrorDialog("Invalid response column " + columnLabel +
                    ": all data must be numbers.");
            return;
        }
        
        // row labels column
        String rowLabel = RowComboBox.getSelectedItem().toString();
        text += ", row factor " + rowLabel;
        if (rowLabel.equals("")) { // no row variable
            app.showErrorDialog("Select the row factor variable.");
            return;
        }
        int rowColumn = ParentSpreadsheet.parseColumnNumber(rowLabel);
        Vector<Cell> RowVector = ParentSpreadsheet.getColumn(rowColumn);
        RowVector = HelperFunctions.removeEndingEmptyCells(RowVector);
        if (RowVector.size() < ResponseVector.size()) {
            app.showErrorDialog("missing row factor label(s)");
            return;
        }
        
        // column labels column
        String colLabel = ColComboBox.getSelectedItem().toString();
        text += ", column factor " + colLabel + "<br>";
        if (colLabel.equals("")) { // no col variable
            app.showErrorDialog("Select the column factor variable.");
            return;
        }
        int colColumn = ParentSpreadsheet.parseColumnNumber(colLabel);
        Vector<Cell> ColVector = ParentSpreadsheet.getColumn(colColumn);
        ColVector = HelperFunctions.removeEndingEmptyCells(ColVector);
        if (ColVector.size() < ResponseVector.size()) {
            app.showErrorDialog("missing column factor label(s)");
            return;
        }
        
        // set up category counters for row and column
        CategoryCounter rowCategoryCounter = new CategoryCounter();
        for (int i = 0; i < RowVector.size(); ++i)
            rowCategoryCounter.addCategory(RowVector.elementAt(i).getContents());
        CategoryCounter colCategoryCounter = new CategoryCounter();
        for (int i = 0; i < ColVector.size(); ++i)
            colCategoryCounter.addCategory(ColVector.elementAt(i).getContents());
        
        TwoWayANOVA anova = new TwoWayANOVA(rowCategoryCounter.getSize(),
                colCategoryCounter.getSize());
        
        // add observations to TwoWayANOVA object
        for (int i = 0; i < ResponseVector.size(); ++i) {
            Double value = ResponseVector.elementAt(i);
            if (value != null) {
                double observation = value.doubleValue();
                Cell row = RowVector.elementAt(i);
                Cell col = ColVector.elementAt(i);
                if (row == null || col == null || 
                        row.getContents().equals("") || 
                        col.getContents().equals("")) {
                    app.showErrorDialog("Missing row or column label for data "
                            + observation);
                    return;
                }
                anova.addObservation(rowCategoryCounter.getIndex(row.getContents()), 
                        colCategoryCounter.getIndex(col.getContents()), observation);
            }
        }
        
        if (!anova.isValidData()) {
            app.showErrorDialog("Invalid data for two-way ANOVA--" +
                    "all treatment group must be of the same size.");
            return;
        }
        
        // print results
       
        text += anova;
        
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
    private javax.swing.JComboBox ColComboBox;
    private javax.swing.JButton OKButton;
    private javax.swing.JComboBox ResponseComboBox;
    private javax.swing.JComboBox RowComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
    
}
