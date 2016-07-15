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
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.swing.JOptionPane;

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
        return new Category().getData(catId).get(0).getName();
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

    
    public Question() {
    }

    public Question(int quesId, int catId, int level, String quesContent, boolean isActive) {
        this.quesId = quesId;
        this.catId = catId;
        this.level = level;
        this.quesContent = quesContent;
        this.isActive = isActive;
    }
    
    public int getNumAllRow(){
        int allnum = 0;
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select count(quesID) from tb_Question ");
                while (rs.next()) {
                    allnum = rs.getInt(1);
                }
                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return allnum;
    }
    
    public ArrayList<Question> getData() {
        ArrayList<Question> list = new ArrayList<>();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from tb_Question ");
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
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return list;
    }

    public ArrayList<Question> getData(int page, int row) {
        ArrayList<Question> list = new ArrayList<>();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("Select * from tb_Question Order By quesID OFFSET (?-1)*? ROWS FETCH NEXT ? ROWS ONLY");
                pst.setInt(1, page+1);
                pst.setInt(2, row);
                pst.setInt(3, row);
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
                JOptionPane.showMessageDialog(null, e.getMessage());
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
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return q;
    }

    public boolean updateActiveQues(int id) {
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("update tb_Question set isActive ='false' where quesId=?");
                pst.setInt(1, id);
                pst.executeUpdate();
                pst.close();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return false;
            }
        }
        return true;
    }

    public boolean insert() {
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("insert tb_Question values (?,?,?,?)");
                pst.setInt(1, catId + 1);
                pst.setString(2, quesContent);
                pst.setBoolean(3, isActive);
                pst.setInt(4, level + 1);
                pst.executeUpdate();

                pst.close();
                con.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        return true;
    }

    public boolean update(String qCon) {
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("update tb_Question set catId=?, quesContent=?,isActive=?,level=? where quesID = ?");
                pst.setInt(1, catId);
                pst.setString(2, quesContent);
                pst.setBoolean(3, isActive);
                pst.setInt(4, level);
                pst.setString(5, qCon);
                pst.executeUpdate();
                pst.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete() {
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("delete from tb_Question where quesContent = ?");
                pst.setString(1, quesContent);
                pst.executeUpdate();
                pst.close();
                con.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return true;
    }
     public boolean deleteQ(int i){
       
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText(null);
                alert.setContentText("Xóa dữ liệu?");
                Optional <ButtonType> action = alert.showAndWait();
            if(MyConnect.checkData()){
                if(action.get() == ButtonType.OK){
                    if(deleteA(i)){
                        try {

                            String query = "delete from tb_Question where quesID = ?";
                            Connection con = MyConnect.getConnect();                 
                            PreparedStatement pst = con.prepareStatement(query);
                            pst.setString(1, String.valueOf(i));
                            pst.executeUpdate();

                            pst.close();
                            con.close();
                       
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "K ton tai");
                    }
                }
         }
          
        return true;
    }
    public boolean deleteA(int i){
        if(MyConnect.checkData()){
         try {
                        
                        String query = "delete from tb_Answer where quesID = ?";
                        Connection con = MyConnect.getConnect();
                    
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1, String.valueOf(i));
                        pst.executeUpdate();
                        
                        pst.close();
                        con.close();
            
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        ex.printStackTrace();
                    }
        }
          return true;
    }
   
}

