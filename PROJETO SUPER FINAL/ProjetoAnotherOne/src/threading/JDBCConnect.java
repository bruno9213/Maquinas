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


public class JDBCConnect {

    private final String url = "jdbc:postgresql://localhost/bdptda";
    private final String user = "postgres";
    private final String password = "123";

    public JDBCConnect() {

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

    public ResultSet getQueryResult(String str) {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        ResultSet rs = null;
        try {
            Statement stm = (Statement) conn.createStatement();
            rs = stm.executeQuery(str);
        } catch (SQLException ex) {
        }
        return rs;
    }

    public void exQuery(String str) {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        try {
            Statement stm = (Statement) conn.createStatement();
            stm.executeQuery(str);
        } catch (SQLException ex) {
        }
    }

    public void insert_login(String user, String pass) {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        String inserir = "insert into login values('" + user + "','" + pass + "')";
        try {
            Statement stm2 = (Statement) conn.createStatement();
            stm2.executeQuery(inserir);
        } catch (SQLException ex) {
        }

    }
    
    public void insert_entidade(int id, String nome, String user,String mail, int id_type) {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        String inserir = "insert into entidades values('" + id + "','" + nome + "','" + user + "','" + mail + "','" + id_type + "')";
        try {
            Statement stm2 = (Statement) conn.createStatement();
            stm2.executeQuery(inserir);
        } catch (SQLException ex) {
        }

    }

    public void get_type() {
        try {
            JDBCConnect app = new JDBCConnect();
            Connection conn = app.connect();
            
            String consulta = "select * from type_entidades";
            Statement stm = (Statement) conn.createStatement();
            ResultSet rs = stm.executeQuery(consulta);
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                System.out.println(id + "\t" + nome + "\t");
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void main(String[] args) {
           JDBCConnect j= new JDBCConnect();
           j.get_type();
    }
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

