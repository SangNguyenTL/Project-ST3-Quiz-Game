/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;


import DBModel.Question;
import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
public class FrQuestion {
    private ObservableList<DBModel.Question> data = FXCollections.observableList(new DBModel.Question().getData()); ;
    private Pagination pagination;
    private HBox boxTable = new HBox();
    int count = 0;
    double totalRow = Math.ceil((double) new DBModel.Question().getNumAllRow()/itemsPerPage());
    TableView<Question> table = new TableView();
    TextField txtQuestion;
    ComboBox cbbLevel;
    ComboBox cbbCategory;
    String cat;
    
    public int itemsPerPage() {
        return 10;
    }
    
    public FrQuestion(HBox root) {
        Pane main = new Pane();
 
        Label lblCategory = new Label("Chủ đề: ");
        lblCategory.setFont(new Font("Arial",20));
        lblCategory.setTextFill(Color.web("#fff"));
        lblCategory.setLayoutX(10);
        lblCategory.setLayoutY(10);
        
        cbbCategory = new ComboBox();
        ArrayList<DBModel.Category> listCategory = new DBModel.Category().getData();
        for(int i = 0; i < listCategory.size(); i++){          
                cbbCategory.getItems().add(listCategory.get(i).getName());      
        }
        cbbCategory.getSelectionModel().selectFirst();
        cbbCategory.setLayoutX(100);
        cbbCategory.setLayoutY(10);
        
        Label lblLevel = new Label();
        lblLevel.setText("Độ khó: ");
        lblLevel.setFont(new Font("Arial",20));
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
        btnSearch.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnSearch.setLayoutX(500);
        btnSearch.setLayoutY(10);
        btnSearch.setPrefSize(90, 30);
        
        Label lblQuestion = new Label();
        lblQuestion.setText("Nhập Câu hỏi: ");
        lblQuestion.setFont(new Font("Arial",20));
        lblQuestion.setTextFill(Color.web("#fff"));
        lblQuestion.setLayoutX(10);
        lblQuestion.setLayoutY(58);
        
        
        
        Button btnViewAll = new Button("Xem tất cả");        
        btnViewAll.setMaxWidth(Double.MAX_VALUE);
        btnViewAll.getStyleClass().add("btnNor");
        btnViewAll.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnViewAll.setLayoutX(500);
        btnViewAll.setLayoutY(55);
        btnViewAll.setPrefSize(90, 30);
        
        txtQuestion = new TextField();    
        
        txtQuestion.setPrefSize(600, 50);
        txtQuestion.setLayoutX(20);
        txtQuestion.setLayoutY(100);
  
        //table
        this.buildData();
        boxTable.setLayoutX(20);
        boxTable.setLayoutY(230);

        Button btnAdd = new Button("Thêm");
        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnAdd.getStyleClass().add("btnNor");
        btnAdd.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnAdd.setLayoutX(50);
        btnAdd.setLayoutY(570);
        btnAdd.setPrefSize(90, 30);
            
        Button btnUpdate = new Button("Cập nhật");
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.getStyleClass().add("btnNor");
        btnUpdate.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnUpdate.setLayoutX(200);
        btnUpdate.setLayoutY(570);
        btnUpdate.setPrefSize(90, 30);
        
        Button btnDelete = new Button("Xóa");
        btnDelete.setMaxWidth(Double.MAX_VALUE);
        btnDelete.getStyleClass().add("btnNor");
        btnDelete.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnDelete.setLayoutX(360);
        btnDelete.setLayoutY(570);
        btnDelete.setPrefSize(90, 30);
        
        Button btnExit = new Button("Thoát");
        btnExit.setMaxWidth(Double.MAX_VALUE);
        btnExit.getStyleClass().add("btnNor");
        btnExit.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        btnExit.setLayoutX(500);
        btnExit.setLayoutY(570);
        btnExit.setPrefSize(90, 30);
        
   
        
        main.getChildren().addAll(lblCategory,cbbCategory,lblLevel,cbbLevel,lblQuestion,btnSearch,btnViewAll,txtQuestion,boxTable,btnAdd,btnUpdate,btnDelete,btnExit);
        root.getChildren().add(main);
        
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().clear();
                new FrCheckQuestion(root);
            }
            
        });
        btnExit.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
            
        });
        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Question ques = (Question)table.getSelectionModel().getSelectedItem();
                int i = ques.getQuesId();
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete?");
                Optional <ButtonType> action = alert.showAndWait();          
                if(action.get() == ButtonType.OK){
                if(ques.delete()==true){
                  buildData();
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
       
        btnSearch.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                
                if(cbbCategory.getSelectionModel().isSelected(0)){
                    cat = "1";
                }
                if(cbbCategory.getSelectionModel().isSelected(1)){
                    cat = "2";
                }
                if(cbbCategory.getSelectionModel().isSelected(2)){
                    cat = "3";
                }
                if(cbbCategory.getSelectionModel().isSelected(3)){
                    cat = "4";
                }
               String level = cbbLevel.getSelectionModel().getSelectedItem().toString();
               
         
                
            }
            
        });
      
        txtQuestion.textProperty().addListener(new ChangeListener() {
             @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                
            }
     
        });  
  }
    
    public VBox createPage(int pageIndex) {
        ArrayList<Question> newdata = new ArrayList<>();
        for(int i = (pageIndex+1-1)*this.itemsPerPage(); i < (pageIndex+1-1)*this.itemsPerPage()+this.itemsPerPage(); i++)
        {
            if(data.size() == i) break;
            newdata.add(data.get(i));
        }
        data =  FXCollections.observableList(newdata);
        VBox box = new VBox();
        table.setEditable(true);
        table.setPrefSize(600, 300);
        TableColumn category = new TableColumn("Thể loại");
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
        
        category.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        level.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        content.prefWidthProperty().bind(table.widthProperty().multiply(0.58));
        status.prefWidthProperty().bind(table.widthProperty().multiply(0.12));
        table.getColumns().addAll(category, level, content, status);
        
        table.setItems(data);
        box.getChildren().add(table);
        return box;
    }
  
    public void buildData(){
        boxTable.getChildren().clear();
        pagination = new Pagination((int) totalRow, 0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                totalRow = Math.ceil((double) new DBModel.Question().getNumAllRow()/itemsPerPage());
                pagination.setMaxPageIndicatorCount((int) totalRow);
                if (pageIndex >= totalRow) {
                    return null;
                } else {
                    return createPage(pageIndex);
                }
            }
        });
        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().addAll(pagination);
        boxTable.getChildren().add(anchor);
   }

}

