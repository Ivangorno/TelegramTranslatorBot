package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class WordUtils {
    @Autowired
    private EnglishToFrenchDictionary englishToFrenchDictionary;
    @Autowired
    private FrenchToEnglishDictionary frenchToEnglishDictionary;

    //How can we simplify this method ?
    public String[] parseEngToFreWord(String newEngWordToAdd) {
        return newEngWordToAdd.split("\\s");
    }

    // It's better to separate this method from EnglishToFrenchDictionary class as it's part of bussines logic
    // move here FrenchToEnglishDictionary logic as well.
    public  boolean isWordValid(String enteredWords) {
        for (char c : enteredWords.toLowerCase().toCharArray()) {
            if (!englishToFrenchDictionary.getEnglishLetters().contains(c) || !frenchToEnglishDictionary.getFrenchLetters().contains(c)) {
                return false;
            }
        }
        return true;
    }

    public void addNewWord(String[] englishAndFrenchWord, Map<String, String> dictionary) {
        dictionary.put(englishAndFrenchWord[1], englishAndFrenchWord[2]);
    }

    public void deleteWord(String wordToDelete, Map<String, String> dictionary) {
        dictionary.remove(wordToDelete);
    }

    public void updateWord(String[] englishAndFrenchWord, Map<String, String> dictionary) {
        dictionary.computeIfPresent(
                englishAndFrenchWord[1], (engWord, frWord) -> engWord.replace(engWord, englishAndFrenchWord[2]));
    }

}

