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
//                else if (comando.equals("lista entidades")) {
//                    listaEntidades(conexao);
//                }
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
     * Método que retorna um Objeto da Classe Entidades com todos os dados da BD
     * das entidades
     *
     * @return
     */
    public void listaEntidades(Socket conexao) {
        ObjectOutputStream oos = null;
        try {
            JDBCConnect c = new JDBCConnect();
            //criacao objeto Entidades
            Entidades e = new Entidades();
            //historico sentido 1
            ResultSet rs1 = c.getQueryResult("select e.id, e.nome, e.user, e.mail, t.nome from entidades e join type_entidades t on e.type_entidadeid=t.id");
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> nome = new ArrayList<>();
            ArrayList<String> user = new ArrayList<>();
            ArrayList<String> mail = new ArrayList<>();
            ArrayList<String> type = new ArrayList<>();
            try {
                while (rs1.next()) {
                    id.add(rs1.getString(1));
                    nome.add(rs1.getString(2));
                    user.add(rs1.getString(3));
                    mail.add(rs1.getString(4));
                    type.add(rs1.getString(5));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.setId(id);
            e.setMail(mail);
            e.setNome(nome);
            e.setType(type);
            e.setUser(user);
            oos = new ObjectOutputStream(conexao.getOutputStream());
            oos.flush();
            oos.writeObject(e);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
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
