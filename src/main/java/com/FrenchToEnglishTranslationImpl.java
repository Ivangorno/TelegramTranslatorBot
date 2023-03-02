package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FrenchToEnglishTranslationImpl {
    @Autowired
    private TgDictionaryBot tgDictionaryBot;
    @Autowired
    private EngToFrenchDictionary engToFrenchDictionary;

    private String frenchWord;
    private String englishWord;

    public String frToEngTranslate(String frenchWord){
        englishWord = engToFrenchDictionary.getEngToFrenchDictionary().get(frenchWord);
        return  englishWord;
    }

    public String engToFrTranslate(String englishWord) {
        return engToFrenchDictionary.getTranslation(englishWord);
    }
}