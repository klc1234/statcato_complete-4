/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato;

import javax.swing.undo.CompoundEdit;

/**
 * A compound edit for a dialog.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class DialogEdit extends CompoundEdit {
    String name;
    
    public DialogEdit(String name) {
        super();
        this.name = name;
    }
    
    @Override
    public String getPresentationName() {
        return name;
    }
    
    @Override
    public String getRedoPresentationName() {
        return "Redo " + name;
    }
    
    @Override
    public String getUndoPresentationName() {
        return "Undo " + name;
    }
}
