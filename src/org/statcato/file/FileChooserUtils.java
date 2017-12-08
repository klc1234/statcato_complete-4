/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.file;

import java.io.File;

/**
 * Utilities functions for files.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class FileChooserUtils {
    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public final static String txt = "txt";
    public final static String html = "html";
    public final static String pdf = "pdf";
    
    /**
     * Returns the extension of a file.
     * 
     * @param f file object
     * @return file extension
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();

        return getExtension(s);
    }
    
    /**
     * Returns the extension of a file name
     * 
     * @param s file name
     * @return file extension
     */
    public static String getExtension(String s) {
        String ext = null;

        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}