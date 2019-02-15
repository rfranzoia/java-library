package br.com.fr.commons.util;
import java.io.Serializable;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class Finger implements Serializable{
    
	private static final long serialVersionUID = 831330746020641281L;
	
	private int numDedo;
    private byte[] imagemDigital;
    private byte[] codigoValidacao;
    private Integer situacao;

    public Finger() {
    }

    public Finger(int numDedo) {
        this.numDedo = numDedo;
    }
    
    public Finger(int numDedo, Integer situacao) {
        this.numDedo = numDedo;
        this.situacao = situacao;
    }
    
    public int getNumDedo() {
        return numDedo;
    }

    public void setNumDedo(int numDedo) {
        this.numDedo = numDedo;
    }

    public byte[] getImagemDigital() {
        return imagemDigital;
    }

    public void setImagemDigital(byte[] imagemDigital) {
        this.imagemDigital = imagemDigital;
    }

    public byte[] getCodigoValidacao() {
        return codigoValidacao;
    }

    public void setCodigoValidacao(byte[] codigoValidacao) {
        this.codigoValidacao = codigoValidacao;
    }

    public Integer getSituacao() {
        return situacao;
    }

    public void setSituacao(Integer situacao) {
        this.situacao = situacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.numDedo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Finger other = (Finger) obj;
        if (this.numDedo != other.getNumDedo()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return HandsUtil.getInstance().getNomeDedoById(numDedo);
    }
    
}
