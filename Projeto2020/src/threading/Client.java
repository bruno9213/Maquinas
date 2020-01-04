package threading;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.*;
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
    public Socket conexao;
    private JFrame_Cliente j;
    public boolean logado, admin;

    public Client(Socket s, JFrame_Cliente j) {
        conexao = s;
        this.j = j;
        logado = false; //cliente normal começa sempre sem login
        admin = false;
    }

    public Socket getConexao() {
        return conexao;
    }
    
    public void login_ent(){
        logado=true;
    }
    
    public void login_admin(){
        logado=true;
        admin=true;
    }

    public int screenWidth(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();       
        return (int) screenSize.getWidth();
    }
    
    public int screenHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();       
        return (int) screenSize.getHeight();
    }
    
    public void sendStatus(Client c){
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
            dout.flush(); // send the message
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {

        //centrar a form
        j.setLocation((screenWidth()/2)-(j.getSize().width/2), (screenHeight()/2)-(j.getSize().height/2));
        
        //STATUS
        sendStatus(this);
        
        //Ler todos os dados 
        try {
            ObjectInputStream ois = new ObjectInputStream(conexao.getInputStream());
            Dados r = (Dados) ois.readObject();
            j.setAllData(r);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: " + ex);
        }

        //DADOS RADAR SENTIDO 1 e 2 (manter em ultimo por causa do loop)
        String[] ar = new String[12];
        int i = 0;
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
            j.setDadosSentido(ar);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        
        //FIM DA THREAD
        done = true;
    }
}
