/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class Cronometer {
    
    protected Timer timer;

    protected int seconds;
    protected int minutes;
    protected int hours;
    
    protected ActionListener runner = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            seconds++;
            if (seconds / 60 == 1) {
                minutes++;
                seconds = 0;
            }
            if (minutes / 60 == 1) {
                hours++;
                minutes = 0;
            }
            
            if (command != null) {
                command.run();
            }
        }
    };

    private Runnable command;
    
    public Cronometer() {
        this(null);
    }
    
    public Cronometer(Runnable command) {
        timer = new Timer(1000, runner);
        
        seconds = 0;
        minutes = 0;
        hours = 0;
        
        this.command = command;
    }
    
    public Cronometer(Runnable command, int startingSeconds) {
        timer = new Timer(1000, runner);
        
        if (startingSeconds >= 3600) {
            hours = startingSeconds / 3600;
            startingSeconds = startingSeconds - (hours * 60 * 60);
        }
        
        if (startingSeconds >= 60) {
            minutes = startingSeconds / 60;
            startingSeconds = startingSeconds - (minutes * 60);
        }
        
        seconds = startingSeconds;
        
        this.command = command;
    }
    
    protected void setRunner(ActionListener runner) {
        boolean wasRunning = timer.isRunning();
        
        if (wasRunning) {
            timer.stop();
        }
        
        timer.removeActionListener(this.runner);
        this.runner = runner;
        timer.addActionListener(runner);
        
        if (wasRunning) {
            timer.start();
        }
    }
    
    public void start() {
        timer.start();
    }
    
    public void pause() {
        timer.stop();
    }
    
    public void stop() {
        timer.stop();
    }
    
    public void resume() {
        timer.start();
    }
    
    public void reset() {
        timer.stop();
        
        seconds = 0;
        minutes = 0;
        hours = 0;
        
        if (command != null) {
            command.run();
        }
    }
    
    public int getTime() {
        return seconds + (minutes * 60) + (hours * 3600);
    }

    public String getFormatedTime() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
