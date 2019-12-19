package threading;

import java.io.*;
import java.net.*;

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

    public Client(Socket s, JFrame_Cliente j) {
        conexao = s;
        this.j = j;
    }

    public void run() {
        
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
            j.setDados(ar);
            for(int z=0; z<ar.length; z++){             
                System.out.println(ar[z]);
            }
            
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        done = true;
    }
}
