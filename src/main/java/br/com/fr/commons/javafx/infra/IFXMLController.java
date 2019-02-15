package br.com.fr.commons.javafx.infra;

import java.io.Serializable;
import javafx.stage.Stage;

public interface IFXMLController<T extends Serializable> {

    void setParameters(T t, Stage stage);
    
    void setEntity(T t, T t1);
    
    T getEntity();
    
    void save();
    
    void delete(T t);
    
    void cancel();
    
    boolean isDataValid();
    
}
