package com;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EnglishToFrenchDictionary {

    private Map<String, String> engToFrDictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    {
        engToFrDictionary.put("Hello", "Bonjour");
        engToFrDictionary.put("Cat", "Chat");
    }

    public Map<String, String> getEngToFrDictionary() {
        return engToFrDictionary;
    }

    public String getEnglishToFrenchTranslation(String text) {
        return engToFrDictionary.get(text);
    }

    public void addEngWord(String... words) {
        engToFrDictionary.put(words[0], words[1]);
    }

    public void deleteWord(String wordToDelete) {
        engToFrDictionary.remove(wordToDelete);
    }

    public void updateWord(String[] englishAndFrenchWord) {
        engToFrDictionary.computeIfPresent(
                englishAndFrenchWord[1], (engWord, frWord) -> engWord.replace(engWord, englishAndFrenchWord[2]));
    }

}

