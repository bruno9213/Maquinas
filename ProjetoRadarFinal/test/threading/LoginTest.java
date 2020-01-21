package threading;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Esta classe serve para testar os métodos da classe Login.
 */
public class LoginTest {
    
    /**
     * Metodo AutenticarUserTest (copiado da classe Login)
     */
    public boolean autenticarUserTest(String user, String pass) {
        ArrayList<String> dataUser = new ArrayList<>();

        dataUser.add("userdummy,passdummy");
        dataUser.add("userdummy1,passdummy1");
        dataUser.add("userdummy2,passdummy2");

        for (String tmp : dataUser) {
            if (tmp.equals(user + "," + pass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo checkAdminTest (copiado da classe Login)
     */
    public boolean checkAdminTest(String user) {
        ArrayList<String> dataUser = new ArrayList<>();

        dataUser.add("userdummy,0");
        dataUser.add("userdummy1,1");
        dataUser.add("userdummy2,0");
        boolean admin = false;
        for (String tmp : dataUser) {
            if (tmp.equals(user + "," + "0")) {
                admin = true;
            }
        }
        return admin;
    }

    /**
     * Teste do metodo autenticarUserTest, da classe Login.
     */
    @Test
    public void testAutenticarUserTest() {
        String user = "userdummy1";
        String pass = "passdummy1";
        boolean expResult = true;
        boolean result = autenticarUserTest(user, pass);
        assertEquals(expResult, result);
    }

    /**
     * Teste do metodo checkAdmin, da classe Login.
     */
    @Test
    public void testCheckAdmin() {
        String user = "userdummy";
        boolean expResult = true;
        boolean result = checkAdminTest(user);
        assertEquals(expResult, result);
    }

}
