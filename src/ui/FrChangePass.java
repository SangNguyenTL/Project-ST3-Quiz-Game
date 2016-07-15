/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author QUANGTU
 */
public class FrChangePass {

    private TableView table = new TableView();

    public FrChangePass(HBox root) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        Label lblCategory = new Label();

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));

        Text scenetitle = new Text("Đổi mật khẩu");
        scenetitle.setEffect(ds);
        scenetitle.setFill(Color.WHITE);
        gridPane.add(scenetitle, 0, 1);
        scenetitle.setFont(new Font("Arial", 35));

        Label Pass = new Label("Mật khẩu mới:");
        Pass.setEffect(ds);
        Pass.setTextFill(Color.WHITE);
        gridPane.add(Pass, 0, 2);
        Pass.setFont(new Font("Arial", 20));

        PasswordField PassTextField = new PasswordField();
        PassTextField.getStyleClass().add("txtField");
        PassTextField.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        gridPane.add(PassTextField, 1, 2);
        PassTextField.setFont(new Font("Arial", 20));

        Label Pass1 = new Label("Nhập lại mật khẩu mới:");
        Pass1.setEffect(ds);
        Pass1.setTextFill(Color.WHITE);
        gridPane.add(Pass1, 0, 3);
        Pass1.setFont(new Font("Arial", 20));

        PasswordField Pass1TextField = new PasswordField();
        Pass1TextField.getStyleClass().add("txtField");
        Pass1TextField.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        gridPane.add(Pass1TextField, 1, 3);
        Pass1TextField.setFont(new Font("Arial", 20));

        Label Pass2 = new Label("Mật khẩu hiện tại:");
        Pass2.setEffect(ds);
        Pass2.setTextFill(Color.WHITE);
        gridPane.add(Pass2, 0, 4);
        Pass2.setFont(new Font("Arial", 20));

        PasswordField Pass2TextField = new PasswordField();
        Pass2TextField.getStyleClass().add("txtField");
        Pass2TextField.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        gridPane.add(Pass2TextField, 1, 4);
        Pass2TextField.setFont(new Font("Arial", 20));

        Text ND = new Text("Để lưu thay đổi, chúng tôi cần xác nhận bạn chính là\nchủ tài khoản này bằng cách nhập lại mật khẩu hiện tại");
        ND.setFill(Color.CHOCOLATE);
        gridPane.add(ND, 1, 5);
        ND.setFont(new Font("Arial", 20));

        Button btnRe = new Button("Xóa trắng");
        btnRe.getStyleClass().add("btnNor");
        btnRe.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnRe.setFont(new Font("Arial",15));
        
        Button btnCN = new Button("Cập nhật");
        btnCN.getStyleClass().add("btnNor");
        btnCN.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnCN.setFont(new Font("Arial",15));
        
        Button btnExit = new Button("Thoát");
        btnExit.getStyleClass().add("btnNor");
        btnExit.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnExit.setFont(new Font("Arial",15));
       
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnRe);
        hbBtn.getChildren().add(btnCN);
        hbBtn.getChildren().add(btnExit);

        root.getChildren().add(gridPane);

        gridPane.add(hbBtn, 1, 6);
        btnRe.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                PassTextField.setText(null);
                Pass1TextField.setText(null);
                Pass2TextField.setText(null);
            }
        });

        btnExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });

    }

}
