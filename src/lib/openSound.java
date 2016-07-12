/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
/**
 *
 * @author nhats
 */
public class openSound {
    Media hit;
    MediaPlayer mediaPlayer;
    public openSound(String path) {
            File    f = new File(System.getProperty("user.dir"), path);
            hit = new Media(f.toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            
    }

    public openSound(String path,int startTime) {
        this(path);
        mediaPlayer.setStartTime(Duration.millis(startTime));
    }
    

    public void play() {
        
        mediaPlayer.play();
    }
    
    public void stopSound(){
        mediaPlayer.stop();
    }
    
}
