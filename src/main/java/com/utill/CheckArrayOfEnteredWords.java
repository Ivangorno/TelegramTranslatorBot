package com.utill;

import com.EnglishToFrenchDictionary;
import com.TgDictionaryBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// Try to use one method to checkArray
// Little hint you could move logic of checking if word exist into db layer where interactions
// with words should take place.
public class CheckArrayOfEnteredWords {

    @Autowired
    private EnglishToFrenchDictionary englishToFrenchDictionary;

    @Autowired
    private TgDictionaryBot tgDictionaryBot;

    public boolean checkArray(String[] englishAndFrenchWord) {
        if (englishAndFrenchWord.length == 3) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkArrayToDeleteWords(String[] wordToDelete) {
        if (wordToDelete.length == 2) {
            return true;
        } else {
            return false;
        }
    }
}



