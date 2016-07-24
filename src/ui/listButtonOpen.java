/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import lib.AlertGame;
import java.util.ArrayList;
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
public class listButtonOpen{
    
    public listButtonOpen(Pane root) {
        root.getChildren().clear();
        GridPane grid = new GridPane();
        root.getChildren().add(grid);
        grid.setPrefSize(root.getWidth(), root.getHeight());
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 0, 0, root.getWidth()*0.073));
       
        Button btnLogin = new Button("Đăng nhập");
        Button btnRule = new Button("Luật chơi");
        Button btnTop = new Button("Xếp hạng");
        Button bntSetting = new Button("Thiết đặt");
        Button btnExit = new Button("Thoát");

        ArrayList<Button> allButton = new ArrayList();
        allButton.add(btnLogin);
        allButton.add(btnTop);
        allButton.add(btnRule);
        allButton.add(bntSetting);
        allButton.add(btnExit);
        
        openSound hoverButton  = new openSound("sounds/hoverButton.mp3",500);
        for(int i=0;i<allButton.size();i++){
            grid.add(allButton.get(i), 0, i);
            allButton.get(i).getStyleClass().add("btnCus");
            allButton.get(i).setMinWidth(root.getWidth()*0.11);
            allButton.get(i).setMinHeight(root.getHeight()*0.05);
            allButton.get(i).setOnMouseEntered((MouseEvent t) -> {
                hoverButton.play();
            });
            allButton.get(i).setOnMouseExited((MouseEvent t) -> {
                hoverButton.stopSound();
            });
        }
        //login
        btnLogin.setOnMouseClicked((MouseEvent t) -> {
            new frLogin(root);
        });
        
        //Luật
        btnRule.setOnMouseClicked((MouseEvent t) -> {
            new frHelp(root);
        });
        
        //Thiết đặt
        bntSetting.setOnMouseClicked((MouseEvent t) -> {
            new frSetting(root);
        });
        
        
        //out
        btnExit.setOnMouseClicked((MouseEvent t) -> {
            new AlertGame("Thoát chương trình", "'Ok' để thoát hoặc 'Thôi' để quay lại", Alert.AlertType.CONFIRMATION) {
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
         btnTop.setOnMouseClicked((MouseEvent t) -> {
             new frRank(root);
        });
    }
    
}
