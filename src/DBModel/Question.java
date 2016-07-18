/*
 * To change this template, choose Tools | Templates
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
import javafx.scene.control.ButtonType;
import ui.AlertGame;

/**
 *
 * @author nhats
 */
public class Question {

    public int quesId, catId, level;
    public String quesContent;
    public boolean isActive;

    public String getQuesContent() {
        return quesContent;
    }

    public String getCat() {
        Category newCate = new Category().getData(catId);
        if(newCate == null){
            return "Không nhận đươc giá trị";
        }else return newCate.getName();
    }

    public int getCatId() {
        return catId;
    }

    public String getActive() {
        if (isActive == true) {
            return "Khả dụng";
        } else {
            return "Không khả dụng";
        }
    }

    public int getQuesId() {
        return quesId;
    }

    public int getLevel() {
        return level;
    }

    public void setQuesId(int quesId) {
        this.quesId = quesId;
    }

    public Question() {
        this.catId = 0;
        this.level = 0;
        this.quesId = 0;
    }

    public void setQuesContent(String quesContent) {
        this.quesContent = quesContent;
    }

    public Question(int quesId, int catId, int level, String quesContent, boolean isActive) {
        this.quesId = quesId;
        this.catId = catId;
        this.level = level;
        this.quesContent = quesContent;
        this.isActive = isActive;
    }

    public int getNumAllRow() {
        int allnum = this.getData().size();
        return allnum;
    }

    public void error(Exception e) {
        new AlertGame("Lỗi", e.getMessage(), Alert.AlertType.ERROR) {

            @Override
            public void processResult() {
                if (getResult().get() == ButtonType.OK) {
                    System.exit(0);
                }
            }
        };
    }

    public ArrayList<Question> getData(String content) {
        ArrayList<Question> list = new ArrayList<>();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("select * from tb_Question where quesContent LIKE ?");
                pst.setString(1, "%"+content+"%");
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Question q = new Question();
                    q.quesId = rs.getInt(1);
                    q.catId = rs.getInt(2);
                    q.quesContent = rs.getString(3);
                    q.isActive = rs.getBoolean(4);
                    q.level = rs.getInt(5);
                    list.add(q);
                }
                rs.close();
                pst.close();
                con.close();
            } catch (Exception e) {
                error(e);
            }
        }
        return list;
    }

    public ArrayList<Question> getData(int id) {
        ArrayList<Question> list = new ArrayList<>();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("select * from tb_Question where quesID = ?");
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Question q = new Question();
                    q.quesId = rs.getInt(1);
                    q.catId = rs.getInt(2);
                    q.quesContent = rs.getString(3);
                    q.isActive = rs.getBoolean(4);
                    q.level = rs.getInt(5);
                    list.add(q);
                }
                rs.close();
                pst.close();
                con.close();
            } catch (Exception e) {
                error(e);
            }
        }
        return list;
    }

    public ArrayList<Question> getData() {
        ArrayList<Question> list = new ArrayList<>();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("Select * from tb_Question");
                while (rs.next()) {
                    Question q = new Question();
                    q.quesId = rs.getInt(1);
                    q.catId = rs.getInt(2);
                    q.quesContent = rs.getString(3);
                    q.isActive = rs.getBoolean(4);
                    q.level = rs.getInt(5);
                    list.add(q);
                }
                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                error(e);
            }
        }
        return list;
    }

    public Question getRadomQues(int lev, int cat) {
        Question q = new Question();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("select top 1 * from tb_Question where level= ? and catId= ? and isActive='true' order by NEWID() ");
                pst.setInt(1, lev);
                pst.setInt(2, cat);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    q.quesId = rs.getInt(1);
                    q.catId = rs.getInt(2);
                    q.quesContent = rs.getString(3);
                    q.isActive = rs.getBoolean(4);
                    q.level = rs.getInt(5);
                }
                rs.close();
                pst.close();
                con.close();
            } catch (Exception e) {
                error(e);
            }
        }
        return q;
    }

    public boolean insert() {
        int result = 0;
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("insert tb_Question values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, catId);
                pst.setString(2, quesContent);
                pst.setBoolean(3, isActive);
                pst.setInt(4, level);
                pst.executeUpdate();
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.setQuesId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                pst.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update() {
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("update tb_Question set catId=?, quesContent=?,isActive=?,level=? where quesID = ?");
                pst.setInt(1, catId);
                pst.setString(2, quesContent);
                pst.setBoolean(3, isActive);
                pst.setInt(4, level);
                pst.setInt(5, this.quesId);
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

    public boolean delete() {
        Answer delA = new Answer();
        delA.setQuesID(this.quesId);
        if (delA.delete()) {
            try {
                if (MyConnect.checkData()) {
                    Connection con = MyConnect.getConnect();
                    PreparedStatement pst = con.prepareStatement("delete from tb_Question where quesID = ?");
                    pst.setInt(1, this.quesId);
                    pst.executeUpdate();
                    pst.close();
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

}
