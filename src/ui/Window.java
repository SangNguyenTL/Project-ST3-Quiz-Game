/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 *
 * @author nhats
 */
public class Window extends Application {

    private Double width;
    private Double height;
    private static Stage pStage;
    private MediaView mediaView;
    private lib.openVideo opening;
    public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
        Window.pStage = pStage;
    }
    
    public Parent videoOpen(String url) {
        Pane root = new Pane();
        
        opening = new lib.openVideo(url, width, height);
        opening.setAutoPlay(true);
        mediaView = opening.getMediaView();
        
        Button back = new Button("Bỏ qua");
        back.setLayoutX(width*0.85);
        back.setLayoutY(height*0.88);
        back.getStylesheets().add(frOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        back.getStyleClass().add("btnCus");
        back.setMinSize(width*0.11,height*0.05);
        
        root.getChildren().addAll(mediaView);
        root.getChildren().add(back);
        back.setOnMouseClicked((MouseEvent t) -> {
            opening.stop();
        });
        return root;
    }
    private boolean loadResolution(){
        FileInputStream fIs;
        ObjectInputStream oIs;
        try{
            fIs = new FileInputStream("resolution.ini");
            oIs = new ObjectInputStream(fIs);
            String i = oIs.readObject().toString();
            width = Double.parseDouble(i);
            oIs.close();
            fIs.close();
        }catch(ClassNotFoundException e){
            return false;
        }catch(FileNotFoundException e){
            return false;
        }catch(IOException e){
            return false;
        }
        return true;
    }
    
    public void error(Exception ex) {
        new lib.AlertGame("Lỗi", ex.getMessage(), Alert.AlertType.WARNING) {

            @Override
            public void processResult() {
                getAlert().setOnCloseRequest(new EventHandler<DialogEvent>() {

                    @Override
                    public void handle(DialogEvent t) {
                        System.exit(0);
                    }
                });
            }
        };
    }
    @Override
    public void start(Stage stage) throws Exception {
        setPrimaryStage(stage);
        if(!loadResolution()){
            width = Double.valueOf("1056");
        }
        height  = (width / 16) * 9;
        stage.setTitle("Ai là triệu phú");
        stage.setMaxWidth(width);
        stage.setMaxHeight(height);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
        stage.setScene(new Scene(videoOpen("video/open.mp4"), width, height));
        stage.setResizable(false);
        opening.getMediaPlayer().setOnEndOfMedia(() -> {
            try {
                opening.stop();
            } catch (Exception ex) {
                error(ex);
            }
        });
        opening.getMediaPlayer().setOnStopped(() -> {
            try {
                new frOpenGame(stage);
            } catch (Exception ex) {
                error(ex);
            }
        });
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
