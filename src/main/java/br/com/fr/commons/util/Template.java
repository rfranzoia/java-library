package br.com.fr.commons.util;
import java.io.Serializable;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class Template implements Serializable {
	
	private static final long serialVersionUID = -1865153628075184653L;
	
	private byte[] data;
    private int size;
    private int quality;

    public Template(byte[] data, int size, int quality) {
        this.data = data;
        this.size = size;
        this.quality = quality;
    }
    
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
    
}
