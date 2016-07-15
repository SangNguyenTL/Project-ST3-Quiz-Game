/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author QUANGTU
 */
public class Help {

    HBox QuickContent(String text, String image){
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));
        Text ND2 = new Text(text);
        ND2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
        ND2.setEffect(ds);
        ND2.setFill(Color.WHITE);
         ImageView imageView = new ImageView(
            new Image(getClass().getResource(image).toString()));
          HBox layout = new HBox(10);  
          layout.setAlignment(Pos.CENTER_LEFT);
          layout.getChildren().addAll(imageView, ND2);
          return layout;
    }
    public Help(Pane root) {
        GridPane grid = new GridPane();
        grid.setPrefSize(root.getWidth(), root.getHeight());
        root.getChildren().add(grid);
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#00A1FF"));
        
        HBox align = new HBox();      
        align.setPrefSize(root.getPrefWidth(), 100);
        align.setAlignment(Pos.CENTER);
        Text scenetitle = new Text("Luật chơi");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setEffect(ds);
        scenetitle.setFill(Color.WHITE);
        align.getChildren().add(scenetitle);
        grid.add(scenetitle, 0, 0, 6, 1);
        scenetitle.setFont(new Font("Arial", 50));

        Text ND = new Text("Người chơi sẽ vượt qua 15 câu hỏi của chương trình và có 3 mốc rất quan trọng 5,10,15. ");
        ND.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        ND.setEffect(ds);
        ND.setFill(Color.WHITE);
        grid.add(ND, 0, 1);
        ND.setFont(new Font("Arial", 30));

        grid.add(QuickContent("Có 4 sự trợ giúp cho bạn trong quá trình chơi:", "/images/50-50.png"), 0, 2);
        grid.add(QuickContent("Bỏ đi hai phương án sai", "/images/50-50.png"), 0, 3);
        grid.add(QuickContent("Đổi câu hỏi", "/images/50-50.png"), 0, 4);
        grid.add(QuickContent("Sử dụng nút để dừng cuộc chơi", "/images/50-50.png"), 0, 6);

        Text ND4 = new Text("Bạn có thời gian là 60s để trả lời cho mỗi câu hỏi. Trong khi sử dụng  sự trợ giúp, thời gian vẫn\nđược tính.");
        ND4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        ND4.setEffect(ds);
        ND4.setFill(Color.WHITE);
        grid.add(ND4, 0, 5);
        ND4.setFont(new Font("Arial", 30));

        

        Text ND6 = new Text("Trả lời đúng mỗi câu hỏi bạn sễ nhận được mức thưởng tương ứng. Trả lời sai hoặc hết thời gian,\nbạn sẽ nhận phần thưởng của mốc quan trọng trước đó. Nếu chọn dừng cuộc chơi, bạn sẽ được\ngiữ nguyên mức thưởng hiện tại.");
        ND6.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        ND6.setEffect(ds);
        ND6.setFill(Color.WHITE);
        grid.add(ND6, 0, 7);
        ND6.setFont(new Font("Arial", 30));

        //Quay lai
        Button back = new Button("Quay lại");
        back.getStylesheets().add(frameOpenGame.class.getResource("/css/frameOpenGame.css").toExternalForm());
        HBox hbback = new HBox(10);
        back.getStyleClass().add("btnCus");
        back.setMinWidth(162);
        back.setMinHeight(42);
        hbback.getChildren().add(back);
        hbback.setPadding(new Insets(10));
        root.getChildren().add(hbback);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                root.getChildren().clear();
                new listButtonOpen(root);

            }
        });
    }
}
