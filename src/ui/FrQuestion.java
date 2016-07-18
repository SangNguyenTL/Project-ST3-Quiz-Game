/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

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
    private ComboBox cbbActive;
    private int cat;
    private TableQuestion tableQuestion = new TableQuestion();
    public FrQuestion(HBox root) {
        GridPane main = new GridPane();
        // never size the gridpane larger than its preferred size:
        main.setAlignment(Pos.CENTER);
        main.setVgap(10);
        main.setHgap(10);
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

        Label lblCategory = new Label("Chủ đề: ");
        lblCategory.setFont(new Font("Arial", 20));
        lblCategory.setTextFill(Color.web("#fff"));
        //Them lable vao grid cot 0 dong 0
        main.add(lblCategory, 0, 0);

        cbbCategory = new ComboBox();
        cbbCategory.getItems().add("Chọn...");
        ArrayList<DBModel.Category> listCategory = new DBModel.Category().getData();
        for (int i = 0; i < listCategory.size(); i++) {
            cbbCategory.getItems().add(listCategory.get(i));
        }
        cbbCategory.getSelectionModel().selectFirst();
        //them cbbCategory cot 1 dong 0
        main.add(cbbCategory, 1, 0);

        Label lblLevel = new Label();
        lblLevel.setText("Độ khó: ");
        lblLevel.setFont(new Font("Arial", 20));
        lblLevel.setTextFill(Color.web("#fff"));
        // cot 2 dong 0
        main.add(lblLevel, 6, 0);

        cbbLevel = new ComboBox();
        cbbLevel.getItems().add("Chọn...");
        cbbLevel.getItems().addAll(
                1,
                2,
                3,
                4
        );
        cbbLevel.getSelectionModel().selectFirst();;
        // cot 3 dong 0
        main.add(cbbLevel, 7, 0);

        Label lbActive = new Label();
        lbActive.setText("Tình trạng: ");
        lbActive.setFont(new Font("Arial", 20));
        lbActive.setTextFill(Color.web("#fff"));
        // cot 0 dong 1
        main.add(lbActive, 0, 1);

        cbbActive = new ComboBox();
        cbbActive.getItems().add("Chọn...");
        cbbActive.getItems().addAll(
                "Khả dụng",
                "Không khả dụng"
        );
        cbbActive.getSelectionModel().selectFirst();;
        // cot 1 dong 1
        main.add(cbbActive, 1, 1);

        Button btnSearch = new Button("Tìm kiếm");
        btnSearch.setMaxWidth(Double.MAX_VALUE);
        btnSearch.getStyleClass().add("btnNor");
        btnSearch.setPrefHeight(30);
        // cot 2 dong 1
        main.add(btnSearch, 6, 1);

        Button btnViewAll = new Button("Xem tất cả");
        btnViewAll.setMaxWidth(Double.MAX_VALUE);
        btnViewAll.getStyleClass().add("btnNor");
        btnViewAll.setPrefHeight(30);
        // cot 3 dong 1
        main.add(btnViewAll, 7, 1);

        Label lblQuestion = new Label();
        lblQuestion.setText("Nhập Câu hỏi: ");
        lblQuestion.setFont(new Font("Arial", 20));
        lblQuestion.setTextFill(Color.web("#fff"));
        // cot 0 dong 2
        main.add(lblQuestion, 0, 2);

        txtQuestion = new TextField();
        txtQuestion.setMaxWidth(Double.MAX_VALUE);
        txtQuestion.setPrefHeight(50);
        main.add(txtQuestion, 1, 2, 7, 1);

        //table cot 0 dong 3 col pan 5
        boxTable = new HBox(new TableQuestion().init(table, filteredData));
        boxTable.setMaxWidth(Double.MAX_VALUE);
        boxTable.setPrefHeight(300);
        main.add(boxTable, 0, 3, 8, 4);

        Button btnAdd = new Button("Thêm");
        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnAdd.getStyleClass().add("btnNor");
        btnAdd.setPrefHeight(30);
        main.add(btnAdd, 8, 3);

        Button btnUpdate = new Button("Cập nhật");
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.getStyleClass().add("btnNor");
        btnUpdate.setPrefHeight(30);
        main.add(btnUpdate, 8, 4);

        Button btnDelete = new Button("Xóa");
        btnDelete.setMaxWidth(Double.MAX_VALUE);
        btnDelete.getStyleClass().add("btnNor");
        btnDelete.setPrefHeight(30);
        main.add(btnDelete, 8, 5);
        root.getChildren().add(main);
        btnUpdate.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
                root.getChildren().clear();
                new FrAddAnswer(root, (Question) table.getSelectionModel().getSelectedItem(),true);
            }

        });

        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().clear();
                new FrCheckQuestion(root);
            }

        });

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
                Question ques = (Question) table.getSelectionModel().getSelectedItem();
                new AlertGame("Xóa đối tượng", "Bạn chắc chắn muốn xóa?", AlertType.CONFIRMATION) {
                    @Override
                    public void processResult() {
                        if(result.get()==ButtonType.OK){
                            String textStatus;
                            if (ques.delete()){
                                masterData.clear();
                                masterData.addAll(new Question().getData());
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
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).

        btnSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Category selectedCat;
                filteredData.clear();
                filteredData.addAll(masterData);
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
                //Lặp mảng đối tượng lọc (ko lặp mảng đới tượng chính do mảng chính ko được lọc qua nội dung câu hỏi)
                for (Question q : filteredData) {
                    //Điều kiện lọc
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
                updateFilteredData();
            }
        });
    }

    //Khởi tạo bảng
    private void initializeTable() {
        table.setPrefWidth(750);
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
        content.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        status.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
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
        boxTable.getChildren().add(tableQuestion.init(table, filteredData));
    }
}
