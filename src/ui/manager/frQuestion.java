/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import DBModel.Category;
import DBModel.Question;
import java.util.ArrayList;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lib.AlertGame;
import lib.TableQuestion;

/**
 *
 * @author Mattias
 */
public class frQuestion extends frManager{

    private TableView<Question> table;
    private ObservableList<DBModel.Question> filteredData;
    private HBox boxTable;
    private TextField txtQuestion;
    private ComboBox<String> cbbLevel;
    private ComboBox<DBModel.Category> cbbCategory;
    private ComboBox<String> cbbActive;
    private int cat;
    
    
    public frQuestion(Pane root, DBModel.Player player, ObservableList<DBModel.Question> masterDataQuestion){
        super(root,player);
        init(masterDataQuestion);
    }
    public void init(ObservableList<DBModel.Question> masterDataQuestion) {
        super.setMasterDataQuestion(masterDataQuestion);
        installBoxCount();
        root.getChildren().clear();
        GridPane main = new GridPane();
        // never size the gridpane larger than its preferred size:
        main.setAlignment(Pos.TOP_CENTER);
        main.setVgap(10);
        main.setHgap(10);
        filteredData = FXCollections.observableArrayList();
        filteredData.addAll(masterDataQuestion);
        masterDataQuestion.addListener((ListChangeListener.Change<? extends Question> change) -> {
            updateFilteredData();
        });

        //table cot 0 dong 3 col pan 5
        boxTable = new HBox();
        boxTable.setMaxWidth(root.getWidth()*0.9);
        initializeTable();
        boxTable.getChildren().add(new TableQuestion().init(table, filteredData));
        
        
        Label lblCategory = new Label("Chủ đề: ");
        lblCategory.setFont(new Font("Arial", 20));
        lblCategory.setTextFill(Color.web("#fff"));
        //Them lable vao grid cot 0 dong 0
        main.add(lblCategory, 0, 0);

        cbbCategory = new ComboBox<DBModel.Category>();
        cbbCategory.getItems().add(new DBModel.Category(0,"Chọn..."));
        cbbCategory.getItems().addAll(new DBModel.Category().getData());
        cbbCategory.getSelectionModel().selectFirst();
        //them cbbCategory cot 1 dong 0
        main.add(cbbCategory, 1, 0);

        Label lblLevel = new Label();
        lblLevel.setText("Độ khó: ");
        lblLevel.setFont(new Font("Arial", 20));
        lblLevel.setTextFill(Color.web("#fff"));
        // cot 2 dong 0
        main.add(lblLevel, 2, 0);

        cbbLevel = new ComboBox<String>();
        cbbLevel.getItems().addAll("Chọn...","1","2","3","4");
        cbbLevel.getSelectionModel().selectFirst();;
        // cot 3 dong 0
        main.add(cbbLevel, 3, 0);

        Label lbActive = new Label();
        lbActive.setText("Tình trạng: ");
        lbActive.setFont(new Font("Arial", 20));
        lbActive.setTextFill(Color.web("#fff"));
        // cot 0 dong 1
        main.add(lbActive, 4, 0);

        cbbActive = new ComboBox<String>();
        cbbActive.getItems().add("Chọn...");
        cbbActive.getItems().addAll(
                "Khả dụng",
                "Không khả dụng"
        );
        cbbActive.getSelectionModel().selectFirst();;
        // cot 1 dong 1
        main.add(cbbActive, 5, 0);

        Button btnSearch = new Button("Tìm kiếm");
        btnSearch.setPrefWidth(100);
        btnSearch.getStyleClass().add("btnNor");
        btnSearch.setPrefHeight(30);
        // cot 2 dong 1
        main.add(btnSearch, 6, 0);

        Button btnViewAll = new Button("Xem tất cả");
        btnViewAll.setPrefWidth(100);
        btnViewAll.getStyleClass().add("btnNor");
        btnViewAll.setPrefHeight(30);
        // cot 3 dong 1
        main.add(btnViewAll, 7,0);

        Label lblQuestion = new Label();
        lblQuestion.setText("Nhập Câu hỏi: ");
        lblQuestion.setFont(new Font("Arial", 20));
        lblQuestion.setTextFill(Color.web("#fff"));
        // cot 0 dong 2
        main.add(lblQuestion, 0, 1);

        txtQuestion = new TextField();
        txtQuestion.setPrefWidth(100);
        txtQuestion.setPrefHeight(50);
        main.add(txtQuestion, 1, 1, 7, 1);

        main.add(boxTable, 0, 2, 8, 3);
        Button btnAdd = new Button("Thêm");
        btnAdd.setPrefWidth(100);
        btnAdd.getStyleClass().add("btnNor");
        btnAdd.setPrefHeight(30);

        Button btnUpdate = new Button("Cập nhật");
        btnUpdate.setPrefWidth(100);
        btnUpdate.getStyleClass().add("btnNor");
        btnUpdate.setPrefHeight(30);
        main.add(btnUpdate, 8, 4);

        Button btnDelete = new Button("Xóa");
        btnDelete.setPrefWidth(100);
        btnDelete.getStyleClass().add("btnNor");
        btnDelete.setPrefHeight(30);
        VBox boxButton = new VBox(btnAdd,btnUpdate,btnDelete);
        boxButton.setSpacing(10);
        main.add(boxButton, 8, 3);
        
        root.getChildren().add(main);
        btnUpdate.setOnMouseClicked((MouseEvent event) -> {
            if (table.getSelectionModel().isEmpty()) {
                new AlertGame("Lỗi", "Bạn chưa chọn đối tượng trong bảng!", AlertType.WARNING) {
                    
                    @Override
                    public void processResult() {
                    }
                };
                return;
            }
            new frQuestionAddAnswer(root, player,(Question) table.getSelectionModel().getSelectedItem(),true);
        });

        btnAdd.setOnMouseClicked((MouseEvent event) -> {
            new frQuestionAdd(root,player);
        });

        btnDelete.setOnMouseClicked((MouseEvent event) -> {
            if (table.getSelectionModel().isEmpty()) {
                new AlertGame("Lỗi", "Bạn chưa chọn đối tượng trong bảng!", AlertType.WARNING) {
                    
                    @Override
                    public void processResult() {
                    }
                };
                return;
            }
            Question ques = (Question) table.getSelectionModel().getSelectedItem();
            new AlertGame("Xóa đối tượng", "Bạn chắc chắn muốn xóa?", AlertType.CONFIRMATION) {
                @Override
                public void processResult() {
                    if(getResult().get()==ButtonType.OK){
                        String textStatus;
                        if (ques.delete()){
                            masterDataQuestion.clear();
                            masterDataQuestion.addAll(new Question().getData());
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
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).

        btnSearch.setOnAction((ActionEvent event) -> {
            search();
        });
        // Thực hiện hành động lọc nội dung câu hỏi khi trường text Question thay đổi
        txtQuestion.textProperty().addListener((Observable o) -> {
                lib.textAnimation a = new lib.textAnimation();
                a.setAnimation(500, ()->{
                    search();
                    a.getTimeline().stop();
                });
        });

        btnViewAll.setOnAction((ActionEvent t) -> {
            updateFilteredData();
        });
    }
    public void search(){
            Category selectedCat;
            filteredData.clear();
            filteredData.addAll(masterDataQuestion);
            int selectCate = cbbCategory.getSelectionModel().getSelectedIndex();
            if (selectCate != 0) {
                selectedCat = (Category) cbbCategory.getSelectionModel().getSelectedItem();
                cat = selectedCat.getId();
            }
            
            int level = cbbLevel.getSelectionModel().getSelectedIndex();
            int selectActive = cbbActive.getSelectionModel().getSelectedIndex();
            String statusActive = cbbActive.getSelectionModel().getSelectedItem().toString();
            String lowCaseQuest = txtQuestion.getText().toLowerCase();
            ObservableList<DBModel.Question> newFilteredData = FXCollections.observableArrayList();
            filteredData.stream().forEach((q) -> {
                Boolean filter = true;
                if (selectCate != 0) {
                    filter = filter && q.getCatId() == cat;
                }
                if (level != 0) {
                    filter = filter && q.getLevel() == level;
                }
                if (selectActive != 0) {
                    filter = filter && q.getActive().equals(statusActive);
                }
                if (!lowCaseQuest.equals("") || lowCaseQuest != null) {
                    filter = filter && (q.getQuesContent().toLowerCase().indexOf(lowCaseQuest) != -1);
                }
                if (filter) {
                    newFilteredData.add(q);
                }
            });
            filteredData.clear();
            filteredData.addAll(newFilteredData);
            //Gọi lại table với giữ liệu được lọc
            reapplyTableSortOrder(); 
    }
    
    //Khởi tạo bảng
    private void initializeTable() {
        table = new TableView<>();
        table.getStyleClass().add("table-quiz");
        table.setPrefWidth(boxTable.getMaxWidth());
        // Gọi và đặt tên cho từng cột
        TableColumn<DBModel.Question, String> category = new TableColumn<>("Chủ đề");
        //Gán giá trị cho cột theo đối tượng (đối tượng phải chứa phưng thức get<...>(). <...> là khóa nội dung của đối tượng muốn lấy,
        // ví dụ như bên dưới rõ hơn thì vào từn class để xem).
        category.setCellValueFactory(
                new PropertyValueFactory<>("Cat"));
        TableColumn<DBModel.Question, String> level = new TableColumn<>("Cấp độ");
        level.setCellValueFactory(
                new PropertyValueFactory<>("Level"));
        TableColumn<DBModel.Question, String> contentQuestion = new TableColumn<>("Nội dung");
        contentQuestion.setCellValueFactory(
                new PropertyValueFactory<>("QuesContent"));
        TableColumn<DBModel.Question, String> status = new TableColumn<>("Trạng thái");
        status.setCellValueFactory(
                new PropertyValueFactory<>("Active"));
        //Căn đều các cột theo tỉ lệ nhât định qua phương thức bind().
        category.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        level.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        contentQuestion.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        status.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        ArrayList<TableColumn<DBModel.Question, String>> listCol = new ArrayList<>();
        listCol.add(category);
        listCol.add(level);
        listCol.add(contentQuestion);
        listCol.add(status);
        
        table.getColumns().addAll(listCol);
    }

    /**
     * Updates the filteredData to contain all data from the masterData that
     * matches the current filter.
     */
    //Cập nhật filter data khi có thay đổi ở trường txtQuest tạm thời chỉ đối với trường này vì còn đang xem xét.
    private void updateFilteredData() {
        filteredData.clear();
        masterDataQuestion.stream().filter((p) -> (matchesFilter(p))).forEach((p) -> {
            filteredData.add(p);
        });
        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    // Lọc từ khóa
    private boolean matchesFilter(Question q) {
        String filterString = txtQuestion.getText();
        if (filterString == null || filterString.isEmpty()) {
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();
        return q.getQuesContent().toLowerCase().indexOf(lowerCaseFilterString) != -1; 
    }

    // mỗi lần lọc xong ta đc filteredData mới và chỉ cần chạy phương thức này bảng sẽ được cập nhật lại.
    private void reapplyTableSortOrder() {
        super.setMasterDataPlayer(masterDataPlayer);
        boxTable.getChildren().clear();
        boxTable.getChildren().add(new TableQuestion().init(table, filteredData));
    }
}
