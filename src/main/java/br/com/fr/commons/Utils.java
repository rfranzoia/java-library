/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class Utils {

    public static final String PROTOCOLO_FORMAT = "([0-9]{4})([0-9]{2})([0-9]{2})([0-9]{4})";

    public static final String NUM_DEDO_REGEX = "([\\D]{0,5})([0-9]{0,2})";
    
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("00");
    public static final SimpleDateFormat DATE_FORMAT_LONG_BR = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_BR = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm:ss");
    
    public static ApplicationFrame APPLICATION_FRAME;

    private static String pathToFiles = "./";
    
    public static void setApplicationFrame(ApplicationFrame applicationFrame) {
        APPLICATION_FRAME = applicationFrame;
    }

    public static String extractFileExtension(File f) {
        String ext = null;
        if (f != null) {
            String s = f.getName();
            int i = s.lastIndexOf('.');

            if (i > 0 && i < s.length() - 1) {
                ext = s.substring(i + 1).toLowerCase();
            }
        }
        return ext;
    }

    /*
        DATE & TIME
     */
    public static String convertSecondsToFormatedDateString(int seconds, boolean isMiliseconds) {

        int hours = 0;
        int minutes = 0;

        if (isMiliseconds) {
            seconds = seconds / 1000;
        }

        if (seconds >= 3600) {
            hours = seconds / 3600;
            seconds = seconds - (hours * 60 * 60);
        }

        if (seconds >= 60) {
            minutes = seconds / 60;
            seconds = seconds - (minutes * 60);
        }

        Calendar cal = Calendar.getInstance(Locale.UK);

        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2016);

        return Constantes.HOUR_FORMAT.format(cal.getTime());
    }

    public static String[] getMonthStrings() {
        String[] months = new java.text.DateFormatSymbols().getMonths();
        int lastIndex = months.length - 1;

        if (months[lastIndex] == null || months[lastIndex].length() <= 0) { //last item empty
            String[] monthStrings = new String[lastIndex];
            System.arraycopy(months, 0,
                    monthStrings, 0, lastIndex);
            return monthStrings;

        } else { //last item not empty
            return months;
        }
    }
    
    public static void setApplicationIcon(JFrame frame) {
        // Icone do Sistema
        try {
            Image img = ImageIO.read(Utils.class.getResource(BaseParameters.getInstance().getLibImgPath() + "64x64.png"));
            frame.setIconImage(img);
        } catch (IOException e) {
            Logger.getLogger(Utils.class.getName()).warn("Icone da aplicação não pode ser carregado...", e);

        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).warn("Icone da aplicação não pode ser carregado...", ex);
        }
    }
    
    public static void setDefaultIconImage(JDialog dialog) {
        try {

            Image img = ImageIO.read(Utils.class.getResource(BaseParameters.getInstance().getLibImgPath() + "64x64.png"));
            dialog.setIconImage(img);

        } catch (IOException e) {
            Logger.getLogger(Utils.class.getName()).warn("Imagem não pode ser carregada...", e);

        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).warn("Imagem não pode ser carregada...", ex);
        }
    }
    
        
    public static void setDefaultFrameIcon(JInternalFrame frame) {
        try {
            Image img = ImageIO.read(Utils.class.getResource(BaseParameters.getInstance().getLibImgPath() + "64x64.png"));
            frame.setFrameIcon(new ImageIcon(img));

        } catch (IOException e) {
            Logger.getLogger(Utils.class.getName()).warn("Imagem não pode ser carregada...", e);

        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).warn("Imagem não pode ser carregada...", ex);
        }
    }

    public static int showOptionsDialog(String message, String[] options) {
        int opcao = JOptionPane.showOptionDialog(null,
                                                 message,
                                                 "Atenção", JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        return opcao;
    }
    
    public static boolean showYesNoDialog(String message) {
        String opcoes[] = {"Sim", "Não"};
        int opcao = JOptionPane.showOptionDialog(null,
                message,
                "Atenção", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, opcoes, opcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    public static KeyAdapter getDefaultKeyPressed(JButton btnEntrar, JButton btnSair) {
        return new DefaultKeyPressed(btnEntrar, btnSair);
    }
    
    public static FocusListener getSelectAllFocusListener() {

        FocusListener selectAllOnFocus = new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(final java.awt.event.FocusEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (evt.getSource() instanceof JFormattedTextField) {
                            ((JFormattedTextField) evt.getSource()).selectAll();

                        } else if (evt.getSource() instanceof JTextField) {
                            ((JTextField) evt.getSource()).selectAll();
                        }
                    }
                });
            }

            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (evt.getSource() instanceof JFormattedTextField) {
                            ((JFormattedTextField) evt.getSource())
                                    .setText(((JFormattedTextField) evt.getSource()).getText().trim());

                        } else if (evt.getSource() instanceof JTextField) {
                            ((JTextField) evt.getSource())
                                    .setText(((JTextField) evt.getSource()).getText().trim());
                        }
                    }
                });
            }
            
        };

        return selectAllOnFocus;
    }
    
    public static void removeMinMaxClose(Component comp) {
        if (comp instanceof JButton) {
            String accName = ((JButton) comp).getAccessibleContext().getAccessibleName();
            Logger.getLogger(Utils.class.getName()).info(accName);
            if (accName.equals("Maximize") || accName.equals("Iconify")
                    || accName.equals("Close")) {
                comp.getParent().remove(comp);
            }
        }
        if (comp instanceof Container) {
            Component[] comps = ((Container) comp).getComponents();
            for (int x = 0, y = comps.length; x < y; x++) {
                removeMinMaxClose(comps[x]);
            }
        }
    }
    
    public static WindowStateListener getDefaultWindowStateListener(JInternalView currentView) {
        return new DefaultWindowStateListener(currentView);
    }
    
    public static String negrito(String string) {
        if (!string.contains("<html>")) {
            return "<html><b>" + string + "</b></html>";
        } else {
            return string;
        }
    }
}
