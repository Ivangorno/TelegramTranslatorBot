package com.utill;

import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class SpellCheck {

    public String[] parseToSeparateWords(String enteredText) {
        return enteredText.split("\\s");
    }

    public boolean isWordValid(String enteredWord, Set<Character> allowedLetters) {
        for (char c : enteredWord.toLowerCase().toCharArray()) {
            if (!allowedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

}
