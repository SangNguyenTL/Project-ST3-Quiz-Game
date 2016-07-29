/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import DBModel.Player;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author nhats
 */
public class frLogin{

    protected Pane root;
    protected static DBModel.Player player;
    protected Double width;
    protected Double height;
    private CheckBox saveId;
    private Label lbUserName;
    private Label lbPassword;
    private TextField txtUserName;
    private TextField txtPassowrd;
    
    
    public frLogin(Pane root) {
        this.root = root;
        this.width = root.getWidth();
        this.height = root.getHeight();
        this.init();
    }

    public frLogin(Pane root, Player player) {
        this.root = root;
        this.player = player;
    }

    public static Player getPlayer() {
        return player;
    }
    
    
    public void init() {
        root.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setPrefSize(root.getWidth(), root.getHeight());
        root.getChildren().add(grid);
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(width*0.183, width*0.183, width*0.183, width*0.146));
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));
        
        Text scenetitle = new Text("Đăng nhập");
        scenetitle.setEffect(ds);
        scenetitle.setFill(Color.WHITE);
        grid.add(scenetitle, 0, 0, 2, 1);
        scenetitle.setFont(new Font("Arial",width*0.0292));

        lbUserName = new Label("Tên tài khoản:");
        lbUserName.setEffect(ds);
        lbUserName.setTextFill(Color.WHITE);
        lbUserName.setFont(new Font("Arial",width*0.0146));
        grid.add(lbUserName, 0, 1);

        txtUserName = new TextField();
        txtUserName.setPromptText("abc@email.com");
        txtUserName.getStyleClass().add("txtField");
        txtUserName.setFont(new Font("Arial",width*0.0146));
        grid.add(txtUserName, 1, 1);

        lbPassword = new Label("Mật khẩu:");
        lbPassword.setEffect(ds);
        lbPassword.setTextFill(Color.WHITE);
        lbPassword.setFont(new Font("Arial",width*0.0146));
        grid.add(lbPassword, 0, 2);

        txtPassowrd = new PasswordField();
        txtPassowrd.setPromptText("Mật khẩu");
        txtPassowrd.getStyleClass().add("txtField");
        txtPassowrd.setFont(new Font("Arial",width*0.0146));
        grid.add(txtPassowrd, 1, 2);
        loadIdPass();
        
        Button btnSinIn = new Button("Đăng nhập");
        btnSinIn.getStyleClass().add("btnNor");

        Button btnSignUp = new Button("Đăng ký");
        btnSignUp.getStyleClass().add("btnNor");
       
        

        
        Label saveLabel = new Label("Lưu mật khẩu");
        saveLabel.setEffect(ds);
        saveLabel.setTextFill(Color.WHITE);
        saveLabel.setFont(new Font("Arial",width*0.0146));
        
        saveId = new CheckBox();
        saveId.setSelected(Boolean.valueOf(saveFile.getProperty("checkSave")));
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(saveLabel);
        hbBtn.getChildren().add(saveId);
        hbBtn.getChildren().add(btnSignUp);
        hbBtn.getChildren().add(btnSinIn);
        
        grid.add(hbBtn, 1, 4);
        
        Button back = new Button("Quay lại");
        HBox hbback = new HBox(width*0.0073);
        back.getStyleClass().add("btnCus");
        back.setMinWidth(root.getWidth()*0.11);
        back.setMinHeight(root.getHeight()*0.05);
        
        hbback.getChildren().add(back);
        hbback.setPadding(new Insets(width*0.0073));
        root.getChildren().add(hbback);
        final Text actiontarget = new Text();
            grid.add(actiontarget, 1, 6);
            
        //Ấn nút đăng nhập và thiết đặt kiểm tra điều kiện
        btnSinIn.setDefaultButton(true);
            
        btnSinIn.setOnAction((ActionEvent e) -> {
            String strUserName = txtUserName.getText();
            String strPassword = txtPassowrd.getText();
            SimpleStringProperty strError = new SimpleStringProperty("");
            strError.addListener(new ChangeListener<String>() {
                
                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    if("".equals(t1)) return;
                    else{
                        new lib.AlertGame("Lỗi", t1, Alert.AlertType.WARNING) {
                            
                            @Override
                            public void processResult() {
                            }
                        };
                    }
                };
            });
            if(strUserName == null || strUserName.isEmpty()){
                strError.set("Tên đăng nhập không được để trống");
                txtUserName.requestFocus();
                return;
            }
            if(strPassword == null || strPassword.isEmpty()){
                strError.set("Mật khẩu không được để trống");
                txtPassowrd.requestFocus();
                return;
            }
            player = new Player().getData(strUserName, strPassword);
            if(player.getUserID()!=0){
                saveIdPass(player.getEmail(), player.getPassEnscript());
                new listButtonLogged(root,player);
            }else{
                strError.set("Tên tài khoản hoặc email không đúng!\nXin nhập lại.");
            }
        });
        back.setCancelButton(true);
        back.setOnAction((ActionEvent t) -> {
            root.getChildren().clear();
            new listButtonOpen(root);
        });
        //Registration
        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                new frRegistration(root,player);
            }
        });
    }
    private Properties saveFile;
    private void saveIdPass(String email, String password) {
        FileOutputStream fOs;
        Properties saveFile = new Properties();
        saveFile.put("email", email);
        saveFile.put("password",password);
        saveFile.put("checkSave", Boolean.toString( saveId.isSelected()));
        try{
            fOs = new FileOutputStream("data.properties");
            saveFile.store(fOs, "InforId");
            fOs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private boolean loadIdPass(){
        FileInputStream fIs;
        try{
            fIs = new FileInputStream("data.properties");
            saveFile = new Properties();
            saveFile.load(fIs);
            txtUserName.setText(saveFile.getProperty("email"));
            txtPassowrd.setText(new DBModel.Player().getPasword(saveFile.getProperty("password")));
            fIs.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
