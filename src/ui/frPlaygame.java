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
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lib.openSound;

/**
 *
 * @author nhats
 */
public class frPlaygame {
    private VBox listLevel;
    private openSound hoverButton  = new openSound("sounds/hoverButton.mp3",500);
    private Double sizeRoot;
    public frPlaygame(Pane root) {
        sizeRoot = root.getWidth();
        listLevel = new VBox();
        listLevel.setMaxWidth(root.getWidth()*0.3);
        listLevel.getChildren().addAll(listBtnHelp(),listLevel());
        BorderPane border = new BorderPane();
        VBox boxQuest = new VBox();
        boxQuest.setPadding(new Insets(50,0,50,0));
        boxQuest.setMaxWidth(root.getWidth()*0.7);
        border.setRight(listLevel);
        VBox boxTextQuest = new VBox();
        Text textQuest = new Text("Câu hỏi abcdef là một dãy ký tự gọi là alphatest... là đúng hay sai?");
        textQuest.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        textQuest.setFill(Paint.valueOf("#FFF"));
        boxTextQuest.getChildren().add(textQuest);
        boxTextQuest.setPadding(new Insets(sizeRoot/45.5,sizeRoot/17.075,sizeRoot/45.5,sizeRoot/17.075));
        boxTextQuest.setMaxHeight(this.sizeRoot/11.38);
        boxTextQuest.setMinHeight(this.sizeRoot/11.38);
        boxTextQuest.setPrefHeight(this.sizeRoot/11.38);
        boxTextQuest.setId("boxTextQuest");
        
        GridPane boxListBtnAns = new GridPane();
        boxListBtnAns.setPadding(new Insets(sizeRoot/136.6,0,sizeRoot/136.6,0));
        boxListBtnAns.setAlignment(Pos.CENTER);
        boxListBtnAns.setVgap(20);
        Button btnA = new Button("Đúng");
        btnA.setId("btnA");
        setEffectButton(btnA);
        Button btnB = new Button("Sai");
        setEffectButton(btnB);
        btnB.setId("btnB");
        Button btnC = new Button("Gần đúng");
        setEffectButton(btnC);
        btnC.setId("btnC");
        Button btnD = new Button("Gần sai");
        setEffectButton(btnD);
        btnD.setId("btnD");
        boxListBtnAns.add(btnA,0,0);
        boxListBtnAns.add(btnB,1,0);
        boxListBtnAns.add(btnC,0,1);
        boxListBtnAns.add(btnD,1,1);
        BorderPane boxTime = new BorderPane();
        boxTime.setPadding(new Insets(sizeRoot/68.3));
        boxTime.setPrefHeight(sizeRoot/5.464);
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#fff"));
        
        ImageView imgMain = new ImageView(new Image(getClass().getResource("/images/mainQuest.png").toString()));
        imgMain.setFitHeight(sizeRoot/4.55);
        imgMain.setFitWidth(sizeRoot/4.55);
        imgMain.setEffect(ds);
        
        ImageView imgPlayer = new ImageView(new Image(getClass().getResource("/images/player.png").toString()));
        imgPlayer.setFitHeight(sizeRoot/4.55);
        imgPlayer.setFitWidth(sizeRoot/4.55);
        imgPlayer.setEffect(ds);
        
        VBox boxImgMain = new VBox(imgMain);
        boxImgMain.setPrefWidth(boxQuest.getPrefWidth()/3);
        boxImgMain.setAlignment(Pos.BOTTOM_RIGHT);
        
        GridPane boxBoxTime = new GridPane();
        boxBoxTime.setAlignment(Pos.CENTER);
        boxBoxTime.setVgap(10);
        boxBoxTime.setPrefWidth(boxQuest.getPrefWidth()/3);

        Text textVs = new Text("VS");
        textVs.setEffect(ds);
        textVs.setFont(Font.font("Tahoma", FontWeight.BLACK, sizeRoot/45.55));
        textVs.setFill(Paint.valueOf("#ff0000"));
        textVs.setStroke(Paint.valueOf("#FFF"));
        textVs.setStrokeWidth(1);
        HBox boxttextVs = new HBox(textVs);
        boxttextVs.setPadding(new Insets(10));
        boxttextVs.setAlignment(Pos.CENTER);
        boxttextVs.setMaxSize(this.sizeRoot/21.015, this.sizeRoot/21.015);
        
        Text numTime = new Text("60");
        numTime.setEffect(ds);
        numTime.setFont(Font.font("Tahoma", FontWeight.BLACK, sizeRoot/45.55));
        numTime.setFill(Paint.valueOf("#fff"));
        numTime.setStroke(Paint.valueOf("#FFF"));
        numTime.setStrokeWidth(1);
        
        HBox boxtCountTime = new HBox(numTime);
        boxtCountTime.setId("boxtCountTime");
        boxtCountTime.setAlignment(Pos.CENTER);
        boxtCountTime.setMaxSize(this.sizeRoot/21.015, this.sizeRoot/21.015);
        
        boxBoxTime.add(boxttextVs,0,0);
        boxBoxTime.add(boxtCountTime,0,1);
        
        VBox boxPlayer = new VBox(imgPlayer);
        boxPlayer.setPrefWidth(boxQuest.getPrefWidth()/3);
        boxPlayer.setAlignment(Pos.BOTTOM_LEFT);
        
        boxTime.setRight(boxImgMain);
        boxTime.setCenter(boxBoxTime);
        boxTime.setLeft(boxPlayer);
        
        boxQuest.getChildren().add(boxTime);
        boxQuest.getChildren().add(boxTextQuest);
        boxQuest.getChildren().add(boxListBtnAns);
        border.setLeft(boxQuest);
        root.getChildren().add(border);
    }
    public void setEffectButton(Button button){
            button.setMinWidth(this.sizeRoot/2.449);
            button.setMinHeight(this.sizeRoot/20.08);
            button.setFont(Font.font("Tahoma", FontWeight.MEDIUM, this.sizeRoot/68.8));
            button.setOnMouseEntered(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                hoverButton.play();
            }
        });
            button.setOnMouseExited(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                hoverButton.stopSound();
            }
        });
    }
    public VBox listLevel(){
        VBox boxListLevel = new VBox();
        boxListLevel.setAlignment(Pos.CENTER);
        for(DBModel.PrizeMoney one : new DBModel.PrizeMoney().getData()){
            Label oneLevel = new Label();
            oneLevel.setText(one.getMoney());
            oneLevel.getStyleClass().addAll("btnCus","text-warning");
            oneLevel.setMinWidth(this.sizeRoot/8.43);
            oneLevel.setMinHeight(this.sizeRoot/32.523);
            oneLevel.setId("level-"+one.getPriId());
            oneLevel.setAlignment(Pos.CENTER);
            oneLevel.setTextAlignment(TextAlignment.CENTER);
            boxListLevel.getChildren().add(oneLevel);
        }
        return boxListLevel;
    }
    
    public GridPane listBtnHelp(){
        GridPane listBtnHelp = new GridPane();
        listBtnHelp.setPadding(new Insets(this.sizeRoot/91.06));
        listBtnHelp.setHgap(this.sizeRoot/136.6);
        listBtnHelp.setAlignment(Pos.CENTER);
        Button btnPer = new Button();
        btnPer.setId("btnper50");
        Button btnChange = new Button();
        btnChange.setId("btnchangeQuiz");
        Button btnStop = new Button();
        btnStop.setId("btnstop");

        ArrayList<Button> allButton = new ArrayList();
        allButton.add(btnPer);
        allButton.add(btnChange);
        allButton.add(btnStop);
        
        for(int i=0;i<allButton.size();i++){
            listBtnHelp.add(allButton.get(i),i,0);
            allButton.get(i).setMinWidth(this.sizeRoot/27.2);
            allButton.get(i).setMinHeight(this.sizeRoot/27.2);
            allButton.get(i).setOnMouseEntered(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                hoverButton.play();
            }
        });
            allButton.get(i).setOnMouseExited(new EventHandler<MouseEvent>
        () {

            @Override
            public void handle(MouseEvent t) {
                hoverButton.stopSound();
            }
        });
        }
        return listBtnHelp;
    }
}
