/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.manager;

import DBModel.Player;
import DBModel.Question;
import ui.frLogin;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import ui.listButtonLogged;


/**
 *
 * @author nhats
 */
public class frManager extends frLogin{
  
    static protected HBox content;
    protected ObservableList<DBModel.Question> masterDataQuestion;
    static protected ObservableList<DBModel.Player> masterDataPlayer;
    protected SimpleIntegerProperty countPlayer, countQuestion;
    protected Label lblCountQ, lblCountP;
    protected DBModel.Player rootPlayer;
    public frManager(Pane root, DBModel.Player player) {
        super(root, player);
        init(player);
    }
    protected void setRootPlayer(Player player){
        this.rootPlayer = player;
    }

    public static ObservableList<Player> getMasterDataPlayer() {
        return masterDataPlayer;
    }
    
    
    protected void installBoxCount(){
        countPlayer = new SimpleIntegerProperty(masterDataPlayer.size());
        countQuestion = new SimpleIntegerProperty(masterDataQuestion.size());
        lblCountQ = new Label("Số câu hỏi: "+countQuestion.getValue());
        lblCountP = new Label("Số người chơi: "+countPlayer.getValue());     

        countPlayer.addListener(new ChangeListener<Number>() {
            
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                 lblCountP = new Label("Số người chơi: "+t1);
            }
        });
        countQuestion.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                lblCountQ = new Label("Số câu hỏi: "+t1);
            }
        });
        
    }
    protected void setContent(){
        if(frManager.content==null)
            frManager.content = new HBox();
        frManager.content.setPrefSize(root.getWidth()*0.8,root.getHeight()-100);
        frManager.content.setAlignment(Pos.CENTER);
    }
    public void init(DBModel.Player player) {
        root.getChildren().clear();
        setRootPlayer(player);
        setContent();
        masterDataQuestion = FXCollections.observableArrayList(new Question().getData());
        masterDataPlayer = FXCollections.observableArrayList(new Player().getData());
        installBoxCount();
        final BorderPane border = new BorderPane();
        HBox banner = new HBox();
        banner.setPrefWidth(root.getWidth());
        Image bannerImage = new Image(getClass().getResource("/images/adminTop.png").toString());
        ImageView iv = new ImageView(bannerImage);
        iv.setFitHeight(100);
        iv.setFitWidth(800);
        banner.setAlignment(Pos.CENTER);
        banner.getChildren().add(iv);
        VBox slideBar = new VBox();
        
        slideBar.setPrefWidth(root.getWidth()*0.2);
        

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
        

        
        ArrayList<Button> btnGroup = new ArrayList<Button>();
        
        Button btnUsers = new Button("Người chơi");
        Button btnQuest = new Button("Câu hỏi");
        Button btnMoney = new Button("Tiền thưởng");
        Button btnInfo = new Button("Thông tin ID");
        Button btnReport = new Button("Thống kê");
        Button btnExit = new Button("Quay lại");
        if(player.isAdmin()){
            btnGroup.add(btnUsers);
            btnGroup.add(btnQuest);
            btnGroup.add(btnMoney);
            btnGroup.add(btnReport);
        }
        btnGroup.add(btnInfo);
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
        btnUsers.setOnMouseClicked((MouseEvent event) -> {
            new frPlayer(root,content,rootPlayer,masterDataPlayer);
        });
        btnExit.setOnMouseClicked((MouseEvent event) -> {
            new listButtonLogged(root,rootPlayer);
        });
        btnQuest.setOnMouseClicked((MouseEvent event) -> {
            new frQuestion(root,content,rootPlayer,masterDataQuestion);
        });
        btnInfo.setOnMouseClicked((MouseEvent event) -> {
            content.getChildren().clear();
            new frPlayerUpdate(root,content,rootPlayer,rootPlayer);
        });
        btnMoney.setOnMouseClicked((MouseEvent event) -> {
            content.getChildren().clear();
            new frPrizeMoney(content);
        });
        btnReport.setOnAction((ActionEvent event)->{
            new frReport(root, content);
        });
        root.getChildren().add(border);
        

        lblCountQ.setFont(new Font("Arial",20));
        lblCountQ.setTextFill(Color.web("#fff"));
        
        lblCountP.setFont(new Font("Arial",20));
        lblCountP.setTextFill(Color.web("#fff"));
        
        
        VBox boxCount = new VBox(10);
        boxCount.setAlignment(Pos.BOTTOM_CENTER);     
        boxCount.getChildren().addAll(lblCountQ,lblCountP);
        
        boxCount.setVisible(rootPlayer.isAdmin());

        slideBar.getChildren().addAll(boxTitle,listButton,boxCount);
        border.setTop(banner);
        border.setLeft(slideBar);
        border.setRight(content);
        BorderPane.setAlignment(banner,Pos.CENTER);
        BorderPane.setAlignment(content,Pos.CENTER);
        BorderPane.setAlignment(slideBar,Pos.CENTER);
    }

    protected void setMasterDataPlayer(ObservableList<Player> masterDataPlayer) {
        frManager.masterDataPlayer = FXCollections.observableArrayList();
        frManager.masterDataPlayer.addAll(masterDataPlayer);
    }

    protected void setMasterDataQuestion(ObservableList<Question> masterDataQuestion) {
        this.masterDataQuestion = FXCollections.observableArrayList();
        this.masterDataQuestion.addAll(masterDataQuestion);
    }

}
