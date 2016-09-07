/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import DBModel.MyConnect;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
import javafx.stage.Screen;
import lib.AlertGame;

/**
 *
 * @author nhats
 */
class frSetting{
    private Pane root;
    private Double width;
    private Double height;
    private Label lbServer;
    private Label lbPort;
    private Label lbDatabse;
    private Label lbUser;
    private Label lbPassword;
    private Label lbResolution;
    private Label lbIsMute;
    private TextField txtServer;
    private TextField txtPort;
    private TextField txtDatabase;
    private TextField txtUser;
    private TextField txtPass;
    private ComboBox<String> cbbResolution;
    private CheckBox cbMute;
    private String fileName;
    private ArrayList<TextField> listTextFeild;
    private ArrayList<Label> listLabel;
    private Button btnSubmit;
    private Properties saveFile;
    public frSetting(Pane root) {
        
        this.root = root;
        this.fileName = "data.properties";
        this.width = root.getWidth();
        this.height = root.getHeight();
        root.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setPrefSize(root.getWidth(), root.getHeight());
        root.getChildren().add(grid);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(width*0.0073);
        grid.setVgap(width*0.0073);
        grid.setPadding(new Insets(width*0.018));
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));
        
        lbServer = new Label("Địa chỉ server");
        lbPort = new Label("Port");
        lbDatabse = new Label("Tên cơ sở dữ liệu");
        lbUser = new Label("Tên tài khoản");
        lbPassword = new Label("Mật khẩu");
        lbResolution = new Label("Độ phân giải");
        lbIsMute = new Label("Tắt Âm thanh");
        
        listLabel = new ArrayList<Label>();
        listLabel.add(lbServer);
        listLabel.add(lbPort);
        listLabel.add(lbDatabse);
        listLabel.add(lbUser);
        listLabel.add(lbPassword);
        listLabel.add(lbResolution);
        listLabel.add(lbIsMute);
        for(int i = 0; i < listLabel.size(); i++){
            listLabel.get(i).setEffect(ds);
            listLabel.get(i).setTextFill(Color.WHITE);
            listLabel.get(i).setFont(new Font("Arial",20));
            grid.add(listLabel.get(i),0, i);
        }
        
        txtServer = new TextField();
        txtPort = new TextField();
        txtDatabase = new TextField();
        txtUser = new TextField();
        txtPass = new PasswordField();
        listTextFeild = new ArrayList<TextField>();
        listTextFeild.add(txtServer);
        listTextFeild.add(txtPort);
        listTextFeild.add(txtDatabase);
        listTextFeild.add(txtUser);
        listTextFeild.add(txtPass);
        for(int i = 0; i < listTextFeild.size(); i++){
            listTextFeild.get(i).getStyleClass().add("txtField");
            listTextFeild.get(i).setPromptText(listLabel.get(i).getText());
            listTextFeild.get(i).setFont(new Font("Arial", 20));
            grid.add(listTextFeild.get(i),1, i);
        }
        
        cbbResolution = new ComboBox<String>();
        cbbResolution.setMaxWidth(Double.MAX_VALUE);
        cbbResolution.getItems().addAll(
                "1366",
                "1280",
                "1056"
        );
        grid.add(cbbResolution, 1, 5);
        
        cbMute = new CheckBox();
        grid.add(cbMute, 1, 6);
        
        btnSubmit = new Button("Áp dụng");
        btnSubmit.getStyleClass().add("btnNor");
        HBox boxBtnSubmit = new HBox(btnSubmit);
        boxBtnSubmit.setMaxHeight(Double.MAX_VALUE);
        boxBtnSubmit.setAlignment(Pos.CENTER);
        grid.add(boxBtnSubmit, 0, 7, 2, 1);
        
        loadData();
        
        
        //Quay lai
        Button back = new Button("Quay lại");
        HBox hbback = new HBox(10);
        back.getStyleClass().add("btnCus");
        back.setMinWidth(width*0.11);
        back.setMinHeight(height*0.05);
        hbback.getChildren().add(back);
        hbback.setPadding(new Insets(10));
        root.getChildren().add(hbback);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        back.setCancelButton(true);
        back.setOnAction((ActionEvent t) -> {
            if(DBModel.MyConnect.checkData())
                new listButtonOpen(root);
            else
                new AlertGame("Lỗi", "Kết nối với cơ sở dữ liệu thất bại, xin đặt lại cấu hình.", Alert.AlertType.ERROR) {
                    
                    @Override
                    public void processResult() {
                        
                    }
                };
        });
        
        btnSubmit.setOnAction((ActionEvent t) -> {
            if(saveData())
            if(DBModel.MyConnect.checkData()){
                new AlertGame("Thành công", "Kết nối với cơ sở dữ liệu đã được thiết lập.", Alert.AlertType.ERROR) {
                    
                    @Override
                    public void processResult() {
                        if(getResult().get() == ButtonType.OK){
                            try {
                                Window.getPrimaryStage().setMaxWidth(Double.valueOf(cbbResolution.getSelectionModel().getSelectedItem()));
                                Window.getPrimaryStage().setMinWidth(Double.valueOf(cbbResolution.getSelectionModel().getSelectedItem()));
                                Window.getPrimaryStage().setMinHeight((Double.valueOf(cbbResolution.getSelectionModel().getSelectedItem())/ 16) * 9);
                                Window.getPrimaryStage().setMaxHeight((Double.valueOf(cbbResolution.getSelectionModel().getSelectedItem())/ 16) * 9);
                                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                                Window.getPrimaryStage().setX((primScreenBounds.getWidth() - Window.getPrimaryStage().getWidth()) / 2);
                                Window.getPrimaryStage().setY((primScreenBounds.getHeight() - Window.getPrimaryStage().getHeight()) / 2);
                                new frOpenGame(Window.getPrimaryStage());
                            } catch (Exception ex) {
                                Logger.getLogger(frSetting.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                };
            }else
                new AlertGame("Lỗi", "Kết nối với cơ sở dữ liệu thất bại, xin đặt lại cấu hình.", Alert.AlertType.ERROR) {
                    
                    @Override
                    public void processResult() {
                    }
                };
        });
        
    }
    
    private void loadData(){
        MyConnect m = new MyConnect();
        loadSaveFile();
        cbbResolution.getSelectionModel().select(saveFile.getProperty("width","1056"));
        cbMute.setSelected(Boolean.valueOf(saveFile.getProperty("isMute", "false")));
        if(MyConnect.loadData()){
            txtServer.setText(MyConnect.getServer());
            txtPort.setText(MyConnect.getPort());
            txtDatabase.setText(MyConnect.getDatabase());
            txtUser.setText(MyConnect.getUser());
            txtPass.setText(MyConnect.getPass());
        }
    }
    private boolean checkFeild(){
        SimpleStringProperty strError = new SimpleStringProperty();
        strError.addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            new AlertGame("Lỗi nhập trường", t1, Alert.AlertType.WARNING) {
                
                @Override
                public void processResult() {
                    
                }
            };
        });
        for(TextField one : listTextFeild){
            if(one.getText().isEmpty() || one.getText() == null){
                strError.set(one.getPromptText()+" không được để trống");
                one.requestFocus();
                return false;
            }
            if(one.getText().trim().length() > 100){
                strError.set(one.getPromptText()+" không được quá 100 ký tự");
                one.requestFocus();
                return false;
            }
        }
        return true;
    }

    private boolean loadSaveFile(){
        FileInputStream fIs;
        saveFile = new Properties();
        try{
            fIs = new FileInputStream(fileName);
            saveFile.load(fIs);
            fIs.close();
        }catch(FileNotFoundException e){
            return false;
        }catch(IOException e){
            return false;
        }
        return true;
    }
    private boolean saveResolution(String resolution){
        FileOutputStream fOs;
        try{
            fOs = new FileOutputStream(fileName);
            saveFile.put("width", resolution);
            saveFile.put("isMute", Boolean.toString(cbMute.isSelected()));
            saveFile.store(fOs, "====");
            fOs.close();
        }catch(FileNotFoundException e){
            return false;
        }catch(IOException e){
            return false;
        }
        return true;
    }
    private boolean saveData(){
        if(!checkFeild()) return false;
        MyConnect m = new MyConnect();
        MyConnect.setServer(txtServer.getText().trim());
        MyConnect.setPort(txtPort.getText().trim());
        MyConnect.setDatabase(txtDatabase.getText().trim());
        MyConnect.setUser(txtUser.getText().trim());
        MyConnect.setPass((txtPass.getText().trim()));
        MyConnect.saveData();
        loadSaveFile();
        if(!saveResolution(cbbResolution.getSelectionModel().getSelectedItem().toString())){
            new AlertGame("Lỗi", "Không thể lưu độ phân giải", Alert.AlertType.NONE) {
                
                @Override
                public void processResult() {
                    
                }
            };
            return false;
        }
        return true;
    }
    
}
