/*
 * TableCellRenderer for Variable Header
 */

package org.statcato.spreadsheet;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;

/**
 * A table cell renderer for the variable header.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
class VarHeaderRenderer extends DefaultTableCellRenderer
{
    /**
     * Constructor.  Sets opacity, background color, foreground color,
     * font, and alignment.
     */
    public VarHeaderRenderer()
    {
        super();
        setOpaque(true);
        setBorder(null);
        setBackground(UIManager.getColor("TableHeader.background"));
        setForeground(UIManager.getColor("TableHeader.foreground"));
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setFont(UIManager.getFont("TableHeader.font"));
        setHorizontalAlignment(SwingConstants.CENTER);  

    }

}
