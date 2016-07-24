/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.io.File;
import javafx.scene.media.Media;
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
    public openVideo(String path, Double width, Double height) {
        this.hit = new Media(new File(System.getProperty("user.dir"), path).toURI().toString());
        this.mediaPlayer = new MediaPlayer(hit);
        mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(width);
        mediaView.setFitHeight(height);
    }

    public MediaView getMediaView() {
        return mediaView;
    }
    
    public void star(){
        mediaPlayer.play();
    }
    
    public void stop(){
        mediaPlayer.stop();
    }

    public void setAutoPlay(boolean b) {
        mediaPlayer.setAutoPlay(b);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
