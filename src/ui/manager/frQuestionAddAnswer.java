/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import lib.AlertGame;
import DBModel.Answer;
import DBModel.Question;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Mattias
 */
public final class frQuestionAddAnswer extends ui.manager.frQuestionAdd{

    Answer ans;
    TextField txtA;
    TextField txtB;
    TextField txtC;
    TextField txtD;
    ComboBox<String> cbAns;
    ComboBox<String> cbLv;
    ComboBox<DBModel.Category> cbCat;
    CheckBox ckActive;


    public void setType(Boolean type) {
        super.type = type;
    }

    public Boolean getType(Boolean type) {
        return super.type;
    }

    public frQuestionAddAnswer(Pane root, DBModel.Player player, Question ques, boolean type) {
        super(root, player);
        init(root, ques, type);
    }
   
    
    public void init(Pane rootChild, Question ques, boolean type) {
        super.ques = ques;
        setType(type);
        root.getChildren().clear();
        GridPane main = new GridPane();
        main.setHgap(10);
        main.setVgap(10);
        ans = new Answer();
        
        Label lblRightAns = new Label("Đáp án đúng");
        lblRightAns.setFont(new Font("Cambria", 25));
        lblRightAns.setTextFill(Color.web("#fff"));
        main.add(lblRightAns, 0, 0);

        cbAns = new ComboBox<String>();
        cbAns.getItems().addAll("Chọn...","A","B","C","D");

        cbAns.getSelectionModel().selectFirst();
        main.add(cbAns, 1,0);
        
        Label lblCat = new Label("Chủ đề");
        lblCat.setFont(new Font("Cambria", 25));
        lblCat.setTextFill(Color.web("#fff"));
        main.add(lblCat, 2, 0);

        cbCat = new ComboBox<DBModel.Category>();
        ObservableList<DBModel.Category> listCategory = FXCollections.observableArrayList(new DBModel.Category().getData());
        cbCat.getItems().add(new DBModel.Category(0,"Chọn..."));
        cbCat.getItems().addAll(listCategory);

        cbCat.getSelectionModel().selectFirst();
        if (ques.getCatId() != 0) {
            cbCat.getSelectionModel().select(new DBModel.Category().getData(ques.getCatId()));
        }

        main.add(cbCat, 3, 0);

        Label lblLv = new Label("Độ khó");
        lblLv.setFont(new Font("Cambria", 25));
        lblLv.setTextFill(Color.web("#fff"));
        main.add(lblLv, 4, 0);

        cbLv = new ComboBox<String>();
        
        cbLv.getItems().addAll("Chọn...","1","2","3","4");

        cbLv.getSelectionModel().selectFirst();

        if (ques.getLevel() != 0) {
            cbLv.getSelectionModel().select(ques.getLevel());
        }

        main.add(cbLv, 5, 0);

        Label lblQues = new Label("Câu hỏi");
        lblQues.setFont(new Font("Cambria", 25));
        lblQues.setTextFill(Color.web("#fff"));
        
        main.add(lblQues, 0, 1);


        txtQues = new TextArea();
        txtQues.setText(ques.quesContent);
        txtQues.setFont(new Font("Tahoma", 20));
        txtQues.setLayoutX(20);
        txtQues.setLayoutY(100);
        txtQues.setPrefSize(650, 100);
        main.add(txtQues, 0, 2, 6, 1);
        
        lbCount = new Label(String.valueOf(txtQues.getText().length()));
        lbCount.setFont(new Font("Arial", 20));
        lbCount.setTextFill(Color.web("#fff"));
        main.add(lbCount, 1, 1);
        
        txtQues.setOnKeyPressed((KeyEvent t) -> {
            lbCount.setText(String.valueOf(txtQues.getText().length()));
            if(txtQues.getText().length()<10 || txtQues.getText().length()>270){
                lbCount.setTextFill(Color.RED);
            }else{
                lbCount.setTextFill(Color.WHITE);
            }
        });
        
        Label lblAnsA = new Label("Đáp án A");
        lblAnsA.setFont(new Font("Cambria", 25));
        lblAnsA.setTextFill(Color.web("#fff"));
        main.add(lblAnsA, 0, 3, 3, 1);

        Label lblAnsB = new Label("Đáp án B");
        lblAnsB.setFont(new Font("Cambria", 25));
        lblAnsB.setTextFill(Color.web("#fff"));
        main.add(lblAnsB, 3, 3, 3, 1);
                
        txtA = new TextField();
        txtA.getStyleClass().add("txtField");
        txtA.setFont(new Font("Arial", 25));
        main.add(txtA, 0, 4, 3, 1);

        txtB = new TextField();
        txtB.getStyleClass().add("txtField");
        txtB.setFont(new Font("Arial", 25));
        main.add(txtB, 3, 4, 3, 1);

        Label lblAnsC = new Label("Đáp án C");
        lblAnsC.setFont(new Font("Cambria", 25));
        lblAnsC.setTextFill(Color.web("#fff"));
        main.add(lblAnsC, 0, 5, 3, 1);

        Label lblAnsD = new Label("Đáp án D");
        lblAnsD.setFont(new Font("Cambria", 25));
        lblAnsD.setTextFill(Color.web("#fff"));
        main.add(lblAnsD, 3, 5, 3, 1);
        
        txtC = new TextField();
        txtC.getStyleClass().add("txtField");
        txtC.setFont(new Font("Arial", 25));
        main.add(txtC, 0, 6, 3, 1);

        txtD = new TextField();
        txtD.getStyleClass().add("txtField");
        txtD.setFont(new Font("Arial", 25));
         main.add(txtD, 3, 6, 3, 1);



        Label lblActive = new Label("Hiển thị");
        lblActive.setFont(new Font("Cambria", 25));
        lblActive.setTextFill(Color.web("#fff"));
        main.add(lblActive, 2, 1);

        ckActive = new CheckBox();
        ckActive.setSelected(true);
        main.add(ckActive, 3, 1);

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
        btnSubmit.setPrefSize(90, 30);

        Button btnBack = new Button("Quay lại");
        btnBack.getStyleClass().add("btnNor");
        btnBack.setPrefSize(90, 30);
        
        HBox boxButton = new HBox(10);
        boxButton.setAlignment(Pos.CENTER);
        boxButton.getChildren().addAll(btnSubmit,btnBack);
        
        main.add(boxButton, 0, 7, 6, 1);
        
        root.getChildren().add(main);

        btnBack.setOnMouseClicked((MouseEvent event) -> {
            new frQuestion( root,player, FXCollections.observableArrayList(new DBModel.Question().getData()));
        });
        btnSubmit.setOnMouseClicked((MouseEvent event) -> {
            if (checkQuestion()&&checkAddQuestion()) {
                DBModel.Category cate = (DBModel.Category) cbCat.getSelectionModel().getSelectedItem();
                ques.catId = cate.getId();
                
                ques.level = (cbLv.getSelectionModel().getSelectedIndex());
                ques.quesContent = txtQues.getText();
                ques.isActive = ckActive.isSelected();
                
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
                                ans.isCorrect = cbAns.getSelectionModel().getSelectedIndex() ==  i+1;
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
                            if (getResult().get() == ButtonType.OK) {
                                new frQuestion( root ,player, FXCollections.observableArrayList(new DBModel.Question().getData()));
                            }else{
                                getAlert().close();
                            }
                        }
                    };
                    
                }
                
            }
        });
    }
    private boolean check = true;
    
    public boolean checkAddQuestion() {
        check=true;
        String ansA = txtA.getText();
        String ansB = txtB.getText();
        String ansC = txtC.getText();
        String ansD = txtD.getText();
        TreeMap<String, TextField> listTextAnswer = new TreeMap<String,TextField>();
        listTextAnswer.put("Đáp án D", txtD);
        listTextAnswer.put("Đáp án C", txtC);
        listTextAnswer.put("Đáp án B", txtB);
        listTextAnswer.put("Đáp án A", txtA);

        Pattern patternCheckAnswerBlankkSpace = Pattern.compile("\\s{2,}");
        Pattern patternCheckAnswer = Pattern.compile("^\\S.{0,49}\\.$");

        SimpleStringProperty strError = new SimpleStringProperty("");
        strError.addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            if (!t1.equals("")) {
                new AlertGame("Lỗi", t1, Alert.AlertType.WARNING) {
                    
                    @Override
                    public void processResult() {
                        
                    }
                };
            }
        });
        if(cbAns.getSelectionModel().getSelectedItem().equals("Chọn...")){
            strError.set("Bạn chưa chọn đáp án đúng");
            return false;
        }
        if(cbLv.getSelectionModel().getSelectedItem().equals("Chọn...")){
            strError.set("Bạn chưa chọn độ khó");
            return false;
        }
        if(cbCat.getSelectionModel().getSelectedItem().equals("Chọn...")){
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
