package br.com.fr.commons.util;
import java.util.logging.Logger;

import javafx.stage.Stage;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class ValidationAdapter implements ValidationAction {

    @Override
    public void onSuccess(Stage stage) {
    }

    
    /*
        Pode haver problemas de memoria e travamento da aplicacao
        se não houver uma chamada CapturaDigitalUtil.getInstance().stop() 
        pra quem for sobrescrever este método
    */
    @Override
    public void onFail(Stage stage) {
    }

    @Override
    public void onError(Exception ex, Stage stage) {
        Logger.getLogger(ValidationAdapter.class.getName()).severe(ex.getMessage());
        stage.close();
    }

}
