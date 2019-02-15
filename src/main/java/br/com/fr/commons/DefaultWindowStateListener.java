/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class DefaultWindowStateListener implements WindowStateListener {

    private final JInternalView currentView;

    public DefaultWindowStateListener(JInternalView currentView) {
        this.currentView = currentView;
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        if (currentView != null) {
            currentView.updateWindowLocation();
        }
    }
}