/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.fr.commons;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

 /** 
  * Titulo: JExtenso 
  * <p> 
  * Descrição: Programa converte um numero para o valor em extenso 
  * <p> 
  *  
  * @author Sérgio Eduardo Rodrigues 
  * @version 1.0 
  * @created 10 de Janeiro de 2001 
  */  
 public class JExtenso {  
     
    private ArrayList nro;  
    private BigInteger num;  
    
    private String Qualificadores[][] = { { "centavo", "centavos" },
    									  { "", "" }, 
    									  { "mil", "mil" }, 
    									  { "milhão", "milhões" },
    									  { "bilhão", "bilhões" }, 
    									  { "trilhão", "trilhões" },
    									  { "quatrilhão", "quatrilhões" }, 
    									  { "quintilhão", "quintilhões" },
    									  { "sextilhão", "sextilhões" }, 
    									  { "septilhão", "septilhões" } };  
   
    private String Numeros[][] = { { "zero", "um", "dois", "tres", "quatro", "cinco", 
    								 "seis", "sete", "oito", "nove", "dez", 
    								 "onze", "doze", "treze", "quatorze", "quinze", 
    								 "desesseis", "desessete", "dezoito", "desenove" },
    							   { "vinte", "trinta", "quarenta", "cinquenta", 
    								 "sessenta", "setenta", "oitenta", "noventa" },
    							   { "cem", "cento", "duzentos", "trezentos", "quatrocentos",  
    							     "quinhentos", "seiscentos", "setecentos", "oitocentos",
    							     "novecentos" } };  
   
    /** 
     * Construtor 
     */  
    public JExtenso() {  
       nro = new ArrayList();  
    }  
   
    /** 
     * Construtor 
     *  
     * @param dec 
     *            valor para colocar por extenso 
     */  
    public JExtenso(BigDecimal dec) {  
       this();  
       setNumber(dec);  
    }  
   
    /** 
     * Constructor for the JExtenso object 
     *  
     * @param dec 
     *            valor para colocar por extenso 
     */  
    public JExtenso(double dec) {  
       this();  
       setNumber(dec);  
    }  
   
    /** 
     * Sets the Number attribute of the JExtenso object 
     *  
     * @param dec 
     *            The new Number value 
     */  
    public void setNumber(BigDecimal dec) {  
       // Converte para inteiro arredondando os centavos  
       num = dec.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(  
             BigDecimal.valueOf(100)).toBigInteger();  
   
       // Adiciona valores  
       nro.clear();  
       if (num.equals(BigInteger.ZERO)) {  
          // Centavos  
          nro.add(new Integer(0));  
          // Valor  
          nro.add(new Integer(0));  
       } else {  
          // Adiciona centavos  
          addRemainder(100);  
   
          // Adiciona grupos de 1000  
          while (!num.equals(BigInteger.ZERO)) {  
             addRemainder(1000);  
          }  
       }  
    }  
   
    public void setNumber(double dec) {  
       setNumber(new BigDecimal(dec));  
    }  
   
    /** 
     * Description of the Method 
     */  
    public void show() {  
       Iterator valores = nro.iterator();  
   
       while (valores.hasNext()) {  
          System.out.println(((Integer) valores.next()).intValue());  
       }  
       System.out.println(toString());  
    }  
   
    /** 
     * Description of the Method 
     *  
     * @return Description of the Returned Value 
     */  
    public String toString() {  
       StringBuffer buf = new StringBuffer();  
   
       int numero = ((Integer) nro.get(0)).intValue();  
       int ct;  
   
       for (ct = nro.size() - 1; ct > 0; ct--) {  
          // Se ja existe texto e o atual não é zero  
          if (buf.length() > 0 && !ehGrupoZero(ct)) {  
             buf.append(" ");  
          }  
          buf.append(numToString(((Integer) nro.get(ct)).intValue(), ct));  
       }  
       if (buf.length() > 0) {  
          if (ehUnicoGrupo())  
             buf.append(" de ");  
          while (buf.toString().endsWith(" "))  
             buf.setLength(buf.length() - 1);  
          if (ehPrimeiroGrupoUm())  
             buf.insert(0, "");  
          
          /*
          if (nro.size() == 2 && ((Integer) nro.get(1)).intValue() == 1) {  
             buf.append(" real");  
          } else {  
             buf.append(" reais");  
          }  
          if (((Integer) nro.get(0)).intValue() != 0) {  
             buf.append(" e ");  
          } 
          */ 
       }  
       if (((Integer) nro.get(0)).intValue() != 0) {  
          buf.append(numToString(((Integer) nro.get(0)).intValue(), 0));  
       }  
       return buf.toString();  
    }  
   
    private boolean ehPrimeiroGrupoUm() {  
       if (((Integer) nro.get(nro.size() - 1)).intValue() == 1)  
          return true;  
       return false;  
    }  
   
    /** 
     * Adds a feature to the Remainder attribute of the JExtenso object 
     *  
     * @param divisor 
     *            The feature to be added to the Remainder attribute 
     */  
    private void addRemainder(int divisor) {  
       // Encontra newNum[0] = num modulo divisor, newNum[1] = num dividido  
       // divisor  
       BigInteger[] newNum = num.divideAndRemainder(BigInteger  
             .valueOf(divisor));  
   
       // Adiciona modulo  
       nro.add(new Integer(newNum[1].intValue()));  
   
       // Altera numero  
       num = newNum[0];  
    }  
   
    /** 
     * Description of the Method 
     *  
     * @param ps 
     *            Description of Parameter 
     * @return Description of the Returned Value 
     */  
    private boolean temMaisGrupos(int ps) {  
       for (; ps > 0; ps--) {  
          if (((Integer) nro.get(ps)).intValue() != 0) {  
             return true;  
          }  
       }  
   
       return false;  
    }  
   
    /** 
     * Description of the Method 
     *  
     * @param ps 
     *            Description of Parameter 
     * @return Description of the Returned Value 
     */  
    private boolean ehUltimoGrupo(int ps) {  
       return (ps > 0) && ((Integer) nro.get(ps)).intValue() != 0  
             && !temMaisGrupos(ps - 1);  
    }  
   
    /** 
     * Description of the Method 
     *  
     * @return Description of the Returned Value 
     */  
    private boolean ehUnicoGrupo() {  
       if (nro.size() <= 3)  
          return false;  
       if (!ehGrupoZero(1) && !ehGrupoZero(2))  
          return false;  
       boolean hasOne = false;  
       for (int i = 3; i < nro.size(); i++) {  
          if (((Integer) nro.get(i)).intValue() != 0) {  
             if (hasOne)  
                return false;  
             hasOne = true;  
          }  
       }  
       return true;  
    }  
   
    boolean ehGrupoZero(int ps) {  
       if (ps <= 0 || ps >= nro.size())  
          return true;  
       return ((Integer) nro.get(ps)).intValue() == 0;  
    }  
   
    /** 
     * Description of the Method 
     *  
     * @param numero 
     *            Description of Parameter 
     * @param escala 
     *            Description of Parameter 
     * @return Description of the Returned Value 
     */  
    private String numToString(int numero, int escala) {  
       int unidade = (numero % 10);  
       int dezena = (numero % 100); // * nao pode dividir por 10 pois  
                               // verifica de 0..19  
       int centena = (numero / 100);  
       StringBuffer buf = new StringBuffer();  
   
       if (numero != 0) {  
          if (centena != 0) {  
             if (dezena == 0 && centena == 1) {  
                buf.append(Numeros[2][0] );  
             } else {  
                buf.append(Numeros[2][centena]);  
             }  
          }  
   
          if ((buf.length() > 0) && (dezena != 0)) {  
             buf.append(" ehh ");  
          }  
          if (dezena > 19) {  
             dezena /= 10;  
             buf.append(Numeros[1][dezena - 2]);  
             if (unidade != 0) {  
                buf.append(" ehh ");  
                buf.append(Numeros[0][unidade]);  
             }  
          } else if (centena == 0 || dezena != 0) {  
             buf.append(Numeros[0][dezena]);  
          }  
   
          buf.append(" ");  
          if (numero == 1) {  
             buf.append(Qualificadores[escala][0] );  
          } else {  
             buf.append(Qualificadores[escala][1] );  
          }  
       }  
   
       return buf.toString();  
    }  
   
    private static void soundPlay(AudioInputStream stream)
			throws LineUnavailableException, IOException {

		AudioFormat format = stream.getFormat();// Distinção entre formato de
												// dados e formato de arquivo
		if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
			format = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED, // codificação
					format.getSampleRate(), format.getSampleSizeInBits() * 2,
					format.getChannels(), // numero de canais
					format.getFrameSize() * 2, // tamanho frame
					format.getFrameRate(), // taxa do frame
					true); // big endian
			stream = AudioSystem.getAudioInputStream(format, stream);
		}

		SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class,
				stream.getFormat(), ((int) stream.getFrameLength() * format
						.getFrameSize()));
		SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

		// Entrada de áudio com stream
		line.open(stream.getFormat());
		line.start();
		int numRead = 0;

		// stream = streamtp+streamsh+srteamnr;
		byte[] buf = new byte[line.getBufferSize()];
		while ((numRead = stream.read(buf, 0, buf.length)) >= 0) {
			int offset = 0;
			while (offset < numRead) {
				offset += line.write(buf, offset, numRead - offset);
			}
		}
		line.drain();
		line.stop();
	}
    
    
   //public static final String SOUND_PATH = "C:\\romeu\\novo_som\\aus\\";
    
//    /**
//    * Para teste
//    * 
//    * @param args
//    *            numero a ser convertido
//    */  
//   public static void main(String[] args) {
//        if (args.length == 0) {
//                System.out.println("Sintax : ...Extenso <numero>");
//                return;
//        }
//
//        try {
//                for (int j = 100; j < 212; j++) {
//                        JExtenso numero = new JExtenso(new BigDecimal(j));
//                        String str_numero = numero.toString();
//                        String[] sons = str_numero.split("[ ]");
//
//                        if (sons.length > 0) {
//                                for (int i = 0; i < sons.length; i++) {
//                                        AudioInputStream stream = AudioSystem
//                                                        .getAudioInputStream(new File(SOUND_PATH
//                                                                        + sons[i].trim() + ".au"));
//                                        soundPlay(stream);
//                                }
//                        }
//                }
//
//        } catch (UnsupportedAudioFileException e) {
//                e.printStackTrace();
//        } catch (IOException e) {
//                e.printStackTrace();
//        } catch (LineUnavailableException e) {
//                e.printStackTrace();
//        }
//
//     }  
 } 