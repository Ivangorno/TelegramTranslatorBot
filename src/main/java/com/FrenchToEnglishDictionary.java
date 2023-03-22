package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FrenchToEnglishDictionary {
    @Autowired
    private UTF8Validation utf8Validation;

    @Autowired
    private TgDictionaryBot tgDictionaryBot;

    private Set<Character> frenchLetters = new HashSet<>(
            Arrays.asList('a','b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t',
                    'v','w','x','z','e','i','o','u','y', 'â', 'à', 'ç', 'é', 'ê', 'ë', 'è', 'ï', 'î', 'ô', 'û', 'ù'));

    private Map<String, String> frenchToEnglishDictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    {
        frenchToEnglishDictionary.put("Bonjour", "Hello");
        frenchToEnglishDictionary.put("Chat", "Cat");
    }


    public String getFrenchToEnglishTranslation(String text) {
        return frenchToEnglishDictionary.get(text);
    }

    public boolean isFrench(String enteredWords){
        for (char c : enteredWords.toLowerCase().toCharArray()) {
            if (!frenchLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public void addNewWord(String[] frenchAndEnglishWord) {

        frenchToEnglishDictionary.put(frenchAndEnglishWord[1], frenchAndEnglishWord[2]);
    }

}
