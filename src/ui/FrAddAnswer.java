/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

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
/**
 *
 * @author Mattias
 */
public class FrAddAnswer {

    public FrAddAnswer(HBox root) {
        Pane main = new Pane();
        main.setPrefSize(600, 450);
       
        Label lblCat = new Label("Chủ đề");
        lblCat.setFont(new Font("Cambria", 25));
        lblCat.setTextFill(Color.web("#fff"));
        lblCat.setLayoutX(10);
        lblCat.setLayoutY(20);
        
        ComboBox cbCat = new ComboBox();
        cbCat.getItems().addAll(
            "History",
            "Generality",
            "Sports",
            "Geographic"
        );
        cbCat.getSelectionModel().selectFirst();
        cbCat.setLayoutX(100);
        cbCat.setLayoutY(20);
        
        Label lblLv = new Label("Độ khó");
        lblLv.setFont(new Font("Cambria", 25)); 
        lblLv.setTextFill(Color.web("#fff"));
        lblLv.setLayoutX(350);
        lblLv.setLayoutY(20);
        
        ComboBox cbLv = new ComboBox();
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
        
        TextArea txtQues = new TextArea();
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
        
        TextField txtA = new TextField();
        txtA.getStyleClass().add("txtField");
        txtA.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        txtA.setFont(new Font("Arial",25));
        txtA.setLayoutX(20);
        txtA.setLayoutY(250);
        
        
        TextField txtB = new TextField();
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
        
        TextField txtC = new TextField();
        txtC.getStyleClass().add("txtField");
        txtC.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        txtC.setFont(new Font("Arial",25));
        txtC.setLayoutX(20);
        txtC.setLayoutY(350);
        
        TextField txtD = new TextField();
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
        
        ComboBox cbAns = new ComboBox();
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
        
        CheckBox ckActive = new CheckBox();
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
    }
    
}
