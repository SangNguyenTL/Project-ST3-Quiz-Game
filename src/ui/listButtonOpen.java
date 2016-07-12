/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import lib.openSound;

/**
 *
 * @author nhats
 */
public class listButtonOpen {

    public listButtonOpen(Pane root) {
        GridPane grid = new GridPane();
        root.getChildren().add(grid);
        grid.setPrefSize(root.getWidth(), root.getHeight());
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 0, 0, 100));
       
        Button btnLogin = new Button("Đăng nhập");
        Button btnRule = new Button("Luật chơi");
        Button btnTop = new Button("Xếp hạng");
        Button btnExit = new Button("Thoát");

        ArrayList<Button> allButton = new ArrayList();
        allButton.add(btnLogin);
        allButton.add(btnTop);
        allButton.add(btnRule);
        allButton.add(btnExit);
        
        openSound hoverButton  = new openSound("sounds/hoverButton.mp3",500);
        for(int i=0;i<allButton.size();i++){
            grid.add(allButton.get(i), 0, i);
            allButton.get(i).getStyleClass().add("btnCus");
            allButton.get(i).setMinWidth(162);
            allButton.get(i).setMinHeight(42);
            allButton.get(i).setOnMouseEntered(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                hoverButton.play();
            }
        });
            allButton.get(i).setOnMouseExited(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                hoverButton.stopSound();
            }
        });
        }
        //login
        btnLogin.setOnMouseClicked(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                root.getChildren().clear();
                new login(root);
                
            }
        });
        
        
        //out
        btnExit.setOnMouseClicked(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                new AlertGame("Ai là triệu phú", "Bạn thực sự muốn thoát");
            }
        });
        
    }
    
}
