package threading;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe Login
 *
 */
public class Login {

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
