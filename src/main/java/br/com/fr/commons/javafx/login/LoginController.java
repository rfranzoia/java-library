/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.javafx.login;

import br.com.fr.commons.UserLogin;
import br.com.fr.commons.BaseParameters;
import br.com.fr.commons.FxUtils;
import br.com.fr.commons.exception.LoginInvalidoException;
import br.com.fr.commons.exception.InvalidProfileException;
import br.com.fr.commons.service.LoginService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Romeu Franzoia Jr
 */
public class LoginController implements Initializable {

    private Logger logger = Logger.getLogger(getClass());
    
    private Runnable postLoginAction;
    private List<String> validationGroups = new ArrayList<>();
    private String sistema;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnLogin;

    @FXML
    private ImageView imgLogo;

    private Stage stage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgLogo.setImage(new Image(getClass().getResourceAsStream(BaseParameters.getInstance().getLibImgPath() + "logomarca.png")));

        btnCancelar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(BaseParameters.getInstance().getLibImgPath() + "cancel.png"))));
        btnCancelar.setOnAction(e -> stage.close());

        btnLogin.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(BaseParameters.getInstance().getLibImgPath() + "account_circle.png"))));
        btnLogin.setOnAction(e -> validarLogin());

        FxUtils.addCpfFocusFormatter(txtLogin);
        
        txtLogin.setText("");
        txtPassword.setText("");
        
        txtLogin.requestFocus();
    }

    public void setParameters(Stage stage, Runnable postLoginAction, List<String> validationGroups, String sistema) {
        this.stage = stage;
        this.postLoginAction = postLoginAction;
        this.validationGroups = validationGroups;
        this.sistema = sistema;
    }

    public void validarLogin() {
        
        String login = txtLogin.getText().trim().replaceAll("[.-]", "");
        
        if (login.isEmpty() || login.length() < 11 || login.length() > 11) {
            FxUtils.showWarningDialog("Você deve informar o CPF do usuário com 11 digitos", null);
            txtLogin.requestFocus();
            return;
            
        } else if (txtPassword.getText().trim().isEmpty()) {
            FxUtils.showWarningDialog("A senha do usuário deve ser informada e não pode ser vazia!!", null);
            txtPassword.requestFocus();
            return;
        }
        
        UserLogin usuarioLogin = new UserLogin();
        usuarioLogin.setLogin(login);
        usuarioLogin.setSenha(txtPassword.getText());
        usuarioLogin.setSistema(sistema);

        try {
            usuarioLogin = LoginService.getInstance().loginUsuarioSenha(usuarioLogin);
            
            if (usuarioLogin.getErro() != null || usuarioLogin.getNome() == null) {
                throw new LoginInvalidoException(usuarioLogin.getErro());
            }

            if (usuarioLogin.getPerfil() == null || !isUserGroupValid(usuarioLogin.getPerfil())) {
                logger.error("Perfil Inválido!!!");
                throw new InvalidProfileException();
            }
            
            LoginService.getInstance().setCurrentUser(usuarioLogin);
            
            if (postLoginAction != null) {
                Platform.runLater(postLoginAction);
            }
            
        } catch (InvalidProfileException ex) {
            logger.error("Perfil Inválido!!", ex);
            FxUtils.showMessageDialog("O usuário informado não está autorizado a configurar esta aplicação!", null);
            
        } catch (LoginInvalidoException ex) {
            logger.error("Erro no Login!!", ex);
            FxUtils.showMessageDialog("Não foi possível efetuar o login com o usuário informado!", null);
            
        } catch (Exception ex) {
            logger.error("Erro no Login!!", ex);
            FxUtils.showMessageDialog("Ocorreu um problema ao tentar efetuar o login!", null);
        }

    }
    
    private boolean isUserGroupValid(String userGroup) {
        
        for (String group : validationGroups) {
            if (group.equalsIgnoreCase(userGroup)) {
                return true;
            }
        }
        
        return false;
    }
    
}
