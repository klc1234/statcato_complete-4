/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.utils;

import org.statcato.file.Project;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author myau
 */
public class SetProjectAutoSaveTimer {
    Project project;
    Timer timer;
    final int startDelay = 60000;
    int saveInterval;

    /**
     * Constructor.
     *
     * @param project for which the timer is set to auto save
     * @param seconds number of seconds in which the auto save action will be
     * performed
     */
    public SetProjectAutoSaveTimer(Project project, int seconds) {
        timer = new Timer();
        this.project = project;
        saveInterval = seconds;
        timer.scheduleAtFixedRate(new AutoSaveTask(), startDelay, seconds*1000);
	}

    /** 
     * Sats the save interval to the given value.  Restarts timer.
     * 
     * @param seconds auto save interval in seconds
     */
    public void setInterval(int seconds) {
        saveInterval = seconds;
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new AutoSaveTask(), startDelay, seconds*1000);
    }

    class AutoSaveTask extends TimerTask {
        @Override
        public void run() {
            project.autoSaveWrite();
        }
    }

}
