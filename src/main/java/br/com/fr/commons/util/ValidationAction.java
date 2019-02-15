package br.com.fr.commons.util;
import javafx.stage.Stage;

/**
 *
 * @author Romeu Franzoia Jr
 */
public interface ValidationAction {
    
    public void onSuccess(Stage stage);
    
    public void onFail(Stage stage);
    
    public void onError(Exception ex, Stage stage);
    
}
