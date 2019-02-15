/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.javafx.downloadmanager;

import br.com.fr.commons.javafx.updatemanager.HTTPDownloadUtil;
import br.com.fr.commons.exception.DownloadCanceledException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class GenericDownloadTask extends Task<Void> {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private static final int BUFFER_SIZE = 4096;
    private final String nomeArquivo;
    private final String downloadURL;
    private final String savePath;
    private final Runnable onFinishAction;
    
    public GenericDownloadTask(String nomeArquivo, String downloadURL, String savePath, Runnable onFinishAction) {
        this.nomeArquivo = nomeArquivo;
        this.savePath = savePath;
        this.onFinishAction = onFinishAction;
        this.downloadURL = downloadURL; // + (downloadURL.endsWith("/") ? nomeArquivo : File.separator + nomeArquivo);
    }

    @Override
    protected Void call() throws Exception {

        try {
            HTTPDownloadUtil downloadUtil = new HTTPDownloadUtil();
            downloadUtil.downloadFile(downloadURL);

            String saveFilePath = savePath + File.separator + nomeArquivo; //downloadUtil.getFileName();
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
                onFinishAction.run();
            });
        }

    }

}
