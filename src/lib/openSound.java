/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 *
 * @author nhats
 */
public class openSound {

    Media hit;
    MediaPlayer mediaPlayer;

    public openSound(String path) {
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
                    if (mediaPlayer.getError() == null) {
                        mediaPlayer.setOnError(new Runnable() {
                            public void run() {
                                // Handle asynchronous error in MediaPlayer object.
                            }
                        });
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

    public openSound(String path, int startTime) {
        this(path);
        mediaPlayer.setStartTime(Duration.millis(startTime));
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void play() {

        mediaPlayer.play();
    }

    public void stopSound() {
        mediaPlayer.stop();
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
