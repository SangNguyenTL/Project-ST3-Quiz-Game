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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    openSound hoverButton  = new openSound("sounds/hoverButton.mp3",500);
    public frPlaygame(Pane root) {
        listLevel = new VBox();
        listLevel.setPrefWidth(250);
        listLevel.getChildren().addAll(listBtnHelp(),listLevel());
        BorderPane border = new BorderPane();
        VBox boxQuest = new VBox();
        boxQuest.setPadding(new Insets(50));
        boxQuest.setPrefWidth(root.getWidth()-250);
        border.setRight(listLevel);
        VBox boxTextQuest = new VBox();
        Text textQuest = new Text("Câu hỏi abcdef là một dãy ký tự gọi là alphatest... là đúng hay sai?");
        textQuest.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        textQuest.setFill(Paint.valueOf("#FFF"));
        boxTextQuest.getChildren().add(textQuest);
        boxTextQuest.setPadding(new Insets(20));
        boxTextQuest.setId("boxTextQuest");
        
        GridPane boxListBtnAns = new GridPane();
        boxListBtnAns.setPadding(new Insets(10));
        boxListBtnAns.setAlignment(Pos.CENTER);
        boxListBtnAns.setHgap(20);
        boxListBtnAns.setVgap(20);
        Button btnA = new Button("A. Đúng");
        setEffectButton(btnA);
        Button btnB = new Button("B. Sai");
        setEffectButton(btnB);
        Button btnC = new Button("C. Gần đúng");
        setEffectButton(btnC);
        Button btnD = new Button("D. Gần sai");
        setEffectButton(btnD);
        boxListBtnAns.add(btnA,0,0);
        boxListBtnAns.add(btnB,1,0);
        boxListBtnAns.add(btnC,0,1);
        boxListBtnAns.add(btnD,1,1);
        BorderPane boxTime = new BorderPane();
        boxTime.setPadding(new Insets(50));
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#fff"));
        
        ImageView imgMain = new ImageView(new Image(getClass().getResource("/images/mainQuest.png").toString()));
        imgMain.setFitHeight(300);
        imgMain.setFitWidth(300);
        imgMain.setEffect(ds);
        VBox boxImgMain = new VBox(imgMain);
        boxImgMain.setPrefWidth((boxQuest.getPrefWidth()/100)*33);
        boxImgMain.setAlignment(Pos.BOTTOM_RIGHT);
        
        VBox boxBoxTime = new VBox();
        boxBoxTime.setAlignment(Pos.CENTER);
        boxBoxTime.setPrefWidth((boxQuest.getPrefWidth()/100)*33);

        Text textVs = new Text("VS");
        textVs.setEffect(ds);
        textVs.setFont(Font.font("Tahoma", FontWeight.BLACK, 30));
        textVs.setFill(Paint.valueOf("#ff0000"));
        textVs.setStroke(Paint.valueOf("#FFF"));
        textVs.setStrokeWidth(1);
        boxBoxTime.getChildren().add(textVs);
        
        VBox boxPlayer = new VBox();
        boxPlayer.setPrefWidth((boxQuest.getPrefWidth()/100)*33);
        boxPlayer.setAlignment(Pos.BOTTOM_RIGHT);
        
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
        button.getStyleClass().add("btnCus");
            button.setMinWidth(250);
            button.setMinHeight(60);
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
            oneLevel.setText(Integer.toString(one.getMoney()));
            oneLevel.getStyleClass().addAll("btnCus","text-warning");
            oneLevel.setMinWidth(162);
            oneLevel.setMinHeight(42);
            oneLevel.setId("level-"+one.getPriId());
            oneLevel.setAlignment(Pos.CENTER);
            oneLevel.setTextAlignment(TextAlignment.CENTER);
            boxListLevel.getChildren().add(oneLevel);
        }
        return boxListLevel;
    }
    
    public GridPane listBtnHelp(){
        GridPane listBtnHelp = new GridPane();
        listBtnHelp.setPadding(new Insets(15));
        listBtnHelp.setHgap(10);
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
            allButton.get(i).setMinWidth(50);
            allButton.get(i).setMinHeight(50);
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
