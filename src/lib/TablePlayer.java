/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import DBModel.Player;
import DBModel.Question;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.omg.CORBA.DATA_CONVERSION;

/**
 *
 * @author QUANGTU
 */
public class TablePlayer {
    private HBox boxTable = new HBox();
    
    private ObservableList<DBModel.Player> filteredData = FXCollections.observableArrayList();
    private TableView<Player> table;
    private Pagination pagination;
    private int itemsPerPage = 10;
    private int totalRow ;
    public TablePlayer() {
    }
    public AnchorPane init(TableView<Player> table,ObservableList<DBModel.Player> filteredData) {
        this.table = table;
        // Initially add all data to filtered data
        this.filteredData = filteredData;
        pagination = new Pagination((filteredData.size() / itemsPerPage + 1), 0);
        pagination.setPageFactory(this::createPage);
        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().addAll(pagination);
        return anchor;
    }

    
    public Node createPage(int pageIndex) {
        VBox box = new VBox();
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, filteredData.size());
        table.setItems(FXCollections.observableArrayList(filteredData.subList(fromIndex, toIndex)));
        box.getChildren().add(table);
        return box;
    }
}
