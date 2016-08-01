/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import DBModel.Question;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.function.Consumer;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import lib.openSound;
import lib.openVideo;
/**
 *
 * @author nhats
 */
public class frPlaygame {

    private VBox listLevel;
    private VBox boxQuest;
    private Double sizeRoot;
    private DBModel.Player player;
    private DBModel.statusGame newGame;
    private ArrayList<DBModel.Question> listQuestion;
    private ArrayList<DBModel.Question> listSorted;
    private Text numTime;
    private SimpleIntegerProperty time;
    private Label textQuest;
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;
    private GridPane boxListBtnAns;
    private ArrayList<Button> listButton;
    private Timeline timeLineQuest;
    private Timeline timeLineGame;
    private Timeline timeLineAns;
    private VBox boxListLevel;
    private ArrayList<DBModel.Answer> listAns;
    private SimpleIntegerProperty statusGame;
    private BorderPane border;
    private Button btnPer;
    private Button btnChange;
    private Button btnStop;
    private openSound soundGame;
    private openSound soundClap;
    private openSound soundIsCorrect;
    private openSound soundIsNotCorrect;
    private openSound soundWaitResult;
    private openSound soundNextQuest;
    private openSound soundWinner;
    private openSound soundPer50;
    private openSound soundFail;
    private openSound processSound;
    static private openVideo videoWinner;
    private SimpleStringProperty mess;
    private Label txtMess;
    private DropShadow ds;
    private Pane root;
    private DropShadow dsB;

    public static openVideo getVideoWinner() {
        return videoWinner;
    }
    
    public frPlaygame(Pane root) {
        player = frLogin.getPlayer();
        installUi(root);
        installGame();
        this.root = root;
    }

    public BorderPane getBorder() {
        return border;
    }

    public void setStatusGame(int num) {
        this.statusGame.set(num);
    }

    public void setPlaySoundGame() {
        soundGame.play();
        soundClap.play();
    }

    protected void installGame() {

        soundClap = new openSound("sounds/clap.mp3");
        soundClap.getMediaPlayer().setStopTime(Duration.seconds(7));
        soundClap.getMediaPlayer().setOnEndOfMedia(() -> {
            soundClap.stopSound();
        });
        soundIsCorrect = new openSound("sounds/isCorrect.mp3");
        soundIsCorrect.getMediaPlayer().setStopTime(Duration.seconds(3));
        soundIsNotCorrect = new openSound("sounds/isnotCorrect.mp3");
        soundWaitResult = new openSound("sounds/checkAns.mp3");
        soundWaitResult.getMediaPlayer().setStopTime(Duration.seconds(3));
        soundPer50 = new openSound("sounds/50-50.mp3");
        soundNextQuest = new openSound("sounds/nextQuest.mp3");
        soundNextQuest.getMediaPlayer().setStopTime(Duration.seconds(6));
        soundFail = new openSound("sounds/fail.mp3");
        soundWinner = new openSound("sounds/winner.mp3");
        processSound = new openSound("sounds/process.mp3");
        processSound.getMediaPlayer().setStopTime(Duration.seconds(5));
        soundPer50.getMediaPlayer().setOnEndOfMedia(()->{
            soundPer50.stopSound();
            soundGame.play();
            
        });
        soundWinner.getMediaPlayer().setOnEndOfMedia(()->{
            soundWinner.stopSound();
        });
        soundNextQuest.getMediaPlayer().setOnEndOfMedia(() -> {
            soundNextQuest.stopSound();
            soundGame.play();
            statusGame.set(2);
        });
        soundIsCorrect.getMediaPlayer().setOnEndOfMedia(() -> {
            if(newGame.getLevelQuestion() == 14){
                mess.set("Chúc mừng bạn đã vượt qua 15 câu hỏi. Và bạn đã là người chiến thắng.");
                statusGame.set(3);
            }else{
                mess.set("Chúc mừng bạn đã trả lời chình xác. Hãy chuẩn bị để sang câu tiếp theo");
                soundIsCorrect.stopSound();
                textQuest.setText("");

                installButtonAns();

                root.getStylesheets().retainAll();
                newGame.setLevelQuestion(newGame.getLevelQuestion() + 1);
                soundNextQuest.play();
            }
        });
        soundIsNotCorrect.getMediaPlayer().setOnEndOfMedia(() -> {
            soundFail.play();
            statusGame.set(3);
        });
        videoWinner = new openVideo("video/winner.mp4", Window.getPrimaryStage().getWidth(), Window.getPrimaryStage().getHeight());
        videoWinner.getMediaPlayer().setOnEndOfMedia(()->{
            videoWinner.getMediaPlayer().stop();
        });
        
        time = new SimpleIntegerProperty();
        // KHởi tạo game với người chơi, cấp độ, cơ hội đổi câu hỏi, cơ hội bỏ đi 2 phương án sai, số thời gian, số tiền.
        newGame = new DBModel.statusGame(player, 0, true, true, 0, 0);
        // Lấy ra 4 câu hỏi cấp độ 1 thuộc 4 chủ đề và sắp xếp ngẫu nhiên
        listQuestion = new DBModel.Question().getRadomQues(1,4);
        // Lấy tiếp 2 câu cấp độ 1
        int count = 0;
        do{
            ArrayList<Question> reQuestion = new DBModel.Question().getRadomQues(1,4);
            for(int i = 0; i < reQuestion.size() ; i++){
                // KIểm tra xem trong danh sách trc đó có 2 câu này chưa, chưa có thì thêm vào
                //System.out.println(!listQuestion.contains(reQuestion.get(i)));
                if(!listQuestion.contains(reQuestion.get(i))){
                    listQuestion.add(reQuestion.get(i));
                    // Tăng biến đếm lên khi nào bằng 2 thì out vòng lặp.
                    ++count;
                    if(count == 2) break;
                }
            }
        }while(count < 2);
        
        // Lấy ra 4 câu hỏi cấp độ 2 thuộc 4 chủ đề và sắp xếp ngẫu nhiên
        listQuestion.addAll(new DBModel.Question().getRadomQues(2,4));
        // KHởi tạo biến đếm bằng 0;
        count = 0;
        do{
            ArrayList<Question> reQuestion = new DBModel.Question().getRadomQues(2,4);
            for(int i = 0; i < reQuestion.size() ; i++){
                 // KIểm tra xem trong danh sách trc đó có 2 câu này chưa, chưa có thì thêm vào
                //System.out.println(!listQuestion.contains(reQuestion.get(i)));
                if(!listQuestion.contains(reQuestion.get(i))){
                    
                    listQuestion.add(reQuestion.get(i));
                    // Tăng biến đếm lên khi nào bằng 2 thì out vòng lặp.
                    ++count;
                    if(count == 2) break;
                }
            }
        }while(count < 2);
        // Thêm 3 câu cấp độ 3 và 2 câu cấp độ 4
        listQuestion.addAll(new DBModel.Question().getRadomQues(3,3));
        listQuestion.addAll(new DBModel.Question().getRadomQues(4,2));
        // Lấy ra 5 câu cấp độ 1, 5 câu cấp độ 2, 3 câu cấp độ 3 và 2 câu cấp độ 4, chừa lại vị trí 5 và 11 để dành cho trợ giúp đổi câu hỏi
        listSorted = new ArrayList<Question>();
        for (int i = 0; i < listQuestion.size(); i++) {
            Question checkQ = listQuestion.get(i);
            if (i < 5 && checkQ.getLevel() == 1) {
                listSorted.add(checkQ); // 0-4
            } else if (i > 5 && i < 11 && checkQ.getLevel() == 2) {
                listSorted.add(checkQ); // 6-10
            } else if (i > 11 && i < 15 && checkQ.getLevel() == 3) {
                listSorted.add(checkQ); //12-14
            } else if ( i > 14 && checkQ.getLevel() == 4) {
                listSorted.add(checkQ); //14-16
            }
        }
        //Tắt các sự trợ giúp cho tới khi vượt qua câu 5
        btnChange.setDisable(true);
        btnPer.setDisable(true);
        
        lib.textAnimation textAnimationGame = new lib.textAnimation();
        statusGame = new SimpleIntegerProperty(1);
        statusGame.addListener((ObservableValue<? extends Number> ov, Number t, Number t1) -> {
            if (t1.intValue() == 2) {
                if(newGame.getLevelQuestion() > 12){
                    soundWaitResult.getMediaPlayer().setStopTime(Duration.millis(7000));
                }
                if(newGame.getLevelQuestion() > 11){
                    soundWaitResult.getMediaPlayer().setStopTime(Duration.millis(6000));
                }
                if(newGame.getLevelQuestion() > 10){
                    soundWaitResult.getMediaPlayer().setStopTime(Duration.millis(5000));
                }
                if(newGame.getLevelQuestion() > 9){
                    soundGame.stopSound();
                    soundGame.getMediaPlayer().dispose();
                    soundWaitResult.getMediaPlayer().setStopTime(Duration.millis(4000));
                    soundGame = new openSound("sounds/questLast.mp3");
                    soundGame.stopSound();
                    soundGame.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
                    soundGame.play();
                    
                }else if (newGame.getLevelQuestion() > 4) {
                    soundGame.stopSound();
                    soundGame.getMediaPlayer().dispose();
                    soundGame = new openSound("sounds/questMidle.mp3");
                    soundGame.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
                    soundGame.play();
                    soundWaitResult.getMediaPlayer().setStopTime(Duration.millis(3500));
                    if (newGame.isChangeQuest()) {
                        btnChange.setDisable(false);
                    }
                    if (newGame.isPer50()) {
                        btnPer.setDisable(false);
                    }
                }
                // Bắt đầu game với nhạc game.
                if(newGame.getLevelQuestion() == 0){
                    //sound
                    soundGame = new openSound("sounds/soundOpenGame.mp3");
                    soundGame.getMediaPlayer().setStopTime(Duration.seconds(52));
                    soundGame.getMediaPlayer().setOnEndOfMedia(()->{
                        soundGame.stopSound();
                        soundGame.play();
                    });
                    soundGame.getMediaPlayer().setOnStopped(()->{
                        soundGame.getMediaPlayer().setStartTime(Duration.seconds(6));
                    });
                    soundGame.play();
                    soundClap.play();
                }
                // Đánh dấu câu hỏi đang trả lời
                lib.textAnimation textAnimationQuest = new lib.textAnimation();
                for (int i = boxListLevel.getChildren().size() - 1; i >= 0; i--) {
                    Label one = (Label) boxListLevel.getChildren().get(i);
                    one.setDisable(false);
                    if (one.getId().equals("level-" + newGame.getLevelQuestion())) {
                        one.setDisable(true);
                        break;
                    }
                }
                // Lấy câu hỏi
                textAnimationQuest.setAnimation(textQuest, newGame.getLevelQuestion()+1+". "+listSorted.get(newGame.getLevelQuestion()).quesContent, () -> {
                    listAns = new DBModel.Answer().getData(listSorted.get(newGame.getLevelQuestion()).quesId);
                    long seed = System.nanoTime();
                    Collections.shuffle(listAns, new Random(seed));
                    listButton = new ArrayList<>();
                    listButton.add(btnA);
                    listButton.add(btnB);
                    listButton.add(btnC);
                    listButton.add(btnD);
                    // Lấy danh sachsh câu trả lời
                    for (int i = 0; i < listButton.size(); i++) {
                        if (i < 4) {
                            lib.textAnimation textAnimationAns = new lib.textAnimation();
                            // bỏ cờ đúng
                            listButton.get(i).getStyleClass().remove("isCorrect");
                            //bỏ class kiểm tra số lượng câu trả lời đc ấn.
                            listButton.get(i).getStyleClass().remove("disable");
                            if (listAns.get(i) != null) {
                                //nếu đúng thì gắn cờ cho nó
                                if (listAns.get(i).isCorrect) {
                                    listButton.get(i).getStyleClass().add("isCorrect");
                                }
                                //Thêm hiệu ứng hiện câu trả lời
                                timeLineAns = textAnimationAns.setAnimation(listButton.get(i), listAns.get(i).getAnsContent());
                            }
                            //Tắt vô hiệu hóa nút trả lời
                            listButton.get(i).setDisable(false);
                        }
                    }
                });
                timeLineQuest = textAnimationQuest.getTimeline();
                // Đưa về mặc định là 1 đang trả lời
                statusGame.set(1);
                // thời gian trả lời là 60s
                time.set(60);
                // đưa vào bộ đếm ngược
                timeLineGame = textAnimationGame.setAnimation(numTime, time);
            }
            if (t1.intValue() == 3) {
                soundGame.stopSound();
                if(newGame.getLevelQuestion()!=14){
                    soundWinner = new lib.openSound("sounds/fail.mp3");
                    soundWinner.play();
                    root.getChildren().clear();
                }else{
                    videoWinner.star();
                    soundWinner.play();
                    root.getChildren().clear();
                    root.getChildren().add(videoWinner.getMediaView());
                }
                new frResult(root, player, newGame);
            }
        });

        time.addListener((ObservableValue<? extends Number> ov, Number t, Number t1) -> {
            switch (t1.intValue()) {
                case 57:
                    if (!btnChange.isDisable() && !btnPer.isDisable()) {
                        mess.set("Bạn còn cả 2 sự trợ giúp hãy cân nhắc sử dụng hợp lý");
                    }
                    if (btnChange.isDisable() && btnPer.isDisable() && newGame.getLevelQuestion() < 5) {
                        mess.set("Vượt qua câu số 5 bạn sẽ có 2 sự trợ giúp. Ngược lại bạn sẽ không nhận được gì nếu dừng cuộc chơi.");
                    }
                    if (!btnChange.isDisable() && btnPer.isDisable()) {
                        mess.set("Bạn còn một quyền trợ giúp duy nhất là \"Đổi câu hỏi\". Hãy cân nhắc!");
                    }
                    if (btnChange.isDisable() && !btnPer.isDisable()) {
                        mess.set("Bạn còn một quyền trợ giúp duy nhất là \"Bỏ đi hai phương án sai\". Hãy cân nhắc!");
                    }
                    break;
                case 45:
                    if (!btnPer.isDisable()) {
                        mess.set("Bỏ đi hai phương án sai sẽ giúp bạn tăng thêm cơ hội trả lời đúng. Từ 25% lên 50%, rất là hiệu quả.");
                    }
                    break;
                case 35:
                    if (!btnChange.isDisable()) {
                        mess.set("Chọn đổi câu hỏi bạn sẽ được nhận một câu hỏi dễ hơn. Chọn ngay nếu thấy câu hiện tại quá khó.");
                    }
                    break;
                case 25:
                    if (!btnChange.isDisable() && !btnPer.isDisable() && newGame.getLevelQuestion() > 5) {
                        mess.set("Bạn đã hết quyền trợ giúp rồi, dừng lại đúng lúc cũng là một cách hay.");
                    }
                    break;
                case 10:
                    mess.set("Gần hết giờ rồi mau đưa ra sự lựa chọn của bạn đi.");
                    break;
                case 0:
                    timeLineGame.stop();
                    mess.set("Tiếc quá hết giờ mất rồi.");
                    btnStop.setDisable(true);
                    btnChange.setDisable(true);
                    btnPer.setDisable(true);
                    if (newGame.getLevelQuestion() > 4 && newGame.getLevelQuestion() < 9) {
                        newGame.setMoney(getCurrentMoney(9));
                        newGame.setTimeTotal(newGame.getTimeTotal4());
                    } else if (newGame.getLevelQuestion() > 9) {
                        newGame.setMoney(getCurrentMoney(4));
                        newGame.setTimeTotal(newGame.getTimeTotal9());
                    } else {
                        newGame.setMoney(0);
                        newGame.setTimeTotal(0);
                    }
                    statusGame.setValue(3);
                    break;
            }
        });

    }

    protected void installUi(Pane root) {
        mess = new SimpleStringProperty("Chào mừng bạn đến với chương trình 'Ai là triệu phú'.");
        mess.addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            new lib.textAnimation().setAnimation(txtMess, t1, () -> {
            });
        });
        //hieu ung
        ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#fff"));
        dsB = new DropShadow();
        dsB.setOffsetY(3.0f);
        dsB.setColor(Color.BLACK);

        // Khung chua giao dien choi game
        border = new BorderPane();
        border.getStyleClass().add("playGame");
        sizeRoot = root.getWidth();

        listLevel = new VBox();
        listLevel.setPrefWidth(root.getWidth() * 0.15);
        listLevel.getChildren().addAll(this.listLevel());
        listLevel.setPrefHeight(root.getHeight());
        listLevel.setAlignment(Pos.CENTER);
        boxQuest = new VBox();
        boxQuest.setMaxWidth(root.getWidth() * 0.85);
        boxQuest.setMinWidth(root.getWidth() * 0.85);
        boxQuest.setPrefWidth(root.getWidth() * 0.85);

        border.setRight(listLevel);
        VBox boxTextQuest = new VBox();

        textQuest = new Label();
        textQuest.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        textQuest.setEffect(dsB);

        textQuest.setWrapText(true);
        textQuest.setTextFill(Paint.valueOf("#FFF"));

        boxTextQuest.getChildren().add(textQuest);
        boxTextQuest.setPadding(new Insets(sizeRoot / 45.5, sizeRoot / 17.075, sizeRoot / 45.5, sizeRoot / 17.075));
        boxTextQuest.setPrefHeight(root.getHeight() / 6);
        boxTextQuest.setId("boxTextQuest");

        boxListBtnAns = new GridPane();
        installButtonAns();

        BorderPane boxTime = new BorderPane();
        boxTime.setPadding(new Insets(0, sizeRoot / 68.3, sizeRoot / 68.3, sizeRoot / 68.3));
        boxTime.setMaxHeight(root.getHeight() / 3);
        boxTime.setMinHeight(root.getHeight() / 2);

        ImageView imgMain = new ImageView(new Image(getClass().getResource("/images/mainQuest.png").toString()));
        imgMain.setFitHeight(sizeRoot / 5.55);
        imgMain.setFitWidth(sizeRoot / 5.55);
        imgMain.setEffect(ds);

        txtMess = new Label(mess.get());
        txtMess.setFont(new Font("Arial", 20));
        txtMess.setEffect(dsB);
        txtMess.setTextFill(Color.WHITE);
        txtMess.setPrefSize(boxQuest.getPrefWidth() / 3, 100);
        txtMess.setWrapText(true);
        Pane kMess = new Pane();
        kMess.setPrefSize(250, 200);
        kMess.getChildren().add(txtMess);

        ImageView imgPlayer = new ImageView(new Image(getClass().getResource("/images/player.png").toString()));
        imgPlayer.setFitHeight(sizeRoot / 5.55);
        imgPlayer.setFitWidth(sizeRoot / 5.55);
        imgPlayer.setEffect(ds);

        VBox boxImgMain = new VBox(kMess, imgMain);
        boxImgMain.setPrefWidth(boxQuest.getPrefWidth() / 3);
        boxImgMain.setAlignment(Pos.BOTTOM_RIGHT);
        GridPane boxBoxTime = new GridPane();
        boxBoxTime.setAlignment(Pos.CENTER);
        boxBoxTime.setVgap(10);
        boxBoxTime.setPrefWidth(boxQuest.getPrefWidth() / 3);

        Text textVs = new Text("VS");
        textVs.setEffect(ds);
        textVs.setFont(Font.font("Tahoma", FontWeight.BLACK, sizeRoot / 45.55));
        textVs.setFill(Paint.valueOf("#ff0000"));
        textVs.setStroke(Paint.valueOf("#FFF"));
        textVs.setStrokeWidth(1);
        HBox boxttextVs = new HBox(textVs);
        boxttextVs.setPadding(new Insets(10));
        boxttextVs.setAlignment(Pos.CENTER);
        boxttextVs.setPrefWidth(boxQuest.getPrefWidth() / 3);

        numTime = new Text("60");
        numTime.setEffect(ds);
        numTime.setFont(Font.font("Tahoma", FontWeight.BLACK, sizeRoot / 45.55));
        numTime.setFill(Paint.valueOf("#fff"));
        numTime.setStroke(Paint.valueOf("#FFF"));
        numTime.setStrokeWidth(1);

        HBox boxtCountTime = new HBox(numTime);
        boxtCountTime.setId("boxtCountTime");
        boxtCountTime.setAlignment(Pos.CENTER);
        boxtCountTime.setMaxSize(this.sizeRoot / 21.015, this.sizeRoot / 21.015);
        HBox boxCenterTime = new HBox(boxtCountTime);
        boxCenterTime.setAlignment(Pos.CENTER);
        boxCenterTime.setPrefWidth(boxQuest.getPrefWidth() / 3);

        boxBoxTime.add(boxttextVs, 0, 0);
        boxBoxTime.add(boxCenterTime, 0, 1);
        boxBoxTime.add(this.listBtnHelp(), 0, 2);

        VBox boxPlayer = new VBox(imgPlayer);
        boxPlayer.setPrefWidth(boxQuest.getPrefWidth() / 3);
        boxPlayer.setAlignment(Pos.BOTTOM_LEFT);
        boxTime.setPrefHeight(root.getHeight() * 0.58);
        boxTime.setRight(boxImgMain);
        boxTime.setCenter(boxBoxTime);
        boxTime.setLeft(boxPlayer);

        boxQuest.getChildren().add(boxTime);
        boxQuest.getChildren().add(boxTextQuest);
        boxQuest.getChildren().add(boxListBtnAns);
        border.setLeft(boxQuest);

        btnPer.setOnAction((ActionEvent t) -> {
            timeLineGame.pause();
            soundGame.getMediaPlayer().pause();
            listButton.forEach((Button k)->{
                k.setDisable(true);
            });
            if(timeLineAns.getStatus() != Animation.Status.STOPPED){
                mess.set("Hãy đợi cho tới khi chúng tôi đưa ra hết các đáp án!");
                return;
            }
            mess.set("Bạn đã chọn sự trợ giúp 50/50. Bây giờ máy tính sẽ bỏ đi hai phương án sai.");
            btnPer.setDisable(true);
            newGame.setPer50(false);
            lib.textAnimation a = new lib.textAnimation();
            processSound.play();
            processSound.getMediaPlayer().setOnEndOfMedia(() -> {
                Random rand = new Random();
                int count = 0;
                do{
                    int i = rand.nextInt(listButton.size());
                    if (!listButton.get(i).getStyleClass().contains("isCorrect") && !listButton.get(i).getText().isEmpty() && listButton.get(i).getText() != null){
                        listButton.get(i).setText("");
                        count++;
                        if(count==2){
                            processSound.stopSound();
                            timeLineGame.play();
                            soundPer50.play();
                            listButton.forEach((Button k)->{
                                k.setDisable(false);
                            });
                        }
                    }
                }while(count<2);
            });
        });
        btnChange.setOnAction((ActionEvent t) -> {
            if(timeLineAns.getStatus() != Animation.Status.STOPPED){
                mess.set("Hãy đợi cho tới khi chúng tôi đưa ra hết các đáp án!");
                return;
            }
            timeLineGame.stop();
            mess.set("Bạn đã chọn sự trợ giúp \"Đổi câu hỏi\". Bây giờ máy tính sẽ đổi câu hỏi cho bạn.");
            btnChange.setDisable(true);
            newGame.setChangeQuest(false);
            lib.textAnimation a = new lib.textAnimation();
            processSound.play();
            a.setAnimation(5000, () -> {
                processSound.stopSound();
                if (newGame.getLevelQuestion() > 4 && newGame.getLevelQuestion() < 9) {
                    listSorted.set(newGame.getLevelQuestion(), listQuestion.get(5));
                } else {
                    listSorted.set(newGame.getLevelQuestion(), listQuestion.get(11));
                }
                statusGame.set(2);
                a.getTimeline().stop();
            });
        });
        btnStop.setOnAction((ActionEvent t) ->{
            mess.set("Vậy bạn đã xin ngừng cuộc chơi. Chúng tôi sẽ chuyển bạn tới phần kết quả");
            timeLineGame.stop();
            btnStop.setDisable(true);
            lib.textAnimation a = new lib.textAnimation();
            if(newGame.getLevelQuestion()<5){
                newGame.setLevelQuestion(0);
                newGame.setTimeTotal(0);
            }else{
                newGame.setLevelQuestion(newGame.getLevelQuestion()-1);
                newGame.setMoney(getCurrentMoney(15));  
            }
            a.setAnimation(2000, () -> {
                statusGame.set(3);
                a.getTimeline().stop();
            });
        });
    }

    public int getCurrentMoney(int i) {
        int money = 0;
        if (i < 15) {
            Label onelv = (Label) boxListLevel.getChildren().get(i);
            if (onelv.getId().equals("level-" + newGame.getLevelQuestion())) {
                money = Integer.parseInt(onelv.getAccessibleText());
            }
        }
        for (int k = boxListLevel.getChildren().size() - 1; k >= 0; k--) {
            Label onelv = (Label) boxListLevel.getChildren().get(k);
            if (onelv.getId().equals("level-" + newGame.getLevelQuestion())) {
                money = Integer.parseInt(onelv.getAccessibleText());
                break;
            }
        }
        return money;
    }

    public void setEffectButton(Button button) {
        button.setMinWidth(boxQuest.getPrefWidth() / 2);
        button.setMinHeight(button.getMinWidth() / 8.2);
        button.setFont(Font.font("Tahoma", FontWeight.MEDIUM, this.sizeRoot / 68.8));
        button.setOnMouseClicked((MouseEvent t) -> {
            if (button.getStyleClass().contains("disable")) {
                mess.set("Chỉ được chọn một đáp án thôi!");
                return;
            }
            listButton.forEach(new Consumer<Button>() {
                public void accept(Button t) {
                    t.getStyleClass().add("disable");
                }
            });
            button.getStyleClass().add("clickAns");
            soundGame.stopSound();
            soundWaitResult.play();
            timeLineGame.stop();
            new lib.textAnimation().setAnimation(1500, () -> {
                if (soundWaitResult.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING && soundWaitResult.getMediaPlayer().getCurrentTime() == Duration.millis(1200));
                button.getStyleClass().add("trans");
            });
            soundWaitResult.getMediaPlayer().setOnEndOfMedia(() -> {
                soundWaitResult.stopSound();
                listButton.forEach((Button v) -> {
                    if (v.getStyleClass().contains("isCorrect")) {
                        v.getStyleClass().add("trueAns");
                    }
                });
                if (button.getStyleClass().contains("isCorrect")) {
                    soundIsCorrect.play();
                    if(newGame.getLevelQuestion()>9) soundClap.play();
                    newGame.setMoney(getCurrentMoney(15));
                    if(newGame.getLevelQuestion()==4){
                        newGame.setTimeTotal4(newGame.getTimeTotal() + (60-time.intValue()));
                    }else if(newGame.getLevelQuestion()==9){
                        newGame.setTimeTotal9(newGame.getTimeTotal() + (60-time.intValue()));
                    }
                    newGame.setTimeTotal(newGame.getTimeTotal() + (60-time.intValue()));
                }
                if (!button.getStyleClass().contains("isCorrect")) {
                    button.getStyleClass().add("falseAns");
                    btnStop.setDisable(true);
                    btnChange.setDisable(true);
                    btnPer.setDisable(true);
                    mess.set("Bạn đã đưa ra phương án sai. Rất tiếc cho bạn...");
                    soundIsNotCorrect.play();
                    if (newGame.getLevelQuestion() > 4 && newGame.getLevelQuestion() < 9) {
                        newGame.setMoney(getCurrentMoney(9));
                        newGame.setTimeTotal(newGame.getTimeTotal4());
                    } else if (newGame.getLevelQuestion() > 9) {
                        newGame.setMoney(getCurrentMoney(4));
                        newGame.setTimeTotal(newGame.getTimeTotal9());
                    } else {
                        newGame.setMoney(0);
                        newGame.setTimeTotal(0);
                    }
                }
            });
        });

    }

    public VBox listLevel() {
        boxListLevel = new VBox();
        boxListLevel.setAlignment(Pos.CENTER);
        int i = 14;
        for (DBModel.PrizeMoney one : new DBModel.PrizeMoney().getData()) {
            Label oneLevel = new Label();
            oneLevel.setText("$ "+one.getMoney());
            if ((i + 1) % 5 == 0) {
                oneLevel.getStyleClass().add("flag");
            }
            oneLevel.setAccessibleText(one.getMoney());
            oneLevel.getStyleClass().add("btnCus");
            oneLevel.setPrefSize(listLevel.getPrefWidth() * 0.82, (listLevel.getPrefWidth() * 0.8) / 3.8);
            oneLevel.setId("level-" + i);
            oneLevel.setAlignment(Pos.CENTER);
            oneLevel.setTextAlignment(TextAlignment.CENTER);
            boxListLevel.getChildren().add(oneLevel);
            i--;
        }
        return boxListLevel;
    }

    public GridPane listBtnHelp() {
        GridPane listBtnHelp = new GridPane();
        listBtnHelp.setPadding(new Insets(10));
        listBtnHelp.setHgap(10);
        listBtnHelp.setAlignment(Pos.CENTER);
        btnPer = new Button();
        btnPer.setId("btnper50");
        btnChange = new Button();
        btnChange.setId("btnchangeQuiz");
        btnStop = new Button();
        btnStop.setId("btnstop");

        ArrayList<Button> allButton = new ArrayList<Button>();
        allButton.add(btnPer);
        allButton.add(btnChange);
        allButton.add(btnStop);

        for (int i = 0; i < allButton.size(); i++) {
            listBtnHelp.add(allButton.get(i), i, 0);
            allButton.get(i).setMinWidth(this.sizeRoot / 27.2);
            allButton.get(i).setMinHeight(this.sizeRoot / 27.2);
        }
        return listBtnHelp;
    }

    private void installButtonAns() {
        boxListBtnAns.getChildren().clear();
        boxListBtnAns.setPadding(new Insets(sizeRoot / 136.6, 0, sizeRoot / 136.6, 0));
        boxListBtnAns.setAlignment(Pos.CENTER);
        boxListBtnAns.setVgap(20);

        btnA = new Button();
        btnA.setId("btnA");
        btnA.setDisable(true);
        setEffectButton(btnA);
        btnB = new Button();
        btnB.setId("btnB");
        btnB.setDisable(true);
        setEffectButton(btnB);
        btnC = new Button();
        btnC.setId("btnC");
        btnC.setDisable(true);
        setEffectButton(btnC);
        btnD = new Button();
        btnD.setId("btnD");
        btnD.setDisable(true);
        setEffectButton(btnD);

        boxListBtnAns.add(btnA, 0, 0);
        boxListBtnAns.add(btnB, 1, 0);
        boxListBtnAns.add(btnC, 0, 1);
        boxListBtnAns.add(btnD, 1, 1);
    }
}
