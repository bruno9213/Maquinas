/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bruno
 */
public class PassTest {

    /**
     * Teste do metodo generateRandomPassword, da classe Pass.
     */
    @Test
    public void testGenerateRandomPassword() {
        int length = 10;
        Pass instance = new Pass();
        String result = instance.generateRandomPassword(length);
        assertNotEquals("", result);    //Testar se nao e vazia
        assertNotEquals(null, result);  //Testar se nao e nula
        assertEquals(length, result.length());  //testar se o tamanho esta correto
    }

    /**
     * Teste do metodo shuffleString, da classe Pass.
     */
    @Test
    public void testShuffleString() {
        String string = "ABCDEFGH";
        Pass instance = new Pass();
        String result = instance.shuffleString(string);
        assertNotEquals(string, result);
    }

}
