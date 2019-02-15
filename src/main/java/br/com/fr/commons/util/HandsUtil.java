package br.com.fr.commons.util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class HandsUtil {
    
    private static HandsUtil instance;
    
    public static HandsUtil getInstance() {
        if (instance == null) {
            instance = new HandsUtil();
        }
        return instance;
    }
    
    private HandsUtil() {
    }
    
    public List<Finger> createDedosMaoE() {
        List<Finger> lst = new ArrayList<>();
        
        lst.add(new Finger(1));
        lst.add(new Finger(2));
        lst.add(new Finger(3));
        lst.add(new Finger(4));
        lst.add(new Finger(5));
        
        return lst;
    }
    
    public List<Finger> createDedosMaoD() {
        
        List<Finger> lst = new ArrayList<>();
        
        lst.add(new Finger(6));
        lst.add(new Finger(7));
        lst.add(new Finger(8));
        lst.add(new Finger(9));
        lst.add(new Finger(10));
        
        return lst;
    }
    
	public String getNomeDedoById(int id) {
		return Arrays.asList("Mindinho Esquerdo (1)", "Anelar Esquerdo (2)", "M�dio Esquerdo (3)", "Indicador Esquerdo (4)", "Polegar Esquerdo (5)",
				"Polegar Direito (6)", "Indicador Direito (7)", "M�dio Direito (8)", "Anelar Direito (9)", "Mindinho Direito (10)").get(id - 1);
	}
}
