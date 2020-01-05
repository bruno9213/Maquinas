package threading;

import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe Server serve de servidor para a aplicação. Aceita vários clientes,
 * criando uma thread para cada.
 *
 */
public class Server extends Thread {

    private Socket conexao;

    public Server(Socket s) {
        conexao = s;
    }

    /**
     * No main, é iniciada uma conexão por sockets. É feito o refresh das views
     * das estatísticas na Base de Dados de 10 em 10 minutos. Fica à espera de
     * aceitar clientes.
     *
     * @param args
     */
    public static void main(String args[]) {

        try {

            ServerSocket s = new ServerSocket(8090);

            int n_cliente = 0;
            while (true) {
                //refresh das views materializadas das estatísticas de 10 em 10 minutos
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    public void run() {
                        JDBCConnect c = new JDBCConnect();
                        c.exQuery("refresh materialized view sentido1_last_10");
                        c.exQuery("refresh materialized view sentido2_last_10");
                        c.exQuery("refresh materialized view estatisticas_sentido_1");
                        c.exQuery("refresh materialized view estatisticas_sentido_2");
                        System.out.println("Estatísticas dos últimos 10 minutos foram atualizadas.");
                    }
                };
                timer.scheduleAtFixedRate(task, 0, 600000); //1000ms = 1sec

                //conexao com clientes e criação de threads para cada um
                System.out.println("Esperando alguem se conectar...");
                Socket conexao = s.accept();
                Thread t = new Server(conexao);
                n_cliente++;
                t.start();
                System.out.println("Nova conexão: cliente nº " + n_cliente);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    /**
     * Ao ser criada uma nova thread, são enviadas ao cliente todas as
     * estatísticas.
     */
    public void run() {

        try {
            PrintStream cliente = new PrintStream(conexao.getOutputStream());

            boolean id = true;
            while (id == true) {
                enviarEstatisticas(cliente);
                id = false;
            }
            conexao.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

    }

    /**
     * O método enviarEstatisticas serve para enviar todas as estatisticas da
     * base de dados ao cliente. Faz uma conexão à base de dados, envia-lhes
     * queries e guarda esses dados em ResultSet. Guarda num objeto da classe
     * Dados, os dados do Radar e do Histórico de cada sentido e envia ao
     * cliente esse objeto. Envia também por PrintStream as estatísticas de cada
     * sentido dos ultimos 10 minutos.
     *
     * @param cliente
     * @throws IOException
     */
    public void enviarEstatisticas(PrintStream cliente) throws IOException {
        String[] dados = new String[10];
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
        //criacao objeto Dados
        Dados allData = new Dados();
        allData.setRadarData(dados_radar[0], dados_radar[1], dados_radar[2], dados_radar[3], dados_radar[4]);

        //historico sentido 1    
        ResultSet rs1 = c.getQueryResult("select * from historico_sentido_1");

        ArrayList<String> stha = new ArrayList<>();
        ArrayList<String> sthb = new ArrayList<>();

        try {
            while (rs1.next()) {
                stha.add(rs1.getString(1));
                sthb.add(rs1.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        allData.setData(stha);
        allData.setVel(sthb);

        //historico sentido 2   
        rs1 = c.getQueryResult("select * from historico_sentido_2");

        ArrayList<String> stha2 = new ArrayList<>();
        ArrayList<String> sthb2 = new ArrayList<>();

        try {
            while (rs1.next()) {
                stha2.add(rs1.getString(1));
                sthb2.add(rs1.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        allData.setData2(stha2);
        allData.setVel2(sthb2);

        //WriteObject
        ObjectOutputStream oos = new ObjectOutputStream(conexao.getOutputStream());
        oos.flush();
        oos.writeObject(allData);

        /*
        Estatísticas dos sentidos, nos ultimos 10 minutos.
        Seria feito com as views estatisticos_sentido_1 e estatisticos_sentido_2,
        que vão sendo atualizadas, mas como neste caso não temos sempre novos dados, 
        fizemos com views que têm os dados todos.
         */
        rs1 = c.getQueryResult("select * from sentido1");
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

        //envia as estatisticas de cada sentido dos últimos 10 minutos
        for (int i = 0; i < dados.length; i++) {
            cliente.println(dados[i]);
        }

    }
}
