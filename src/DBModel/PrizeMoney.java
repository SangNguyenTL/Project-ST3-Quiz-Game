/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author LUCAS
 */
public class PrizeMoney {
    public int priId,money;

    public PrizeMoney() {
    }

    public PrizeMoney(int priId, int money) {
        this.priId = priId;
        this.money = money;
    }
    
    public ArrayList<PrizeMoney> getData(){
        ArrayList<PrizeMoney> list=new ArrayList<>();
        if(MyConnect.checkData())
        {
            try {
                Connection con=MyConnect.getConnect();
                Statement st=con.createStatement();
                ResultSet rs=st.executeQuery("select * from tb_PrizeMoney order by priID DESC");
                while(rs.next()){
                    PrizeMoney p=new PrizeMoney();
                    p.priId=rs.getInt(1);
                    p.money = rs.getInt(2);
                    list.add(p);
                }
                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }                   
        }
        return list;    
    }
     public boolean update(){
          if(MyConnect.checkData()){
            String spl = "update tb_PrizeMoney set money=? where priID=?";
        try {
            Connection con=MyConnect.getConnect();
            PreparedStatement pst = con.prepareStatement(spl);
            pst.setInt(1, money );
            pst.setInt(2, priId);
            //thi hanh lenh update
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }

          }
               
       return true;
   }

    public String getMoney() {
        return Integer.toString(money);
    }

    public int getPriId() {
        return priId;
    }

    public void setPriId(int priId) {
        this.priId = priId;
    }

    public void setMoney(int money) {
        this.money = money;
    }
     
}
