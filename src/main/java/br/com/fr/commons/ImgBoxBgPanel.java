/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class ImgBoxBgPanel extends JPanel {

    private BufferedImage bgImage;
    private Rectangle[] box;

    public ImgBoxBgPanel(BufferedImage bgImage, Rectangle box) {
        this.box = new Rectangle[1];
        this.box[0] = box;
        this.bgImage = bgImage;
    }
    
    public ImgBoxBgPanel(BufferedImage bgImage, Rectangle[] box) {
        this.box = box;
        this.bgImage = bgImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // a imagem de fundo
        g2.drawImage(bgImage, 0, 0, this);
        
        // os quadros que existirem
        for (int b = 0; b < box.length; b++) {
            if (box[b] != null) {
                g2.setPaint(Color.red);
                g2.draw(box[b]);
            }    
        }
        
    }
    
    public void setClip(int x, int y, int index) {
        if (x < 0) {
            x = 0;
        }
        
        if (y < 0) {
            y = 0;
        }
        
        if ((x + box[index].width) > bgImage.getWidth()) {
            x = bgImage.getWidth() - box[index].width;
        }
        
        if ((y + box[index].height) > bgImage.getHeight()) {
            y = bgImage.getHeight() - box[index].height;
        }
        
        box[index].setLocation(x, y);
        repaint();   
    }
    
    public void setClip(int x, int y) {
        setClip(x, y, 0);
    }
}
