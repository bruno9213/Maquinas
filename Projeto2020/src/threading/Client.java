package threading;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe Client é a aplicação cliente que se conecta à aplicação Server.
 *
 */
public class Client extends Thread {

    private static boolean done = false;

    public Socket conexao;

    private JFrame_Cliente j;

    /**
     * Construtor desta Classe, recebe como parâmetros Socket, que é a conexão
     * com o servidor e uma nova JFrame_Cliente, que será a interface da
     * aplicação.
     *
     * @param s
     * @param j
     */
    public Client(Socket s, JFrame_Cliente j) {
        conexao = s;
        this.j = j;
    }

    /**
     * No main inicia uma conexão ao servidor e cria uma nova Thread de Client e
     * coloca a JFrame, a interface do cliente visível.
     *
     * @param args
     */
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

    /**
     * Retorna a largura do ecrã.
     *
     * @return
     */
    public int screenWidth() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screenSize.getWidth();
    }

    /**
     * Retorna a altura do ecrã.
     *
     * @return
     */
    public int screenHeight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screenSize.getHeight();
    }

    /**
     * Vai correr quando a nova thread começa, recebe os dados que o Server
     * envia.
     */
    public void run() {

        //centrar a form
        j.setLocation((screenWidth() / 2) - (j.getSize().width / 2), (screenHeight() / 2) - (j.getSize().height / 2));

        //Ler objeto Dados que tem os dados do Radar e do Historico
        try {
            ObjectInputStream ois = new ObjectInputStream(conexao.getInputStream());
            Dados r = (Dados) ois.readObject();
            j.setAllData(r);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: " + ex);
        }

        //Estatisticas de cada sentido dos últimos 10 minutos (manter em ultimo por causa do loop)
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
