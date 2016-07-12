/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author nhats
 */
public class frameOpenGame{
        Pane root = new Pane();
    public frameOpenGame(Stage primaryStage) {
        Double width = primaryStage.getWidth(), height = primaryStage.getHeight();
        root.setMinSize(width, height);
        root.setMaxSize(width, height);
        root.setPrefSize(width, height);
        root.autosize();
        new listButtonOpen(root);
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        primaryStage.setScene(scene);
    }
}
