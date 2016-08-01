/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.manager;

import DBModel.MyConnect;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author nhats
 */

public class frReport {
    private Pane root;
    private HBox content;
    private JasperDesign jd;
    private Connection cn;
    public frReport(Pane root, HBox content) {
        this.root = root;
        this.content = content;
        if(MyConnect.checkData())
        cn = new DBModel.MyConnect().getConnect();
        init();        
    }
    
    private void init(){
        content.getChildren().clear();
        GridPane main = new GridPane();
        content.getChildren().add(main);

        main.setAlignment(Pos.TOP_CENTER);
        
        main.setHgap(10);
        main.setVgap(10);
        
        Button btnPlayerFAge = new Button("Người chơi theo độ tuổi");
        Button btnQuestionFCat = new Button("Câu hỏi theo chủ đề");
        Button btnQuestionFLevel = new Button("Câu hỏi theo cấp độ");
        Button btnPlayerFRank = new Button("Người chơi theo thứ hạng");
        Label lbCb = new Label("Chỉ xuất file PDF");
        lbCb.setFont(new Font("Arial", 20));
        lbCb.setTextFill(Color.web("#fff"));
        CheckBox cbPDF = new CheckBox();
        Label processText = new Label();
        processText.setFont(new Font("Arial", 20));
        processText.setTextFill(Color.RED);
        
        main.add(btnPlayerFAge,0,0);
        main.add(btnPlayerFRank,1,0);
        main.add(btnQuestionFCat, 0, 1);
        main.add(btnQuestionFLevel,1,1);
        main.add(lbCb,0,2);
        main.add(cbPDF,1,2);
        main.add(processText,0,3,2,1);
        SimpleStringProperty mess = new SimpleStringProperty("");
        mess.addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            processText.setText(t1);
        });
        
        main.getChildren().stream().forEach(new Consumer<Node>() {
            @Override
            public void accept(Node t) {
                if(t.getClass().getName() == "javafx.scene.control.Button"){
                    Button one = (Button) t;
                    one.setMaxWidth(250);
                    one.setMinWidth(250);
                    one.getStyleClass().add("btnNor");
                }
            }
        });
        
        btnPlayerFAge.setOnAction((ActionEvent t) -> {
            mess.set("Đang xử lý...");
            try { 
                
                jd = JRXmlLoader.load(this.getClass().getResourceAsStream("/ui/reportPlayerFAge.jrxml"));
                String sql = "select (YEAR(GETDATE()) - year) as N'Tuổi', userName as N'Tên người chơi', email as 'Email', year as N'Năm Sinh', money as N'Tiền thưởng', totalTime as N'Tổng thời gian' from tb_Player where userID <> 1 order by year DESC";
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(sql);
                jd.setQuery(newQuery);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, cn);
                if(!cbPDF.isSelected()) JasperViewer.viewReport(jp, false);
                JasperExportManager.exportReportToPdfFile(jp,System.getProperty("user.home") + "\\Desktop\\reportPlayerFAge.pdf");
                mess.set("Thành công, file đã được lưu tại Destop với tên reportPlayerFAge.pdf");
            } catch (JRException ex) {
                error(ex);
            }
        });
        btnPlayerFRank.setOnAction((ActionEvent t) -> {
            mess.set("Đang xử lý...");
            try {
                jd = JRXmlLoader.load(this.getClass().getResourceAsStream("/ui/reportPlayerFRank.jrxml"));
                String sql = "select userName as N'Tên người chơi', email as 'Email', year as N'Năm Sinh', money as N'Tiền thưởng', totalTime as N'Tổng thời gian' from tb_Player where userID <> 1 order by money DESC, totalTime ASC";
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(sql);
                jd.setQuery(newQuery);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, cn);
                if(!cbPDF.isSelected()) JasperViewer.viewReport(jp, false);
                JasperExportManager.exportReportToPdfFile(jp,System.getProperty("user.home") + "\\Desktop\\reportPlayerFRank.pdf");
                mess.set("Thành công, file đã được lưu tại Destop với tên reportPlayerFRank.pdf");
            } catch (Exception ex) {
                error(ex);
            }
        });
        btnQuestionFCat.setOnAction((ActionEvent t) -> {
                mess.set("Đang xử lý...");
                try {
                    jd = JRXmlLoader.load(this.getClass().getResourceAsStream("/ui/reportQuestionFCategory.jrxml"));
                    String sql = "SELECT A.ansContent as N'Câu trả lời', Q.quesContent as N'Nội dung câu hỏi', C.catName N'Chuyên mục', A.isCorrect as N'Tính đúng sai', Q.level as N'Cấp độ' from tb_Answer as A left join tb_Question as Q on A.quesID = Q.quesID LEFT JOIN tb_Category as C on C.catID = Q.catID order by C.catName ASC, Q.level ASC";
                    JRDesignQuery newQuery = new JRDesignQuery();
                    newQuery.setText(sql);
                    jd.setQuery(newQuery);
                    JasperReport jr = JasperCompileManager.compileReport(jd);
                    JasperPrint jp = JasperFillManager.fillReport(jr, null, cn);
                    if(!cbPDF.isSelected()) JasperViewer.viewReport(jp, false);
                    JasperExportManager.exportReportToPdfFile(jp,System.getProperty("user.home") + "\\Desktop\\reportQuestionFCategory.pdf");
                    mess.set("Thành công, file đã được lưu tại Destop với tên reportQuestionFCategory.pdf");
                } catch (Exception ex) {
                    error(ex);
                }
        });
        btnQuestionFLevel.setOnAction((ActionEvent t) -> {
                 
                try {
                    mess.set("Đang xử lý...");
                    jd = JRXmlLoader.load(this.getClass().getResourceAsStream("/ui/reportQuestionFLevel.jrxml"));
                    String sql = "SELECT A.ansContent as N'Câu trả lời', Q.quesContent as N'Nội dung câu hỏi', C.catName N'Chuyên mục', A.isCorrect as N'Tính đúng sai', Q.level as N'Cấp độ' from tb_Answer as A left join tb_Question as Q on A.quesID = Q.quesID LEFT JOIN tb_Category as C on C.catID = Q.catID order by Q.level ASC, C.catName ASC";
                    JRDesignQuery newQuery = new JRDesignQuery();
                    newQuery.setText(sql);
                    jd.setQuery(newQuery);
                    JasperReport jr = JasperCompileManager.compileReport(jd);
                    JasperPrint jp = JasperFillManager.fillReport(jr, null, cn);
                    if(!cbPDF.isSelected()) JasperViewer.viewReport(jp, false);
                    JasperExportManager.exportReportToPdfFile(jp,System.getProperty("user.home") + "\\Desktop\\reportQuestionFLevel.pdf");
                    mess.set("Thành công, file đã được lưu tại Destop với tên reportQuestionFLevel.pdf");
                } catch (Exception ex) {
                    error(ex);
                }
        });
    }
    public void error(Exception ex){
        new lib.AlertGame("Lỗi", ex.getLocalizedMessage(), Alert.AlertType.ERROR) {
            
            @Override
            public void processResult() {
            }
        };
    }
}
