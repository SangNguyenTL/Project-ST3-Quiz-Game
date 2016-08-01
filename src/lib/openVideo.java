/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 *
 * @author nhats
 */
public class openVideo {

    protected Media hit;
    protected MediaPlayer mediaPlayer;
    protected MediaView mediaView;
    Properties saveFile;
    boolean isMute;

    private boolean loadResolution() {
        FileInputStream fIs;
        saveFile = new Properties();
        try {
            fIs = new FileInputStream("data.properties");
            saveFile.load(fIs);
            isMute = Boolean.valueOf(saveFile.getProperty("isMute", "false"));
            fIs.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public openVideo(String path, Double width, Double height) {
        loadResolution();
        try {
            this.hit = new Media(getClass().getResource("/" + path).toURI().toString());
            if (hit.getError() == null) {
                hit.setOnError(new Runnable() {
                    public void run() {
                        // Handle asynchronous error in Media object.
                    }
                });
                try {
                    mediaPlayer = new MediaPlayer(hit);
                    mediaPlayer.setMute(isMute);
                    if (mediaPlayer.getError() == null) {
                        mediaPlayer.setOnError(new Runnable() {
                            public void run() {
                                // Handle asynchronous error in MediaPlayer object.
                            }
                        });
                        mediaView = new MediaView(mediaPlayer);
                        mediaView.setOnError(new EventHandler<MediaErrorEvent>() {

                            @Override
                            public void handle(MediaErrorEvent t) {
                                error(t.getMediaError().getLocalizedMessage());
                            }
                        });
                        mediaView = new MediaView(mediaPlayer);
                        mediaView.setFitWidth(width);
                        mediaView.setFitHeight(height);
                    } else {
                        error(hit.getError().getLocalizedMessage());
                    }
                } catch (Exception mediaPlayerException) {
                    error(mediaPlayerException.getLocalizedMessage());
                }
            } else {
                // Handle synchronous error creating Media.
            }
        } catch (Exception mediaException) {
            error(mediaException.getLocalizedMessage());
        }
    }

    public MediaView getMediaView() {
        return mediaView;
    }

    public void star() {
        mediaPlayer.play();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void setAutoPlay(boolean b) {
        mediaPlayer.setAutoPlay(b);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void error(String a) {
        new lib.AlertGame("Lỗi", a, Alert.AlertType.ERROR) {

            @Override
            public void processResult() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
}
