/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import DBModel.Question;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Mattias
 */
public class FrQuestion {


    private TableView<Question> table = new TableView<Question>();
    
    private ObservableList<DBModel.Question> masterData;
    private ObservableList<DBModel.Question> filteredData;
    private HBox boxTable;
    private TextField txtQuestion;
    private ComboBox cbbLevel;
    private ComboBox cbbCategory;
    private String cat;

    public FrQuestion(HBox root) {
        Pane main = new Pane();
        masterData = FXCollections.observableArrayList(new Question().getData());
        filteredData = FXCollections.observableArrayList();
        filteredData.addAll(masterData);
        masterData.addListener(new ListChangeListener<Question>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Question> change) {
                updateFilteredData();
            }
        });
        initializeTable();
        boxTable = new HBox(new TableQuestion().init(table,filteredData));
        Label lblCategory = new Label("Chủ đề: ");
        lblCategory.setFont(new Font("Arial", 20));
        lblCategory.setTextFill(Color.web("#fff"));
        lblCategory.setLayoutX(10);
        lblCategory.setLayoutY(10);

        cbbCategory = new ComboBox();
        ArrayList<DBModel.Category> listCategory = new DBModel.Category().getData();
        for (int i = 0; i < listCategory.size(); i++) {
            cbbCategory.getItems().add(listCategory.get(i).getName());
        }
        cbbCategory.getSelectionModel().selectFirst();
        cbbCategory.setLayoutX(100);
        cbbCategory.setLayoutY(10);

        Label lblLevel = new Label();
        lblLevel.setText("Độ khó: ");
        lblLevel.setFont(new Font("Arial", 20));
        lblLevel.setTextFill(Color.web("#fff"));
        lblLevel.setLayoutX(280);
        lblLevel.setLayoutY(10);

        cbbLevel = new ComboBox();
        cbbLevel.getItems().addAll(
                1,
                2,
                3,
                4
        );

        cbbLevel.getSelectionModel().selectFirst();
        cbbLevel.setLayoutX(360);
        cbbLevel.setLayoutY(10);

        Button btnSearch = new Button("Tìm kiếm");
        btnSearch.setMaxWidth(Double.MAX_VALUE);
        btnSearch.getStyleClass().add("btnNor");
        btnSearch.setLayoutX(500);
        btnSearch.setLayoutY(10);
        btnSearch.setPrefSize(90, 30);

        Label lblQuestion = new Label();
        lblQuestion.setText("Nhập Câu hỏi: ");
        lblQuestion.setFont(new Font("Arial", 20));
        lblQuestion.setTextFill(Color.web("#fff"));
        lblQuestion.setLayoutX(10);
        lblQuestion.setLayoutY(58);

        Button btnViewAll = new Button("Xem tất cả");
        btnViewAll.setMaxWidth(Double.MAX_VALUE);
        btnViewAll.getStyleClass().add("btnNor");
        btnViewAll.setLayoutX(500);
        btnViewAll.setLayoutY(55);
        btnViewAll.setPrefSize(90, 30);

        txtQuestion = new TextField();

        txtQuestion.setPrefSize(600, 50);
        txtQuestion.setLayoutX(20);
        txtQuestion.setLayoutY(100);

        //table
        
        boxTable.setLayoutX(20);
        boxTable.setLayoutY(230);

        Button btnAdd = new Button("Thêm");
        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnAdd.getStyleClass().add("btnNor");
        btnAdd.setLayoutX(50);
        btnAdd.setLayoutY(570);
        btnAdd.setPrefSize(90, 30);

        Button btnUpdate = new Button("Cập nhật");
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.getStyleClass().add("btnNor");
        btnUpdate.setLayoutX(200);
        btnUpdate.setLayoutY(570);
        btnUpdate.setPrefSize(90, 30);

        Button btnDelete = new Button("Xóa");
        btnDelete.setMaxWidth(Double.MAX_VALUE);
        btnDelete.getStyleClass().add("btnNor");
        btnDelete.setLayoutX(360);
        btnDelete.setLayoutY(570);
        btnDelete.setPrefSize(90, 30);

        Button btnExit = new Button("Thoát");
        btnExit.setMaxWidth(Double.MAX_VALUE);
        btnExit.getStyleClass().add("btnNor");
        btnExit.setLayoutX(500);
        btnExit.setLayoutY(570);
        btnExit.setPrefSize(90, 30);

        main.getChildren().addAll(lblCategory, cbbCategory, lblLevel, cbbLevel, lblQuestion, btnSearch, btnViewAll, txtQuestion, boxTable, btnAdd, btnUpdate, btnDelete, btnExit);
        root.getChildren().add(main);

        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().clear();
                new FrCheckQuestion(root);
            }

        });
        btnExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }

        });
        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(table.getSelectionModel().isEmpty()) return;
                Question ques = (Question) table.getSelectionModel().getSelectedItem();
                int i = ques.getQuesId();
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText(null);
                alert.setContentText("Bạn chắc chắn muốn xóa?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    if (ques.delete() == true) {
                        boxTable = new TableQuestion().init(table,filteredData);
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Thông báo");
                        alert.setHeaderText(null);
                        alert.setContentText("Xóa thành công");
                        alert.showAndWait();
                    }
                }
            }
        });
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).

        btnSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (cbbCategory.getSelectionModel().isSelected(0)) {
                    cat = "1";
                }
                if (cbbCategory.getSelectionModel().isSelected(1)) {
                    cat = "2";
                }
                if (cbbCategory.getSelectionModel().isSelected(2)) {
                    cat = "3";
                }
                if (cbbCategory.getSelectionModel().isSelected(3)) {
                    cat = "4";
                }
                String level = cbbLevel.getSelectionModel().getSelectedItem().toString();
                ObservableList<DBModel.Question> newFilteredData = FXCollections.observableArrayList();
                //Lặp mảng đối tượng lọc (ko lặp mảng đới tượng chính do mảng chính ko được lọc qua nội dung câu hỏi)
                for (Question q : filteredData){
                    //Điều kiện lọc
                    if(q.getCatId()==Integer.parseInt(cat)&&q.getLevel()==Integer.parseInt(level)){
                        newFilteredData.add(q);
                    }
                }
                filteredData.clear();
                filteredData.addAll(newFilteredData);
                //Gọi lại table với giữ liệu được lọc
                reapplyTableSortOrder();
            }

        });
        // Thực hiện hành động lọc nội dung câu hỏi khi trường text Question thay đổi
        txtQuestion.textProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable o) {
                // Sleep một 0.5 giây để đợi kết thúc ấn nội dung lọc thì mới bắt đầu lọc tránh nặng xử lý
                Task<Void> sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            //Đặt thời gian
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                        return null;
                    }
                };
                // KHi sleep hết thời gian sẽ chạy đoạn code trong handle
                sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        updateFilteredData();
                    }
                });
                new Thread(sleeper).start();
            }
        });
        
        btnViewAll.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                filteredData.clear();
                filteredData.addAll(masterData);
                reapplyTableSortOrder();
            }
        });
    }
    //Khởi tạo bảng
    private void initializeTable() {
        //Đặt kích thước bảng
        table.setPrefSize(620, 300);
        // Gọi và đặt tên cho từng cột
        TableColumn category = new TableColumn("Chủ đề");
        //Gán giá trị cho cột theo đối tượng (đối tượng phải chứa phưng thức get<...>(). <...> là khóa nội dung của đối tượng muốn lấy,
        // ví dụ như bên dưới rõ hơn thì vào từn class để xem).
        category.setCellValueFactory(
            new PropertyValueFactory<DBModel.Question, String>("Cat"));
        TableColumn level = new TableColumn("Cấp độ");
        level.setCellValueFactory(
            new PropertyValueFactory<DBModel.Question, String>("Level"));
        TableColumn content = new TableColumn("Nội dung");  
        content.setCellValueFactory(
            new PropertyValueFactory<DBModel.Question, String>("QuesContent"));
        TableColumn status = new TableColumn("Trạng thái"); 
        status.setCellValueFactory(
            new PropertyValueFactory<DBModel.Question, String>("Active"));
        //Căn đều các cột theo tỉ lệ nhât định qua phương thức bind().
        category.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        level.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        content.prefWidthProperty().bind(table.widthProperty().multiply(0.58));
        status.prefWidthProperty().bind(table.widthProperty().multiply(0.12));
        table.getColumns().addAll(category, level, content, status);
    }
    /**
     * Updates the filteredData to contain all data from the masterData that
     * matches the current filter.
     */
    //Cập nhật filter data khi có thay đổi ở trường txtQuest tạm thời chỉ đối với trường này vì còn đang xem xét.
    private void updateFilteredData() {
        filteredData.clear();

        for (Question p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        System.out.println(filteredData.size());
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
        //Đặt điều kiện lọc dùng phương thức getQuesContetn() thông qua đối tượng được truyền vào là q so sánh với lowerCaseFilterString nếu khác -1
        // thì chuỗi tìm kiếm có trong nội dung câu hỏi của đối tượng q => trả về true.
        if (q.getQuesContent().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        // ko có trả về false
        return false; // Does not match
    }
    // mỗi lần lọc xong ta đc filteredData mới và chỉ cần chạy phương thức này bảng sẽ được cập nhật lại.
    private void reapplyTableSortOrder() {
        boxTable.getChildren().clear();
        boxTable.getChildren().add(new TableQuestion().init(table, filteredData));
    }
}
