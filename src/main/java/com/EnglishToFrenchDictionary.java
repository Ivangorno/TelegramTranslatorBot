package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EnglishToFrenchDictionary {

    private  Set<Character> englishLetters = Set.of('a', 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't',
                    'v', 'w', 'x', 'z', 'e', 'i', 'o', 'u', 'y');

    public  Set<Character> getEnglishLetters() {
        return englishLetters;
    }

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

}

