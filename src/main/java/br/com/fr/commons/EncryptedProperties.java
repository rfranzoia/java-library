/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class EncryptedProperties extends Properties {

    private final Logger logger = Logger.getLogger(getClass());
    
    private boolean encryptEnable = true;
    
    private static Decoder decoder = Base64.getDecoder();
    private static Encoder encoder = Base64.getEncoder();
    private Cipher encrypter, decrypter;
    private static byte[] salt = {(byte) 0x01, (byte) 0x02,
        (byte) 0x03, (byte) 0x05, (byte) 0x07, (byte) 0x11,
        (byte) 0x13, (byte) 0x17};

    public EncryptedProperties(String password) throws Exception {
        PBEParameterSpec ps = new javax.crypto.spec.PBEParameterSpec(salt, 20);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey k = kf.generateSecret(new javax.crypto.spec.PBEKeySpec(password.toCharArray()));
        encrypter = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
        decrypter = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
        encrypter.init(Cipher.ENCRYPT_MODE, k, ps);
        decrypter.init(Cipher.DECRYPT_MODE, k, ps);
    }

    public String getProperty(String key) {
        try {
            return decrypt(super.getProperty(encrypt(key)));
        } catch (Exception ex) {
            logger.log(Level.WARN, "Chave "+ key + " não existe!!", ex);
            return null;
        }
    }

    public synchronized Object setProperty(String key, String value) {
        try {
            return super.setProperty(encrypt(key), encrypt(value));
        } catch (Exception ex) {
            logger.log(Level.WARN, "Não foi possível gravar esta chave/valor " + key + "/" + value, ex);
            return null;
        }
    }

    private synchronized String decrypt(String str) throws Exception {
        if (str != null) {
            if (encryptEnable) {
                byte[] dec = decoder.decode(str);
                byte[] utf8 = decrypter.doFinal(dec);
                return new String(utf8, "UTF-8");
            } else {
                return str;
            }
        } else {
            return "";
        }
    }

    private synchronized String encrypt(String str) throws Exception {
        if (str != null) {
            if (encryptEnable) {
                byte[] utf8 = str.getBytes("UTF-8");
                byte[] enc = encrypter.doFinal(utf8);
                return encoder.encodeToString(enc);
            } else {
                return str;
            }
        } else {
            return "";
        }
    }

    public void setEncryptEnable(boolean encryptEnable) {
       this.encryptEnable = encryptEnable;
    }
    
}
