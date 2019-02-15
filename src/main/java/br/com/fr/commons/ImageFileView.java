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
import javax.swing.filechooser.*;

/* ImageFileView.java is used by FileChooserDemo2.java. */
public class ImageFileView extends FileView {

    @Override
    public String getName(File f) {
        return null; //let the L&F FileView figure this out
    }

    @Override
    public String getDescription(File f) {
        return null; //let the L&F FileView figure this out
    }

    @Override
    public Boolean isTraversable(File f) {
        return null; //let the L&F FileView figure this out
    }

    @Override
    public String getTypeDescription(File f) {
        String extension = Utils.extractFileExtension(f);
        String type = null;

        if (extension != null) {
            if (extension.equals(Constantes.JPEG)
                    || extension.equals(Constantes.JPG)) {
                type = "JPEG Image";
            } else if (extension.equals(Constantes.GIF)) {
                type = "GIF Image";
            } else if (extension.equals(Constantes.TIFF)
                    || extension.equals(Constantes.TIF)) {
                type = "TIFF Image";
            } else if (extension.equals(Constantes.PNG)) {
                type = "PNG Image";
            }
        }
        return type;
    }

}
