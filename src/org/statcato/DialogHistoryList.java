/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato;

import org.statcato.utils.MutableList;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

/**
 * A dialog history list.
 *
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class DialogHistoryList extends MutableList {
    /**
     * Length of the dialog history.
     */
    public static int length = 21;
    /**
     * Internal list of dialogs.
     */
    private ArrayList<StatcatoDialog> history;

    /**
     * Constructor.  
     */
    public DialogHistoryList() {
        history = new ArrayList<StatcatoDialog>();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setLayoutOrientation(JList.VERTICAL);
        setVisibleRowCount(-1);
        addListSelectionListener(new HistoryListListener());
        StatcatoDialog dummy = new StatcatoDialog(null, true);
        dummy.setDialogName("Select to open a recently used dialog");
        history.add(dummy);
        setListData(history.toArray());
    }

    /**
     * Adds a dialog to the history list.  Maintains a constant number of
     * dialog by removing the least recently used dialog if necessary.
     *
     * @param dialog a Statcatodialog instance
     */
    public void addDialog(StatcatoDialog dialog) {
        if (history.size() == length) { // history is full
            // remove the oldest dialog
            history.remove(1);
        }
        if (history.contains(dialog))
            history.remove(dialog);
        history.add(dialog);

        setListData(history.toArray());
    }

    /**
     * Returns the dialog at the given position.
     *
     * @param index position of the dialog to be returned
     * @return dialog at the given index
     */
    public StatcatoDialog getDialog(int index) {
        return history.get(index);
    }

    /**
     * A list selection listener for the history list.
     */
    class HistoryListListener implements ListSelectionListener {
        /**
         * Displays the selected dialog.
         *
         * @param e ListSelectionEvent instance
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {
                if (getSelectedIndex() > 0) {
                    getDialog(getSelectedIndex()).setVisible(true);
                    setSelectedIndex(0);
                }
            }
        }
    }

}

