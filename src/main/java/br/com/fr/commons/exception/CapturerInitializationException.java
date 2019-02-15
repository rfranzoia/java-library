/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.exception;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class CapturerInitializationException extends Exception {

    public CapturerInitializationException() {
    }

    public CapturerInitializationException(String message) {
        super(message);
    }

    public CapturerInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CapturerInitializationException(Throwable cause) {
        super(cause);
    }

    public CapturerInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
