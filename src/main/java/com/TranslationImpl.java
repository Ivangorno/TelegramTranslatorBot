package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TranslationImpl {
    @Autowired
    private TgDictionaryBot tgDictionaryBot;
    @Autowired
    private EnglishToFrenchDictionary englishToFrenchDictionary;
    @Autowired
    private FrenchToEnglishDictionary frenchToEnglishDictionary;

    public String translateEnglishToFrench(String frenchWord) {
        return englishToFrenchDictionary.getEnglishToFrenchTranslation(frenchWord);
    }
    public String translateFrenchToEnglish(String engWord) {
        return frenchToEnglishDictionary.getFrenchToEnglishTranslation(engWord);
    }
}