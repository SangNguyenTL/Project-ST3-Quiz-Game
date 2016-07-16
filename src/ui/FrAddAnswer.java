/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import DBModel.Answer;
import DBModel.Question;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
/**
 *
 * @author Mattias
 */
public class FrAddAnswer {
    TextArea txtQues;
    Question ques = new Question();
    Answer ans =  new Answer();
    TextField txtA;
    TextField txtB;
    TextField txtC;
    TextField txtD;
    ComboBox cbAns;
    ComboBox cbLv;
    ComboBox cbCat;
    CheckBox ckActive;
    public FrAddAnswer(HBox root,String text) {
        Pane main = new Pane();
        main.setPrefSize(600, 450);
       
        Label lblCat = new Label("Chủ đề");
        lblCat.setFont(new Font("Cambria", 25));
        lblCat.setTextFill(Color.web("#fff"));
        lblCat.setLayoutX(10);
        lblCat.setLayoutY(20);
        
        cbCat = new ComboBox();
        ObservableList<DBModel.Category> listCategory = FXCollections.observableArrayList(new DBModel.Category().getData()) ;
        cbCat.getItems().addAll(listCategory);

        cbCat.getSelectionModel().selectFirst();
        cbCat.setLayoutX(100);
        cbCat.setLayoutY(20);
        
        Label lblLv = new Label("Độ khó");
        lblLv.setFont(new Font("Cambria", 25)); 
        lblLv.setTextFill(Color.web("#fff"));
        lblLv.setLayoutX(350);
        lblLv.setLayoutY(20);
        
        cbLv = new ComboBox();
        cbLv.getItems().addAll(
            "1",
            "2",
            "3",
            "4"
        );
        cbLv.getSelectionModel().selectFirst();
        cbLv.setLayoutX(450);
        cbLv.setLayoutY(20);
        
        Label lblQues = new Label("Câu hỏi");
        lblQues.setFont(new Font("Cambria", 25)); 
        lblQues.setTextFill(Color.web("#fff"));
        lblQues.setLayoutX(10);
        lblQues.setLayoutY(70);
        
        txtQues = new TextArea();
        txtQues.setText(text);
        txtQues.setFont(new Font("Tahoma",20));
        txtQues.setLayoutX(20);
        txtQues.setLayoutY(100);
        txtQues.setPrefSize(650, 100);
        txtQues.setEditable(false);
        txtQues.getStyleClass().add("txtDisable");
        txtQues.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        
        Label lblAnsA = new Label("Đáp án A");
        lblAnsA.setFont(new Font("Cambria", 25)); 
        lblAnsA.setTextFill(Color.web("#fff"));
        lblAnsA.setLayoutX(10);
        lblAnsA.setLayoutY(220);
        
        Label lblAnsB = new Label("Đáp án B");
        lblAnsB.setFont(new Font("Cambria", 25)); 
        lblAnsB.setTextFill(Color.web("#fff"));
        lblAnsB.setLayoutX(350);
        lblAnsB.setLayoutY(220);
        
        txtA = new TextField();
        txtA.getStyleClass().add("txtField");
        txtA.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        txtA.setFont(new Font("Arial",25));
        txtA.setLayoutX(20);
        txtA.setLayoutY(250);
        
        
        txtB = new TextField();
        txtB.getStyleClass().add("txtField");
        txtB.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        txtB.setFont(new Font("Arial",25));
        txtB.setLayoutX(360);
        txtB.setLayoutY(250);
        
        Label lblAnsC = new Label("Đáp án C");
        lblAnsC.setFont(new Font("Cambria", 25)); 
        lblAnsC.setTextFill(Color.web("#fff"));
        lblAnsC.setLayoutX(10);
        lblAnsC.setLayoutY(320);
        
        Label lblAnsD = new Label("Đáp án D");
        lblAnsD.setFont(new Font("Cambria", 25)); 
        lblAnsD.setTextFill(Color.web("#fff"));
        lblAnsD.setLayoutX(350);
        lblAnsD.setLayoutY(320);
        
        txtC = new TextField();
        txtC.getStyleClass().add("txtField");
        txtC.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        txtC.setFont(new Font("Arial",25));
        txtC.setLayoutX(20);
        txtC.setLayoutY(350);
        
        txtD = new TextField();
        txtD.getStyleClass().add("txtField");
        txtD.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        txtD.setFont(new Font("Arial",25));
        txtD.setLayoutX(360);
        txtD.setLayoutY(350);
              
        Label lblRightAns = new Label("Đáp án đúng");
        lblRightAns.setFont(new Font("Cambria", 25)); 
        lblRightAns.setTextFill(Color.web("#fff"));
        lblRightAns.setLayoutX(10);
        lblRightAns.setLayoutY(420);
        
        cbAns = new ComboBox();
        cbAns.getItems().addAll(
            "A",
            "B",
            "C",
            "D"
        );
        cbAns.getSelectionModel().selectFirst();
        cbAns.setLayoutX(160);
        cbAns.setLayoutY(420);
        
        Label lblActive = new Label("Hiển thị");
        lblActive.setFont(new Font("Cambria", 25)); 
        lblActive.setTextFill(Color.web("#fff"));
        lblActive.setLayoutX(350);
        lblActive.setLayoutY(420);
        
        ckActive = new CheckBox();
        ckActive.setSelected(true);
        ckActive.setLayoutX(445);
        ckActive.setLayoutY(427);
        
        Button btnSubmit = new Button("Xác nhận");
        btnSubmit.getStyleClass().add("btnNor");
        btnSubmit.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnSubmit.setLayoutX(240);
        btnSubmit.setLayoutY(480);
        btnSubmit.setPrefSize(90, 30);
        
        Button btnBack = new Button("Quay lại");
        btnBack.getStyleClass().add("btnNor");
        btnBack.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnBack.setLayoutX(360);
        btnBack.setLayoutY(480);
        btnBack.setPrefSize(90, 30);
        
        main.getChildren().addAll(lblCat,cbCat,lblLv,cbLv,lblQues,txtQues,lblAnsA,lblAnsB,txtA,txtB,lblAnsC,lblAnsD,txtC,txtD,lblRightAns,cbAns,lblActive,ckActive,btnSubmit,btnBack);
        
        root.getChildren().add(main);
        
        btnBack.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().clear();
                new FrCheckQuestion(root);
            }
        
        });
        btnSubmit.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            if (checkAddQuestion()) {
                System.out.println(cbCat.getSelectionModel().getSelectedIndex());
                DBModel.Category cate = (DBModel.Category) cbCat.getSelectionModel().getSelectedItem();
                System.out.println(cate.getId());
                ques.catId = cate.getId();
      

            ques.level = (cbLv.getSelectionModel().getSelectedIndex());
            ques.quesContent = txtQues.getText();
  
            if(ckActive.isSelected())
            {
                ques.isActive = true;
            }
            else{
                ques.isActive = false;
            }
           
            String qu = txtQues.getText();
            
            if (ques.insert()) {
                TreeMap<Integer, String> tmAddAns = new TreeMap<>();
                tmAddAns.put(0, txtA.getText());
                tmAddAns.put(1, txtB.getText());
                tmAddAns.put(2, txtC.getText());
                tmAddAns.put(3, txtD.getText());
                
                for (Question item : ques.getData(ques.getQuesId())) {
                    if (qu.equals(item.quesContent)) {
                        ques.quesId = item.quesId;
                        ques.catId = item.catId;
                        
                        for (int i = 0; i < tmAddAns.size(); i++) {
                            ans.quesID = item.quesId;
                            if (cbAns.getSelectionModel().getSelectedIndex() == i) {
                                ans.isCorrect = true;
                            } else {
                                ans.isCorrect = false;
                            }
                            ans.ansContent = tmAddAns.get(i);
                            ans.insert();
                        }
                    }
                }
                   JOptionPane.showMessageDialog(null, "Inserted Successful!");
 
            }


        }
            }
            
        });
    }
    
    public boolean checkAddQuestion() {
        boolean check = true;
        String ansA = txtA.getText();
        String ansB = txtB.getText();
        String ansC = txtC.getText();
        String ansD = txtD.getText();
        if (txtA.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Đáp án A trống");
            txtA.requestFocus();
            return check = false;
        }
        if (txtB.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Đáp án B trống");
            txtB.requestFocus();
            return check = false;
        }
        if (txtC.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Đáp án C trống");
            txtC.requestFocus();
            return check = false;
        }
        if (txtD.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Đáp án D trống");
            txtD.requestFocus();
            return check = false;
        }
        if (ansA.equals(ansB) || ansA.equals(ansC) || ansA.equals(ansD) || ansB.equals(ansC) || ansB.equals(ansD) || ansC.equals(ansD)) {
            JOptionPane.showMessageDialog(null, "Đáp án không được giống nhau");
            txtA.requestFocus();
            return check = false;
        }

        Pattern p2 = Pattern.compile("^\\w.*\\.$");
        Matcher m5 = p2.matcher(txtA.getText());
        Matcher m6 = p2.matcher(txtB.getText());
        Matcher m7 = p2.matcher(txtC.getText());
        Matcher m8 = p2.matcher(txtD.getText());
        //JOptionPane.showMessageDialog(this,m1.matches());
        if (m5.matches() == false) {
            JOptionPane.showMessageDialog(null, "Vui lòng kết thúc đáp án A với dấu chấm");
            txtA.setText(null);
           // txtA.setSelectionStart(0);
            txtA.requestFocus();
            return check = false;
        }
        if (m6.matches() == false) {
            JOptionPane.showMessageDialog(null, "Vui lòng kết thúc đáp án B với dấu chấm");
            txtB.setText(null);
          //  txtB.setSelectionStart(0);
            txtB.requestFocus();
            return check = false;
        }
        if (m7.matches() == false) {
            JOptionPane.showMessageDialog(null, "Vui lòng kết thúc đáp án C với dấu chấm");
            txtC.setText(null);
         //   txtC.setSelectionStart(0);
            txtC.requestFocus();
            return check = false;
        }
        if (m8.matches() == false) {
            JOptionPane.showMessageDialog(null, "Vui lòng kết thúc đáp án D với dấu chấm");
            txtD.setText(null);
        //    txtD.setSelectionStart(0);
            txtD.requestFocus();
            return check = false;
        }
        Pattern p3 = Pattern.compile("\\s{2,}");
        Matcher m9 = p3.matcher(txtA.getText());
        Matcher m10 = p3.matcher(txtB.getText());
        Matcher m11 = p3.matcher(txtC.getText());
        Matcher m12 = p3.matcher(txtD.getText());
        //JOptionPane.showMessageDialog(this,m1.matches());

        if (m9.find() == true) {
            JOptionPane.showMessageDialog(null, "Đáp án A không thể chứa 2 khoảng trắng liền nhau");
            txtA.requestFocus();
            return check = false;
        }
        if (m10.find() == true) {
            JOptionPane.showMessageDialog(null, "Đáp án B không thể chứa 2 khoảng trắng liền nhau");
            txtB.requestFocus();
            return check = false;
        }
        if (m11.find() == true) {
            JOptionPane.showMessageDialog(null, "Đáp án C không thể chứa 2 khoảng trắng liền nhau");
            txtC.requestFocus();
            return check = false;
        }
        if (m12.find() == true) {
            JOptionPane.showMessageDialog(null, "Đáp án D không thể chứa 2 khoảng trắng liền nhau");
            txtD.requestFocus();
            return check = false;
        }


        return check;
    }
}
