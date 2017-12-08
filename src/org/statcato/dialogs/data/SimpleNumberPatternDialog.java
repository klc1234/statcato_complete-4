/*
 * SimpleNumberPatternDialog.java
 *
 * Created on April 14, 2008, 10:21 AM
 */

package org.statcato.dialogs.data;

import org.statcato.*;
import java.util.Vector;

/**
 * A dialog for generating a simple number pattern in which numbers of evenly spaced.
 * Each number in the pattern, as well as the whole pattern,
 * can be repeated for a specified number of times.
 * 
 * @author  Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class SimpleNumberPatternDialog extends StatcatoDialog {

    /** Creates new form SimpleNumberPatternDialog */
    public SimpleNumberPatternDialog(java.awt.Frame parent, boolean modal, Statcato app) {
        super(parent, modal);
        ParentSpreadsheet = app.getSpreadsheet();
        this.app = app;        
        initComponents();
        
        getRootPane().setDefaultButton(OKButton);
        setHelpFile("data-number-sequence");
        name = "Simple Number Sequence";
        description = "For generating a simple number sequence.";
        helpStrings.add("Provide the column(s) in which the results will be stored.");
        helpStrings.add("Specify the first and last value of the sequence, " +
                "the step size, the number of times a value is repeated, " +
                "and the number of times the whole sequence of values is repeated.");
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
        StoreTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        RepeatSeqTextField = new javax.swing.JTextField();
        RepeatItemTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        StartTextField = new javax.swing.JTextField();
        EndTextField = new javax.swing.JTextField();
        StepTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Simple Number Sequence");

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Results"));

        jLabel1.setText("Store number pattern in:");

        jLabel7.setText("<html>\n- Enter valid column names separated by space.<br>\nFor a continuous range of columns, separate using dash (e.g. C1-C30).<br>\n- The random sampling process is repeated for each column.\n</html>\n");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(StoreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(StoreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));

        jLabel4.setText("In steps of:");

        jLabel2.setText("From first number:");

        RepeatSeqTextField.setText("1");

        RepeatItemTextField.setText("1");

        jLabel5.setText("Number of times to list each number:");

        StepTextField.setText("1");

        jLabel6.setText("Number of times to list each sequence:");

        jLabel3.setText("To last number:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RepeatItemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RepeatSeqTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(StepTextField)
                            .addComponent(EndTextField)
                            .addComponent(StartTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {RepeatItemTextField, RepeatSeqTextField});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(StartTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EndTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(StepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(RepeatItemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(RepeatSeqTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(OKButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CancelButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 369, Short.MAX_VALUE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CancelButton, OKButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelButton)
                    .addComponent(OKButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        app.compoundEdit = new DialogEdit("number pattern");
        
        // get and check column for storing data pattern
        int storeColumnNum;
        String StoreColumn = StoreTextField.getText();

        // get and check the first value, last value, and step value
        double first = 0;
        try {
            first = Double.parseDouble(StartTextField.getText());
        }
        catch (NumberFormatException e) {
            app.showErrorDialog("Enter a valid number for the first value.");
            return;
        }

        double last = 0;
        try {
            last = Double.parseDouble(EndTextField.getText());          
        }
        catch (NumberFormatException e) {
            app.showErrorDialog("Enter a valid number for the last value.");
            return;
        }

        double step = 0;
        try {
            step = Double.parseDouble(StepTextField.getText());
            if (step == 0) {
                app.showErrorDialog("Step must be non-zero");
                return;
            }
        }
        catch (NumberFormatException e) {
            app.showErrorDialog("Enter a valid number for step.");
            return;
        }
        
        // get and check repetition values
        int repeatNum = 0;
        try {
            repeatNum = Integer.parseInt(RepeatItemTextField.getText());
        }
        catch (NumberFormatException e) {
            app.showErrorDialog("Enter an integer for the number of repetitions for each value.");
            return;
        }
        int repeatSeq = 0;
        try {
            repeatSeq = Integer.parseInt(RepeatSeqTextField.getText());            
        }
        catch (NumberFormatException e) {
            app.showErrorDialog("Enter an integer for the number of repetitions for the number pattern.");
            return;
        }
        
        // generate the number sequence as a vector
        Vector<String> Pattern = new Vector<String>();
        for (int seq = 0; seq < repeatSeq; ++seq) {
            if (first <= last) {    // increasing sequence
                if (step < 0) 
                    step = -step;
                double num = first;
                while (num <= last) {
                    for (int k = 0; k < repeatNum; ++k) {
                        Pattern.addElement(num + "");
                    }
                    num += step;
                }
            }
            else {
                if (step > 0)
                    step = -step;
                double num = first;
                while (num >= last) {
                    for (int k = 0; k < repeatNum; ++k) {
                        Pattern.addElement(num + "");
                    }
                    num += step;
                }
            }
        }
        
        Vector<Integer> nums = ParentSpreadsheet.getColumnNumbersFromString(StoreColumn);
        if (nums == null) {
            app.showErrorDialog(
                            "Invalid column(s) for storing results.");
            return;
        }

            for (int i = 0; i < nums.size(); ++i) {
                // store results
                ParentSpreadsheet.setColumn(nums.elementAt(i).intValue(), Pattern);
            }

            app.addLogParagraph("Generate Simple Number Sequence in " + StoreColumn,
                    "First: " + first + " - Last: " + last + "<br>" +
                    "Step size: " + step + "<br>" +
                    "Number of times to list each number: " + repeatNum + "<br>" +
                    "Number of times to list each sequence: " + repeatSeq);

            app.compoundEdit.end();
            app.addCompoundEdit(app.compoundEdit);

            setVisible(false);

    }//GEN-LAST:event_OKButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_CancelButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JTextField EndTextField;
    private javax.swing.JButton OKButton;
    private javax.swing.JTextField RepeatItemTextField;
    private javax.swing.JTextField RepeatSeqTextField;
    private javax.swing.JTextField StartTextField;
    private javax.swing.JTextField StepTextField;
    private javax.swing.JTextField StoreTextField;
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
