/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.EventQueue;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class ClockRunnable implements Runnable {

    private final Logger logger = Logger.getLogger(getClass());
    private Runnable runCommand;

    public ClockRunnable(Runnable runCommand) {
        this.runCommand = runCommand;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        try {
            while (true) {
                EventQueue.invokeLater(runCommand);
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            logger.warn( "ClockRunnable.run()", ex);
        }
    }
}
