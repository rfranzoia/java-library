/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.exception;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class LoginInvalidoException extends Exception {

    public LoginInvalidoException() {
    }

    public LoginInvalidoException(String message) {
        super(message);
    }

    public LoginInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginInvalidoException(Throwable cause) {
        super(cause);
    }

    public LoginInvalidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
