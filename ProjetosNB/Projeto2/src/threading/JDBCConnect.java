/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class JDBCConnect {

    private final String url = "jdbc:postgresql://localhost/bdptda";
    private final String user = "postgres";
    private final String password = "123";

    public JDBCConnect(){
        
    }
    
    public Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
//
//    public static void viewTable(Connection con, String dbName)
//            throws SQLException {
//
//        Statement stmt = null;
//        String query = "select COF_NAME, SUP_ID, PRICE, "
//                + "SALES, TOTAL "
//                + "from " + dbName + ".COFFEES";
//        try {
//            stmt = (Statement) con.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            while (rs.next()) {
//                String coffeeName = rs.getString("COF_NAME");
//                int supplierID = rs.getInt("SUP_ID");
//                float price = rs.getFloat("PRICE");
//                int sales = rs.getInt("SALES");
//                int total = rs.getInt("TOTAL");
//                System.out.println(coffeeName + "\t" + supplierID
//                        + "\t" + price + "\t" + sales
//                        + "\t" + total);
//            }
//        } catch (SQLException e) {
//        } finally {
//            if (stmt != null) {
//                stmt.close();
//            }
//        }
//    }

    public ResultSet getEverything() {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        ResultSet rs = null;
        try {
            String consulta = "select * from login";
            Statement stm = (Statement) conn.createStatement();
            rs = stm.executeQuery(consulta);
        } catch (SQLException ex) {
        }
        return rs;
    }
//
//    public static void main(String[] args) throws SQLException {
//        JDBCConnect app = new JDBCConnect();
//        Connection conn = app.connect();
//
////        String consulta = "select * from type_objeto";
////        Statement stm = (Statement) conn.createStatement();
////        ResultSet rs = stm.executeQuery(consulta);
////
////        while (rs.next()) {
////            int id = rs.getInt("id_type");
////            String nome = rs.getString("nome");
////            System.out.println(id + "\t" + nome + "\t");
////        }
////        
////        String inserir = "insert into login values('bruno','bruno9213')";
////        Statement stm2 = (Statement) conn.createStatement();
////        ResultSet rs2 = stm2.executeQuery(inserir);
//        String consulta = "select * from login";
//        Statement stm = (Statement) conn.createStatement();
//        ResultSet rs = stm.executeQuery(consulta);
//
//        while (rs.next()) {
//            String user = rs.getString("user");
//            String pass = rs.getString("pass");
//            System.out.println(user + "\t" + pass + "\t");
//        }
//
//    }
}
