/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.javafx.downloadmanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class DownloadManager {

    private static final Logger logger = Logger.getLogger(DownloadManager.class);
    private static DownloadManager instance;

    public static DownloadManager getInstance() {
        if (instance == null) {
            instance = new DownloadManager();
        }

        return instance;
    }

    private DownloadManager() {
    }
    
    public void downloadFile(String nomeArquivo, String downloadURL, String savePath, Stage downloadStage) throws Exception {
        
        try {
            FXMLLoader loader = new FXMLLoader(DownloadManagerViewController.class.getResource("DownloadManagerView.fxml"));
            Parent root = (Parent) loader.load();
            
            DownloadManagerViewController controller = loader.<DownloadManagerViewController>getController();
            controller.setDownloadParams(nomeArquivo, downloadURL, savePath, downloadStage);

            downloadStage.setTitle("Download de Arquivo");
            downloadStage.setScene(new Scene(root));
            downloadStage.showAndWait();
            
            //Platform.exit();
        } catch (Exception ex) {
            logger.error("Não foi possível concluir o download!!", ex);
            throw ex;
        }
    }
}
