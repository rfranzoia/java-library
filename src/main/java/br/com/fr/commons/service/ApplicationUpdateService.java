/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.service;

import br.com.fr.commons.ApplicationVersion;
import br.com.fr.commons.exception.WSException;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class ApplicationUpdateService {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private static ApplicationUpdateService instance;

    private String defaultWebServer = "";

    public static ApplicationUpdateService getInstance() {
        if (instance == null) {
            instance = new ApplicationUpdateService();
        }

        return instance;
    }

    private ApplicationUpdateService() {
    }

    public String getDefaultWebServer() {
        return defaultWebServer;
    }

    public void setDefaultWebServer(String defaultWebServer) {
        this.defaultWebServer = defaultWebServer;
    }

    public ApplicationVersion checkApplicationVersion(String applicationName, String applicationUpdateURL) throws WSException {
        try {
            String[] param = new String[]{applicationName};
            ApplicationVersion appUpdInfo = WSService.sendAndReceiveClassByURL(defaultWebServer + applicationUpdateURL, param, ApplicationVersion.class);

            if (appUpdInfo == null) {
                throw new WSException("Verificação de versão falhou!");
            }

            return appUpdInfo;
        } catch (Exception ex) {
            logger.error("Falha ao buscar versão da aplicação!", ex);
            throw new WSException("Falha ao buscar versão da aplicação!", ex);
        }
    }

    public ApplicationVersion isApplicationUpdated(String appName, ApplicationVersion currentInfo, String appUpdateURL) throws WSException {
        try {
            ApplicationVersion newInfo = checkApplicationVersion(appName, appUpdateURL);

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
            logger.error("Não foi possível concluir a verificação!", ex);
            throw ex;
        }

        return null;
    }

}
