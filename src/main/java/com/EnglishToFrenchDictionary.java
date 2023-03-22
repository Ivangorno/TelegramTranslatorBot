package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EnglishToFrenchDictionary {
    @Autowired
    private UTF8Validation utf8Validation;

    @Autowired
    private TgDictionaryBot tgDictionaryBot;

    private Set<Character> englishLetters = new HashSet<>(
            Arrays.asList('a','b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t',
            'v','w','x','z','e','i','o','u','y'));

        private Map<String, String> englishToFrenchDictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public Map<String, String> getEnglishToFrenchDictionary() {
        return englishToFrenchDictionary;
    }

    {
        englishToFrenchDictionary.put("Hello", "Bonjour");
        englishToFrenchDictionary.put("Cat", "Chat");
    }

//    public Map<String, String> getEnglishToFrenchDictionary() {
//        return englishToFrenchDictionary;
//    }

    public String getEnglishToFrenchTranslation(String text) {
        return englishToFrenchDictionary.get(text);
    }

    public boolean isEnglish(String enteredWords){
        for (char c : enteredWords.toLowerCase().toCharArray()) {
            if (!englishLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public void addNewWord(String[] englishAndFrenchWord) {
        englishToFrenchDictionary.put(englishAndFrenchWord[1], englishAndFrenchWord[2]);
    }

    public  void deleteWord(String wordToDelete){
             englishToFrenchDictionary.remove(wordToDelete);
    }

    public void updateWord(String[] englishAndFrenchWord){
        englishToFrenchDictionary.computeIfPresent(englishAndFrenchWord[1], (engWord, frWord) -> engWord.replace(engWord, englishAndFrenchWord[2]));
    }
}

