/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login {

    //TODO
    public boolean autenticarUser(String user, String pass) {
        JDBCConnect c = new JDBCConnect();
        ResultSet rs = c.getQueryResult("select * from login");
        ArrayList<String> dataUser = new ArrayList<>();

        try {
            while (rs.next()) {
                dataUser.add(rs.getString(1) + "," + rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (String tmp : dataUser) {
            if (tmp.equals(user + "," + pass)) {
                return true;
            }
        }

        return false; //TODO
    }

    public boolean checkAdmin(String user) {
        JDBCConnect c = new JDBCConnect();
        ResultSet rs = c.getQueryResult("select * from entidades");
        ArrayList<String> dataUser = new ArrayList<>();
        try {
            while (rs.next()) {
                dataUser.add(rs.getString(3) + "," + rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean ok = false;
        for (String tmp : dataUser) {
            System.out.println(tmp);
            if (tmp.equals(user + "," + "0")) {
                ok = true;
                System.out.println("entrouuuu");
            }
        }
        return ok;
    }

}
