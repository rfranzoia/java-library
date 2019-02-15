/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

/**
 *
 * @author Romeu Franzoia Jr
 */
public enum PasswordScore {
    BLANK(0), VERY_WEAK(1), WEAK(2), MEDIUM(3), STRONG(4), VERY_STRONG(5);
    
    private final int score;

    private PasswordScore(int score) {
        this.score = score;
    }
    
    public static PasswordScore getByScore(int score) {
        switch (score) {
            case 1: return VERY_WEAK;
            case 2: return WEAK;
            case 3: return MEDIUM;
            case 4: return STRONG;
            case 5: return VERY_STRONG;
            default: return BLANK;
        }
    }
}
