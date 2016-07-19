/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import lib.AlertGame;

/**
 *
 * @author Mattias
 */
public class Answer {

    public int ansID, quesID;
    public boolean isCorrect;
    public String ansContent;

    public Answer() {
    }

    public Answer(int ansID, int quesID, String ansContent, boolean isCorrect) {
        this.ansID = ansID;
        this.quesID = quesID;
        this.ansContent = ansContent;
        this.isCorrect = isCorrect;
    }

    public int getAnsID() {
        return ansID;
    }

    public int getQuesID() {
        return quesID;
    }

    public String getAnsContent() {
        return ansContent;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setAnsID(int ansID) {
        this.ansID = ansID;
    }

    public void setQuesID(int quesID) {
        this.quesID = quesID;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public void setAnsContent(String ansContent) {
        this.ansContent = ansContent;
    }

    public ArrayList<Answer> getData() {
        ArrayList<Answer> list = new ArrayList<>();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from tb_Answer ");
                while (rs.next()) {
                    Answer a = new Answer();
                    a.ansID = rs.getInt(1);
                    a.quesID = rs.getInt(2);
                    a.ansContent = rs.getString(3);
                    a.isCorrect = rs.getBoolean(4);

                    list.add(a);
                }
                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                new AlertGame("Lỗi", e.getMessage(), Alert.AlertType.ERROR) {

                    @Override
                    public void processResult() {
                    }
                };
            }
        }
        return list;
    }

    public ArrayList<Answer> getData(int id) {
        ArrayList<Answer> list = new ArrayList<>();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("select * from tb_Answer where quesID = ? ORDER BY ansID ASC");
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Answer a = new Answer();
                    a.ansID = rs.getInt(1);
                    a.quesID = rs.getInt(2);
                    a.ansContent = rs.getString(3);
                    a.isCorrect = rs.getBoolean(4);

                    list.add(a);
                }
                rs.close();
                pst.close();
                con.close();
            } catch (Exception e) {
                new AlertGame("Lỗi", e.getMessage(), Alert.AlertType.ERROR) {

                    @Override
                    public void processResult() {
                    }
                };
            }
        }
        return list;
    }

    public boolean delete() {
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("delete from tb_Answer where quesID = ?");
                pst.setInt(1, this.quesID);
                pst.executeUpdate();
                pst.close();
                con.close();
            }
        } catch (Exception e) {
            new AlertGame("Lỗi", e.getMessage(), Alert.AlertType.ERROR) {

                @Override
                public void processResult() {
                }
            };
        }
        return true;
    }

    public boolean update() {
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("update tb_Answer set ansContent=?, isCorrect=? where ansID = ?");
                pst.setString(1, this.getAnsContent());
                pst.setBoolean(2, this.getIsCorrect());
                pst.setInt(3, this.getAnsID());
                pst.executeUpdate();
                pst.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    
    public boolean insert() {
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("insert tb_Answer values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, quesID);
                pst.setString(2, ansContent);
                pst.setBoolean(3, isCorrect);
                pst.executeUpdate();
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.setAnsID(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                pst.close();
                con.close();
            }
        } catch (Exception e) {
            new AlertGame("Lỗi", e.getMessage(), Alert.AlertType.ERROR) {

                @Override
                public void processResult() {
                }
            };
            return false;
        }
        return true;
    }
}
