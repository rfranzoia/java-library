/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.javafx.downloadmanager;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import br.com.fr.commons.BaseParameters;
import br.com.fr.commons.FxUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Romeu Franzoia Jr
 */
public class DownloadManagerViewController implements Initializable {

    @FXML
    private TextField txtNomeArquivo;

    @FXML
    private ProgressBar progressDownload;

    @FXML
    private Button btnAbrir;

    @FXML
    private Button btnCancelar;

    private Stage stage;
    
    private final Logger logger = Logger.getLogger(getClass());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAbrir.setDisable(true);
    }

    public void setDownloadParams(String nomeArquivo, String downloadURL, String savePath, Stage stage) {
        this.stage = stage;

        txtNomeArquivo.setText(nomeArquivo);

        GenericDownloadTask downloadTask = new GenericDownloadTask(nomeArquivo, downloadURL, savePath, () -> enableOpenButton());
        this.stage.setOnShowing(we -> startDownload(downloadTask));

        btnCancelar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(BaseParameters.getInstance().getLibImgPath() + "cancel.png"))));
        btnCancelar.setOnAction(e -> {
            downloadTask.cancel();
            stage.close();
        });

        btnAbrir.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(BaseParameters.getInstance().getLibImgPath() + "open_in_new.png"))));
        btnAbrir.setOnAction(e -> openFile(nomeArquivo, savePath));

    }
    
    public void startDownload(GenericDownloadTask downloadTask) {
        progressDownload.progressProperty().unbind();
        progressDownload.progressProperty().bind(downloadTask.progressProperty());
        new Thread(downloadTask).start();
    }

    public void enableOpenButton() {
        btnAbrir.setDisable(false);
    }

    public void openFile(String nomeArquivo, String savePath) {
        new Thread(() -> {
            try {
                String fileName = savePath +
                        (savePath.endsWith(File.separator)? nomeArquivo : File.separator + nomeArquivo);
                File file = new File(fileName);
                
                if (file.exists() && file.length() > 0) {
                    Desktop.getDesktop().open(file);
                } else {
                    FxUtils.showMessageDialog("O arquivo não existe ou possui tamanho ZERO!\n" + fileName, stage);
                }
            } catch (IOException ioex) {
                logger.error("", ioex);
            }
        }).start();
    }
}
