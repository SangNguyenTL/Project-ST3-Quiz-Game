/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import DBModel.MyConnect;
import DBModel.Player;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author QUANGTU
 */
public class frPlayer extends frManager{

    private TableView<Player> table;
    private ObservableList<DBModel.Player> filteredData;
    private HBox boxTable;
    private TextField txtSearch;
    private TextField txtYear;
    private TextField txtEmail;
    private TextField txtPrize;
    private TextField txtTime;
    private TreeMap<Integer,TextField> txtText;
    private JasperDesign jd;
    public frPlayer(Pane root, Player player, ObservableList<DBModel.Player> masterDataPlayer) {
        super(root, player);
        init(masterDataPlayer);
    }

    public ObservableList<Player> getMasterDataPlayer() {
        return masterDataPlayer;
    }
    
    public void init(ObservableList<DBModel.Player> masterDataPlayer) {
        super.setRootPlayer(player);
        super.setMasterDataPlayer(masterDataPlayer);
        installBoxCount();
        root.getChildren().clear();
        GridPane main = new GridPane();
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
        
        main.setAlignment(Pos.TOP_CENTER);
        main.setHgap(10);
        main.setVgap(10);
        // BIến gốc
        
        //BIến dùng để lọc dữ liệu
        filteredData = FXCollections.observableArrayList();
        filteredData.addAll(masterDataPlayer);
        super.masterDataPlayer.addListener((ListChangeListener.Change<? extends Player> change) -> {
            updateFilteredData();
        });
        boxTable = new HBox();
        boxTable.setMaxWidth(root.getWidth()*0.8);
        initializeTable();
        boxTable.getChildren().add(new TablePlayer().init(table, filteredData));
        
        txtSearch = new TextField();
        txtSearch.setPromptText("Nhập tên");
        txtEmail = new TextField();
        txtEmail.setPromptText("Nhập email");
        txtYear = new TextField();
        txtYear.setPromptText("Nhập năm sinh");
        txtPrize = new TextField();
        txtPrize.setPromptText("Nhập tiền thưởng");
        txtTime = new TextField();
        txtTime.setPromptText("Nhập thời gian");
        
        txtText = new TreeMap<>();
        txtText.put(0, txtSearch);
        txtText.put(1, txtEmail);
        txtText.put(2, txtYear);
        txtText.put(3, txtPrize);
        txtText.put(4, txtTime);
        
        for(int i = 0; i <txtText.size(); i++){
            txtText.get(i).getStyleClass().add("txtField");
            txtText.get(i).setFont(new Font("Arial", 15));
            txtText.get(i).setPrefWidth(100);
            txtText.get(i).textProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable o) {
                lib.textAnimation a = new lib.textAnimation();
                a.setAnimation(500, ()->{
                    updateFilteredData();
                    a.getTimeline().stop();
                });
            }
        });
            main.add(txtText.get(i),i,0);
        }
       
        main.add(boxTable, 0, 1, 5, 2);

        // button list
        Button btnDelete = new Button("Xóa");
        btnDelete.getStyleClass().add("btnNor");
        btnDelete.setPrefSize(90, 30);
        main.add(btnDelete,6,0);

        Button btnUpdate = new Button("Cập nhật");
        btnUpdate.getStyleClass().add("btnNor");
        btnUpdate.setPrefSize(90, 30);
        main.add(btnUpdate,6,1);

        Button btnPrint = new Button("Thống kê");
        btnPrint.getStyleClass().add("btnNor");
        btnPrint.setPrefSize(90, 30);
        main.add(btnPrint,6,2);

        root.getChildren().add(main);
        
        btnPrint.setOnAction((ActionEvent t) -> {
            if (MyConnect.checkData()) {
                try {
                    Connection cn = new DBModel.MyConnect().getConnect();
                    jd = JRXmlLoader.load(".\\src\\ui\\reportPlayer.jrxml");
                    String sql = "SELECT  userName, email, YEAR(getdate()) - year as 'age', money, totalTime FROM tb_Player where userID <> 1 ORDER BY money DESC, totalTime ASC";
                    JRDesignQuery newQuery = new JRDesignQuery();
                    newQuery.setText(sql);
                    jd.setQuery(newQuery);
                    JasperReport jr = JasperCompileManager.compileReport(jd);
                    JasperPrint jp = JasperFillManager.fillReport(jr, null, cn);
                    JasperViewer.viewReport(jp, false);
                    JasperExportManager.exportReportToPdfFile(jp,System.getProperty("user.home") + "\\Desktop\\reportPlayer.pdf");
                } catch (JRException ex) {
                    Logger.getLogger(frPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        });
        
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
            new frPlayerUpdate(root, childPlayer, rootPlayer);
        });

        // Khi ấn nút delete
        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (table.getSelectionModel().isEmpty()) {
                    new AlertGame("Lỗi", "Bạn chưa chọn đối tượng trong bảng!", AlertType.WARNING) {

                        @Override
                        public void processResult() {
                        }
                    };
                    return;
                }
                Player playerObject = (Player) table.getSelectionModel().getSelectedItem();
                if (player.getUserID()!=1 && playerObject.isAdmin()) {
                    new AlertGame("Lỗi", "Bạn không có quyền để xóa người chơi này!", AlertType.WARNING) {

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
                                getMasterDataPlayer().clear();
                                getMasterDataPlayer().addAll(new Player().getData());
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
            }
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
        
        Boolean filter = true;
        if (filterString != null || !filterString.isEmpty()) {
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
