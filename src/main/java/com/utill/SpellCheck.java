package com.utill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.utill.AllowedLetters.*;

@Component
public class SpellCheck {

    @Autowired
    ModeSelector activeLanguage;

    public String[] parseToSeparateWords(String enteredText) {
        return enteredText.split("\\s");
    }

    private Set<Character> allowedLetters;
    private Set<Character> allowedLetters2;
    private Set<Character> allowedLetters3;


    public boolean isWordValid(String enteredWord) {

        if (activeLanguage.isEnglish()) {
            allowedLetters = ENGLISH_LETTERS;
        } else {
            allowedLetters = FRENCH_LETTERS;
        }

        for (char c : enteredWord.toLowerCase().toCharArray()) {
            if (!allowedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

}
