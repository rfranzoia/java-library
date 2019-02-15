/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.javafx.infra;

import br.com.fr.commons.BaseParameters;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Romeu Franzoia Jr
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CRUDListController implements Initializable {

	@FXML
	TableView table;

	@FXML
	TextField edtPesquisar;

	@FXML
	Button btnSair;

	@FXML
	Button btnPesquisar;

	@FXML
	Button btnLimparPesquisa;

	@FXML
	Button btnIncluir;

	private Stage stage;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		btnPesquisar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(BaseParameters.getInstance().getLibImgPath() + "search.png"))));

		btnLimparPesquisa.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(BaseParameters.getInstance().getLibImgPath() + "delete.png"))));
		btnLimparPesquisa.setTooltip(new Tooltip("Limpar Pesquisa"));

		btnSair.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(BaseParameters.getInstance().getLibImgPath() + "power_settings_new.png"))));

		btnIncluir.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(BaseParameters.getInstance().getLibImgPath() + "add_circle.png"))));
		table.requestFocus();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setOnShown((event) -> {
			table.requestFocus();
			table.getSelectionModel().selectRange(0, 0);
		});
		
		this.stage.centerOnScreen();
	}

	public <T extends Serializable> void setInsertAction(Consumer<T> insertAction) {
		if (insertAction != null) {
			btnIncluir.setOnAction(event -> {
				insertAction.accept(null);
				table.refresh();
			});
		}
	}

	public <T extends Serializable> void configureTable(List<ColumnInfo<T>> columnsInfo, ObservableList data, List<RowAction<T>> rowActions) {

		TableController<T> tblController = new TableController<>(table);
		tblController.configureTable(columnsInfo, data);
		tblController.addTableAction(table, rowActions);
		
		table.setItems(data);

		FilteredList<T> filteredData = new FilteredList<>(data, p -> true);
		btnPesquisar.setOnAction(event -> {
			filteredData.setPredicate((T t1) -> {
				return (t1.toString().toLowerCase().contains(edtPesquisar.getText().trim().toLowerCase()));
			});

			table.setItems(filteredData);
			table.refresh();
			table.requestFocus();
		});

		btnLimparPesquisa.setOnAction(event -> {
			edtPesquisar.setText("");
			table.setItems(data);
			table.refresh();
			table.requestFocus();
		});

		btnSair.setOnAction(event -> stage.close());
	}

}
