/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBModel;

/**
 *
 * @author NhutNM
 */
import java.sql.*;
import java.util.ArrayList;
import java.sql.PreparedStatement;
public class Category {

    public int id;
    public String name;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Category> getData() {
        ArrayList<Category> list = new ArrayList<>();
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from tb_Category");
                String catName;
                int catId;
                while (rs.next()) {
                    catId = rs.getInt(1);
                    catName = rs.getString(2);
                    list.add(new Category(catId, catName));
                }
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<Category> getData(int id) {
        ArrayList<Category> list = new ArrayList<>();
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("select * from tb_Category WHERE catID = ?");
                pst.setInt(1, id);
                ResultSet rs =  pst.executeQuery();
                if(rs.next()){
                    int catId = rs.getInt(1);
                    String catName = rs.getString(2);
                    list.add(new Category(catId, catName));
                }
                rs.close();
                pst.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
