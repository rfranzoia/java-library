package br.com.fr.commons.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class Util {

	private Util() {
		
	}
	
	public static String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
	
	public static byte[] getBytesFromFile(String filename) {
		try
		{
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			byte[] bytesFace = new byte[(int) file.length()];
			file.read(bytesFace);
			file.close();
			return bytesFace;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}

}