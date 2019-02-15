/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class MyDesktopPane extends JDesktopPane {

    private Logger logger = Logger.getLogger(getClass());
    
    private Image image;
    private JFrame frame;

    public MyDesktopPane(Image image) {
        this.image = image;
    }
    
    public MyDesktopPane(Image image, JFrame frame) {
        this.image = image;
        this.frame = frame;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Dimension screenSize;
        if (frame == null) {
            screenSize = getSize();
        } else {
            screenSize = frame.getSize();
        }
        
        try {
            Image scaledImage = ImageUtils.createResizedCopy(image, screenSize.width, screenSize.height, true);
            g.drawImage(scaledImage, 0, 0, this);    
        } catch (Exception ex) {
            logger.error(ex);
        }
        
    }
    
}
