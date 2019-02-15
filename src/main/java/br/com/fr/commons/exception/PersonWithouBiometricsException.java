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
public class PersonWithouBiometricsException extends Exception {

    public PersonWithouBiometricsException() {
    }

    public PersonWithouBiometricsException(String message) {
        super(message);
    }

    public PersonWithouBiometricsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonWithouBiometricsException(Throwable cause) {
        super(cause);
    }

    public PersonWithouBiometricsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
