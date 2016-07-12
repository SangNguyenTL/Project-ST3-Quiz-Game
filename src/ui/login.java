/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


/**
 *
 * @author nhats
 */
public class login{

    public login(Pane root) {
        GridPane grid = new GridPane();
        grid.setPrefSize(root.getWidth(), root.getHeight());
        root.getChildren().add(grid);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));
        
        Text scenetitle = new Text("Đăng nhập");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setEffect(ds);
        scenetitle.setFill(Color.WHITE);
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Tên tài khoản:");
        userName.setEffect(ds);
        userName.setTextFill(Color.WHITE);
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.getStyleClass().add("txtField");
        userTextField.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Mật khẩu:");
        pw.setEffect(ds);
        pw.setTextFill(Color.WHITE);
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        pwBox.getStyleClass().add("txtField");
        pwBox.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        grid.add(pwBox, 1, 2);
                                                                            
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        btn.getStyleClass().add("btnNor");
        btn.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        
        grid.add(hbBtn, 1, 4);
        
        Button back = new Button("Quay lại");
        back.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        HBox hbback = new HBox(10);
        back.getStyleClass().add("btnCus");
        back.setMinWidth(162);
        back.setMinHeight(42);
        hbback.getChildren().add(back);
        hbback.setPadding(new Insets(10));
        root.getChildren().add(hbback);
        final Text actiontarget = new Text();
            grid.add(actiontarget, 1, 6);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in button pressed");
            }
        });
        back.setOnMouseClicked(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                root.getChildren().clear();
                new listButtonOpen(root);
                
            }
        });
    }
        
}
