package threading;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe Login, serve de servidor para a autenticação.
 *
 */
public class Login extends Thread {

    private final Socket conexao;

    /**
     * Contrutor classe Login que recebe como parâmetros a conexao socket.
     *
     * @param s
     */
    public Login(Socket s) {
        conexao = s;
    }

    /**
     * O método startLoginServer serve para iniciar uma nova thread.
     *
     */
    public void startLoginServer() {
        Thread t = new Login(conexao);
        t.start();
    }

    /**
     * A thread vai começar e esperar por comandos: login, criar ou eliminar,
     * para fazer a autenticação de utilizadores, criação de entidades ou
     * eliminação de entidades, respetivamente.
     *
     */
    @Override
    public void run() {
        try {
            try (conexao) {
                boolean id = true;
                while (id == true) {
                    DataInputStream dis = new DataInputStream(conexao.getInputStream());
                    String comando = dis.readUTF();
                    if (comando.equals("login")) {
                        login(conexao);
                    }
                    if (comando.equals("criar")) {
                        registarEntidade(conexao);
                    }
                    if (comando.equals("eliminar")) {
                        eliminarEntidade(conexao);
                    }
                    id = false;
                }
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    /**
     * Método registarEntidade recebe como parâmetros uma conexao Socket, vai
     * receber os dados da entidade, gerar uma Password aleatória e inserir os
     * dados na tabela do login e das entidades.
     *
     * @param conexao
     */
    public void registarEntidade(Socket conexao) {
        try {
            JDBCConnect c = new JDBCConnect();
            DataInputStream dis = new DataInputStream(conexao.getInputStream());
            String nome = dis.readUTF();
            String user = dis.readUTF();
            String mail = dis.readUTF();
            int type = dis.readInt();

            ResultSet rs1 = c.getQueryResult("select id from entidades order by id desc");
            rs1.next();
            int id = rs1.getInt(1);

            Pass pass = new Pass();
            String pwd = pass.generateRandomPassword(10);
            c.insert_login(user, pwd);
            c.insert_entidade(id + 1, nome, user, mail, type);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }
    }

    /**
     * Método login recebe como parâmetros uma conexao Socket e vai receber e
     * validar o login.
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
            System.out.println("IOException: " + ex);
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
            System.out.println("SQLException: " + ex);
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
            System.out.println("SQLException: " + ex);
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

    /**
     * Método eliminarEntidade recebe como parâmetros uma conexao Socket e vai
     * receber uma String do user a eliminar, e elimana-o da BD.
     *
     * @param conexao
     */
    private void eliminarEntidade(Socket conexao) {
        try {
            JDBCConnect c = new JDBCConnect();
            DataInputStream dis = new DataInputStream(conexao.getInputStream());
            String user = dis.readUTF();
            c.delete_entidade(user);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }
    }
}
