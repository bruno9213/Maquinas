package threading;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe Login, serve de servidor para a autenticação.
 *
 */
public class Login extends Thread {

    private Socket conexao;

    /**
     * Recebe como parâmetros a conexao socket.
     *
     * @param s
     */
    public Login(Socket s) {
        conexao = s;
    }

    /**
     * O método startLoginServer serve para iniciar uma nova thread.
     */
    public void startLoginServer() {
        Thread t = new Login(conexao);
        t.start();
        System.out.println("Nova conexão ao server Login");

    }

    /**
     * A thread vai começar e vai receber e enviar a validação ou não do login
     */
    public void run() {

        try {
            boolean id = true;
            while (id == true) {
                DataInputStream dis = new DataInputStream(conexao.getInputStream());
                String comando = dis.readUTF();
                if (comando.equals("login")) {
                    login(conexao);
                }
                if (comando.equals("criar")) {
                    System.out.println("here1");
                    registarEntidade(conexao);
                }

                /*
                ACHO QUE AQUI SE PODIA FAZER ALGO PARA AS ENTIDADES.
                COM COMANDOS SE LHE ENVIASSE O COMANDO PARA FAZER LOGIN FAZIA O QUE ESTÁ EM CIMA
                SE LHE ENVIASSE O COMANDO PARA IR VER AS ENTIDADES IA VER, 
                SE LHE ENVIASSE O COMANDO PARA IR REGISTAR ENTIDADES IA REGISTÁ-LAS
                OU ALGO ASSIM
                 */
                id = false;
            }
            conexao.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

    }

    public void registarEntidade(Socket conexao) {
        try {
            System.out.println("here2");
            JDBCConnect c = new JDBCConnect();
            DataInputStream dis = new DataInputStream(conexao.getInputStream());
            String nome = dis.readUTF();
            String user = dis.readUTF();
            String mail = dis.readUTF();
            int type = dis.readInt();
            System.out.println("here3");
            ResultSet rs1 = c.getQueryResult("select id from entidades order by id desc");
            rs1.next();
            int id = rs1.getInt(1);
            System.out.println(id);
            System.out.println("here5");
            Pass pass = new Pass();
            String pwd = pass.generateRandomPassword(5);
            c.insert_login(user, pwd);
            System.out.println("here6");
            c.insert_entidade(id + 1, nome, user, mail, type);

        } catch (SQLException ex) {
            System.out.println("SQLException1: " + ex);
        } catch (IOException ex) {
            System.out.println("IOExceptionaaa: " + ex);
        }
    }

    /**
     * Método login recebe como parâmetros uma conexao Socket e vai receber e
     * validar o login
     *
     * @param conexao
     */
    public void login(Socket conexao) {
        try {
            //receber login
            DataInputStream dis = new DataInputStream(conexao.getInputStream());
            String user = dis.readUTF();
            String pass = dis.readUTF();
            System.out.println("autenticação dados" + user + "," + pass);

            //verificar login
            boolean login = autenticarUser(user, pass);
            boolean admin = checkAdmin(user);

            //enviar login
            DataOutputStream dout = new DataOutputStream(conexao.getOutputStream());
            dout.writeBoolean(login);
            dout.writeBoolean(admin);
            dout.flush();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método autenticarUser recebe como parâmetros Srings com o username e a
     * password. Vai se existem na BD e se são coincidentes. Caso isso aconteça
     * retorna true.
     *
     * @param user
     * @param pass
     * @return
     */
    public boolean autenticarUser(String user, String pass) {
        JDBCConnect c = new JDBCConnect();
        ResultSet rs = c.getQueryResult("select * from login");
        ArrayList<String> dataUser = new ArrayList<>();

        try {
            while (rs.next()) {
                dataUser.add(rs.getString(1) + "," + rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (String tmp : dataUser) {
            if (tmp.equals(user + "," + pass)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Método checkAdmin recebe uma String com o user e vai à BD verificar se
     * este user é administrador ou não, pois isso foi guardado num campo na
     * tabela entidades da BD.
     *
     * @param user
     * @return
     */
    public boolean checkAdmin(String user) {
        JDBCConnect c = new JDBCConnect();
        ResultSet rs = c.getQueryResult("select * from entidades");
        ArrayList<String> dataUser = new ArrayList<>();
        try {
            while (rs.next()) {
                dataUser.add(rs.getString(3) + "," + rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean admin = false;
        for (String tmp : dataUser) {
            if (tmp.equals(user + "," + "0")) {
                admin = true;
                System.out.println("Administrador fez login.");
            }
        }
        return admin;
    }

}
