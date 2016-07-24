/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import DBModel.Player;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author QUANGTU
 */
public class frRank {
 
    private HBox rank[] = new HBox[11];
    private HBox name[] = new HBox[11];
    private HBox money[] = new HBox[11];
    private HBox totalTime[] = new HBox[11];
    
    public frRank(Pane root) {
        root.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setPrefSize(root.getWidth(), root.getHeight());
        root.getChildren().add(grid);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));
                
        Text title = new Text("Top 10");
        title.setFont(new Font("Tahoma",60));
        title.setEffect(ds);
        title.setFill(Color.WHITE);
        HBox boxTitle = new HBox(title);
        boxTitle.setPrefWidth(1000);
        boxTitle.setAlignment(Pos.CENTER);
        
        Text txtRank = new Text("Hạng");
        txtRank.setFont(new Font("Tahoma",40));
        txtRank.setEffect(ds);
        txtRank.setFill(Color.WHITE);
        rank[0] = new HBox(10);
        rank[0].setAlignment(Pos.CENTER);
        rank[0].getChildren().add(txtRank);
        
        Text txtName = new Text("Tên người chơi");
        txtName.setFont(new Font("Tahoma",40));
        txtName.setEffect(ds);
        txtName.setFill(Color.WHITE);
        name[0] = new HBox(10);
        name[0].setAlignment(Pos.CENTER_LEFT);
        name[0].getChildren().add(txtName);
        
        Text txtMoney = new Text("Money");
        txtMoney.setFont(new Font("Tahoma",40));
        txtMoney.setEffect(ds);
        txtMoney.setFill(Color.WHITE);
        money[0] = new HBox(10);
        money[0].setAlignment(Pos.CENTER);
        money[0].getChildren().add(txtMoney);
        
        Text txtTime = new Text("Time");
        txtTime.setFont(new Font("Tahoma",40));
        txtTime.setEffect(ds);
        txtTime.setFill(Color.WHITE);
        totalTime[0] = new HBox(10);
        totalTime[0].setAlignment(Pos.CENTER);
        totalTime[0].getChildren().add(txtTime);
        
        grid.add(boxTitle, 0, 0, 4, 1);
        grid.add(rank[0], 0, 1);
        grid.add(name[0], 1,1);
        grid.add(money[0], 2,1);
        grid.add(totalTime[0], 3,1);
        
        ArrayList<Player> listRank = new DBModel.Player().getRank();
        for(int i=0; i < listRank.size(); i++){
            Text ran = new Text(String.valueOf(i+1));
            ran.setFont(new Font("Tahoma",30));
            ran.setEffect(ds);
            ran.setFill(Color.WHITE);
            rank[i+1] = new HBox(10);
            rank[i+1].setAlignment(Pos.CENTER);
            rank[i+1].getChildren().add(ran);
            
            Text nam =  new Text(listRank.get(i).getUserName());
            nam.setFont(new Font("Tahoma",30));
            nam.setEffect(ds);
            nam.setFill(Color.WHITE);
            name[i+1] = new HBox(10);
            name[i+1].getChildren().add(nam);
            
            Text mon =  new Text(String.valueOf(listRank.get(i).getMoney()));
            mon.setFont(new Font("Tahoma",30));
            mon.setEffect(ds);
            mon.setFill(Color.WHITE);
            money[i+1] = new HBox(10);
            money[i+1].setAlignment(Pos.CENTER);
            money[i+1].getChildren().add(mon);
            
            Text total = new Text(String.valueOf(listRank.get(i).getTotalTime()));
            total.setFont(new Font("Tahoma",30));
            total.setEffect(ds);
            total.setFill(Color.WHITE);
            totalTime[i+1] = new HBox(10);
            totalTime[i+1].setAlignment(Pos.CENTER);
            totalTime[i+1].getChildren().add(total);
            
            grid.add(rank[i+1],0, i+2 );
            grid.add(name[i+1],1, i+2 );
            grid.add(money[i+1],2, i+2 );
            grid.add(totalTime[i+1],3, i+2 );
            
        }
        
        
        Button back = new Button("Quay lại");
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
                new listButtonOpen(root);
            }
        });
    }
}
