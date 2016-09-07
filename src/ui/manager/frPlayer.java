/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import DBModel.Player;
import java.util.ArrayList;
import java.util.TreeMap;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import lib.AlertGame;
import lib.TablePlayer;

/**
 *
 * @author QUANGTU
 */
public class frPlayer{

    private TableView<Player> table;
    private ObservableList<DBModel.Player> filteredData;
    private HBox boxTable;
    private TextField txtSearch;
    private TextField txtYear;
    private TextField txtEmail;
    private TextField txtPrize;
    private TextField txtTime;
    private TreeMap<Integer,TextField> txtText;
    protected HBox content;
    protected Pane root;
    protected Player rootPlayer;
    private ObservableList<DBModel.Player> masterDataPlayer;
    public frPlayer(Pane root, HBox Content, Player player, ObservableList<DBModel.Player> masterDataPlayer) {
        this.root = root;
        this.content = Content;
        this.rootPlayer = player;
        this.masterDataPlayer = masterDataPlayer;
        init();        
    }
    public void init() {
        content.getChildren().clear();
        GridPane main = new GridPane();
        main.setAlignment(Pos.TOP_CENTER);
        main.setHgap(10);
        main.setVgap(10);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(15);
        ColumnConstraints col4 = new ColumnConstraints();
        col3.setPercentWidth(15);
        ColumnConstraints col5 = new ColumnConstraints();
        col3.setPercentWidth(15);
        main.getColumnConstraints().addAll(col1,col2,col3,col4,col5);
        
        
        // BIến gốc
        
        //BIến dùng để lọc dữ liệu
        filteredData = FXCollections.observableArrayList();
        filteredData.addAll(masterDataPlayer);
        masterDataPlayer.addListener((ListChangeListener.Change<? extends Player> change) -> {
            updateFilteredData();
        });
        boxTable = new HBox();
        boxTable.setMaxWidth(root.getWidth()*0.75);
        initializeTable();
        boxTable.getChildren().add(new TablePlayer().init(table, filteredData));
        
        txtSearch = new TextField();
        txtSearch.setPromptText("Tên");
        txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtYear = new TextField();
        txtYear.setPromptText("Năm sinh");
        txtPrize = new TextField();
        txtPrize.setPromptText("Tiền thưởng");
        txtTime = new TextField();
        txtTime.setPromptText("Thời gian");
        
        txtText = new TreeMap<>();
        txtText.put(0, txtSearch);
        txtText.put(1, txtEmail);
        txtText.put(2, txtYear);
        txtText.put(3, txtPrize);
        txtText.put(4, txtTime);
        
        for(int i = 0; i <txtText.size(); i++){
            txtText.get(i).getStyleClass().add("txtField");
            txtText.get(i).setFont(new Font("Arial", 20));
            txtText.get(i).setPrefWidth(100);
            txtText.get(i).textProperty().addListener((Observable o) -> {
                lib.textAnimation a = new lib.textAnimation();
                a.setAnimation(500, ()->{
                    updateFilteredData();
                    a.getTimeline().stop();
                });
            });
            main.add(txtText.get(i),i,0);
        }
       
        main.add(boxTable, 0, 1, 5, 2);

        // button list
        Button btnDelete = new Button("Xóa");
        btnDelete.getStyleClass().add("btnNor");
        btnDelete.setPrefSize(150, 30);
        main.add(btnDelete,6,0);

        Button btnUpdate = new Button("Cập nhật");
        btnUpdate.getStyleClass().add("btnNor");
        btnUpdate.setPrefSize(150, 30);
        main.add(btnUpdate,6,1);

        content.getChildren().add(main);
        
        btnUpdate.setOnAction((ActionEvent t) -> {
            if (table.getSelectionModel().isEmpty()) {
                new AlertGame("Lỗi", "Bạn chưa chọn đối tượng trong bảng!", AlertType.WARNING) {
                    
                    @Override
                    public void processResult() {
                    }
                };
                return;
            }
            Player childPlayer = (Player) table.getSelectionModel().getSelectedItem();
            new frPlayerUpdate(root,content, childPlayer, rootPlayer);
        });

        // Khi ấn nút delete
        btnDelete.setOnMouseClicked((MouseEvent event) -> {
            if (table.getSelectionModel().isEmpty()) {
                new AlertGame("Lỗi", "Bạn chưa chọn đối tượng trong bảng!", AlertType.WARNING) {
                    
                    @Override
                    public void processResult() {
                    }
                };
                return;
            }
            Player playerObject = (Player) table.getSelectionModel().getSelectedItem();
            if (rootPlayer.getUserID()!=1 && playerObject.isAdmin()) {
                new AlertGame("Lỗi", "Bạn không có quyền để xóa tài khoản này!", AlertType.WARNING) {
                    
                    @Override
                    public void processResult() {
                    }
                };
                return;
            }
            new AlertGame("Xóa đối tượng", "Bạn chắc chắn muốn xóa?", AlertType.CONFIRMATION) {
                @Override
                public void processResult() {
                    if(getResult().get()==ButtonType.OK){
                        String textStatus;
                        if (playerObject.delete()){
                            masterDataPlayer.clear();
                            masterDataPlayer.addAll(new Player().getData());
                            new frManager(root, rootPlayer);
                            textStatus = "Thành công";
                        }
                        else{
                            textStatus = "Thất bại";
                        }
                        new AlertGame("Trạng thái", "Xóa "+textStatus, AlertType.INFORMATION) {
                            
                            @Override
                            public void processResult() {
                            }
                        };
                    }
                }
            };
        });
}
    private void initializeTable() {
        table = new TableView<Player>();
        table.getStyleClass().add("table-quiz");
        table.setPrefWidth(boxTable.getMaxWidth());
        table.setMaxHeight(300);
        TableColumn<DBModel.Player, String> userName = new TableColumn<>("Tên người chơi");
        userName.setCellValueFactory(
                new PropertyValueFactory<>("userName"));
        TableColumn<DBModel.Player, String> email = new TableColumn<>("Tên đăng nhập");
        email.setCellValueFactory(
                new PropertyValueFactory<>("Email"));
        TableColumn<DBModel.Player, String> year = new TableColumn<>("Năm sinh");
        year.setCellValueFactory(
                new PropertyValueFactory<>("year"));
        TableColumn<DBModel.Player, String> money = new TableColumn<>("Tiền thưởng");
        money.setCellValueFactory(
                new PropertyValueFactory<>("Money"));
        TableColumn<Player, String> time = new TableColumn<>("Thời gian");
        time.setCellValueFactory(
                new PropertyValueFactory<>("TotalTime"));
        
        userName.prefWidthProperty().bind(table.widthProperty().divide(5)); 
        email.prefWidthProperty().bind(table.widthProperty().divide(5)); 
        year.prefWidthProperty().bind(table.widthProperty().divide(5)); 
        money.prefWidthProperty().bind(table.widthProperty().divide(5)); 
        time.prefWidthProperty().bind(table.widthProperty().divide(5)); 
        
        ArrayList<TableColumn<Player, String>> listCol = new ArrayList<>();
        listCol.add(userName);
        listCol.add(email);
        listCol.add(year);
        listCol.add(money);
        listCol.add(time);
        
        table.getColumns().addAll(listCol);
    }

    private void updateFilteredData() {
        filteredData.clear();

        // lap tung dong table player
        for (Player p : masterDataPlayer) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Player q) {
        String filterString = txtSearch.getText().toLowerCase();
        
        String filterEmail = txtEmail.getText().toLowerCase();
        
        String filterYear = txtYear.getText().toLowerCase();
        
        String filterPrize = txtPrize.getText().toLowerCase();
        
        String filterTime = txtTime.getText().toLowerCase();
        // neu filter = false thi filterData k duoc add 
        Boolean filter = true;
        
        if (filterString != null || !filterString.isEmpty()) {
            // indexOf tra vi tri tìm thay
            // k tim thay se tra ve -1
            // toan tu && true + false = false
            // kiem tra filterString(txt) co nam trong userName hay k
            filter = filter && (q.getUserName().toLowerCase().indexOf(filterString) != -1);
        }
        if (filterEmail != null || !filterEmail.isEmpty()) {
            filter = filter && (q.getEmail().toLowerCase().indexOf(filterEmail) != -1);
        }
        if (filterYear != null || !filterYear.isEmpty()) {
            filter = filter && (String.valueOf(q.getYear()).toLowerCase().indexOf(filterYear) != -1);
        }
        if (filterPrize != null || !filterPrize.isEmpty()) {
            filter = filter && (String.valueOf(q.getMoney()).toLowerCase().indexOf(filterPrize) != -1);
        }
        if (filterTime != null || !filterTime.isEmpty()) {
            filter = filter && (String.valueOf(q.getTotalTime()).toLowerCase().indexOf(filterTime) != -1);
        }
        
        return filter; 
    }

    private void reapplyTableSortOrder() {
        boxTable.getChildren().clear();
        boxTable.getChildren().add(new TablePlayer().init(table, filteredData));
    }
}
