package threading;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.*;

/**
 * Classe Client é a aplicação cliente que se conecta à aplicação Server.
 *
 */
public class Client extends Thread {

    private boolean done = false;

    public Socket conexao;

    private final JFrame_Cliente j;

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
     * Método screenWidth retorna a largura do ecrã.
     *
     * @return
     */
    public int screenWidth() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screenSize.getWidth();
    }

    /**
     * Método screenHeight retorna a altura do ecrã.
     *
     * @return
     */
    public int screenHeight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screenSize.getHeight();
    }

    /**
     * Vai correr quando a nova thread do CLiente começa e recebe os dados que o
     * Server envia.
     */
    @Override
    public void run() {

        //centrar a form
        j.setLocation((screenWidth() / 2) - (j.getSize().width / 2), (screenHeight() / 2) - (j.getSize().height / 2));

        //Ler objeto Dados que tem os dados do Radar, do Historico e das Estatisticas
        try {
            ObjectInputStream ois = new ObjectInputStream(conexao.getInputStream());
            Dados r = (Dados) ois.readObject();
            j.setAllData(r);
            j.setDadosSentido(r.getEstatisticas());
            System.out.println("Dados Recebidos.");
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: " + ex);
        }

        //FIM DA THREAD
        done = true;
        try {
            conexao.close();
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }
    }
}
