/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.utils;

import javax.swing.JLabel;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A timer for setting a status label.
 *
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class SetStatusTimer {
    String originalStatus;
    JLabel statusLabel;
    String status;
    Timer timer;

    /**
     * Constructor.
     *
     * @param status status JLabel
     * @param seconds number of seconds in which the clearing action will be
     * performed
     */
    public SetStatusTimer(JLabel statusLabel, String newStatus, int seconds) {
        timer = new Timer();
        originalStatus = statusLabel.getText();
        this.statusLabel = statusLabel;
        status = newStatus;
        timer.schedule(new SetTask(), seconds*1000);
	}

    /**
     * Cancels the timer.
     */
    public void cancelTimer() {
        try {
            timer.cancel();
        }
        catch (IllegalStateException e) {
        }
    }
    
    class SetTask extends TimerTask {
        @Override
        public void run() {
            statusLabel.setText(status);
            timer.cancel(); //Terminate the timer thread
        }
    }

}
