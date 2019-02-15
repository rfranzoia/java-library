/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class DefaultKeyPressed extends KeyAdapter {

    private final Runnable enterCMD;
    private final Runnable escCMD;

    public DefaultKeyPressed(final JButton btnEntrar, final JButton btnSair) {
        enterCMD = () -> {
            if (btnEntrar != null) {
                btnEntrar.doClick();
            }
        };

        escCMD = () -> {
            if (btnSair != null) {
                btnSair.doClick();
            }
        };
    }

    public DefaultKeyPressed(Runnable enterCmd, Runnable escCmd) {
        this.enterCMD = enterCmd;
        this.escCMD = escCmd;
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        checkKeyPressed(evt);
    }

    private void checkKeyPressed(KeyEvent evt) {

        Object source = evt.getSource();

        if ((source instanceof JTextField)
            || (source instanceof JFormattedTextField)
            || (source instanceof JComboBox)) {

            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

                if (enterCMD != null) {
                    enterCMD.run();
                }
            }

        }

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (escCMD != null) {
                escCMD.run();
            }
        }

    }
}
