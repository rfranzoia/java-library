package br.com.fr.commons.javafx.updatemanager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 * A utility that downloads a file from a URL.
 *
 * @author www.codejava.net
 *
 */
public class HTTPDownloadUtil {

    private final Logger logger = Logger.getLogger(getClass());

    private HttpURLConnection httpConn;
    /**
     * hold input stream of HttpURLConnection
     */
    private InputStream inputStream;
    private String fileName;
    private int contentLength;

    /**
     * Downloads a file from a URL
     *
     * @param fileURL HTTP URL of the file to be downloaded
     * @throws IOException
     */
    public void downloadFile(String fileURL) throws IOException {
        URL url = new URL(fileURL);
        httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            // output for debugging purpose only
            logger.info("Content-Type = " + contentType);
            logger.info("Content-Disposition = " + disposition);
            logger.info("Content-Length = " + contentLength);
            logger.info("fileName = " + fileName);

            // opens input stream from the HTTP connection
            inputStream = httpConn.getInputStream();

        } else {
            logger.error("Não existe arquivo para download no servidor. Servidor respondeu com código HTTP: " + responseCode);
            throw new IOException("Não existe arquivo para download no servidor. Servidor respondeu com código HTTP: " + responseCode);
        }
    }

    public void disconnect() throws IOException {
        inputStream.close();
        httpConn.disconnect();
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getContentLength() {
        return this.contentLength;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }
}
