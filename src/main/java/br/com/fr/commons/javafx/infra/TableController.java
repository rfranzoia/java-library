package br.com.fr.commons.javafx.infra;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.log4j.Logger;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TableController<T extends Serializable> {

    private TableView<T> table;

    public TableController() {
    }

    public TableController(TableView<T> table) {
        this.table = table;
    }

    public TableView<T> getTable() {
        return table;
    }

    public void setTable(TableView<T> table) {
        this.table = table;
    }

    public void configureTable(List<ColumnInfo<T>> columnsInfo, ObservableList data) {

        for (ColumnInfo<T> column : columnsInfo) {
            TableColumn tc = new TableColumn(column.getTitle());

            tc.setCellValueFactory(new PropertyValueFactory<T, Object>(column.getFieldName()));
            tc.setCellFactory(getCustomCellFactory(column.getFormatter()));

            double minWidth = Double.valueOf(column.getSize());

            if (minWidth > 0) {
                tc.setMinWidth(minWidth);
                tc.setPrefWidth(minWidth);
                table.getColumns().add(tc);
            }

            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }

    }

    private Callback<TableColumn<T, Object>, TableCell<T, Object>> getCustomCellFactory(Function<Object, String> formatter) {
        return new Callback<TableColumn<T, Object>, TableCell<T, Object>>() {

            @Override
            public TableCell<T, Object> call(TableColumn<T, Object> param) {
                TableCell<T, Object> cell = new TableCell() {
                    @Override
                    public void updateItem(final Object item, boolean empty) {
                        if (item == null || empty) {
                            setText("");

                        } else if (formatter != null) {
                            setText(formatter.apply(item));
                        } else {
                            try {
                                setText(item.toString());
                            } catch (Exception ex) {
                                Logger.getLogger(getClass()).error(ex);
                                setText("");
                            }
                        }
                    }
                };
                return cell;
            }

        };
    }

    public void setDoubleClickAction(Consumer<T> action) {
        table.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    T item = (T) row.getItem();
                    action.accept(item);
                }
            });
            return row;
        });
    }

    public void addTableAction(TableView<T> table, List<RowAction<T>> rowActions) {

        TableColumn actionColumn = new TableColumn();
        actionColumn.setSortable(false);
        actionColumn.setMinWidth(50);
        actionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<T, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }

        });

        actionColumn.setCellFactory(new Callback<TableColumn<T, Boolean>, TableCell<T, Boolean>>() {
            @Override
            public TableCell<T, Boolean> call(TableColumn<T, Boolean> param) {
                return new ButtonPaneCell<>(table, rowActions);
            }
        });

        table.getColumns().add(actionColumn);

        table.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    T item = (T) row.getItem();
                    rowActions.get(0).getAction().accept(item);
                }
            });
            return row;
        });

    }
}
