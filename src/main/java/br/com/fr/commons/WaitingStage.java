package br.com.fr.commons;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class WaitingStage {

    private static final int shadowSize = 25;

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private Button buttonCancel;
    private double barWidth;
    
    private Task<?> task;
    private Runnable onTaskDone;
    private Runnable onTaskFail;

    private static final double DEFAULT_BAR_WIDTH = 300;
    
    private boolean isShowingCancel = true;

    public WaitingStage() {
        this(DEFAULT_BAR_WIDTH, true);
    }

    public WaitingStage(boolean showCancelButton) {
    	this(DEFAULT_BAR_WIDTH, showCancelButton);
    }
    
    public WaitingStage(double barWidth) {
    	this(barWidth, true);
    }
    
    public WaitingStage(double barWidth, boolean showCancelButton) {

        this.barWidth = barWidth;
        this.isShowingCancel = showCancelButton;
        
        loadProgress = new ProgressBar();
        loadProgress.setPrefHeight(30);
        loadProgress.setPrefWidth(barWidth);

        progressText = new Label("");
        progressText.setAlignment(Pos.CENTER);

        if (showCancelButton) {
	        buttonCancel = new Button();
	        buttonCancel.setGraphic(new ImageView(new Image(getClass().getResource(BaseParameters.getInstance().getLibImgPath() + "cancel.png").toString())));
	        buttonCancel.setPrefSize(30, 30);
	        buttonCancel.setTooltip(new Tooltip("Cancelar"));
        }
        
        this.onTaskDone = () -> {};
        this.onTaskFail = () -> {};

    }
    
    public WaitingStage setTask(Task<?> task) {
        this.task = task;
        return this;
    }
    
    public WaitingStage setOnTaskDone(Runnable onTaskDone) {
        this.onTaskDone = onTaskDone;
        return this;
    }
    
    public WaitingStage setOnTaskFail(Runnable onTaskFail) {
        this.onTaskFail = onTaskFail;
        return this;
    }

    public void doIt(Task<?> task) {
        this.task = task;
        doIt();
    }
    
    public void doIt(Task<?> task, Runnable onTaskDone) {
        this.task = task;
        this.onTaskDone = onTaskDone;
        doIt();
    }
    
    public void doIt(Task<?> task, Runnable onTaskDone, Runnable onTaskFail) {
        this.task = task;
        this.onTaskDone = onTaskDone;
        this.onTaskFail = onTaskFail;
        doIt();
    }
    
    public void doIt() {
        Stage stage = new Stage();
        showIt(stage);
        new Thread(task).start();
    }

    private void showIt(Stage stage) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());

        if (isShowingCancel) {
	        buttonCancel.setOnAction((e) -> {
	            task.cancel();
	        });
        }

        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED 
                    || newState == Worker.State.CANCELLED
                    || newState == Worker.State.FAILED) {
                
                closeStageWindow(stage);

                if (newState == Worker.State.SUCCEEDED) {
                    onTaskDone.run();
                    
                } else if (newState == Worker.State.FAILED) {
                    onTaskFail.run();    
                }
            }

        });

        StackPane stackPane = new StackPane(createShadowPane());
        stackPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);" + 
                           "-fx-background-insets: " + shadowSize + ";");
        stackPane.setPrefWidth(barWidth + 50);

        StackPane barPane = new StackPane();
        barPane.getChildren().addAll(loadProgress, progressText);

        FlowPane flowButton = new FlowPane();
        flowButton.setAlignment(Pos.CENTER);
        flowButton.setHgap(5);
        
        flowButton.getChildren().addAll(barPane);
        
        if (isShowingCancel) {
        	flowButton.getChildren().addAll(buttonCancel);
        }
        
        flowButton.setPrefHeight(40);

        barPane.setAlignment(Pos.CENTER);
        
        if (isShowingCancel) {
        	buttonCancel.setAlignment(Pos.CENTER);
        }

        stackPane.setPrefHeight(100);
        stackPane.getChildren().addAll(flowButton);
        Scene scene = new Scene(stackPane, barWidth + 100, 100);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    private void closeStageWindow(Stage stage) {
        loadProgress.progressProperty().unbind();
        loadProgress.setProgress(1);
        
        stage.toFront();
        
        FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.5), splashLayout);
        fadeSplash.setFromValue(1.0);
        fadeSplash.setToValue(0.0);
        fadeSplash.setOnFinished(actionEvent -> stage.hide());
        fadeSplash.play();
    }

    private Pane createShadowPane() {
        Pane shadowPane = new Pane();

        // a "real" app would do this in a CSS stylesheet.
        shadowPane.setStyle("-fx-background-color: white;" + 
                            "-fx-effect: dropshadow(gaussian, black, " + shadowSize + ", 0, 0, 0);" + 
                            "-fx-background-insets: " + shadowSize + ";");

        Rectangle innerRect = new Rectangle();
        Rectangle outerRect = new Rectangle();
        shadowPane.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            innerRect.relocate(newBounds.getMinX() + shadowSize, newBounds.getMinY() + shadowSize);
            innerRect.setWidth(newBounds.getWidth() - shadowSize * 2);
            innerRect.setHeight(newBounds.getHeight() - shadowSize * 2);

            outerRect.setWidth(newBounds.getWidth());
            outerRect.setHeight(newBounds.getHeight());

            Shape clip = Shape.subtract(outerRect, innerRect);
            shadowPane.setClip(clip);
        });

        return shadowPane;
    }

}
