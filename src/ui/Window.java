/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;


import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    
    public int width = 1366;
    public int height = (width / 16) * 9;
    File f;
    Media m;
    MediaPlayer mediaPlayer;
    MediaView mediaView;
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
        back.setLayoutX(width-200);
        back.setLayoutY(height-100);
        back.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        back.getStyleClass().add("btnCus");
        back.setMinWidth(162);
        back.setMinHeight(42);
        root.getChildren().addAll(mediaView);
        root.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                mediaPlayer.stop();
            }
        });
        return root;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Ai là triệu phú");
        stage.setMaxWidth(width);
        stage.setMaxHeight(height);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
        stage.setScene(new Scene(videoOpen("video/Opening video Of ST3.mp4"),width,height));
        stage.setResizable(false);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                try {
                    mediaPlayer.stop();
                } catch (Exception ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mediaPlayer.setOnStopped(new Runnable() {
            public void run() {
                try {
                    new frameOpenGame(stage);
                } catch (Exception ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        stage.show();
    }
    
    public static void main(String args[]) {
        launch(args);
    }
}
