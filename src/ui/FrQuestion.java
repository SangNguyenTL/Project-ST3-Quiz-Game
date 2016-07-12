/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Mattias
 */
public class FrQuestion{

    private TableView table = new TableView();
    public FrQuestion(HBox root) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        Label lblCategory = new Label();
        
        lblCategory.setText("Chủ đề: ");
        lblCategory.setFont(new Font("Arial",20));
        lblCategory.setTextFill(Color.web("#fff"));
        gridPane.add(lblCategory,0,1);
        
        Label lblQuestion = new Label();
        lblQuestion.setText("Câu hỏi: ");
        lblQuestion.setFont(new Font("Arial",20));
        lblQuestion.setTextFill(Color.web("#fff"));
        gridPane.add(lblQuestion,0,2);
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));
        
        ComboBox cbbCategory = new ComboBox();
        cbbCategory.getItems().addAll(
            "History",
            "Generality",
            "Sports",
            "Geographic"
        );
        cbbCategory.getSelectionModel().selectFirst();
        gridPane.add(cbbCategory, 1, 1);
        TextArea txtQuestion = new TextArea();        
        txtQuestion.setPrefSize(500, 150);
        gridPane.add(txtQuestion, 1, 2);
        
        //button search
        VBox vbBox = new VBox(10);
        Label lb = new Label("");
        Label lb1 = new Label("");
        
        Button btnViewAll = new Button("Xem tất cả");        
        HBox.setHgrow(btnViewAll, Priority.ALWAYS); 
        btnViewAll.setMaxWidth(Double.MAX_VALUE);
        btnViewAll.getStyleClass().add("btnNor");
        btnViewAll.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        
        Button btnSearchAll = new Button("Tìm kiếm");
        HBox.setHgrow(btnSearchAll, Priority.ALWAYS);
        btnSearchAll.setMaxWidth(Double.MAX_VALUE);
        btnSearchAll.getStyleClass().add("btnNor");
        btnSearchAll.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
   
        vbBox.getChildren().addAll(lb,lb1,btnViewAll,btnSearchAll);     
        gridPane.add(vbBox, 2, 2, 1, 3);
        
        //table
        table.setPrefSize(600, 300);
        TableColumn QuesID = new TableColumn("Question ID");
        TableColumn QuesContent = new TableColumn("Question Content");
        TableColumn CateID = new TableColumn("Category ID");    
        QuesID.prefWidthProperty().bind(table.widthProperty().divide(5)); // w * 1/5
        QuesContent.prefWidthProperty().bind(table.widthProperty().divide(1.67)); // w * 3/5
        CateID.prefWidthProperty().bind(table.widthProperty().divide(5)); // w * 1/5

        table.getColumns().addAll(QuesID, CateID, QuesContent);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.getChildren().addAll(table);    
        gridPane.add(vbox, 1, 3);
        // button list
        VBox vbbtn = new VBox(10);
        vbbtn.setPrefWidth(100);
        Label lb2 = new Label("");
        Label lb3 = new Label("");
        Label lb4 = new Label("");
        Button btnAdd = new Button("Thêm");
        Button btnUpdate = new Button("Cập nhật");
        Button btnDelete = new Button("Xóa");
        Button btnCancel = new Button("Thoát");
        
        HBox.setHgrow(btnAdd, Priority.ALWAYS);
        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnAdd.getStyleClass().add("btnNor");
        btnAdd.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        
        HBox.setHgrow(btnUpdate, Priority.ALWAYS);
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.getStyleClass().add("btnNor");
        btnUpdate.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        
        HBox.setHgrow(btnUpdate, Priority.ALWAYS);
        btnDelete.setMaxWidth(Double.MAX_VALUE);
        btnDelete.getStyleClass().add("btnNor");
        btnDelete.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        
        HBox.setHgrow(btnUpdate, Priority.ALWAYS);
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        btnCancel.getStyleClass().add("btnNor");
        btnCancel.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        
        vbbtn.getChildren().addAll(lb2,lb3,lb4,btnAdd,btnUpdate,btnDelete,btnCancel);
        gridPane.add(vbbtn, 2, 3);
        
        root.getChildren().add(gridPane);
        
        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                new FrCheckQuestion();
            }
            
        });
    }


    
}
