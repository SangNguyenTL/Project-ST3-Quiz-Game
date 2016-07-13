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
 * @author QUANGTU
 */
public class RegistrationGame {

    public RegistrationGame(Pane root) {
        GridPane grid = new GridPane();
        grid.setPrefSize(root.getWidth(), root.getHeight());
        root.getChildren().add(grid);
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 200));

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));
        
        Text scenetitle = new Text("Đăng ký");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setEffect(ds);
        scenetitle.setFill(Color.WHITE);
        grid.add(scenetitle, 0, 0, 2, 1);
        scenetitle.setFont(new Font("Arial",40));
        
        Label userName = new Label("Tên người chơi:");
        userName.setEffect(ds);
        userName.setTextFill(Color.WHITE);
        grid.add(userName, 0, 1);
        userName.setFont(new Font("Arial",20));
        
        TextField userTextField = new TextField();
        userTextField.getStyleClass().add("txtField");
        userTextField.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        grid.add(userTextField, 1, 1);
        userTextField.setFont(new Font("Arial",20));
        
        Label email = new Label("Email/Tên đăng nhập:");
        email.setEffect(ds);
        email.setTextFill(Color.WHITE);
        grid.add(email, 0, 2);
        email.setFont(new Font("Arial",20));
        
        TextField emailfield = new TextField();
        emailfield.getStyleClass().add("txtField");
        emailfield.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        grid.add(emailfield, 1, 2);
        emailfield.setFont(new Font("Arial",20));
        
        Label lbPw = new Label("Mật khẩu:");
        lbPw.setEffect(ds);
        lbPw.setTextFill(Color.WHITE);
        grid.add(lbPw, 0, 3);
        lbPw.setFont(new Font("Arial",20));
        
        Label lbRepw = new Label("Nhập lại mật khẩu:");
        lbRepw.setEffect(ds);
        lbRepw.setTextFill(Color.WHITE);
        grid.add(lbRepw, 0, 4);
        lbRepw.setFont(new Font("Arial",20));
        
        PasswordField pwBox = new PasswordField();
        pwBox.getStyleClass().add("txtField");
        pwBox.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        grid.add(pwBox, 1, 3);
        pwBox.setFont(new Font("Arial",20));
        
        PasswordField rePwBox = new PasswordField();
        rePwBox.getStyleClass().add("txtField");
        rePwBox.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        grid.add(rePwBox, 1, 4);
        rePwBox.setFont(new Font("Arial",20));
        
        Label year = new Label("Năm sinh:");
        year.setEffect(ds);
        year.setTextFill(Color.WHITE);
        grid.add(year, 0, 5);
        year.setFont(new Font("Arial",20));
        
        
        TextField yearfield = new TextField();
        yearfield.getStyleClass().add("txtField");
        yearfield.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        grid.add(yearfield, 1, 5);
        yearfield.setFont(new Font("Arial",20));
        
        Button btnOK = new Button("Đồng ý");
        btnOK.getStyleClass().add("btnNor");
        btnOK.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());

        Button btnRe = new Button("Xóa trắng");
        btnRe.getStyleClass().add("btnNor");
        btnRe.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnRe.setOnMouseClicked(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
            userTextField.setText(null);
            emailfield.setText(null);
            pwBox.setText(null);
            yearfield.setText(null);
            
            }
        });
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(btnRe);
        hbBtn.getChildren().add(btnOK);
        
        
        grid.add(hbBtn, 1, 6);
        
        
        

        Button back = new Button("Quay lại");
        back.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        HBox hbback = new HBox(10);
        back.getStyleClass().add("btnCus");
        back.setMinWidth(162);
        back.setMinHeight(42);
        hbback.getChildren().add(back);
        hbback.setPadding(new Insets(10));
        root.getChildren().add(hbback);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                root.getChildren().clear();
                new listButtonOpen(root);

            }
        });
    }
}
