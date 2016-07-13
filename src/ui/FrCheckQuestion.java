/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;



/**
 *
 * @author Mattias
 */
public class FrCheckQuestion {

public FrCheckQuestion(HBox root) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(20);
        grid.setVgap(10);
        
        Label lb = new Label("Đặt câu hỏi!!");
        lb.setFont(new Font("Arial",20));
        lb.setTextFill(Color.web("#fff"));
        grid.add(lb, 0, 0);
        
        TextArea txtQues = new TextArea();
        txtQues.setPrefSize(600, 150);
        txtQues.setFont(new Font("Tahoma",20));
        grid.add(txtQues, 0, 1);
        
        HBox hbbtn = new HBox(10);
        hbbtn.setPrefWidth(100);
        
        Label blank = new Label("                                                                 ");
        Button btnCheck = new Button("Thêm câu trả lời");
        HBox.setHgrow(hbbtn, Priority.ALWAYS); 
        btnCheck.setMaxWidth(Double.MAX_VALUE);
        btnCheck.getStyleClass().add("btnNor");
        btnCheck.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        
        Button btnReset = new Button("Xóa trắng");
        HBox.setHgrow(hbbtn, Priority.ALWAYS); 
        btnReset.setMaxWidth(Double.MAX_VALUE);
        btnReset.getStyleClass().add("btnNor");
        btnReset.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        
        hbbtn.getChildren().addAll(blank,btnCheck,btnReset);    
        grid.add(hbbtn, 0, 2);

        root.getChildren().add(grid);
        
        btnCheck.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().clear();
                new FrAddAnswer(root);
            }     
        });
        
        btnReset.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                txtQues.setText("");
            }
            
        });
    }
    
}
