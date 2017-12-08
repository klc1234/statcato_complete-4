/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.utils;

import javax.swing.*;

/**
 * A mutable list.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class MutableList extends JList {
    public MutableList() {
	super(new DefaultListModel());
    }
    
    /**
     * Gets the list model.
     * 
     * @return list model
     */
    public DefaultListModel getContents() {
	return (DefaultListModel)getModel();
    }
}   