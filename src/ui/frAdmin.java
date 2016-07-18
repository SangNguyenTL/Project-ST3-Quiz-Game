/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lib.openSound;


/**
 *
 * @author nhats
 */
public class frAdmin{
    HBox content = new HBox(10);  
    
    private int countP,countQ;
    public frAdmin(Pane root) {
        final BorderPane border = new BorderPane();
        HBox banner = new HBox();
        banner.setPrefSize(root.getWidth(), root.getHeight()*0.08);
        
        Image bannerImage = new Image(getClass().getResource("/images/adminTop.png").toString());
        ImageView iv = new ImageView(bannerImage);
        banner.setAlignment(Pos.CENTER);
        banner.getChildren().add(iv);
        VBox slideBar = new VBox();
        
        slideBar.setMinWidth(root.getWidth()*0.2);
        content.setMinWidth(root.getWidth()*0.8);
        content.setAlignment(Pos.CENTER);
        GridPane listButton = new GridPane();
        listButton.setPadding(new Insets(10));
        listButton.setVgap(10);
        listButton.setAlignment(Pos.CENTER);
        
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));
        
        Text scenetitle = new Text("Quản lý");
        
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 35));
        scenetitle.setEffect(ds);
        scenetitle.setFill(Color.WHITE);
        
        HBox boxTitle = new HBox(10);
        boxTitle.setAlignment(Pos.CENTER);
        boxTitle.getChildren().add(scenetitle);
        

        
        ArrayList<Button> btnGroup = new ArrayList();
        
        Button btnUsers = new Button("Người chơi");
        Button btnQuest = new Button("Câu hỏi");
        Button btnMoney = new Button("Tiền thưởng");
        Button btnPass = new Button("Password");
        Button btnExit = new Button("Quay lại");
        
        btnGroup.add(btnUsers);
        btnGroup.add(btnQuest);
        btnGroup.add(btnMoney);
        btnGroup.add(btnPass);
        btnGroup.add(btnExit);
        openSound hoverButton  = new openSound("sounds/hoverButton.mp3",500);
        for(int i = 0; i< btnGroup.size();i++){
            listButton.add(btnGroup.get(i), 0, i);
            btnGroup.get(i).getStyleClass().add("btnCus");
            btnGroup.get(i).setMinWidth(162);
            btnGroup.get(i).setMinHeight(42);
            btnGroup.get(i).setOnMouseEntered(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                hoverButton.play();
            }
        });
            btnGroup.get(i).setOnMouseExited(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                hoverButton.stopSound();
            }
        });
        }
        btnUsers.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               content.getChildren().clear();
               new FrPlayer(content);
            }
            
        });
        btnExit.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
               root.getChildren().clear();
               new listButtonLogged(root);
            }
            
        });
        btnQuest.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                content.getChildren().clear();
                new FrQuestion(content);
            }
            
        });
        btnPass.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                content.getChildren().clear();
                new FrChangePass(content);
            }
        
        });
        btnMoney.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                content.getChildren().clear();
                new FrPrizeMoney(content);
            }
            
        });
        root.getChildren().add(border);
        
        Label blank = new Label("");
        countQ = new DBModel.Question().getNumAllRow();
        
        Label lblCountQ = new Label("Số câu hỏi: "+countQ);
        lblCountQ.setFont(new Font("Arial",20));
        lblCountQ.setTextFill(Color.web("#fff"));
        
        Label lblCountP = new Label("Số người chơi: ");
        lblCountP.setFont(new Font("Arial",20));
        lblCountP.setTextFill(Color.web("#fff"));
        
        
        VBox boxCount = new VBox(10);
        boxCount.setAlignment(Pos.BOTTOM_CENTER);     
        boxCount.getChildren().addAll(blank,lblCountQ,lblCountP);

        slideBar.getChildren().addAll(boxTitle,listButton,boxCount);
        border.setTop(banner);
        border.setLeft(slideBar);
        border.setRight(content);
        border.setAlignment(banner,Pos.CENTER);
        border.setAlignment(content,Pos.CENTER);
        border.setAlignment(slideBar,Pos.CENTER);
    }
        
}
