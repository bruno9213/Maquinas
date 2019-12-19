package threading;

import java.io.*;
import java.net.*;

public class Client extends Thread {

    private static boolean done = false;

    public static void main(String args[]) {
        try {
            Socket conexao = new Socket("127.0.0.1", 8090);
            Thread t = new Client(conexao);
            t.start();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
    private Socket conexao;

    public Client(Socket s) {
        conexao = s;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha;
            while (true) {
                linha = entrada.readLine();
                if (linha == null) {
                    System.out.println("Conexão encerrada!");
                    break;
                }
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        done = true;
    }
}
