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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import ui.AlertGame;

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

    public int getId() {
        return id;
    }
    public void error(Exception e) {
        new AlertGame("Lỗi", e.getMessage(), Alert.AlertType.ERROR) {

            @Override
            public void processResult() {
                if(getResult().get()==ButtonType.OK){
                    System.exit(0);
                }
            }
        };
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
            error(e);
        }
        return list;
    }

    public Category getData(int id) {
        Category obCat = new Category();
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("select * from tb_Category WHERE catID = ?");
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    obCat.id = rs.getInt(1);
                    obCat.name = rs.getString(2);
                }
                rs.close();
                pst.close();
                con.close();
            }
        } catch (Exception e) {
            return null;
        }
        return obCat;
    }

    @Override
    public String toString() {
        return name.toString(); //To change body of generated methods, choose Tools | Templates.
    }
}
