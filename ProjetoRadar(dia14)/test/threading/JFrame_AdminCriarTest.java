/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bruno
 */
public class JFrame_AdminCriarTest {
    
    /**
     * Metodo AutenticarUserTest (copiado da classe JFrame_AdminCriar)
     */
    public boolean isValidEmail(String email) {
        boolean valido = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                valido = true;
            }
        }
        return valido;
    }

    /**
     * Teste do metodo isValidEmail, da classe JFrame_AdminCriar.
     */
    @Test
    public void testIsValidEmail() {
        assertEquals(true, isValidEmail("nome@mail.com")); //Teste com endereço correto
        assertEquals(true, isValidEmail("123@mail.com")); //Teste com endereço correto, com numeros
        assertEquals(true, isValidEmail("nome@mail.com.pt")); //Teste com endereço correto, com sub dominio
        assertEquals(false, isValidEmail("nomemail.com")); //Teste com endereço incorreto, sem @
        assertEquals(false, isValidEmail("nome@mailcom")); //Teste com endereço incorreto, sem .
        assertEquals(false, isValidEmail("nome@mail@com")); //Teste com endereço incorreto, com dois @
        assertEquals(false, isValidEmail("nome.mail.com")); //Teste com endereço incorreto, com dois .
        assertEquals(false, isValidEmail("@nome@mail.com")); //Teste com endereço incorreto, com inicio invalido
        assertEquals(false, isValidEmail("nome@mail..com")); //Teste com endereço incorreto, com pontos a mais
        assertEquals(false, isValidEmail("nome@mail.321")); //Teste com endereço incorreto, numeros no fim
        assertEquals(false, isValidEmail("nome@@mail.com")); //Teste com endereço incorreto, dois caracteres @        
        assertEquals(false, isValidEmail("nome@mail.aaaaaa")); //Teste com endereço incorreto, caracteres a mais no dominio
    }
    
}
