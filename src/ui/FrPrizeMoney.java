/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;


import DBModel.MyConnect;
import DBModel.PrizeMoney;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;



/**
 *
 * @author Mattias
 */
public class FrPrizeMoney {
    
    private ObservableList<DBModel.PrizeMoney> data = FXCollections.observableArrayList() ;
    TableView table = new TableView();
    private HBox boxTable = new HBox();
    
    ResultSet rs=null;
    PreparedStatement preparedStatement=null;
    Connection connection=MyConnect.getConnect();
    @FXML TextField txtnumber;
    @FXML TextField txtRight;
    
    public FrPrizeMoney(HBox root) {
        Pane main = new Pane();
        
        Label title = new Label("Quản lý tiền thưởng");
        title.setFont(new Font("Arial",35));
        title.setTextFill(Color.web("#ff0000"));
        title.setLayoutX(130);
        title.setLayoutY(10);
        
        
        data = FXCollections.observableArrayList(new DBModel.PrizeMoney().getData());
       
        VBox box = new VBox();
        table.setPrefSize(600, 400);
        table.setLayoutX(50);
        table.setLayoutY(100);
        table.setEditable(true);
        
        //???
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new lib.EditingCell();
            }
        };
        // tao column trong bang
        TableColumn loca = new TableColumn("Vị trí");
        loca.setCellValueFactory(
            new PropertyValueFactory<DBModel.PrizeMoney, String>("PriId"));
       
        TableColumn money = new TableColumn("Mức thưởng");
        money.setCellValueFactory(
            new PropertyValueFactory<DBModel.PrizeMoney, String>("Money"));
        
        // xu ly su kien tren cot Moeny
        money.setCellFactory(cellFactory);
        money.setOnEditCommit(
                new EventHandler<CellEditEvent<DBModel.PrizeMoney, String>>() {
        @Override
        public void handle(TableColumn.CellEditEvent<DBModel.PrizeMoney, String> t) {
            
            ((DBModel.PrizeMoney) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setMoney(Integer.parseInt(t.getNewValue()));
            DBModel.PrizeMoney updatePrize  = (DBModel.PrizeMoney)table.getSelectionModel().getSelectedItem();
            updatePrize.update();
        
        }
    }
);
       
        //set size cho colum
        loca.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        money.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
  
        table.getColumns().addAll(loca, money);   
        //them du lieu vao bang
        table.setItems(data);   
        box.getChildren().add(table);
        
        boxTable.getChildren().add(box);
        boxTable.setLayoutX(50);
        boxTable.setLayoutY(100);

        Button btnUpdate = new Button("Cập nhật");
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.getStyleClass().add("btnNor");
        btnUpdate.setLayoutX(240);
        btnUpdate.setLayoutY(520);
        btnUpdate.setPrefSize(90, 30);
        
        Button btnExit = new Button("Thoát");
        btnExit.setMaxWidth(Double.MAX_VALUE);
        btnExit.getStyleClass().add("btnNor");
        btnExit.setLayoutX(350);
        btnExit.setLayoutY(520);
        btnExit.setPrefSize(90, 30);
        
        table.setEditable(true);
       
        main.getChildren().addAll(title,boxTable,btnUpdate,btnExit);
        root.getChildren().add(main);
        
    }
    

   
    
 
}
