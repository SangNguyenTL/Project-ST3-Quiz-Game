/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import DBModel.Player;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lib.AlertGame;

/**
 *
 * @author QUANGTU
 */
public class frResult extends frLogin {

    protected Boolean type;
    protected TextField txtplayer;
    protected TextField txtemail;
    protected TextField txtyear;
    protected PasswordField txtpass;
    protected PasswordField txtpass1;
    protected Player playerSign;
    protected GridPane grid;
    protected ArrayList<Text> listText;
    public frResult(Pane root, DBModel.Player player, DBModel.statusGame newGame) {
        super(root, player);
        init(newGame);
    }

    public void init(DBModel.statusGame newGame) {
        root.getChildren().clear();
        grid = new GridPane();
        grid.setPrefSize(root.getWidth(), root.getHeight());
        root.getChildren().add(grid);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));

        Text txtResult = new Text("Thành tích.");
        txtResult.setEffect(ds);
        txtResult.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        HBox boxResult = new HBox(txtResult);
        boxResult.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("text-white");
        grid.add(boxResult,0,0);
        grid.add(new Text("Số tiền đạt được:"+newGame.getMoney()), 0, 1);
        grid.add(new Text("Tổng thời gian:"+newGame.getTimeTotal()), 0, 2);
        grid.add(new Text("Số tiền lần trước:"+newGame.getOldTime()), 0, 3);
        grid.add(new Text("Tổng số tiền lần trước:"+newGame.getOldMoney()), 0, 4);

        Button back = new Button("Quay lại");
        HBox hbback = new HBox(10);
        back.getStyleClass().add("btnCus");
        back.setMinWidth(162);
        back.setMinHeight(42);
        hbback.getChildren().add(back);
        hbback.setPadding(new Insets(10));
        root.getChildren().add(hbback);
        back.setOnAction((ActionEvent t) -> {
            root.getChildren().clear();
            new listButtonLogged(root,player);
        });
    }
    
}
