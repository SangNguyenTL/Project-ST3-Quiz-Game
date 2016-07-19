/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public int userID;
    public String userName, email, pasword;
    public int year, money, totalTime;
    public boolean  isAdmin;
    public Player() {
    }

    public Player(int userID, String userName, String email,String password, int year, int money, int time) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.pasword = password;
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
        return pasword;
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
    

    
    public Player getData(String email, String pass) {
        Player q = new Player();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                PreparedStatement pst = con.prepareStatement("select * from tb_Player WHERE email = ? and password = ?");
                pst.setString(1, email);
                pst.setString(2, pass);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    q.userID = rs.getInt(1);
                    q.userName = rs.getString(2);
                    q.email = rs.getString(3);
                    q.pasword = rs.getString(4);
                    q.year = rs.getInt(5);
                    q.money = rs.getInt(6);
                    q.totalTime = rs.getInt(7);
                    q.isAdmin = rs.getBoolean(8);
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

    public ArrayList<Player> getData() {
        ArrayList<Player> list = new ArrayList<>();
        if (MyConnect.checkData()) {
            try {
                Connection con = MyConnect.getConnect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from tb_Player");
                while (rs.next()) {
                    Player q = new Player();
                    q.userID = rs.getInt(1);
                    q.userName = rs.getString(2);
                    q.email = rs.getString(3);
                    q.pasword = rs.getString(4);
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
        } catch (Exception e) {
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
}
