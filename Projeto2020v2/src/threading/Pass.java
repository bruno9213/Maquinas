/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Carolina Tavares
 */
public class Pass {
    
    private final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private final String NUMBER = "0123456789";
    private final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

    private final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    // optional, make it more random
    private final String PASSWORD_ALLOW_BASE_SHUFFLE = shuffleString(PASSWORD_ALLOW_BASE);
    private final String PASSWORD_ALLOW = PASSWORD_ALLOW_BASE_SHUFFLE;

    private SecureRandom random = new SecureRandom();
    
    public String generateRandomPassword(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            int rndCharAt = random.nextInt(PASSWORD_ALLOW.length());
            char rndChar = PASSWORD_ALLOW.charAt(rndCharAt);


            sb.append(rndChar);

        }

        return sb.toString();

    }
     
     public String shuffleString(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        return letters.stream().collect(Collectors.joining());
    }
  
}
