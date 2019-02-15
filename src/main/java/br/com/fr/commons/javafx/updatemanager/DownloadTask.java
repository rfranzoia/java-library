/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.javafx.updatemanager;

import br.com.fr.commons.exception.DownloadCanceledException;
import br.com.fr.commons.FxUtils;
import br.com.fr.commons.service.ApplicationUpdateService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class DownloadTask extends Task<Void> {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private static final int BUFFER_SIZE = 4096;
    private final String downloadURL;
    private final String savePath;
    private final String appName;

    private final String UPDATE_APP_NAME = "GenericAppUpdater.jar";

    private final Stage stage;

    public DownloadTask(Stage stage, String appName, String downloadURL, String savePath) {
        this.stage = stage;
        this.appName = appName;
        this.savePath = savePath;
        this.downloadURL = ApplicationUpdateService.getInstance().getDefaultWebServer() +
                                downloadURL + 
                                    (downloadURL.endsWith("/") ? appName : File.separator + appName);
        
    }

    @Override
    protected Void call() throws Exception {

        try {
            HTTPDownloadUtil downloadUtil = new HTTPDownloadUtil();
            downloadUtil.downloadFile(downloadURL);

            String saveFilePath = savePath + File.separator + downloadUtil.getFileName() + ".tmp";
            InputStream inputStream = downloadUtil.getInputStream();

            try ( // opens an output stream to save into file

                FileOutputStream outputStream = new FileOutputStream(saveFilePath)) {

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                long totalBytesRead = 0;
                long fileSize = downloadUtil.getContentLength();

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    if (isCancelled()) {
                        throw new DownloadCanceledException();
                    }

                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                    int percentCompleted = (int) (totalBytesRead * 100 / fileSize);

                    if (percentCompleted % 3 == 0 || percentCompleted % 100 == 0) {
                        updateMessage(Integer.toString(percentCompleted) + "%");
                        updateProgress(totalBytesRead, fileSize);
                    }
                }

                updateProgress(fileSize, fileSize);
            }

            downloadUtil.disconnect();

        } catch (IOException ex) {
            logger.error("", ex);
            updateProgress(0, 0);
            cancel();

        } catch (DownloadCanceledException ex) {
            logger.warn("Usuário cancelou o download!!", ex);
            cancel();
        }

        return null;
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            Platform.runLater(() -> {
                FxUtils.showMessageDialog("Download concluído com exito!", stage);
                saveFile();
            });
        }

    }

    private void saveFile() {
        try {
            // apaga o anterior e extrai o arquivo de atualização de dentro do jar da aplicação antes de executar
            // isso garante a existencia do arquivo
            logger.info("Removendo versão anterior da aplicação de atualização .... ");
            File updater = new File(savePath + "/" + UPDATE_APP_NAME);
            if (updater.exists()) {
                try {
                    updater.delete();
                } catch (Exception ex) {
                    logger.error("não foi possível remover a aplicação de atualização anterior!!");
                }
            }

            logger.info("Extraindo nova aplicação de atualização .... ");
            InputStream is = (getClass().getResourceAsStream(UPDATE_APP_NAME));
            FileOutputStream fos = new FileOutputStream(savePath + "/" + UPDATE_APP_NAME);

            if (is == null) {
                is = new FileInputStream(new File(getClass().getResource(UPDATE_APP_NAME).getPath()));
            }

            byte[] data = IOUtils.toByteArray(is);

            fos.write(data);
            fos.flush();
            fos.close();

            is.close();

            // chama o arquivo que roda a nova versão da aplicação recentemente baixada
            updater = new File(savePath + "/" + UPDATE_APP_NAME);

            logger.info("Caminho do updater: " + updater.getCanonicalPath());
            logger.info("Executando aplicação de atualização .... ");

            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java -jar " + UPDATE_APP_NAME + " " + appName);

            //Platform.exit();
            stage.close();

        } catch (Exception ex) {
            logger.error("Ocorreu um erro durante a atualização!!!", ex);
        }

    }

}
