package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EnglishToFrenchDictionary {
    @Autowired
    private UTF8Validation utf8Validation;

    @Autowired
    private TgDictionaryBot tgDictionaryBot;

    private String frenchWord;
    private String englishWord;
    private Map<String, String> englishToFrenchDictionary = new HashMap<>();

    {
        englishToFrenchDictionary.put("Hello", "Bonjour");
        englishToFrenchDictionary.put("Cat", "Chat");
    }

    public Map<String, String> getEnglishToFrenchDictionary() {
        return englishToFrenchDictionary;
    }


    public String getTranslation(String text) {
        return englishToFrenchDictionary.get(text);
    }

//    public void addNewWord(String frenchWord, String englishWord) {
//        englishToFrenchDictionary.put(frenchWord, englishWord);
//    }

    public void addNewWord(String[] englishAndFrenchWord) {

        if  (utf8Validation.isStrUtf8(englishAndFrenchWord[1])) {
            englishToFrenchDictionary.put(englishAndFrenchWord[1], englishAndFrenchWord[2]);
        }
    }
}

