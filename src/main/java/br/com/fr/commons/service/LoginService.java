/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.service;

import br.com.fr.commons.UserLogin;
import br.com.fr.commons.exception.LoginInvalidoException;
import br.com.fr.commons.exception.InvalidProfileException;
import org.apache.log4j.Logger;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class LoginService {
    
    public static final String URL_WS_LOGIN = "/security/login";
    
    private Logger logger = Logger.getLogger(getClass());
    private static LoginService instance;
    private UserLogin currentUser = null;
    
    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }

        return instance;
    }

    private LoginService() {
    }

    public UserLogin loginUsuarioSenha(UserLogin usuarioLogin) throws LoginInvalidoException, InvalidProfileException, Exception {
        
        usuarioLogin = WSService.sendAndReceiveClassByURL(ApplicationUpdateService.getInstance().getDefaultWebServer() + URL_WS_LOGIN, usuarioLogin, UserLogin.class);

        if (usuarioLogin.getNome() == null) {
            throw new LoginInvalidoException();
        }

        if (usuarioLogin.getPerfil() == null) {
            logger.error("Perfil Inválido!");
            throw new InvalidProfileException();
        }

        return usuarioLogin;
    }
    
    public UserLogin getCurrentUser() {
        return this.currentUser;
    }
    
    public void setCurrentUser(UserLogin usuarioLogin) {
        this.currentUser = usuarioLogin;
    }
}
