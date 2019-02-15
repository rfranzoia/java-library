package br.com.fr.commons.util;

public class BiometricsContants {

	// verifica nova atualiza��o a cada hora (contada a partir da execu��o do
	// sistema
	// a primeira verifica��o ja ocorre na inicializa��o
	public static final Character TB_DIGITAL_POUSADA = '0';
	public static final Character TB_DIGITAL_ROLADA = '1';
	public static final Character TB_FOTO = '2';
	public static final Character TB_ASSINATURA = '3';

	public static final Integer ST_CAPTURA_NAO_CAPTURADO = -1;
	public static final Integer ST_CAPTURA_DIGITAL_CAPTURADA = 0;
	public static final Integer ST_CAPTURA_DIGITAL_SEM_DEDO = 1;
	public static final Integer ST_CAPTURA_DIGITAL_ACIDENTADO = 2;
	public static final Integer ST_CAPTURA_DIGITAL_RUIM = 3;

	public static final String NOME_DEDO_REGEX = "([A-Za-z\\s\\u00c0-\\u00ff\\(]{0,21})([0-9]{0,2})([\\)]{0,1})";

}
