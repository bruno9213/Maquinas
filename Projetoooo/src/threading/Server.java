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
        String[] dados = new String[10];
        
        JDBCConnect c = new JDBCConnect();
        ResultSet rs1 = c.getQueryResult("select * from estatisticas_sentido_1");
        
        try {
            while (rs1.next()) {
                dados[0] = rs1.getString("Contagem");
                dados[1] = rs1.getString("Velocidade Media");
                dados[2] = rs1.getString("Velocidade Maxima");
                dados[3] = rs1.getString("Velocidade Maxima");
                dados[4] = rs1.getString("Estado do Transito");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        rs1 = c.getQueryResult("select * from estatisticas_sentido_2");
        
        try {
            while (rs1.next()) {
                dados[5] = rs1.getString("Contagem");
                dados[6] = rs1.getString("Velocidade Media");
                dados[7] = rs1.getString("Velocidade Maxima");
                dados[8] = rs1.getString("Velocidade Maxima");
                dados[9] = rs1.getString("Estado do Transito");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i=0; i<dados.length; i++){
                cliente.println(dados[i]);
            }
        
    }
}
