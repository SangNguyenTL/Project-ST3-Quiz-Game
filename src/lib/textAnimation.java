/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

/**
 *
 * @author nhats
 */
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import javafx.util.Duration;

public class textAnimation {

    private Timeline timeline;

    public Timeline setAnimation(Text text, String str, lib.Callback callBack) {
        final IntegerProperty i = new SimpleIntegerProperty(0);
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(20),
                event -> {
                    if (i.get() > str.length()) {
                        timeline.stop();
                        callBack.function();
                    } else {
                        text.setText(str.substring(0, i.get()));
                        i.set(i.get() + 1);
                    }
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        return timeline;
    }
    
    public Timeline setAnimation(int time, lib.Callback callBack) {
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(time),
                event -> {
                    callBack.function();
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        return timeline;
    }
    
    public Timeline setAnimation(Button text, String str) {
        final IntegerProperty i = new SimpleIntegerProperty(0);
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(20),
                event -> {
                    if (i.get() > str.length()) {
                        timeline.stop();
                    } else {
                        text.setText(str.substring(0, i.get()));
                        i.set(i.get() + 1);
                    }
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        return timeline;
    }

    public Timeline setAnimation(Text text, SimpleIntegerProperty numTime) {
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(1000),
                event -> {
                   numTime.set(numTime.get() -1);
                   text.setText(String.valueOf(numTime.get()));
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        return timeline;
    }

    public Timeline setAnimation(Label text, String str, lib.Callback callBack) {
        final IntegerProperty k = new SimpleIntegerProperty(0);
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(20),
                event -> {
                    if (k.get() > str.length()) {
                        timeline.stop();
                        callBack.function();
                    } else {
                        text.setText(str.substring(0, k.get()));
                        k.set(k.get() + 1);
                    }
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        return timeline;
    }
    
    public Timeline getTimeline() {
        return timeline;
    }

}
