/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Text;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author QUANGTU
 */
public class TopTen {
    public static class Person {
 
        private final SimpleStringProperty rank;
        private final SimpleStringProperty name;
        private final SimpleStringProperty money;
 
        private Person(String rank, String name, String money) {
            this.rank = new SimpleStringProperty(rank);
            this.name = new SimpleStringProperty(name);
            this.money = new SimpleStringProperty(money);
        }
 
        public String getFirstName() {
            return rank.get();
        }
 
        public void setFirstName(String fName) {
            rank.set(fName);
        }
 
        public String getLastName() {
            return name.get();
        }
 
        public void setLastName(String fName) {
            name.set(fName);
        }
 
        public String getEmail() {
            return money.get();
        }
 
        public void setEmail(String fName) {
            money.set(fName);
        }
    }
    private TableView<Person> table = new TableView<>();
    private final  ObservableList<Person> data =
            FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("Ethan", "Williams", "ethan.williams@example.com"),
            new Person("Emma", "Jones", "emma.jones@example.com"),
            new Person("Michael", "Brown", "michael.brown@example.com")
            );
    
    public TopTen(Pane root) {
         GridPane grid = new GridPane();
        grid.setPrefSize(root.getWidth(), root.getHeight());
        root.getChildren().add(grid);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));
                
        Text title = new Text("Top 10");
        title.setFont(new Font("Tahoma",40));
        title.setEffect(ds);
        title.setFill(Color.WHITE);
        
        grid.add(title, 0, 0);
        
       table.setEditable(true);
        
        table.setPrefSize(500, 300);
        TableColumn<Person,String> rank = new TableColumn("Hạng");
        rank.setCellValueFactory(
            new PropertyValueFactory<>("rank")
        );
        
        TableColumn<Person,String> name = new TableColumn("Tên người chơi");
        name.setCellValueFactory(
            new PropertyValueFactory<>("name")
        );
        
        TableColumn<Person,String> money = new TableColumn("Tiền thưởng");
        money.setCellValueFactory(
            new PropertyValueFactory<>("money")
        );
        
        rank.prefWidthProperty().bind(table.widthProperty().divide(5));
        name.prefWidthProperty().bind(table.widthProperty().divide(2));
        money.prefWidthProperty().bind(table.widthProperty().divide(3));
        
        
        table.setItems(data);
        table.getColumns().addAll(rank, name, money);
        grid.add(table, 0, 1);
        
        Button back = new Button("Quay lại");
        back.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        HBox hbback = new HBox(10);
        back.getStyleClass().add("btnCus");
        back.setMinWidth(162);
        back.setMinHeight(42);
        hbback.getChildren().add(back);
        hbback.setPadding(new Insets(10));
        root.getChildren().add(hbback);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                root.getChildren().clear();
                new login(root);

            }
        });
    }
 

}
