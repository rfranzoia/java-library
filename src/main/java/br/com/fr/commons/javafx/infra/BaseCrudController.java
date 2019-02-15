/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.javafx.infra;

import br.com.fr.commons.BaseParameters;
import br.com.fr.commons.FxUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Romeu Franzoia Jr
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseCrudController<T extends Serializable, C extends IFXMLController> implements ICrudController<T> {

    private static final long serialVersionUID = -8465835153698547416L;

    public static final String CRUD_LIST_FXML = "/commons/javafx/infra/CRUDListView.fxml";

    public static final double DEFAULT_WIDTH = 800;
    public static final double DEFAULT_HEIGHT = 600;

    protected ObservableList<T> data = FXCollections.observableArrayList();

    protected C controller;
    protected FXMLLoader loader;
    protected Parent root;
    protected Scene scene;

    protected double sceneWidth;
    protected double sceneHeight;

    protected List<ColumnInfo<T>> columns;
    protected String edtFxml;

    protected List<RowAction<T>> rowActions;

    public BaseCrudController() {
    }

    public BaseCrudController(List<ColumnInfo<T>> columns, String edtFxml) {
        this.columns = columns;
        this.edtFxml = edtFxml;

        try {
            loader = new FXMLLoader(getClass().getResource(this.edtFxml));
            root = (Parent) loader.load();
            scene = new Scene(root);
            controller = loader.<C>getController();

            if (controller == null) {
                System.out.println("controller is null");
            }

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "", ex);
        }
    }

    @Override
    public void setRowAction(List<RowAction<T>> ra) {
        rowActions = ra;
    }

    @Override
    public void run(String title, double width, double height) {
        this.sceneWidth = width;
        this.sceneHeight = height;

        if (rowActions == null) {
            rowActions = Arrays.asList(new RowAction<T>(new Image(BaseParameters.getInstance().getLibImgPath() + "edit.png"),
                    (Consumer<T>) (T t) -> {
                        edit(t);
                    }, "Editar"),
                    new RowAction<T>(new Image(BaseParameters.getInstance().getLibImgPath() + "clear.png"),
                            (Consumer<T>) (T t) -> {
                                if (FxUtils.showDefaultConfirmDialog("EXCLUIR o registro selecionado?", null)) {
                                    delete(t);
                                }
                            }, "Excluir"));
        }

        Consumer<T> insertAction = (Consumer<T>) (T t) -> {
            insert();
        };

        showDynamicView(new CrudCaller(title, columns, data, insertAction, rowActions));
    }

    @Override
    public void edit(T t) {
        data.stream()
                .filter(t1 -> t1.equals(t))
                .forEach((T t1) -> {
                    T t2 = showEditStage(t);
                    controller.setEntity(t1, t2);
                });
    }

    @Override
    public void delete(T t) {
        data.remove(t);
        controller.delete(t);
    }

    @Override
    public void insert() {
        T t = showEditStage(null);

        if (t != null) {
            data.add(t);
        }
    }

    @Override
    public T showEditStage(T t) {
        try {

            Stage editStage = new Stage();
            editStage.initModality(Modality.APPLICATION_MODAL);

            if (t == null) {
                editStage.setTitle("Novo");
            } else {
                editStage.setTitle("Editar");
            }

            controller.setParameters(t, editStage);

            editStage.setScene(scene);
            editStage.showAndWait();

            return (T) controller.getEntity();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "", ex);
            return null;
        }
    }

    public void showDynamicView(CrudCaller caller) {

        try {
            Stage stage = new Stage();
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(caller.getTitle());

            // Carrega a person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(CRUD_LIST_FXML));
            BorderPane anchorPane = (BorderPane) loader.load();

            // Dá ao controlador acesso à the main app.
            CRUDListController listController = loader.getController();
            listController.setInsertAction(caller.getInsertAction());
            listController.configureTable(caller.getColumns(), caller.getData(), rowActions);
            listController.setStage(stage);

            stage.setScene(new Scene(anchorPane, sceneWidth, sceneHeight));
            stage.showAndWait();

        } catch (IOException ioex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "", ioex);
        }
    }

}
