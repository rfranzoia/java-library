package br.com.fr.commons.javafx.infra;

import java.io.Serializable;
import java.util.List;

public interface ICrudController<T extends Serializable> extends Serializable {

    void setRowAction(List<RowAction<T>> ra);

    void run(String title, double width, double height);

    void edit(T t);

    void delete(T t);

    void insert();

    T showEditStage(T t);
}
