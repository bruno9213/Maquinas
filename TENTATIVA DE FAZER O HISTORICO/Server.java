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
                //refresh das views materializadas de 10 em 10 minutos
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

                //conexao com clientes e criação de threads para cada
                System.out.println("Esperando alguem se conectar...");
                Socket conexao = s.accept();
                Thread t = new Server(conexao);
                t.start();
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
            DataInputStream dis = new DataInputStream(conexao.getInputStream());
            String cliente_dados = dis.readUTF();
            System.out.println("tipo de cliente: " + cliente_dados);

            PrintStream cliente = new PrintStream(conexao.getOutputStream());

            boolean id = true;
            while (id == true) {
                sendEstatisticasCliente(cliente);
                if (cliente_dados.equals("1") || cliente_dados.equals("2")) {
                    // sendHistorico(cliente);
                }
                id = false;
            }
            conexao.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

    }

    public void sendEstatisticasCliente(PrintStream cliente) throws IOException {
        String[] dados = new String[10];
        System.out.println("heeere");
        JDBCConnect c = new JDBCConnect(); //conexao bd
        ResultSet rs = c.getQueryResult("select * from radar"); //query da bd
        String[] dados_radar = new String[5];
        try {
            while (rs.next()) {
                dados_radar[0] = rs.getString(1);
                dados_radar[1] = rs.getString(2);
                dados_radar[2] = rs.getString(3);
                dados_radar[3] = rs.getString(4);
                dados_radar[4] = rs.getString(5);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        //criacao objeto radar
        Radar r = new Radar(dados_radar[0], dados_radar[1], dados_radar[2], dados_radar[3], dados_radar[4]);
        //enviar radar
        ObjectOutputStream oos = new ObjectOutputStream(conexao.getOutputStream());
        oos.flush();
        oos.writeObject(r);

//      ResultSet rs1 = c.getQueryResult("select * from estatisticas_sentido_1"); //para 10 em 10 min
        ResultSet rs1 = c.getQueryResult("select * from sentido1");
        try {
            while (rs1.next()) {
                dados[0] = rs1.getString(1);
                dados[1] = rs1.getString(2);
                dados[2] = rs1.getString(3);
                dados[3] = rs1.getString(4);
                dados[4] = rs1.getString(5);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

//      rs1 = c.getQueryResult("select * from estatisticas_sentido_2"); //para 10 em 10 min
        rs1 = c.getQueryResult("select * from sentido2");
        try {
            while (rs1.next()) {
                dados[5] = rs1.getString(1);
                dados[6] = rs1.getString(2);
                dados[7] = rs1.getString(3);
                dados[8] = rs1.getString(4);
                dados[9] = rs1.getString(5);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < dados.length; i++) { //envia dados 
            cliente.println(dados[i]);
        }

    }

    public void sendHistorico(PrintStream cliente) throws IOException {
        JDBCConnect c = new JDBCConnect();
        ResultSet rs1 = c.getQueryResult("select * from historico_sentido_1");
        ResultSet rs2 = c.getQueryResult("select * from historico_sentido_2");
        System.out.println("here1");
        String[] dados_historico1 = new String[2];
        String[] dados_historico2 = new String[2];
        System.out.println("here2");
        ArrayList<Historico> ah1 = new ArrayList<Historico>();
        ArrayList<Historico> ah2 = new ArrayList<Historico>();
        System.out.println("here3");
        try {
            while (rs1.next()) {
                dados_historico1[0] = rs1.getString(1);
                dados_historico1[1] = rs1.getString(2);

                ah1.add(new Historico(dados_historico1[0], dados_historico1[1]));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Historico i: ah1){
            System.out.println(i);;
        }
        
//        try {
//            while (rs2.next()) {
//                dados_historico2[0] = rs2.getString(1);
//                dados_historico2[1] = rs2.getString(2);
//                ah2.add(new Historico(dados_historico2[0], dados_historico2[1]));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//        }

        //System.out.println(dados_radar[0]);
        ObjectOutputStream oos = new ObjectOutputStream(conexao.getOutputStream());
        oos.flush();
        oos.writeObject(ah1);

    }
}
