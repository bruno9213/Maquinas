package threading;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Esta classe serve para testar os métodos da classe JFrame_AdminTest.
 */
public class JFrame_AdminTest {
    
    /**
     * Metodo screenWidth (retorna largura do ecra)
     */
    public int screenWidth() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screenSize.getWidth();
    }
    
    /**
     * Metodo screenHeight (retorna altura do ecra)
     */
    public int screenHeight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screenSize.getHeight();
    }
    
    /**
     * Teste do metodo screenWidth, classe JFrame_Admin.
     */
    @Test
    public void testScreenWidth() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //dimensoes do ecra
        int expResult = (int) screenSize.getWidth(); //largura do ecra
        int result = screenWidth();
        assertEquals(expResult, result);
    }

    /**
     * Teste do metodo screenHeight, classe JFrame_Admin.
     */
    @Test
    public void testScreenHeight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //dimensoes do ecra
        int expResult = (int) screenSize.getHeight(); //altura do ecra
        int result = screenHeight();
        assertEquals(expResult, result);
    }  
}
