/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

/**
 *
 * @author Romeu Franzoia Jr
 */
import java.io.File;
import javax.swing.filechooser.FileFilter;
 
/* ImageFilter.java is used by FileChooserDemo2.java. */
public class ImageFilter extends FileFilter {
 
    //Accept all directories and all gif, jpg, tiff, or png files.
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
 
        String extension = Utils.extractFileExtension(f);
        if (extension != null) {
            if (extension.equals(Constantes.TIFF) ||
                extension.equals(Constantes.TIF) ||
                extension.equals(Constantes.GIF) ||
                extension.equals(Constantes.JPEG) ||
                extension.equals(Constantes.JPG) ||
                extension.equals(Constantes.PNG)) {
                    return true;
            } else {
                return false;
            }
        }
 
        return false;
    }
 
    //The description of this filter
    @Override
    public String getDescription() {
        return "Just Images";
    }
}
