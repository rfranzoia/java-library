/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.javafx.updatemanager;

/**
 *
 * @author Romeu Franzoia Jr
 */
import br.com.fr.commons.BaseParameters;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DownloadViewController implements Initializable {

    @FXML
    private TextField txtVersaoAnterior;

    @FXML
    private TextField txtVersaoAtual;

    @FXML
    private Button btnCancelar;

    @FXML
    private ProgressBar progressDownload;

    @FXML
    private TextField txtAppName;

    private Stage stage;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCancelar.requestFocus();
    }
    
    
    public void setDownloadParams(String appName, String appPrevVersion, String appCurrVersion, String downloadURL, String savePath, Stage stage) {
        this.stage = stage;
        
        txtAppName.setText(appName);
        txtVersaoAnterior.setText(appPrevVersion);
        txtVersaoAtual.setText(appCurrVersion);
        
        DownloadTask downloadTask = new DownloadTask(stage, appName, downloadURL, savePath);
        this.stage.setOnShowing(we -> startDownload(downloadTask));
     
        btnCancelar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(BaseParameters.getInstance().getLibImgPath() + "cancel.png"))));
        btnCancelar.setOnAction(e -> {
            downloadTask.cancel();
            stage.close();
        });
        
    }
    
    public void startDownload(DownloadTask downloadTask) {
        progressDownload.progressProperty().unbind();
        progressDownload.progressProperty().bind(downloadTask.progressProperty());
        new Thread(downloadTask).start();
    }

}
