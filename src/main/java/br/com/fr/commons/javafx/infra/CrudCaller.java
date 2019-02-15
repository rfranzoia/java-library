/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.javafx.infra;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import javafx.collections.ObservableList;

public class CrudCaller<T extends Serializable> {

    private String title;
    private List<ColumnInfo> columns;
    private ObservableList<T> data;
    private Consumer<T> insertAction;
    private List<Consumer<T>> rowActions;

    public CrudCaller() {
    }
    
    public CrudCaller(String title, List<ColumnInfo> columns, ObservableList<T> data, Consumer<T> insertAction, List<Consumer<T>> rowActions) {
        this.title = title;
        this.columns = columns;
        this.data = data;
        this.insertAction = insertAction;
        this.rowActions = rowActions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    public ObservableList<T> getData() {
        return data;
    }

    public void setData(ObservableList<T> data) {
        this.data = data;
    }

    public Consumer<T> getInsertAction() {
		return insertAction;
	}

	public void setInsertAction(Consumer<T> insertAction) {
		this.insertAction = insertAction;
	}

	public List<Consumer<T>> getRowActions() {
        return rowActions;
    }

    public void setRowActions(List<Consumer<T>> rowActions) {
        this.rowActions = rowActions;
    }
    
}
