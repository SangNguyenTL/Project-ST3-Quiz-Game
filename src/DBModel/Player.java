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
import javafx.scene.control.ButtonType;
import lib.AlertGame;

/**
 *
 * @author QUANGTU
 */
public class Player {

    private int userID;
    private String userName, email, password;
    private int year, money, totalTime;
    private boolean  isAdmin;
    private String key = "St3vodoiProject2"; // 128 bit key
    private String initVector = "RandomInitVector"; // 16 bytes IV
    public Player() {
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasword(String password) {
        this.password = lib.Encryption.encrypt(key, initVector, password);
        System.out.println(this.password);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Player(int userID, String userName, String email,String password, int year, int money, int time) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = lib.Encryption.encrypt(key,initVector,password);
        this.year = year;
        this.money = money;
        this.totalTime = time;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasword() {
        return lib.Encryption.decrypt(key, initVector, password);
    }

    public int getYear() {
        return year;
    }

    public int getMoney() {
        return money;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    public boolean isAdmin(){
        return this.isAdmin;
    }
    
   public Player getData(String email) {
        Player q = new Player();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("select * from tb_Player WHERE email = ?");
                pst.setString(1, email);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    q.setUserID( rs.getInt(1));
                    q.setUserName(rs.getString(2));
                    q.setEmail( rs.getString(3));;
                    q.password = rs.getString(4);
                    q.setYear(rs.getInt(5));
                    q.setMoney( rs.getInt(6));
                    q.setTotalTime(rs.getInt(7));
                    q.setIsAdmin( rs.getBoolean(8));
                }
                rs.close();
                pst.close();
                con.close();
            } catch (SQLException e) {
                error(e);
            }
        }
        return q;
    }
    
    public Player getData(String email, String pass) {
        Player q = new Player();
        setPasword(pass);
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("select * from tb_Player WHERE email = ? and password = ?");
                pst.setString(1, email);
                pst.setString(2, this.password);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    q.setUserID( rs.getInt(1));
                    q.setUserName(rs.getString(2));
                    q.setEmail( rs.getString(3));;
                    q.password = rs.getString(4);
                    q.setYear(rs.getInt(5));
                    q.setMoney( rs.getInt(6));
                    q.setTotalTime(rs.getInt(7));
                    q.setIsAdmin( rs.getBoolean(8));
                }
                rs.close();
                pst.close();
                con.close();
            } catch (SQLException e) {
                error(e);
            }
        }
        return q;
    }

    public ArrayList<Player> getData() {
        ArrayList<Player> list = new ArrayList<>();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from tb_Player");
                while (rs.next()) {
                    Player q = new Player();
                    q.setUserID( rs.getInt(1));
                    q.setUserName(rs.getString(2));
                    q.setEmail( rs.getString(3));;
                    q.password = rs.getString(4);
                    q.setYear(rs.getInt(5));
                    q.setMoney( rs.getInt(6));
                    q.setTotalTime(rs.getInt(7));
                    q.setIsAdmin( rs.getBoolean(8));
                    list.add(q);
                }
                rs.close();
                st.close();
                con.close();
            } catch (SQLException e) {
                error(e);
            }
        }
        return list;
    }
    
    public ArrayList<Player> getRank(){
        ArrayList<Player> list = new ArrayList<>();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select top 10 * from tb_Player ORDER BY money DESC, totalTime ASC");
                while (rs.next()) {
                    Player q = new Player();
                    q.userID = rs.getInt(1);
                    q.userName = rs.getString(2);
                    q.email = rs.getString(3);
                    q.password= rs.getString(4);
                    q.year = rs.getInt(5);
                    q.money = rs.getInt(6);
                    q.totalTime = rs.getInt(7);
                    q.isAdmin = rs.getBoolean(8);
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
    
    public boolean delete() {
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("delete from tb_Player where userID = ?");
                pst.setInt(1, this.userID);
                pst.executeUpdate();
                pst.close();
                con.close();
            }
        } catch (SQLException e) {
            error(e);
        }
        return true;
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
    
   public boolean insert() {
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("insert tb_Player values (?,?,?,?,NULL,NULL,?)");
                pst.setString(1, userName);
                pst.setString(2, email);
                pst.setString(3, password);
                pst.setInt(4, year);
                pst.setBoolean(5, false);
                pst.executeUpdate();
                pst.close();
                con.close();
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean update() {
        try {
            if (MyConnect.checkData()) {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("update tb_Player set userName = ?, password = ?, year = ?, isAdmin = ? where userID = ?");
                pst.setString(1, userName);
                pst.setString(2, password);
                pst.setInt(3, year);
                pst.setBoolean(4,isAdmin);
                pst.setInt(5, userID);
                pst.executeUpdate();
                pst.close();
                con.close();
            }
        } catch (SQLException e) {
            return false;
        }
        return true;    
    }
}
