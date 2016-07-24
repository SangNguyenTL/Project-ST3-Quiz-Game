/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import DBModel.Player;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import lib.AlertGame;

/**
 *
 * @author QUANGTU
 */
public class frRegistration extends frLogin {

    protected Boolean type;
    protected TextField txtplayer;
    protected TextField txtemail;
    protected TextField txtyear;
    protected PasswordField txtpass;
    protected PasswordField txtpass1;
    protected Player playerSign;
    protected GridPane grid;
    protected ArrayList<TextField> listTextFeild;
    public frRegistration(Pane root, DBModel.Player player) {
        super(root, player);
        init();
    }

    public void init() {
        root.getChildren().clear();
        grid = new GridPane();
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
        scenetitle.setFont(new Font("Arial", 40));

        Label userName = new Label("Tên người chơi:");
        userName.setEffect(ds);
        userName.setTextFill(Color.WHITE);
        grid.add(userName, 0, 1);
        userName.setFont(new Font("Arial", 20));

        txtplayer = new TextField();
        txtplayer.setPromptText("Tên người chơi.");
        txtemail = new TextField();
        txtemail.setPromptText("Email");
        txtyear = new TextField();
        txtyear.setPromptText("Năm sinh");
        listTextFeild = new ArrayList<>();
        listTextFeild.add(txtplayer);
        listTextFeild.add(txtemail);
        listTextFeild.add(txtyear);
        
        for(int i = 0; i< listTextFeild.size(); i ++){
            listTextFeild.get(i).getStyleClass().add("txtField");
            listTextFeild.get(i).setFont(new Font("Arial", 20));
            grid.add(listTextFeild.get(i), 1, i+1);
        }
        
        Label email = new Label("Email/Tên đăng nhập:");
        email.setEffect(ds);
        email.setTextFill(Color.WHITE);
        grid.add(email, 0, 2);
        email.setFont(new Font("Arial", 20));
        
        Label year = new Label("Năm sinh:");
        year.setEffect(ds);
        year.setTextFill(Color.WHITE);
        grid.add(year, 0, 3);
        year.setFont(new Font("Arial", 20));

        Label lbPw = new Label("Mật khẩu:");
        lbPw.setEffect(ds);
        lbPw.setTextFill(Color.WHITE);
        grid.add(lbPw, 0, 4);
        lbPw.setFont(new Font("Arial", 20));

        txtpass = new PasswordField();
        txtpass.setPromptText("Mật khẩu");
        txtpass.getStyleClass().add("txtField");
        grid.add(txtpass, 1, 4);
        txtpass.setFont(new Font("Arial", 20));

        Label lbRepw = new Label("Nhập lại mật khẩu:");
        lbRepw.setEffect(ds);
        lbRepw.setTextFill(Color.WHITE);
        grid.add(lbRepw, 0, 5);
        lbRepw.setFont(new Font("Arial", 20));

        txtpass1 = new PasswordField();
        txtpass1.setPromptText("Nhập lại mật khẩu");
        txtpass1.getStyleClass().add("txtField");
        grid.add(txtpass1, 1, 5);
        txtpass1.setFont(new Font("Arial", 20));

        
        Button btnOK = new Button("Đồng ý");
        btnOK.getStyleClass().add("btnNor");

        Button btnRe = new Button("Xóa trắng");
        btnRe.getStyleClass().add("btnNor");

        btnOK.setOnMouseClicked((MouseEvent event) -> {
            playerSign = new Player();
            playerSign.setEmail(txtemail.getText());
            if (checkData()) {
                playerSign.setUserName(txtplayer.getText());
                playerSign.setPasword(txtpass.getText());
                playerSign.setYear(Integer.parseInt(txtyear.getText()));
                if (playerSign.insert()){
                    new AlertGame("Thành công", "Đăng ký thành công, bạn có thể đăng nhập\n Tên tài khoản của bạn là:\n\t "+playerSign.getEmail()+"!", Alert.AlertType.INFORMATION) {
                        
                        @Override
                        public void processResult() {
                            if(getResult().get()==ButtonType.OK){
                                root.getChildren().clear();
                                new frLogin(root);    
                            }
                        }
                    };
                }else{
                    new AlertGame("Thất bại", "Xuất hiện lỗi, xin thử lại sau", Alert.AlertType.WARNING) {
                        
                        @Override
                        public void processResult() {
                        }
                    };
                }
            }

        });

        btnRe.setOnMouseClicked((MouseEvent t) -> {
            txtplayer.setText(null);
            txtemail.setText(null);
            txtpass.setText(null);
            txtpass1.setText(null);
            txtyear.setText(null);
        });

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(btnRe);
        hbBtn.getChildren().add(btnOK);

        grid.add(hbBtn, 1, 6);

        Button back = new Button("Quay lại");
        HBox hbback = new HBox(10);
        back.getStyleClass().add("btnCus");
        back.setMinWidth(162);
        back.setMinHeight(42);
        hbback.getChildren().add(back);
        hbback.setPadding(new Insets(10));
        root.getChildren().add(hbback);
        back.setOnAction((ActionEvent t) -> {
            root.getChildren().clear();
            new listButtonOpen(root);
        });
    }

    public boolean checkData() {
        SimpleStringProperty strError = new SimpleStringProperty("");
        strError.addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            if (t1.equals("")) {
                return;
            }
            new AlertGame("Lỗi", t1, Alert.AlertType.WARNING) {
                
                @Override
                public void processResult() {
                }
            };
        });

        for (int i = 0; i < listTextFeild.size(); i++) {
                TextField a = listTextFeild.get(i);
                if (a.getText().isEmpty() || a.getText() == null) {
                    strError.set(a.getPromptText() + " không được để trống");
                    a.requestFocus();
                    return false;
                }
        }
        if (!Pattern.compile("^([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}$").matcher(txtemail.getText().trim()).matches()) {
            strError.set("Email không hợp lệ xin nhập theo định dạng:\n abc@gmail.com");
            txtemail.requestFocus();
            return false;
        }
        
        if(playerSign.getData(playerSign.getEmail()).getUserID() != 0){
            strError.set("Email đã tồn tại xin bạn nhập mới");
            txtemail.requestFocus();
            return false;
        }
        
        if (txtplayer.getText().trim().length() > 50) {
            strError.set("Độ dài tên không được vượt quá 50 ký tự");
            txtplayer.requestFocus();
            return false;
        }
        if (txtemail.getText().trim().length() > 50) {
            strError.set("Độ dài tên không được vượt quá 50 ký tự");
            txtemail.requestFocus();
            return false;
        }
        if (!Pattern.compile("^\\d+").matcher(txtyear.getText().trim()).matches()) {
            strError.set("Năm sinh phải là số");
            txtyear.requestFocus();
            return false;
        }

        if (Integer.parseInt(txtyear.getText().trim()) > (Calendar.getInstance().get(Calendar.YEAR) - 5) || Integer.parseInt(txtyear.getText().trim()) < (Calendar.getInstance().get(Calendar.YEAR) - 151)) {
            strError.set("Năm sinh phải từ " + (Calendar.getInstance().get(Calendar.YEAR) - 150) + " -> " + (Calendar.getInstance().get(Calendar.YEAR) - 5));
            txtyear.requestFocus();
            return false;
        }

        if (txtpass.getText().length() < 8 || txtpass.getText().length() > 12) {
            strError.set("Mật khẩu bạn nhập phải từ 8 đến 12, xin nhập lại");
            txtpass.requestFocus();
            return false;
        }

        if (!txtpass.getText().equals(txtpass1.getText())) {
            strError.set("Mật khẩu bạn nhập không giống nhau, xin nhập lại");
            txtpass1.requestFocus();
            return false;
        }
        return true;
    }
}
