package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EnglishToFrenchDictionary {

    private Map<String, String> englishToFrenchDictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    {
        englishToFrenchDictionary.put("Hello", "Bonjour");
        englishToFrenchDictionary.put("Cat", "Chat");
    }

    public Map<String, String> getEnglishToFrenchDictionary() {
        return englishToFrenchDictionary;
    }

    public String getEnglishToFrenchTranslation(String text) {
        return englishToFrenchDictionary.get(text);
    }

    public void addNewWord(String[] englishAndFrenchWord) {
        englishToFrenchDictionary.put(englishAndFrenchWord[1], englishAndFrenchWord[2]);
    }

    public void deleteWord(String wordToDelete) {
        englishToFrenchDictionary.remove(wordToDelete);
    }

    public void updateWord(String[] englishAndFrenchWord) {
        englishToFrenchDictionary.computeIfPresent(
                englishAndFrenchWord[1], (engWord, frWord) -> engWord.replace(engWord, englishAndFrenchWord[2]));
    }
}

