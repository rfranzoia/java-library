/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class TimerCronometro extends Cronometer {
    
    private Runnable command;
    private int delay;
    private int interval;
    
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
      
            if (getTime() >= interval && command != null) {
                reset();
                timer.start();
            }
        }
    };
    
    public TimerCronometro() {
        this(null, 0, 1);
        this.setRunner(this.runner);
    }

    public TimerCronometro(Runnable command, int interval) {
        this(command, 0, interval);
        this.setRunner(this.runner);
    }
    
    public TimerCronometro(Runnable command, int delay, int interval) {
        super(command);
        this.setRunner(this.runner);
        
        this.command = command;
        this.delay = delay;
        this.interval = interval;
    
        if (delay == 0 && command != null) {
            command.run();
        }
    }
    
    @Override
    public void start() {
        
        if (delay == 0 && command != null) {
            command.run();
        }
        
        timer.start();
    }
    
}
