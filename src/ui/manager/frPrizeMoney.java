/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 *
 * @author Mattias
 */
public class frPrizeMoney extends frManager {

    private ObservableList<DBModel.PrizeMoney> data;
    TableView table = new TableView();

    public frPrizeMoney(Pane root, DBModel.Player player) {
        super(root, player);
        init();
    }

    public void init(){
        root.getChildren().clear();
        HBox main = new HBox();
        main.setMaxWidth(root.getWidth()*0.8);
        data = FXCollections.observableArrayList(new DBModel.PrizeMoney().getData());
        main.setAlignment(Pos.CENTER);
        table = new TableView();
        table.getStyleClass().add("table-quiz");
        table.setPrefWidth(main.getMaxWidth());
        table.setPrefHeight(300);
        table.setEditable(true);

        // gọi hàm edit cho table
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
                                t.getTablePosition().getRow())).setMoney(Integer.parseInt(t.getNewValue()));
                        DBModel.PrizeMoney updatePrize = (DBModel.PrizeMoney) table.getSelectionModel().getSelectedItem();
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
        table.setEditable(true);

        main.getChildren().addAll(table);
        root.getChildren().add(main);

    }

}
