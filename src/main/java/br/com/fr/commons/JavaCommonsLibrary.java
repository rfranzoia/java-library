/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class JavaCommonsLibrary {

    static {
        DOMConfigurator.configure(JavaCommonsLibrary.class.getResource("log4j.xml"));
        Logger.getLogger(JavaCommonsLibrary.class).setLevel(Level.WARN);
    }
    
    public static void main(String[] args) {
        Logger.getLogger(JavaCommonsLibrary.class).warn("prodam-java-commons");
    }
    
}
