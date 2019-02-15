/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.javafx.updatemanager;

import org.apache.log4j.Logger;

import br.com.fr.commons.ApplicationVersion;
import br.com.fr.commons.exception.WSException;
import br.com.fr.commons.service.ApplicationUpdateService;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class UpdateManager {

    private static final Logger logger = Logger.getLogger(UpdateManager.class);
    private static UpdateManager instance;

    public static UpdateManager getInstance() {
        if (instance == null) {
            instance = new UpdateManager();
        }

        return instance;
    }

    private UpdateManager() {

    }

    public void updateApplication(String appName, String appPrevVersion, String appNewVersion, String downloadURL, String savePath, Stage downloadStage) throws Exception {

        try {
            FXMLLoader loader = new FXMLLoader(DownloadViewController.class.getResource("DownloadView.fxml"));
            Parent root = (Parent) loader.load();

            DownloadViewController controller = loader.<DownloadViewController>getController();
            controller.setDownloadParams(appName, appPrevVersion, appNewVersion, downloadURL, savePath, downloadStage);

            downloadStage.setTitle("Atualização de Aplicação");
            downloadStage.setScene(new Scene(root));
            downloadStage.showAndWait();

            Platform.exit();
        } catch (Exception ex) {
            logger.error("Não foi possível concluir o download!!", ex);
            throw ex;
        }
    }

    public ApplicationVersion isApplicationUpdated(String appName, ApplicationVersion currentInfo, String appUpdateURL, Stage updateCheckStage) throws WSException {
        try {
            ApplicationVersion newInfo = ApplicationUpdateService.getInstance().checkApplicationVersion(appName, appUpdateURL);

            if (newInfo.getMajor() > currentInfo.getMajor()) {
                return newInfo;

            } else if (newInfo.getMajor() == currentInfo.getMajor()) {
                if (newInfo.getMinor() > currentInfo.getMinor()) {
                    return newInfo;

                } else if (newInfo.getMinor() == currentInfo.getMinor()) {
                    if (newInfo.getBuild() > currentInfo.getBuild()) {
                        return newInfo;
                    }
                }
            }

        } catch (Exception ex) {
            logger.error("Não foi possível concluir a verificação!!", ex);
            throw ex;
        }

        return null;
    }

}
