package threading;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Esta classe serve para testar os métodos da classe JFrame_Historico.
 */
public class JFrame_HistoricoTest {

    /**
     * O método maxVel calcula o valor máximo do histórico.
     *
     * @param vel1
     * @param vel2
     */
    public int maxVel(String[] vel1, String[] vel2) {
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < vel1.length; i++) {
            a.add(Integer.parseInt(vel1[i]));
        }
        for (int i = 0; i < vel2.length; i++) {
            a.add(Integer.parseInt(vel2[i]));
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) > max) {
                max = a.get(i);
            }
        }
        return max;
    }

    /**
     * O método minVel calcula o valor minimo do histórico.
     *
     * @param vel1
     * @param vel2
     */
    public int minVel(String[] vel1, String[] vel2) {
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < vel1.length; i++) {
            a.add(Integer.parseInt(vel1[i]));
        }
        for (int i = 0; i < vel2.length; i++) {
            a.add(Integer.parseInt(vel2[i]));
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) < min) {
                min = a.get(i);
            }
        }
        return min;
    }

    /**
     * Teste do metodo maxVel, da classe JFrame_Historico.
     */
    @Test
    public void testMaxVel() {
        String[] vel1 = new String[3];
        String[] vel2 = new String[3];
        vel1[0] = "124";
        vel1[1] = "25";
        vel1[2] = "45";
        vel2[0] = "224";
        vel2[1] = "55";
        vel2[2] = "75";
        int expResult = 224;
        int result = maxVel(vel1, vel2);
        assertEquals(expResult, result);
    }

    /**
     * Teste do metodo minVel, da classe JFrame_Historico.
     */
    @Test
    public void testMinVel() {
        String[] vel1 = new String[3];
        String[] vel2 = new String[3];
        vel1[0] = "124";
        vel1[1] = "25";
        vel1[2] = "45";
        vel2[0] = "224";
        vel2[1] = "55";
        vel2[2] = "75";
        int expResult = 25;
        int result = minVel(vel1, vel2);
        assertEquals(expResult, result);
    }

}
