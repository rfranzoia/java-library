/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class FxUtils extends Application {

    private static final Logger logger = Logger.getLogger(FxUtils.class);
    
    public static final String CPF_FORMAT = "([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})";

    /*
     *  LISTENNERS 
     */
    
    public static void addCpfFocusFormatter(TextField textField) {
        textField.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (!newValue) {
                        textField.setText(textField.getText().trim().replaceAll(CPF_FORMAT, "$1.$2.$3-$4"));
                    }
                }
        );
    }

    public static void addCustmomChangeListener(TextField textField, Consumer<Boolean> listener) {
    	textField.focusedProperty().addListener(createCustomListenner(listener));
    }
    
    public static ChangeListener<Boolean> createCustomListenner(Consumer<Boolean> formatter) {
    	
    	ChangeListener<Boolean> cl = (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            formatter.accept(newValue);
        };
        
        return cl;
    }
    
    public static void setMask(TextField textField, String mascara) {

        final List<String> validation = new ArrayList<>();

        for (int cc = 0; cc < mascara.length(); cc++) {
            Character c = mascara.charAt(cc);
            switch (c) {
                case '9':
                case '#':
                    // digito
                    validation.add("[\\d]");
                    break;
                case 'X':
                    // letra maiuscula
                    validation.add("\\p{Upper}");
                    break;
                case 'x':
                    // letra minuscula
                    validation.add("\\p{Lower}");
                    break;
                case '?':
                case '*':
                    // letra minuscula
                    validation.add("[\\p{Graph}\\p{Space}]");
                    break;
                default:
                    validation.add(c.toString());
                    break;
            }
        }
        
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change change) -> {
            final String text = change.getText();
            if (!change.isReplaced()) {
                for (int i = 0; i < text.length(); i++) {

                    if (!Character.valueOf(text.charAt(i)).toString().matches("\\p{Punct}")) {

                        int z = textField.getText().length() - 1;
                        if (z >= 0)  {
                            if (!text.matches(validation.get(z))) {
                                return null;
                            }
                        } else {
                            if (!text.matches(validation.get(0))) {
                                return null;
                            }
                        }
                        
                    }

                }

                if (!change.getText().isEmpty()) {
                    String mascaraSemControles = mascara.replaceAll("\\p{Punct}", "");

                    String tmp = textField.getText().replaceAll("\\p{Punct}", "");
                    
                    if (text.length() >= mascara.length() || tmp.length() >= mascaraSemControles.length() || textField.getText().trim().length() >= mascara.length()) {
                        return null;
                    }

                    if (!change.isDeleted()) {
                        if (textField.getText().length() > 0) {

                            int next = textField.getText().length();
                            
                            if (next < mascara.length() && Character.valueOf(mascara.charAt(next)).toString().matches("\\p{Punct}")) {
                                change.setText("" + mascara.charAt(next) + text);
                                change.selectRange(change.getAnchor() + 1, change.getCaretPosition() + 1);
                            }

                        }
                    }
                }
            }

            return change;
        };

        textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    
    /*
        DIALOGS
    */
    
    public static Stage createDefaultModalStage(String title) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        return stage;
    }
    
    public static Stage createDefaultModalStage(String title, Image icon) {
        Stage stage = createDefaultModalStage(title);
        stage.getIcons().add(icon);
        return stage;
    }
    
    
    public static boolean showYesNoDialog(String message, Window owner) {
        Alert alert = createAlert(Alert.AlertType.CONFIRMATION, "Confirme", owner);
        alert.setContentText(message);
        
        final ButtonType buttonTypeYes = new ButtonType("Sim");
        final ButtonType buttonTypeNo = new ButtonType("Não");
        
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        
        return result.get() == buttonTypeYes;
    }
    
    public static int showOptionsDialog(String message, String[] options, Window owner) {
        int choice = -1;
        
        Alert alert = createAlert(Alert.AlertType.CONFIRMATION, "Selecione", owner);
        alert.setContentText(message);
        
        List<ButtonType> lstOptions = new ArrayList<>();
        for (String option : options) {
            ButtonType btnType = new ButtonType(option);
            lstOptions.add(btnType);
        }
        
        alert.getButtonTypes().setAll(lstOptions);
        Optional<ButtonType> result = alert.showAndWait();
        
        for (ButtonType btn : lstOptions) {
            if (result.get() == btn) {
                choice = lstOptions.indexOf(btn);
                break;
            }
        }
        
        return choice;
    }

    public static void showExceptionDialog(String message, Exception ex) {
        Alert alert = createAlert(Alert.AlertType.ERROR, "Erro");
        alert.setContentText(message);
        
        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Uma exceção ocorreu, segue a lista de causas:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
    
    public static void showMessageDialog(String message, Window owner) {
        Alert alert = createAlert(Alert.AlertType.INFORMATION, "Informação", owner);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void showWarningDialog(String message, Window owner) {
        Alert alert = createAlert(Alert.AlertType.WARNING, "Atenção", owner);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static boolean showDefaultConfirmDialog(String message, Window owner) {
        Alert alert = createAlert(Alert.AlertType.CONFIRMATION, "Confirmação", owner);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    public static Alert createAlert(Alert.AlertType type, String title, Window owner) {
        Alert alert = new Alert(type);
        alert.initModality(Modality.APPLICATION_MODAL);
        
        if (owner != null) {
            alert.initOwner(owner);
        }
        
        alert.setHeaderText(null);
        alert.setTitle(title);
        
        return alert;        
    }
    
    public static Alert createAlert(Alert.AlertType type, String title) {
        return createAlert(type, title, null);
    }
    
    public static void processKeyPress(KeyEvent ke, Map<KeyCode, Runnable> events) {
        Runnable event = events.get(ke.getCode());
        if (event != null) {
            event.run();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DOMConfigurator.configure(FxUtils.class.getResource("log4j.xml"));
        logger.setLevel(Level.WARN);
        logger.warn("FxUtils - Java Commons Library");
        Platform.exit();
    }
    
}
