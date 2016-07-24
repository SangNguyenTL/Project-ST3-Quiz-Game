/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lib.AlertGame;

/**
 *
 * @author nhats
 */
public class frOpenGame{
        public static Pane root = new Pane();
    public frOpenGame(Stage primaryStage) {
        Double width = primaryStage.getWidth(), height = primaryStage.getHeight();
        root.setMinSize(width, height);
        root.setMaxSize(width, height);
        root.setPrefSize(width, height);
        root.autosize();
        if(DBModel.MyConnect.checkData())
            new listButtonOpen(root);
        else {
            new AlertGame("Lỗi", "Kết nối với cơ sở dữ liệu thất bại xin đặt lại cấu hình.", Alert.AlertType.ERROR) {

                @Override
                public void processResult() {
                }
            };
            new frSetting(root);
        }
            
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(frOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        primaryStage.setScene(scene);
    }
}
