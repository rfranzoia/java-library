/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class Constantes {

    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("00");
    public static final SimpleDateFormat DATE_FORMAT_LONG_BR = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_BR = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    public static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static final String NOME_DEDO_REGEX = "([A-Za-z\\s\\u00c0-\\u00ff\\(]{0,21})([0-9]{0,2})([\\)]{0,1})";

    public final static String JPEG = "jpeg";
    public final static String JPG = "jpg";
    public final static String GIF = "gif";
    public final static String TIFF = "tiff";
    public final static String TIF = "tif";
    public final static String PNG = "png";
    
    public static final int TEMPO_VERIFICACAO_LOGOFF = 60 * 60;
    
    public static final String SITUACAO_INATIVO = "I";
    public static final String SITUACAO_ATIVO = "A";
    
    public static final String SIM = "S";
    public static final String NAO = "N";
    
    public static final String DEFAULT_CAMINHO_IMG = "/images/";
    
    public static final int WINDOW_SIZE_SMALL = 0;
    public static final int WINDOW_SIZE_MEDIUM = 1;
    public static final int WINDOW_SIZE_BIG = 2;
    
    public static final String DEFAULT = "DEFAULT";
    
    public static final int TEMPO_ATULIZACAO_PAINEL = 2 * 1000; // 2 segundos
    
    public static final char PERMISSAO_FUNCAO_INCLUIR =   'I';
    public static final char PERMISSAO_FUNCAO_HISTORICO = 'H';
    public static final char PERMISSAO_FUNCAO_RECAPTURA = 'R';
    
    public static final char TIPO_EDIT_INSERT = 'I';
    public static final char TIPO_EDIT_UPDATE = 'U';
    public static final char TIPO_EDIT_VIEW =   'C';
    
}
