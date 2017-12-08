/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.utils;

import javax.swing.JPopupMenu;
import java.awt.event.*;

/**
 * A mouse listener for popups.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class PopupListener extends MouseAdapter {
        JPopupMenu popup;

        /**
         * Constructor, given a popup menu for which the listener is used.
         * 
         * @param popupMenu popup menu corresponding to this listener
         */
        public PopupListener(JPopupMenu popupMenu) {
            popup = popupMenu;
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        /**
         * Shows the popup menu if the given mouse event should trigger
         * a popup.
         * 
         * @param e mouse event
         */
        public void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
    }
