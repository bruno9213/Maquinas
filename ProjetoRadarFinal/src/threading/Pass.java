package threading;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe Pass serve para a geração de uma password aleatória.
 *
 */
public class Pass {

    private final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private final String NUMBER = "0123456789";
    private final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

    private final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;

    private final String PASSWORD_ALLOW_BASE_SHUFFLE = shuffleString(PASSWORD_ALLOW_BASE);
    private final String PASSWORD_ALLOW = PASSWORD_ALLOW_BASE_SHUFFLE;

    private final SecureRandom random = new SecureRandom();

    /**
     * O método generateRandomPassword recebe como parâmetro o tamanho desejado
     * da password a gerar e retorna-a numa String.
     *
     * @param length
     * @return
     */
    public String generateRandomPassword(int length) {
        if (length < 1) {
            throw new IllegalArgumentException();
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            int rndCharAt = random.nextInt(PASSWORD_ALLOW.length());
            char rndChar = PASSWORD_ALLOW.charAt(rndCharAt);

            sb.append(rndChar);
        }
        return sb.toString();
    }

    /**
     * O método shuffleString recebe como parâmetros uma String de várias
     * letras, caracteres e números e baralha esta String.
     *
     * @param string
     * @return
     */
    public String shuffleString(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        return letters.stream().collect(Collectors.joining());
    }

}
