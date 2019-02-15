package br.com.fr.commons.javafx.infra;

import java.io.Serializable;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class ButtonPaneCell<T extends Serializable> extends TableCell<T, Boolean> {

    private final FlowPane flowPane = new FlowPane();

    public ButtonPaneCell(TableView<T> table, List<RowAction<T>> rowActions) {

        flowPane.setPrefHeight(10);
        flowPane.getStylesheets().add(getClass().getResource("buttons.css").toString());

        rowActions.forEach(ra -> {

            Button b = new Button();
            b.setGraphic(new ImageView(ra.getIcon()));
            b.setTooltip(new Tooltip(ra.getHint()));
            b.setPrefSize(17, 17);

            final EventHandler<ActionEvent> actionEvent = (event) -> {

                table.getSelectionModel().select(getTableRow().getIndex());
                T item = (T) table.getItems().get(getTableRow().getIndex());

                if (ra.getValidator() == null || ra.getValidator().test(item)) {
                    if (ra.getAction() != null) {
                        ra.getAction().accept(item);
                    }
                }

                table.refresh();
            };

            b.setOnAction(actionEvent);

            flowPane.getChildren().add(b);
        });

    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(flowPane);
        }
    }
}
