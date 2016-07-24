/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import lib.AlertGame;
import DBModel.Question;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Mattias
 */
public class frQuestionAdd extends ui.manager.frManager{

    protected TextArea txtQues;
    protected Question ques;
    protected Label lbCount;
    protected Boolean type; //Add false, update true
    public frQuestionAdd(Pane root, DBModel.Player player) {
        super(root, player);
        init();
    }
    
    @Override
    public void init() {
        type = false;
        root.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setVgap(10);
        grid.setHgap(20);

        Label lb = new Label("Đặt câu hỏi!!");
        lb.setFont(new Font("Arial", 20));
        lb.setTextFill(Color.web("#fff"));
        grid.add(lb, 0, 0);

        lbCount = new Label("0");
        lbCount.setFont(new Font("Arial", 20));
        lbCount.setTextFill(Color.web("#fff"));
        grid.add(lbCount, 1, 0);
        
        txtQues = new TextArea();
        txtQues.setWrapText(true);
        txtQues.setPrefSize(600, 150);
        txtQues.setFont(new Font("Tahoma", 20));
        grid.add(txtQues, 0, 1, 2, 1);

        txtQues.setOnKeyPressed((KeyEvent t) -> {
            lbCount.setText(String.valueOf(txtQues.getText().length()));
            if(txtQues.getText().length()<10 || txtQues.getText().length()>270){
                lbCount.setTextFill(Color.RED);
            }else{
                lbCount.setTextFill(Color.WHITE);
            }
        });
        
        
        HBox hbbtn = new HBox(10);
        
        Button btnCheck = new Button("Thêm câu trả lời");
        HBox.setHgrow(hbbtn, Priority.ALWAYS);
        btnCheck.setMaxWidth(100);
        btnCheck.getStyleClass().add("btnNor");
        btnCheck.setAlignment(Pos.BOTTOM_LEFT);

        Button btnReset = new Button("Xóa trắng");
        HBox.setHgrow(hbbtn, Priority.ALWAYS);
        btnReset.setMaxWidth(100);
        btnReset.getStyleClass().add("btnNor");
        btnReset.setAlignment(Pos.BOTTOM_RIGHT);

        hbbtn.getChildren().addAll(btnCheck, btnReset);
        grid.add(hbbtn, 0, 2 , 2, 1);

        root.getChildren().add(grid);
        ques = new Question();
        btnCheck.setOnMouseClicked((MouseEvent event) -> {
            if (checkQuestion()) {
                new frQuestionAddAnswer(root,player, ques, false);
            }
        });

        btnReset.setOnMouseClicked((MouseEvent event) -> {
            txtQues.setText("");
        });
    }

    protected boolean checkQuestion() {
        if (txtQues.getText().isEmpty()) {
            new AlertGame("Lỗi", "Bạn chưa nhập câu hỏi", Alert.AlertType.WARNING) {

                @Override
                public void processResult() {

                }
            };
            txtQues.requestFocus();
            return false;
        }
        Pattern p1 = Pattern.compile("\\s{2,}");
        ques.setQuesContent(txtQues.getText());
        Matcher m1 = p1.matcher(txtQues.getText());
        if (m1.find() == true) {
            new AlertGame("Lỗi", "Trong câu hỏi của bạn không được có hơn hai khoảng trắng sát nhau", Alert.AlertType.WARNING) {

                @Override
                public void processResult() {

                }
            };
            //    txtQues.setText(null);
            txtQues.requestFocus();
            return false;
        }

        Pattern p2 = Pattern.compile("^\\S.{9,269}\\?$");
        Matcher m2 = p2.matcher(txtQues.getText().toString());
        System.out.println(txtQues.getText());
        if (m2.matches() == false) {
            new AlertGame("Lỗi", "Câu hỏi phải có dấu hỏi khi kết thúc câu và có từ 10 đến 270 ký tự", Alert.AlertType.WARNING) {

                @Override
                public void processResult() {

                }
            };
            txtQues.requestFocus();
            return false;
        }
        ArrayList a = ques.getData(ques.getQuesContent());
        if (!a.isEmpty()&&type==false){
            
            new AlertGame("Lỗi", "Câu hỏi đã tồn tại", Alert.AlertType.WARNING) {

                @Override
                public void processResult() {
                }
            };
            txtQues.requestFocus();
            return false;
        }
        return true;
    }
}
