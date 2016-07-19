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
public class frOpenGame{
        Pane root = new Pane();
    public frOpenGame(Stage primaryStage) {
        Double width = primaryStage.getWidth(), height = primaryStage.getHeight();
        root.setMinSize(width, height);
        root.setMaxSize(width, height);
        root.setPrefSize(width, height);
        root.autosize();
        new listButtonOpen(root);
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(frOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        primaryStage.setScene(scene);
    }
}
