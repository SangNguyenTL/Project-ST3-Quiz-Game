/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

/**
 *
 * @author nhats
 */
public class frNewUpdateUser {
    
    private javafx.scene.layout.HBox root;
    private DBModel.Player player;
    private javafx.scene.control.TextField txtName;
    private javafx.scene.control.TextField txtEmail;
    private javafx.scene.control.PasswordField txtPass;
    private javafx.scene.control.PasswordField reTxtPass;
    private javafx.scene.control.TextField txtYear;
    private javafx.scene.control.CheckBox cbAdmin;
    private javafx.scene.control.Label lbAdmin;
    private javafx.scene.control.Label lbName;
    private javafx.scene.control.Label lbEmail;
    private javafx.scene.control.Label lbYear;
    private javafx.scene.control.Label lbPass;
    private javafx.scene.control.Label lbRePass;
    
    public frNewUpdateUser(javafx.scene.layout.HBox root,DBModel.Player player){
        this.player = player;
        this.root = root;
        init();
    }
    public void init(){
        javafx.scene.layout.GridPane main = new javafx.scene.layout.GridPane();
        java.util.TreeMap<Integer,javafx.scene.control.TextField> listTxtFeild = new java.util.TreeMap<>();
        java.util.TreeMap<Integer,javafx.scene.control.Label> listLabel = new java.util.TreeMap<>();
        
        lbName = new javafx.scene.control.Label("Tên người chơi");
        lbEmail = new javafx.scene.control.Label("Email");
        lbYear = new javafx.scene.control.Label("Năm sinh");
        lbPass = new javafx.scene.control.Label("Mật khẩu");
        lbRePass = new javafx.scene.control.Label("Nhập lại mật khẩu");
        lbAdmin = new javafx.scene.control.Label("Là Admin: ");
        listLabel.put(0,lbName);
        listLabel.put(1,lbEmail);
        listLabel.put(2,lbYear);
        listLabel.put(3,lbPass);
        listLabel.put(4,lbRePass);
        listLabel.put(5,lbAdmin);
        
        
        txtName = new javafx.scene.control.TextField();
        txtName.setPromptText("Tên người chơi");
        txtEmail = new javafx.scene.control.TextField();
        txtEmail.setEditable(false);
        txtEmail.setPromptText("Email");
        txtYear = new javafx.scene.control.TextField();
        txtYear.setPromptText("Năm sinh");
        txtPass = new javafx.scene.control.PasswordField();
        txtPass.getStyleClass().add("txtField");
        txtPass.setPromptText("Mật khẩu");
        txtPass.setFont(new javafx.scene.text.Font("Arial", 20));
        reTxtPass = new javafx.scene.control.PasswordField();
        reTxtPass.getStyleClass().add("txtField");
        reTxtPass.setPromptText("Nhập mật khẩu");
        reTxtPass.setFont(new javafx.scene.text.Font("Arial", 20));
        
        listTxtFeild.put(0, txtName);
        listTxtFeild.put(1, txtEmail);
        listTxtFeild.put(2, txtYear);
        main.setVgap(10);
        main.setHgap(10);
        
        for(int i = 0; i < listLabel.size(); i++){
            listLabel.get(i).setFont(new javafx.scene.text.Font("Cambria", 25));
            listLabel.get(i).setTextFill(javafx.scene.paint.Color.web("#fff"));
            main.add(listLabel.get(i), 0, i);
        }
        
        for(int i = 0; i < listTxtFeild.size(); i++){
            listTxtFeild.get(i).getStyleClass().add("txtField");
            listTxtFeild.get(i).setFont(new javafx.scene.text.Font("Arial", 20));
            main.add(listTxtFeild.get(i), 1, i);
        }
        main.add(txtPass, 1, 3);
        main.add(reTxtPass, 1, 4);
        
        

        cbAdmin = new javafx.scene.control.CheckBox();
        main.add(cbAdmin,1,5);
        
        javafx.scene.control.Button btnSubmit = new javafx.scene.control.Button("Xác nhận");
        btnSubmit.getStyleClass().add("btnNor");
        btnSubmit.setPrefSize(90, 30);

        javafx.scene.control.Button btnBack = new javafx.scene.control.Button("Quay lại");
        btnBack.getStyleClass().add("btnNor");
        btnBack.setPrefSize(90, 30);
        
        main.add(btnSubmit, 1, 6);
        main.add(btnBack, 0, 6);
        
        if(player.userID != 0 ){
            txtName.setText(player.userName);
            txtEmail.setText(player.email);
            txtYear.setText(String.valueOf(player.getYear()));
            txtPass.setText(player.getPasword());
            reTxtPass.setText(player.getPasword());
            cbAdmin.setSelected(player.isAdmin);
        }
        
        root.getChildren().add(main);
    }
}
