package com;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EnglishDictionary {

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

//    public void addEngWord(String... words) {
//        engToFrDictionary.put(words[0], words[1]);
//    }


}

