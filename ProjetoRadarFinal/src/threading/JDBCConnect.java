package threading;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

/**
 * Classe JDBCConnect serve para criar uma ligação à Base de Dados pretendida.
 *
 */
public class JDBCConnect {

    private final String url = "jdbc:postgresql://localhost/bdptda";
    private final String user = "insira o seu username do postgres";
    private final String password = "insira a sua password do postgres";

    /**
     * Método construtor que retorna um objeto de Connection com a conexão à BD.
     *
     * @return
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex);
        }
        return conn;
    }

    /**
     * Método getQueryResult recebe uma String que é a query, executa-a na BD e
     * retorna um ResultSet com resultado da query da BD.
     *
     * @param str
     * @return
     */
    public ResultSet getQueryResult(String str) {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        ResultSet rs = null;
        try {
            Statement stm = (Statement) conn.createStatement();
            rs = stm.executeQuery(str);
        } catch (SQLException ex) {
        }
        return rs;
    }

    /**
     * Método exQuery recebe uma String que é a query e executa-a na BD.
     *
     * @param str
     */
    public void exQuery(String str) {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        try {
            Statement stm = (Statement) conn.createStatement();
            stm.executeQuery(str);
        } catch (SQLException ex) {
        }
    }

    /**
     * Método insert_login recebe como parâmetros Strings do user e da pass,
     * para inserir um novo tuplo na base de dados, na tabela login.
     *
     * @param user
     * @param pass
     */
    public void insert_login(String user, String pass) {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        String inserir = "insert into login values('" + user + "','" + pass + "')";
        try {
            Statement stm2 = (Statement) conn.createStatement();
            stm2.executeQuery(inserir);
        } catch (SQLException ex) {
        }
    }

    /**
     * Método delete_entidade recebe como parãmetros uma String com o user para
     * depois eliminar da tabela login e entidades.
     *
     * @param user
     */
    public void delete_entidade(String user) {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        String delete = "delete from login where \"user\"" + "='" + user + "'";
        try {
            Statement stm2 = (Statement) conn.createStatement();
            stm2.executeQuery(delete);
        } catch (SQLException ex) {
        }
    }

    /**
     * Método insert_entidade recebe como parâmetros os dados de uma entidade e
     * depois insere-a na tabela entidades da BD:
     *
     * @param id
     * @param nome
     * @param user
     * @param mail
     * @param id_type
     */
    public void insert_entidade(int id, String nome, String user, String mail, int id_type) {
        JDBCConnect app = new JDBCConnect();
        Connection conn = app.connect();
        String inserir = "insert into entidades values('" + id + "','" + nome + "','" + user + "','" + mail + "','" + id_type + "')";
        try {
            Statement stm2 = (Statement) conn.createStatement();
            stm2.executeQuery(inserir);
        } catch (SQLException ex) {
        }
    }
}
