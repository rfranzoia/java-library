package br.com.fr.commons.javafx.infra;

import java.io.Serializable;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.FlowPane;

public class ActionButtonCell<T extends Serializable> extends TableCell<T, Boolean> {

    private final FlowPane flowPane = new FlowPane();

    public ActionButtonCell(List<Button> buttons) {

        flowPane.setPrefHeight(17);
        flowPane.setPrefWidth(buttons.size() * 15);
        flowPane.getStylesheets().add(getClass().getResource("buttons.css").toString());
        flowPane.getChildren().addAll(buttons);

    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(flowPane);
        }
    }
}
