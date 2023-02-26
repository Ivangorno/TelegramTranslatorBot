package com;

import java.util.Map;

public class FrenchToEnglishTranslationImpl {
    private  TgDictionaryBot tgDictionaryBot;
    private  FrToEngDictionary frToEngDictionary;

    private String frenchWord;
    private String englishWord;

    public String frToEngTranslate(String frenchWord){
        englishWord = frToEngDictionary.getFrenchToEnglishDictionary().get(frenchWord);
        return  englishWord;
    }

    public String engToFrTranslate(String englishWord) {
        for (Map.Entry<String, String> entry : frToEngDictionary.getFrenchToEnglishDictionary().entrySet()) {
            if (frToEngDictionary.getFrenchToEnglishDictionary().containsValue(englishWord)) {
                if (entry.getValue().equalsIgnoreCase(englishWord)) {
                    frenchWord = entry.getKey();
                }

            }
        }
        return frenchWord;
    }



}
