package com.utill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    public String capitalizeText(String enteredText){
      return   StringUtils.capitalize(enteredText);
    }

    public String toCorrectCapitalization(String str){
        String output = "";

        for (int i=0; i < str.length(); i++ ){
         output = str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
        }

        return output;
    }

}
