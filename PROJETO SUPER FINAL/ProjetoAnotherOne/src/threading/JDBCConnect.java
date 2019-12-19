/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;



import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    
    public void insert_entidade(String user, String pass) {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        String inserir = "insert into login values('" + user + "','" + pass + "')";
        try {
            Statement stm2 = (Statement) conn.createStatement();
            stm2.executeQuery(inserir);
        } catch (SQLException ex) {
        }

    }

}
//    public void passw {
//    
//    private final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
//    private final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
//    private static final String NUMBER = "0123456789";
//    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
//
//    private final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
//    // optional, make it more random
//    private final String PASSWORD_ALLOW_BASE_SHUFFLE = shuffleString(PASSWORD_ALLOW_BASE);
//    private final String PASSWORD_ALLOW = PASSWORD_ALLOW_BASE_SHUFFLE;
//
//    private SecureRandom random = new SecureRandom();
//    
//     public String generateRandomPassword(int length) {
//        if (length < 1) throw new IllegalArgumentException();
//
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//
//            int rndCharAt = random.nextInt(PASSWORD_ALLOW.length());
//            char rndChar = PASSWORD_ALLOW.charAt(rndCharAt);
//
//
//            sb.append(rndChar);
//
//        }
//
//        return sb.toString();
//
//    }
//     
//     public String shuffleString(String string) {
//        List<String> letters = Arrays.asList(string.split(""));
//        Collections.shuffle(letters);
//        return letters.stream().collect(Collectors.joining());
//    }
//
//}

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

