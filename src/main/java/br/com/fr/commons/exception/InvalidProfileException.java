/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.exception;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class InvalidProfileException extends Exception {

    public InvalidProfileException() {
    }

    public InvalidProfileException(String message) {
        super(message);
    }

    public InvalidProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidProfileException(Throwable cause) {
        super(cause);
    }

    public InvalidProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
