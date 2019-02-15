/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

/**
 *
 * @author romeu
 */
public interface DefaultService {
    
    public String[] getColumnNames();
    public int[] getColumnSizes();
    public Object[][] getData();
    
}
