/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class ImageBackgroundPanel extends JPanel {

    private ImageIcon bgImage;

    public ImageBackgroundPanel(ImageIcon bgImage) {
        this.bgImage = bgImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bgImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
