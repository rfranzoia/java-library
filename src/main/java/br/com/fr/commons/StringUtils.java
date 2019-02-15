/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class StringUtils {

    public static final Pattern UPPER = Pattern.compile("\\p{Upper}");
    public static final Pattern LOWER = Pattern.compile("\\p{Lower}");

    public static final Pattern PUNCT = Pattern.compile("\\p{Punct}");
    public static final Pattern DIGIT = Pattern.compile("\\p{Digit}");
    
    public static String padLeft(String string, int size) {
        return String.format("%1$" + size + "s", string);
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String negritoHTML(String string) {
        if (!string.contains("<html>")) {
            return "<html><b>" + string + "</b></html>";
        } else {
            return string;
        }
    }

    public static String getSenhaMD5(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger bi = new BigInteger(1, md.digest(senha.trim().getBytes()));
            return String.format("%32x", bi).replaceAll("[-]", "");
        } catch (Exception ex) {
            Logger.getLogger("br.com.fr.util.Util").log(Priority.ERROR, "", ex);
        }

        return null;
    }
    
    public static PasswordScore getPasswordScore(String password) {
        
        int score = -1;

        if (password.trim().isEmpty()) {
            score = 0;

        } else {
            score = 1;
            if (password.trim().length() >= 6) {

                if (password.trim().length() > 10)
                    score++;
                    
                Matcher m = UPPER.matcher(password);
                if (m.find()) {
                    m = LOWER.matcher(password);
                    if (m.find()) {
                        score++;
                    }
                }

                m = PUNCT.matcher(password);
                if (m.find())
                    score++;
                
                m = DIGIT.matcher(password);
                if (m.find())
                    score++;
            }

        }
        
        return PasswordScore.getByScore(score);
    }
}
