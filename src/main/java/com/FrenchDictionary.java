package com;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FrenchDictionary {

    private Map<String, String> frToEngDictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    {
        frToEngDictionary.put("Bonjour", "Hello");
        frToEngDictionary.put("Chat", "Cat");
    }

    public Map<String, String> getFrToEngDictionary() {
        return frToEngDictionary;
    }

    public String getFrenchToEnglishTranslation(String text) {
        return frToEngDictionary.get(text);
    }

//    public void addFrWord(String... words) {
//        frToEngDictionary.put(words[0], words[1]);
//    }
//
////    public void deleteWord(String wordToDelete) {
////        frToEngDictionary.remove(wordToDelete);
////    }
//
////    public void updateWord(String[] englishAndFrenchWord) {
////        frToEngDictionary.computeIfPresent(
////                englishAndFrenchWord[1], (frWord, engWord) -> frWord.replace(frWord, englishAndFrenchWord[2]));
////    }

}


