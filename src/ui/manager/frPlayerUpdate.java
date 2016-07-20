/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import java.util.Calendar;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import lib.AlertGame;

/**
 *
 * @author nhats
 */
public class frPlayerUpdate extends frManager {

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
    private java.util.TreeMap<Integer, javafx.scene.control.TextField> listTxtFeild;
    private java.util.TreeMap<Integer, javafx.scene.control.Label> listLabel;
    protected DBModel.Player player;

    public frPlayerUpdate(javafx.scene.layout.Pane root, DBModel.Player player, DBModel.Player rootPlayer) {
        super(root, rootPlayer);
        init(player, rootPlayer);
    }

    public final void init(DBModel.Player player, DBModel.Player rootPlayer) {
        root.getChildren().clear();
        this.player = player;
        super.setRootPlayer(rootPlayer);
        javafx.scene.layout.GridPane main = new javafx.scene.layout.GridPane();
        main.setHgap(10);
        main.setVgap(10);
        main.setAlignment(Pos.CENTER);
        listTxtFeild = new java.util.TreeMap<>();
        listLabel = new java.util.TreeMap<>();

        lbName = new javafx.scene.control.Label("Tên người chơi");
        lbEmail = new javafx.scene.control.Label("Email");
        lbYear = new javafx.scene.control.Label("Năm sinh");
        lbPass = new javafx.scene.control.Label("Mật khẩu");
        lbRePass = new javafx.scene.control.Label("Nhập lại mật khẩu");
        lbAdmin = new javafx.scene.control.Label("Là Admin: ");
        listLabel.put(0, lbName);
        listLabel.put(1, lbEmail);
        listLabel.put(2, lbYear);
        listLabel.put(3, lbPass);
        listLabel.put(4, lbRePass);
        listLabel.put(5, lbAdmin);

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

        for (int i = 0; i < listLabel.size(); i++) {
            listLabel.get(i).setFont(new javafx.scene.text.Font("Cambria", 25));
            listLabel.get(i).setTextFill(javafx.scene.paint.Color.web("#fff"));
            main.add(listLabel.get(i), 0, i);
        }

        for (int i = 0; i < listTxtFeild.size(); i++) {
            listTxtFeild.get(i).getStyleClass().add("txtField");
            listTxtFeild.get(i).setFont(new javafx.scene.text.Font("Arial", 20));
            main.add(listTxtFeild.get(i), 1, i);
        }
        main.add(txtPass, 1, 3);
        main.add(reTxtPass, 1, 4);

        cbAdmin = new javafx.scene.control.CheckBox();
        main.add(cbAdmin, 1, 5);
        if ( super.rootPlayer.getUserID() != 1) {
            cbAdmin.setDisable(true);
        }
        if (player.getUserID() == 1) {
            cbAdmin.setDisable(true);
        }

        javafx.scene.control.Button btnSubmit = new javafx.scene.control.Button("Xác nhận");
        btnSubmit.getStyleClass().add("btnNor");
        btnSubmit.setPrefSize(90, 30);

        main.add(btnSubmit, 1, 6);

        if (player.getUserID() != 0) {
            txtName.setText(player.getUserName());
            txtEmail.setText(player.getEmail());
            txtYear.setText(String.valueOf(player.getYear()));
            txtPass.setText(player.getPasword());
            reTxtPass.setText(player.getPasword());
            cbAdmin.setSelected(player.isAdmin());
        }

        btnSubmit.setOnAction((ActionEvent t) -> {
            boolean per = true;
            if (super.rootPlayer.getUserID() == player.getUserID()) {
                per = true;
            } else if (super.rootPlayer.getUserID() != 1 && player.isAdmin()) {
                new AlertGame("Lỗi", "Bạn không có quyền thay đổi thông tin của tài khoản này!", Alert.AlertType.WARNING) {

                    @Override
                    public void processResult() {
                    }
                };
                per = false;
            }

            if (per) {
                if (checkData()) {
                    player.setUserName(txtName.getText());
                    player.setPasword(txtPass.getText());
                    player.setYear(Integer.parseInt(txtYear.getText()));
                    player.setIsAdmin(cbAdmin.isSelected());
                    if (player.update()) {
                        new AlertGame("Thành công", "Cập nhật thành công", Alert.AlertType.INFORMATION) {

                            @Override
                            public void processResult() {
                            }
                        };
                    } else {
                        new AlertGame("Thất bại", "Xuất hiện lỗi, xin thử lại sau", Alert.AlertType.WARNING) {

                            @Override
                            public void processResult() {
                            }
                        };
                    }
                }
            }
        });

        root.getChildren().add(main);
    }

    public boolean checkData() {
        SimpleStringProperty strError = new SimpleStringProperty("");
        strError.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1.equals("")) {
                    return;
                }
                new AlertGame("Lỗi", t1, Alert.AlertType.WARNING) {

                    @Override
                    public void processResult() {
                    }
                };
            }
        });

        for (int i = 0; i < listTxtFeild.size(); i++) {
            if (listTxtFeild.get(i).getText().isEmpty() || listTxtFeild.get(i).getText() == null) {
                strError.set(listLabel.get(i).getText() + " không được để trống");
                listTxtFeild.get(i).requestFocus();
                return false;
            }
        }
        if (txtName.getText().trim().length() > 50) {
            strError.set("Độ dài tên không được vượt quá 50 ký tự");
            txtName.requestFocus();
            return false;
        }
        if (!Pattern.compile("^\\d+").matcher(txtYear.getText().trim()).matches()) {
            strError.set("Năm sinh phải là số");
            txtYear.requestFocus();
            return false;
        }

        if (Integer.parseInt(txtYear.getText().trim()) > (Calendar.getInstance().get(Calendar.YEAR) - 5) || Integer.parseInt(txtYear.getText().trim()) < (Calendar.getInstance().get(Calendar.YEAR) - 151)) {
            strError.set("Năm sinh phải từ " + (Calendar.getInstance().get(Calendar.YEAR) - 150) + " -> " + (Calendar.getInstance().get(Calendar.YEAR) - 5));
            txtYear.requestFocus();
            return false;
        }

        if (txtPass.getText().length() < 8 || txtPass.getText().length() > 12) {
            strError.set("Mật khẩu bạn nhập phải từ 8 đến 12, xin nhập lại");
            txtPass.requestFocus();
            return false;
        }

        if (!txtPass.getText().equals(reTxtPass.getText())) {
            strError.set("Mật khẩu bạn nhập không giốn nhau, xin nhập lại");
            txtPass.requestFocus();
            return false;
        }
        return true;
    }
}
