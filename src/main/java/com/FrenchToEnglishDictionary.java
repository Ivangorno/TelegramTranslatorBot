package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FrenchToEnglishDictionary {

    @Autowired
    private TgDictionaryBot tgDictionaryBot;

    private Map<String, String> frToEngDictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    {
        frToEngDictionary.put("Bonjour", "Hello");
        frToEngDictionary.put("Chat", "Cat");
    }

    public String getFrenchToEnglishTranslation(String text) {
        return frToEngDictionary.get(text);
    }

    public void addFrWord(String... words) {
        frToEngDictionary.put(words[1], words[2]);
    }

    public void deleteWord(String wordToDelete) {
        frToEngDictionary.remove(wordToDelete);
    }

    public void updateWord(String[] englishAndFrenchWord) {
        frToEngDictionary.computeIfPresent(
                englishAndFrenchWord[1], (engWord, frWord) -> engWord.replace(engWord, englishAndFrenchWord[2]));
    }

}


