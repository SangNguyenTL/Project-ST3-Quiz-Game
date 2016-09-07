/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lib.AlertGame;

/**
 *
 * @author nhats
 */
public class MyConnect {
    //Declare variable:
    private static String user,pass,port,dbName,server;
    private static Connection con;
    private static Properties p;
    private static String username,password,portP,dbNameP,severP,fname;
    private static String key  = "ST3sediToicuoiMa"; // 128 bit key
    private static String initVector = "RandomInitVector"; // 16 bytes IV
    //Default Constructor:
    public MyConnect() {
        username="username";
        password="pass";
        portP="port";
        dbNameP="database";
        severP="sever";
        fname="data.properties";
        
    }
    
    //Set value for variable con:
    public static void setConn(Connection conn) {
       con = conn;
    }
    
    //Get value from variable con:
    public static Connection getConnect() {
        return con;
    }
    
    public static String getDatabase() {
        return dbName;
    }

    public static void setDatabase(String database) {
        dbName = database;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String passw) {
        pass = passw;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String ports) {
        port = ports;
    }

    public static String getServer() {
        return server;
    }

    public static void setServer(String servers) {
        server = servers;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String users) {
       user = users;
    }

    public static boolean checkCon(String userC,String passC,String portC,String dbNameC,String serverC){
        user=userC;
        pass=passC;
        port=portC;
        dbName=dbNameC;
        server=serverC;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con=DriverManager.getConnection(bulid());
        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }
        return true;
    }
    
    private static String bulid() {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:sqlserver://");
        sb.append(server);
        sb.append(":");
        sb.append(port);

        sb.append(";databaseName=");
        sb.append(dbName);

        sb.append(";user=");
        sb.append(user);

        sb.append(";password=");
        sb.append(pass);

        return sb.toString();
    }
    
     public static String getProperty(String prop) {
        if (p == null) {
            return "";
        }
        return lib.Encryption.decrypt(key, initVector,p.getProperty(prop));
    }

    public static void setProperty(String prop, String value) {
        if (p == null) {
            p = new Properties();
        }
        p.put(prop, lib.Encryption.encrypt(key, initVector, value));
    }
    
      public static boolean checkData() {
          MyConnect m = new MyConnect();
          if (MyConnect.loadData()){
                 if (MyConnect.checkCon(    
                    MyConnect.getProperty(MyConnect.username),
                    MyConnect.getProperty(MyConnect.password),
                    MyConnect.getProperty(MyConnect.portP),
                    MyConnect.getProperty(MyConnect.dbNameP),
                    MyConnect.getProperty(MyConnect.severP))) {
                       con = MyConnect.getConnect();
                 }
                 else{
                     return false;
                 }
          } else {
            return false;
        }
        return true;
    }
    
    public static boolean loadData(){
        p = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream(fname);
            p.load(fis);
            fis.close();
        } catch (IOException ex) {     
            return false;
        }
        return true;
    }

    public static void saveData() {
        FileOutputStream fos = null;        
        try {
            setProperty(username, user);
            setProperty(password, pass);
            setProperty(dbNameP, dbName);
            setProperty(portP, port);
            setProperty(severP, server);
            fos = new FileOutputStream(fname);
            p.store(fos, "===Cấu hình game ST3 Quiz===");
            fos.close();
        } catch (IOException ex) {
                new AlertGame("Lỗi", "Không thể lưu kết nối... Xin thử lại sau!", Alert.AlertType.ERROR) {

                    @Override
                    public void processResult() {
                        if(this.getResult().get()== ButtonType.OK){
                            System.exit(0);
                        }
                    }
                };
        }
    }
}
