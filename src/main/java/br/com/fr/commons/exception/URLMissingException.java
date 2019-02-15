/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.exception;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class URLMissingException extends Exception {

    public URLMissingException() {
    }

    public URLMissingException(String message) {
        super(message);
    }

    public URLMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public URLMissingException(Throwable cause) {
        super(cause);
    }

    public URLMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
