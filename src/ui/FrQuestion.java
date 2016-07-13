/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 *
 * @author Mattias
 */
public class FrQuestion{

    private TableView table = new TableView();
    public FrQuestion(HBox root) {
        Pane main = new Pane();
 
        Label lblCategory = new Label("Chủ đề: ");
        lblCategory.setFont(new Font("Arial",20));
        lblCategory.setTextFill(Color.web("#fff"));
        lblCategory.setLayoutX(10);
        lblCategory.setLayoutY(10);
        
        ComboBox cbbCategory = new ComboBox();
        cbbCategory.getItems().addAll(
            "History",
            "Generality",
            "Sports",
            "Geographic"
        );
        cbbCategory.getSelectionModel().selectFirst();
        cbbCategory.setLayoutX(100);
        cbbCategory.setLayoutY(10);
        
        Label lblLevel = new Label();
        lblLevel.setText("Độ khó: ");
        lblLevel.setFont(new Font("Arial",20));
        lblLevel.setTextFill(Color.web("#fff"));
        lblLevel.setLayoutX(280);
        lblLevel.setLayoutY(10);
        
        ComboBox cbbLevel = new ComboBox();
        cbbLevel.getItems().addAll(
            "1",
            "2",
            "3",
            "4"
        );
        cbbLevel.getSelectionModel().selectFirst();
        cbbLevel.setLayoutX(360);
        cbbLevel.setLayoutY(10);
        
        Button btnSearch = new Button("Tìm kiếm");
        btnSearch.setMaxWidth(Double.MAX_VALUE);
        btnSearch.getStyleClass().add("btnNor");
        btnSearch.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnSearch.setLayoutX(500);
        btnSearch.setLayoutY(10);
        btnSearch.setPrefSize(90, 30);
        
        Label lblQuestion = new Label();
        lblQuestion.setText("Nhập Câu hỏi: ");
        lblQuestion.setFont(new Font("Arial",20));
        lblQuestion.setTextFill(Color.web("#fff"));
        lblQuestion.setLayoutX(10);
        lblQuestion.setLayoutY(58);
        
        
        
        Button btnViewAll = new Button("Xem tất cả");        
        btnViewAll.setMaxWidth(Double.MAX_VALUE);
        btnViewAll.getStyleClass().add("btnNor");
        btnViewAll.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnViewAll.setLayoutX(500);
        btnViewAll.setLayoutY(55);
        btnViewAll.setPrefSize(90, 30);
        
        TextArea txatQuestion = new TextArea();        
        txatQuestion.setPrefSize(600, 100);
        txatQuestion.setLayoutX(20);
        txatQuestion.setLayoutY(100);
        
        
        //table
        table.setPrefSize(600, 320);
        TableColumn QuesID = new TableColumn("Nội dung câu hỏi");
        TableColumn QuesContent = new TableColumn("Độ khó");
        TableColumn CateID = new TableColumn("Chủ đề");    
        QuesID.prefWidthProperty().bind(table.widthProperty().divide(1.55)); // w * 1/5
        QuesContent.prefWidthProperty().bind(table.widthProperty().divide(6)); // w * 3/5
        CateID.prefWidthProperty().bind(table.widthProperty().divide(5)); // w * 1/5
        table.getColumns().addAll(QuesID, CateID, QuesContent);
        table.setLayoutX(20);
        table.setLayoutY(230);


        Button btnAdd = new Button("Thêm");
        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnAdd.getStyleClass().add("btnNor");
        btnAdd.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnAdd.setLayoutX(50);
        btnAdd.setLayoutY(570);
        btnAdd.setPrefSize(90, 30);
            
        Button btnUpdate = new Button("Cập nhật");
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.getStyleClass().add("btnNor");
        btnUpdate.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnUpdate.setLayoutX(200);
        btnUpdate.setLayoutY(570);
        btnUpdate.setPrefSize(90, 30);
        
        Button btnDelete = new Button("Xóa");
        btnDelete.setMaxWidth(Double.MAX_VALUE);
        btnDelete.getStyleClass().add("btnNor");
        btnDelete.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnDelete.setLayoutX(360);
        btnDelete.setLayoutY(570);
        btnDelete.setPrefSize(90, 30);
        
        Button btnExit = new Button("Thoát");
        btnExit.setMaxWidth(Double.MAX_VALUE);
        btnExit.getStyleClass().add("btnNor");
        btnExit.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnExit.setLayoutX(500);
        btnExit.setLayoutY(570);
        btnExit.setPrefSize(90, 30);

        main.getChildren().addAll(lblCategory,cbbCategory,lblLevel,cbbLevel,lblQuestion,btnSearch,btnViewAll,txatQuestion,table,btnAdd,btnUpdate,btnDelete,btnExit);
        root.getChildren().add(main);
        
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().clear();
                new FrCheckQuestion(root);
            }
            
        });
        btnExit.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
            
        });
  }


    
}
