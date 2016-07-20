/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 *
 * @author nhats
 */
public class Window extends Application {

    private int width;
    private int height;
    private File f;
    private Media m;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;

    public Parent videoOpen(String url) {
        Pane root = new Pane();
        f = new File(System.getProperty("user.dir"), url);
        m = new Media(f.toURI().toString());
        mediaPlayer = new MediaPlayer(m);
        mediaPlayer.setAutoPlay(true);
        mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(width);
        mediaView.setFitHeight(height);
        Button back = new Button("Bỏ qua");
        back.setLayoutX(width*0.85);
        back.setLayoutY(height*0.88);
        back.getStylesheets().add(frOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        back.getStyleClass().add("btnCus");
        back.setMinSize(width*0.11,height*0.05);
        
        root.getChildren().addAll(mediaView);
        root.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                mediaPlayer.stop();
            }
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
            width = Integer.parseInt(i);
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
        if(!loadResolution()){
            width = 1366;
        }
        height  = (width / 16) * 9;
        stage.setTitle("Ai là triệu phú");
        stage.setMaxWidth(width);
        stage.setMaxHeight(height);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
        stage.setScene(new Scene(videoOpen("video/Opening video Of ST3.mp4"), width, height));
        stage.setResizable(false);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                try {
                    mediaPlayer.stop();
                } catch (Exception ex) {
                    error(ex);
                }
            }
        });
        mediaPlayer.setOnStopped(new Runnable() {
            public void run() {
                try {
                    new frOpenGame(stage);
                } catch (Exception ex) {
                    error(ex);
                }
            }
        });
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
