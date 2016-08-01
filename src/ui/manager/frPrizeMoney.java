/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import DBModel.PrizeMoney;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import lib.AlertGame;

/**
 *
 * @author Mattias
 */
public class frPrizeMoney{
    private TableView<PrizeMoney> table;
    private HBox content;
    public frPrizeMoney(HBox content) {
        this.content = content;
        init();
    }

    public void init(){
        content.getChildren().clear();
        HBox main = new HBox();
        main.setMaxWidth(content.getWidth()*0.8);
        main.setAlignment(Pos.TOP_CENTER);
        table = new TableView<>();
        table.getStyleClass().add("table-quiz");
        table.setPrefWidth(main.getMaxWidth());
        table.setMaxHeight(500);
        table.setEditable(true);

        // gọi hàm edit cho table
        Callback<TableColumn<DBModel.PrizeMoney, String>, TableCell<DBModel.PrizeMoney, String>> cellFactory = new Callback<TableColumn<DBModel.PrizeMoney, String>, TableCell<DBModel.PrizeMoney, String>>() {

            @Override
            public TableCell<DBModel.PrizeMoney, String> call(TableColumn<DBModel.PrizeMoney, String> p) {
                return new lib.EditingCell();
            }

        };
        // tao column trong bang
        TableColumn<DBModel.PrizeMoney, String> loca = new TableColumn<>("Vị trí");
        loca.setCellValueFactory(
                new PropertyValueFactory<>("PriId"));

        TableColumn<DBModel.PrizeMoney, String> money = new TableColumn<>("Mức thưởng");
        money.setCellValueFactory(
                new PropertyValueFactory<>("Money"));

        // xu ly su kien tren cot Moeny
        money.setCellFactory(cellFactory);
        
        money.setOnEditCommit(
                (TableColumn.CellEditEvent<DBModel.PrizeMoney, String> t) -> {
                    SimpleStringProperty strError = new SimpleStringProperty("");
                    strError.addListener((ObservableValue<? extends String> ov, String t1, String t2) -> {
                        if (t2.equals("")) {
                            return;
                        }
                        new AlertGame("Lỗi", t2, Alert.AlertType.WARNING) {
                            @Override
                            public void processResult() {
                            }
                        };
                    });
                    
                    if(t.getNewValue().equals("")){
                        strError.set("Giá trị tiền thưởng không được để trống.");
                        t.getTableColumn().setVisible(false);
                        t.getTableColumn().setVisible(true);
                        return;
                    }
                    if(!Pattern.compile("^[1-9]\\d*").matcher(t.getNewValue().trim()).matches()){
                        strError.set("Giá trị tiền thưởng phải là số nguyên dương và khác 0.");
                        t.getTableColumn().setVisible(false);
                        t.getTableColumn().setVisible(true);
                        return;
                    }
                    try{
                        Integer.parseInt(t.getNewValue());
                    }catch(java.lang.NumberFormatException e){
                            strError.set("Giá trị tiền thưởng không được vượt quá 2 tỷ.");
                            t.getTableColumn().setVisible(false);
                            t.getTableColumn().setVisible(true);
                            return;
                    }
                    ((DBModel.PrizeMoney) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setMoney(Integer.parseInt(t.getNewValue()));
                    DBModel.PrizeMoney updatePrize = (DBModel.PrizeMoney) table.getSelectionModel().getSelectedItem();
                    updatePrize.update();
        });

        //set size cho colum
        loca.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        money.prefWidthProperty().bind(table.widthProperty().multiply(0.5));

        ArrayList<TableColumn<DBModel.PrizeMoney, String>> listCol = new ArrayList<>();
        listCol.add(loca);
        listCol.add(money);
        
        table.getColumns().addAll(listCol);
        //them du lieu vao bang
        table.setItems(FXCollections.observableArrayList(new DBModel.PrizeMoney().getData()));
        table.setEditable(true);

        main.getChildren().addAll(table);
        content.getChildren().add(main);

    }

}
