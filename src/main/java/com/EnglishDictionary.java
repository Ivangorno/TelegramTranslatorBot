package com;

import com.db.DictionaryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EnglishDictionary {
    @Autowired
    private DictionaryController dictionaryController;

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

}

