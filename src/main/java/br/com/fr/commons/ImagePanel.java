/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.apache.log4j.Logger;


public class ImagePanel extends JPanel {

    private Logger logger = Logger.getLogger(getClass());
    private BufferedImage image;

    public ImagePanel(String imagePath) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
       } catch (IOException ex) {
            logger.info("Erro ao carregar imagem", ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
    }
}