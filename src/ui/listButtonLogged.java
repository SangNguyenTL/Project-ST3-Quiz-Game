/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import lib.AlertGame;
import ui.manager.frManager;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import lib.openSound;

/**
 *
 * @author nhats
 */
public class listButtonLogged extends ui.frLogin{

    public listButtonLogged(Pane root, DBModel.Player player) {
        super(root,player);
        init();
    }
    
    public void init() {
        root.getChildren().clear();
        GridPane grid = new GridPane();
        root.getChildren().add(grid);
        grid.setPrefSize(root.getWidth(), root.getHeight());
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 0, 0, 100));
       
        Button btnPlay = new Button("Chơi ngay");
        Button btnMan = new Button("Quản lý");
        Button btnBack = new Button("Quay lại");
        Button btnExit = new Button("Thoát");

        ArrayList<Button> allButton = new ArrayList<Button>();
        allButton.add(btnPlay);
        allButton.add(btnMan);
        allButton.add(btnBack);
        allButton.add(btnExit);
        
        openSound hoverButton  = new openSound("sounds/hoverButton.mp3",500);
        for(int i=0;i<allButton.size();i++){
            grid.add(allButton.get(i), 0, i);
            allButton.get(i).getStyleClass().add("btnCus");
            allButton.get(i).setMinWidth(162);
            allButton.get(i).setMinHeight(42);
            allButton.get(i).setOnMouseEntered((MouseEvent t) -> {
                hoverButton.play();
            });
            allButton.get(i).setOnMouseExited((MouseEvent t) -> {
                hoverButton.stopSound();
            });
        }
        //login
        btnPlay.setOnMouseClicked((MouseEvent t) -> {
            root.getChildren().clear();
            frPlaygame playGame = new frPlaygame(root);
            lib.openVideo introGame = new lib.openVideo("video/intro.mp4", Window.getPrimaryStage().getWidth(), Window.getPrimaryStage().getHeight());
            
            Button backGame = new Button("Bỏ qua");
            backGame.setLayoutX(root.getWidth()*0.85);
            backGame.setLayoutY(root.getHeight()*0.88);
            backGame.getStyleClass().add("btnCus");
            backGame.setMinSize(root.getWidth()*0.11,root.getHeight()*0.05);

            root.getChildren().addAll(introGame.getMediaView());
            introGame.star();
            root.getChildren().add(backGame);
            backGame.setOnMouseClicked((MouseEvent c) -> {
                introGame.stop();
            });
            introGame.getMediaPlayer().setOnEndOfMedia(() -> {
                try {
                    introGame.stop();
                } catch (Exception ex) {
                    
                }
            });
            introGame.getMediaPlayer().setOnStopped(() -> {
                try {
                     root.getChildren().clear();
                     root.getChildren().add(playGame.getBorder());
                     playGame.setStatusGame(2);
                } catch (Exception ex) {
                    
                }
            });
        });
        
        //Vào trang quản lý, là true thì vào admin false thì vào user
        btnMan.setOnMouseClicked((MouseEvent t) -> {
            new frManager(root, player);
        });
        
        
        //out
        btnExit.setOnMouseClicked((MouseEvent t) -> {
            new AlertGame("Thoát chương trình", "'Ok' để thoát hoặc 'Cancel' để quay lại", Alert.AlertType.CONFIRMATION) {
                
                @Override
                public void processResult() {
                    if(getResult().get() == ButtonType.OK){
                        System.exit(0);
                    }else{
                        getAlert().close();
                    }
                }
            };
        });
        //top 
        btnBack.setCancelButton(true);
         btnBack.setOnAction((ActionEvent t) -> {
             root.getChildren().clear();
             new frLogin(root);
        });
    }
    
}
