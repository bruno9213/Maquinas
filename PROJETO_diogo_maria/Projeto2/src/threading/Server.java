package threading;

import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {

    public static void main(String args[]) {

        try {
            ServerSocket s = new ServerSocket(8090);

            while (true) {
                System.out.println("Esperando alguem se conectar...");
                Socket conexao = s.accept();

                Thread t = new Server(conexao);
                t.start();

                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    public void run() {
                        JDBCConnect c = new JDBCConnect();
                        c.exQuery("refresh materialized view sentido1_last_10");
                        c.exQuery("refresh materialized view sentido2_last_10");
                        c.exQuery("refresh materialized view estatisticas_sentido_1");
                        c.exQuery("refresh materialized view estatisticas_sentido_2");
                        System.out.println("tudo ok");
                    }
                };
                timer.scheduleAtFixedRate(task, 0, 10000); //1000ms = 1sec
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    private Socket conexao;

    public Server(Socket s) {
        conexao = s;
    }

    public void run() {
        try {
            PrintStream cliente = new PrintStream(conexao.getOutputStream());

            // NEED TO CHANGE
            boolean id = true;
            while (id == true) {
                sendCommand(cliente);
                id = false;
            }
            conexao.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    public void sendCommand(PrintStream cliente) throws IOException {
        JDBCConnect c = new JDBCConnect();
        ResultSet rs = c.getQueryResult("select * from estatisticas_sentido_1");
        try {
            while (rs.next()) {
                String contagem = rs.getString("Contagem");
                String vel_med = rs.getString("Velocidade Media");
                String vel_max = rs.getString("Velocidade Maxima");
                String vel_min = rs.getString("Velocidade Minima");
                String estado = rs.getString("Estado do Transito");
                cliente.println("Sentido 1: ");
                cliente.println("Contagem: " + contagem);
                cliente.println("Velocidade Media: " + vel_med);
                cliente.println("Velocidade Maxima: " + vel_max);
                cliente.println("Velocidade Minima: " + vel_min);
                cliente.println("Estado do Transito: " + estado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//NAO ESTA A FAZER ESTA PARTE WE NOOBS OOOPSIE
        ResultSet rs2 = c.getQueryResult("select * from estatisticas_sentido_2");
        try {
            while (rs2.next()) {
                String contagem = rs.getString("Contagem");
                String vel_med = rs.getString("Velocidade Media");
                String vel_max = rs.getString("Velocidade Maxima");
                String vel_min = rs.getString("Velocidade Minima");
                String estado = rs.getString("Estado do Transito");
                cliente.println("Sentido 2: ");
                cliente.println("Contagem: " + contagem);
                cliente.println("Velocidade Media: " + vel_med);
                cliente.println("Velocidade Maxima: " + vel_max);
                cliente.println("Velocidade Minima: " + vel_min);
                cliente.println("Estado do Transito: " + estado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}