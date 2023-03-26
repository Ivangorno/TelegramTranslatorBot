package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TranslationImpl {
    @Autowired
    private TgDictionaryBot tgDictionaryBot;
    @Autowired
    private EnglishToFrenchDictionary englishToFrenchDictionary;

    private String frenchWord;
    private String englishWord;

//    public String translateFrenchToEnglish(String frenchWord){
//        return  englishToFrenchDictionary.getEnglishToFrenchDictionary().get(frenchWord);
//    }

    public String translateEnglishToFrench(String frenchWord) {
        return englishToFrenchDictionary.getEnglishToFrenchTranslation(frenchWord);
    }
}