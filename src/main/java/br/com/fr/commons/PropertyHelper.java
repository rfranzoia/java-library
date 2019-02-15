/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class PropertyHelper {

    private static final Logger logger = Logger.getLogger(PropertyHelper.class);

    private static PropertyHelper instance = null;
    private EncryptedProperties properties = null;

    private static boolean isPropertyFileLoaded = false;
    private static File propertyFile;

    private PropertyHelper() {
        try {
            properties = new EncryptedProperties("Pr0d4m2016.");
        } catch (Exception ex) {
            logger.warn("Unable to create EncryptedProperties\n", ex);
        }
    }

    public static PropertyHelper getInstance() {
        if (instance == null) {
            instance = new PropertyHelper();
        }
        
        if (!isPropertyFileLoaded) {
            loadPropertyFile();
        }
        
        return instance;
    }

    public static void loadPropertyFile() {
        try {
            if (propertyFile != null && propertyFile.exists()) {
                FileInputStream in = new FileInputStream(propertyFile);
                instance.getEncryptedProperties().load(in);
                isPropertyFileLoaded = true;
            }
        } catch (Exception ex) {
            logger.warn("Unable to load properties file\n", ex);
        }
    }
    
    public EncryptedProperties getEncryptedProperties() {
        return properties;
    }

    public void setPropertyFileName(String propertyFileName) {
        propertyFile = new File(propertyFileName);
    }
    
    public boolean isPropertyFileLoaded() {
        return isPropertyFileLoaded;
    }

    public void save() {
        try {
            FileOutputStream out = new FileOutputStream(propertyFile);
            properties.store(out, "Browser Properties - Nao edite ou apague este arquivo");
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.warn("Could not save" + propertyFile, ex);
        }
    }

    public String getCustomProperty(String propertyName) {
        if (propertyFile == null || properties == null) {
            return "";
        }

        String s = properties.getProperty(propertyName);
        
        if (s != null)
            return s.trim();
        else
            return "";
    }
    
    public String setCustomProperty(String key, String value) {
        if (properties == null) {
            return null;
        }
        
        Object object = properties.setProperty(key, value);
        return (String) object;
    }

    
}
