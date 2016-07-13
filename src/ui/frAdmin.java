/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    public frAdmin(Pane root) {
        final BorderPane border = new BorderPane();
        HBox banner = new HBox();
        banner.setPrefSize(root.getPrefWidth(), 110);
        
        Image bannerImage = new Image(getClass().getResource("/images/adminTop.png").toString());
        ImageView iv = new ImageView(bannerImage);
        banner.setAlignment(Pos.CENTER);
        banner.getChildren().add(iv);
        VBox slideBar = new VBox();
        
        slideBar.setPrefWidth((root.getPrefWidth()/100)*35);
        content.setPrefWidth((root.getPrefWidth()/100)*65);
        GridPane listButton = new GridPane();
        listButton.setPadding(new Insets(10));
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
        
        Text blank1 = new Text("");
        Text blank2 = new Text("");
        Text blank3 = new Text("");

        
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
        btnExit.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
               root.getChildren().clear();
               new login(root);
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
        root.getChildren().add(border);
        slideBar.getChildren().addAll(boxTitle,blank1,blank2,blank3,listButton);
        border.setTop(banner);
        border.setLeft(slideBar);
        border.setRight(content);
        border.setAlignment(banner,Pos.CENTER);
        border.setAlignment(content,Pos.CENTER);
        border.setAlignment(slideBar,Pos.CENTER);
    }
        
}
