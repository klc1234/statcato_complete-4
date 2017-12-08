package org.statcato.file;

import org.statcato.file.FileChooserUtils;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * Filter for plain text file types.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class TextFileFilter extends FileFilter {
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        String extension = FileChooserUtils.getExtension(f);
        if (extension != null) {
            if (extension.equals(FileChooserUtils.txt))
                return true;
            else 
                return false;
        }
        return false;
    }
    
    public String getDescription() {
        return "Plain text (.txt)";
    }
}
