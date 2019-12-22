package threading;

import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread {

    private static boolean done = false;

    public static void main(String args[]) {
        try {

            Socket conexao = new Socket("127.0.0.1", 8090);

            Thread t;
            JFrame_Cliente j = new JFrame_Cliente();
            t = new Thread(new Client(conexao, j));

            t.start();
            j.setVisible(true);

        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
    private Socket conexao;
    private JFrame_Cliente j;
    public boolean logado, admin;

    public Client(Socket s, JFrame_Cliente j) {
        conexao = s;
        this.j = j;
        logado = true;
        admin = true;
    }

    public void sendStatus(PrintStream cliente) throws IOException {
        System.out.println("here:" + admin + logado);

        if (admin == true) {
            cliente.println(2);
        } else if (logado == true) {
            cliente.println(1);
        } else {
            cliente.println(0);
        }
    }

    public void run() {
        try {
            System.out.println("here:" + admin + logado);
            //PrintStream cliente = new PrintStream(conexao.getOutputStream());
            DataOutputStream dout = new DataOutputStream(conexao.getOutputStream());
            if (admin == true) {
                dout.writeUTF("2");
            } else if (logado == true) {
                dout.writeUTF("1");
            } else {
                dout.writeUTF("0");
            }
            System.out.println("here woo woo");

            dout.flush(); // send the message
            System.out.println("here message sent");

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] ar = new String[12];
        int i = 0;
        Radar r;
        try {
            ObjectInputStream ois = new ObjectInputStream(conexao.getInputStream());
            r = (Radar) ois.readObject();
            j.setRadar(r);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: " + ex);
        }

        ArrayList<Historico> h;
        try {
            ObjectInputStream ois = new ObjectInputStream(conexao.getInputStream());
            h = (ArrayList<Historico>) ois.readObject();
            j.setHistorico(h);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: " + ex);
        }
            
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha;
            while (true) {
                linha = entrada.readLine();
                ar[i] = linha;
                i++;
                if (linha == null) {
                    break;
                }
            }
            j.setDados(ar);

//            for (int z = 0; z < ar.length; z++) {
//                System.out.println(ar[z]);
//            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        done = true;
    }
}
