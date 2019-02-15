/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class ImageUtils {

    private static final Logger logger = Logger.getLogger(ImageUtils.class);

    public static Image createResizedCopy(Image originalImage, int scaledWidth,
            int scaledHeight, boolean preserveAlpha) throws Exception {

        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }

        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

        return scaledBI;
    }

    public static Image createImageFromBytes(byte[] imageData) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        return ImageIO.read(bais);
    }

    public static byte[] createByteArrayFromImage(BufferedImage originalImage) {
        try {
            byte[] imageInByte;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(originalImage, Constantes.JPG, baos);
                baos.flush();
                imageInByte = baos.toByteArray();
            }
            return imageInByte;
        } catch (Exception ex) {
            logger.error("", ex);
        }

        return null;

    }

    public static String imageToBase64String(BufferedImage image, String type) {
        byte[] imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            imageString = Base64.getEncoder().encode(imageBytes);

            bos.close();
        } catch (IOException ex) {
            logger.log(Level.WARN, "", ex);
        }

        return new String(imageString);
    }

    public static BufferedImage base64StringToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;

        try {
            imageByte = Base64.getDecoder().decode(imageString);

            try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
                image = ImageIO.read(bis);
            }
        } catch (Exception ex) {
            logger.log(Level.WARN, "", ex);
        }

        return image;
    }

    public static void putImageIntoLabel(ImageIcon icon, JLabel label) {
        try {
            putImageIntoLabel((Image) icon.getImage(), label);
        } catch (Exception ex) {
            Logger.getLogger("br.com.fr.util.Util").warn(ex);
        }

    }

    public static void putImageIntoLabel(Image image, JLabel label) {
        try {
            putImageIntoLabel(image, label, true);
        } catch (Exception ex) {
            Logger.getLogger("br.com.fr.util.Util").warn(ex);
        }
    }

    public static void putImageIntoLabel(Image image, JLabel label, boolean adjustSizeToLabel) throws Exception {

        try {
            if (adjustSizeToLabel) {
                Image scaledImage = null;
                if (label.getWidth() == 0 || label.getHeight() == 0) {
                    scaledImage = createResizedCopy(image, 50, 50, true);
                } else {
                    scaledImage = createResizedCopy(image, label.getWidth(), label.getHeight(), true);
                }
                label.setIcon(new ImageIcon(scaledImage));
            } else {
                label.setIcon(new ImageIcon(image));
            }

            label.revalidate();
            label.repaint();
        } catch (Exception ex) {
            Logger.getLogger("br.com.fr.util.Util").warn(ex);
        }
    }
}
