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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author QUANGTU
 */
public class FrPlayer {

    private TableView table = new TableView();

    public FrPlayer(HBox root) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        Label lblCategory = new Label();

        lblCategory.setText("Nhập tên: ");
        lblCategory.setFont(new Font("Arial", 20));
        lblCategory.setTextFill(Color.web("#fff"));
        gridPane.add(lblCategory, 0, 1);

        Label lblQuestion = new Label();
        lblQuestion.setText("Thành viên: ");
        lblQuestion.setFont(new Font("Arial", 20));
        lblQuestion.setTextFill(Color.web("#fff"));
        gridPane.add(lblQuestion, 0, 3);

        Label lbTotal = new Label();
        lbTotal.setText("Tổng số thành viên: ");
        lbTotal.setFont(new Font("Arial", 15));
        lbTotal.setTextFill(Color.web("#fff"));
        gridPane.add(lbTotal, 1, 4);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));

        TextField userTextField = new TextField();
        userTextField.getStyleClass().add("txtField");
        userTextField.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        gridPane.add(userTextField, 1, 1);
        userTextField.setFont(new Font("Arial", 15));

        //button search
        VBox vbBox = new VBox(10);
        Button btnSearch = new Button("Tìm kiếm");
        HBox.setHgrow(btnSearch, Priority.ALWAYS);
        btnSearch.setMaxWidth(Double.MAX_VALUE);
        btnSearch.getStyleClass().add("btnNor");
        btnSearch.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        vbBox.getChildren().addAll(btnSearch);
        btnSearch.setFont(new Font("Arial", 20));
        gridPane.add(vbBox, 2, 1);

        //table
        table.setPrefSize(600, 300);
        TableColumn username = new TableColumn("Tên người chơi");
        TableColumn userlogin = new TableColumn("Tên đăng nhập");
        TableColumn pass = new TableColumn("Mật khẩu");
        TableColumn year = new TableColumn("Năm sinh");

        username.prefWidthProperty().bind(table.widthProperty().divide(3)); // w * 1/5
        userlogin.prefWidthProperty().bind(table.widthProperty().divide(4)); // w * 3/5
        pass.prefWidthProperty().bind(table.widthProperty().divide(5)); // w * 1/5
        year.prefWidthProperty().bind(table.widthProperty().divide(4)); // w * 1/5

        table.getColumns().addAll(username, userlogin, pass, year);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.getChildren().addAll(table);
        gridPane.add(vbox, 1, 3);
        // button list
        VBox vbbtn = new VBox(10);
        vbbtn.setPrefWidth(100);
        Button btnPrint = new Button("In");
        btnPrint.getStyleClass().add("btnNor");
        btnPrint.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnPrint.setFont(new Font("Arial",15));
        
        Button btnExit = new Button("Thoát");
        btnExit.getStyleClass().add("btnNor");
        btnExit.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnExit.setFont(new Font("Arial",15));
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnPrint);
        hbBtn.getChildren().add(btnExit);
        
        gridPane.add(hbBtn, 1, 4);
        

        

        root.getChildren().add(gridPane);

        btnExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });

    }

}
