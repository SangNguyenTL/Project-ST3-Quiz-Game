/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import DBModel.Answer;
import DBModel.Question;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Mattias
 */
public class FrAddAnswer {

    TextArea txtQues;
    Question ques = new Question();
    Answer ans = new Answer();
    TextField txtA;
    TextField txtB;
    TextField txtC;
    TextField txtD;
    ComboBox cbAns;
    ComboBox cbLv;
    ComboBox cbCat;
    CheckBox ckActive;
    Label lbCount;
    Boolean type; //Add false, update true

    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getType(Boolean type) {
        return this.type;
    }
    
    
    public FrAddAnswer(HBox root, Question ques, boolean type) {
        Pane main = new Pane();
        this.ques = ques;
        setType(type);
        main.setPrefSize(600, 450);

        Label lblCat = new Label("Chủ đề");
        lblCat.setFont(new Font("Cambria", 25));
        lblCat.setTextFill(Color.web("#fff"));
        lblCat.setLayoutX(10);
        lblCat.setLayoutY(20);

        cbCat = new ComboBox();
        ObservableList<DBModel.Category> listCategory = FXCollections.observableArrayList(new DBModel.Category().getData());
        cbCat.getItems().add("Chọn...");
        cbCat.getItems().addAll(listCategory);

        cbCat.getSelectionModel().selectFirst();
        if (ques.getCatId() != 0) {
            cbCat.getSelectionModel().select(new DBModel.Category().getData(ques.getCatId()));
        }

        cbCat.setLayoutX(100);
        cbCat.setLayoutY(20);

        Label lblLv = new Label("Độ khó");
        lblLv.setFont(new Font("Cambria", 25));
        lblLv.setTextFill(Color.web("#fff"));
        lblLv.setLayoutX(350);
        lblLv.setLayoutY(20);

        cbLv = new ComboBox();
        cbLv.getItems().addAll(
                "Chọn...",
                "1",
                "2",
                "3",
                "4"
        );

        cbLv.getSelectionModel().selectFirst();

        if (ques.getLevel() != 0) {
            cbLv.getSelectionModel().select(ques.getLevel());
        }

        cbLv.setLayoutX(450);
        cbLv.setLayoutY(20);

        Label lblQues = new Label("Câu hỏi");
        lblQues.setFont(new Font("Cambria", 25));
        lblQues.setTextFill(Color.web("#fff"));
        
        lblQues.setLayoutX(10);
        lblQues.setLayoutY(70);


        txtQues = new TextArea();
        txtQues.setText(ques.quesContent);
        txtQues.setFont(new Font("Tahoma", 20));
        txtQues.setLayoutX(20);
        txtQues.setLayoutY(100);
        txtQues.setPrefSize(650, 100);
        txtQues.setEditable(getType(type));
        
        lbCount = new Label(String.valueOf(txtQues.getText().length()));
        lbCount.setFont(new Font("Arial", 20));
        lbCount.setTextFill(Color.web("#fff"));
        lbCount.setLayoutX(200);
        lbCount.setLayoutY(70);
        
        txtQues.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent t) {
                lbCount.setText(String.valueOf(txtQues.getText().length()));
                if(txtQues.getText().length()<10 || txtQues.getText().length()>270){
                    lbCount.setTextFill(Color.RED);
                }else{
                    lbCount.setTextFill(Color.WHITE);
                }
            }
       
        });
        
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
        txtA.setFont(new Font("Arial", 25));
        txtA.setLayoutX(20);
        txtA.setLayoutY(250);

        txtB = new TextField();
        txtB.getStyleClass().add("txtField");
        txtB.setFont(new Font("Arial", 25));
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
        txtC.setFont(new Font("Arial", 25));
        txtC.setLayoutX(20);
        txtC.setLayoutY(350);

        txtD = new TextField();
        txtD.getStyleClass().add("txtField");
        txtD.setFont(new Font("Arial", 25));
        txtD.setLayoutX(360);
        txtD.setLayoutY(350);

        Label lblRightAns = new Label("Đáp án đúng");
        lblRightAns.setFont(new Font("Cambria", 25));
        lblRightAns.setTextFill(Color.web("#fff"));
        lblRightAns.setLayoutX(10);
        lblRightAns.setLayoutY(420);

        cbAns = new ComboBox();
        cbAns.getItems().addAll(
                "Chọn...",
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

        if (ques.getQuesId() != 0) {
            ArrayList<Answer> listAnswer = new Answer().getData(ques.quesId);
            if (!listAnswer.isEmpty()) {
                txtA.setText(listAnswer.get(0).getAnsContent());
                txtA.setId(String.valueOf(listAnswer.get(0).getAnsID()));
                txtB.setText(listAnswer.get(1).getAnsContent());
                txtB.setId(String.valueOf(listAnswer.get(1).getAnsID()));
                txtC.setText(listAnswer.get(2).getAnsContent());
                txtC.setId(String.valueOf(listAnswer.get(2).getAnsID()));
                txtD.setText(listAnswer.get(3).getAnsContent());
                txtD.setId(String.valueOf(listAnswer.get(3).getAnsID()));
                for (int i = 0; i < listAnswer.size(); i++) {
                    if (listAnswer.get(i).getIsCorrect() == true) {
                        cbAns.getSelectionModel().select(i+1);
                    }
                }
            }
            ckActive.setSelected(ques.isActive);
        }
        
        Button btnSubmit = new Button("Xác nhận");
        btnSubmit.getStyleClass().add("btnNor");
        btnSubmit.setLayoutX(240);
        btnSubmit.setLayoutY(480);
        btnSubmit.setPrefSize(90, 30);

        Button btnBack = new Button("Quay lại");
        btnBack.getStyleClass().add("btnNor");
        btnBack.setLayoutX(360);
        btnBack.setLayoutY(480);
        btnBack.setPrefSize(90, 30);

        main.getChildren().addAll(lblCat, cbCat, lblLv, lbCount,cbLv, lblQues, txtQues, lblAnsA, lblAnsB, txtA, txtB, lblAnsC, lblAnsD, txtC, txtD, lblRightAns, cbAns, lblActive, ckActive, btnSubmit, btnBack);

        root.getChildren().add(main);

        btnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().clear();
                new FrCheckQuestion(root);
            }

        });
        btnSubmit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (checkQuestion()&&checkAddQuestion()) {
                    DBModel.Category cate = (DBModel.Category) cbCat.getSelectionModel().getSelectedItem();
                    ques.catId = cate.getId();

                    ques.level = (cbLv.getSelectionModel().getSelectedIndex());
                    ques.quesContent = txtQues.getText();

                    if (ckActive.isSelected()) {
                        ques.isActive = true;
                    } else {
                        ques.isActive = false;
                    }

                    String qu = txtQues.getText();
                    boolean action;
                    System.out.println(getType(type));
                    if(getType(type)) action = ques.update();
                    else{
                        action = ques.insert();
                    }
                    if (action) {
                        TreeMap<Integer, TextField> tmAddAns = new TreeMap<>();
                        tmAddAns.put(0, txtA);
                        tmAddAns.put(1, txtB);
                        tmAddAns.put(2, txtC);
                        tmAddAns.put(3, txtD);

                        for (Question item : ques.getData(ques.getQuesId())) {
                            if (qu.equals(item.quesContent)) {
                                ques.quesId = item.quesId;
                                ques.catId = item.catId;

                                for (int i = 0; i < tmAddAns.size(); i++) {
                                    ans.quesID = item.quesId;
                                    if (cbAns.getSelectionModel().getSelectedIndex() ==  i+1) {
                                        ans.isCorrect = true;
                                    } else {
                                        ans.isCorrect = false;
                                    }
                                    ans.ansContent = tmAddAns.get(i).getText();
                                    
                                    if(getType(type)){
                                        ans.setAnsID(Integer.parseInt(tmAddAns.get(i).getId()));
                                        ans.update();
                                    }
                                    else {
                                        ans.insert();
                                        tmAddAns.get(i).setId(String.valueOf(ans.getAnsID()));
                                    }
                                }
                            }
                        }
                        new AlertGame("Thành công", (!getType(type) ? "Thêm" : "Cập nhật" )+ " thành công\n\t + OK để quay lại bảng câu hỏi\n\t + Cancel để ở lại", Alert.AlertType.CONFIRMATION) {

                            @Override
                            public void processResult() {
                                setType(true);
                                txtQues.setEditable(true);
                                if (result.get() == ButtonType.OK) {
                                    root.getChildren().clear();
                                    new FrQuestion(root);
                                }else{
                                    alert.close();
                                }
                            }
                        };

                    }

                }
            }

        });
    }
    private boolean check = true;
    public boolean checkQuestion() {
        boolean check = true;
        if (txtQues.getText().isEmpty()) {
            new AlertGame("Lỗi", "Bạn chưa nhập câu hỏi", Alert.AlertType.WARNING) {

                @Override
                public void processResult() {

                }
            };
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
            return check = false;
        }

        Pattern p2 = Pattern.compile("^\\S.{9,269}\\?$");
        Matcher m2 = p2.matcher(txtQues.getText());
        if (m2.matches() == false) {
            new AlertGame("Lỗi", "Câu hỏi phải có dấu hỏi khi kết thúc câu và có từ 10 đến 270 ký tự", Alert.AlertType.WARNING) {

                @Override
                public void processResult() {

                }
            };
            txtQues.requestFocus();
            return check = false;
        }
        return check;
    }
    public boolean checkAddQuestion() {
        check=true;
        String ansA = txtA.getText();
        String ansB = txtB.getText();
        String ansC = txtC.getText();
        String ansD = txtD.getText();
        TreeMap<String, TextField> listTextAnswer = new TreeMap();
        listTextAnswer.put("Đáp án D", txtD);
        listTextAnswer.put("Đáp án C", txtC);
        listTextAnswer.put("Đáp án B", txtB);
        listTextAnswer.put("Đáp án A", txtA);

        Pattern patternCheckAnswerBlankkSpace = Pattern.compile("\\s{2,}");
        Pattern patternCheckAnswer = Pattern.compile("^\\S.{0,49}\\.$");

        SimpleStringProperty strError = new SimpleStringProperty("");
        strError.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!t1.equals("")) {
                    new AlertGame("Lỗi", t1, Alert.AlertType.WARNING) {

                        @Override
                        public void processResult() {

                        }
                    };
                }
            }
        });
        if(cbAns.getSelectionModel().getSelectedIndex()==0){
            strError.set("Bạn chưa chọn đáp án đúng");
            return false;
        }
        if(cbLv.getSelectionModel().getSelectedIndex()==0){
            strError.set("Bạn chưa chọn độ khó");
            return false;
        }
        if(cbCat.getSelectionModel().getSelectedIndex()==0){
            strError.set("Bạn chưa chọn chủ đề");
            return false;
        }
        for (Map.Entry<String, TextField> one : listTextAnswer.entrySet()) {
            String text = one.getValue().getText();
            if (text.isEmpty()) {
                strError.set(one.getKey() + " không được trống");
                one.getValue().requestFocus();
                return false;
            }
            if (!patternCheckAnswer.matcher(text).matches()) {
                strError.set(one.getKey() + " phải kết thúc bằng dấu chấm và không được quá 50 ký tự");
                one.getValue().requestFocus();
                return false;
            }
            if (patternCheckAnswerBlankkSpace.matcher(text).find()) {
                strError.set(one.getKey() + " không thể chứa 2 khoảng trắng liền nhau");
                one.getValue().requestFocus();
                return false;
            }
        }
        if (ansA.equals(ansB) || ansA.equals(ansC) || ansA.equals(ansD) || ansB.equals(ansC) || ansB.equals(ansD) || ansC.equals(ansD)) {
            strError.set("Đáp án không được giống nhau");
            txtA.requestFocus();
            check = false;
        }
        return check;
    }
}
