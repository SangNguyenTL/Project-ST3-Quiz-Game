/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import DBModel.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author nhats
 */
public class TableQuestion {
    
    private ObservableList<DBModel.Question> filteredData = FXCollections.observableArrayList();
    private TableView<Question> table;
    private final Pagination pagination = new Pagination();
    private final int itemsPerPage = 10;
    public TableQuestion() {
    }
    public AnchorPane init(TableView<Question> table,ObservableList<DBModel.Question> filteredData) {
        this.table = table;
        // Initially add all data to filtered data
        this.filteredData = filteredData;
        pagination.setPageCount(filteredData.size() / itemsPerPage + 1);
        pagination.setCurrentPageIndex(0);
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
