package com;

import com.dataBase.DictionaryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TranslationImpl {
    @Autowired
    private EnglishDictionary englishDictionary;
    @Autowired
    private DictionaryController dictionaryController;
    @Autowired
    private FrenchDictionary frenchToEnglishDictionary;

    public String translateEnglishToFrench(String englishWord) {
        return dictionaryController.getEnglishToFrenchTranslation(englishWord);
    }

    public String translateFrenchToEnglish(String engWord) {
        return frenchToEnglishDictionary.getFrenchToEnglishTranslation(engWord);
    }
}