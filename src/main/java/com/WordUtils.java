package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Set;


@Component
public class WordUtils {
    @Autowired
    private EnglishToFrenchDictionary englishToFrenchDictionary;
    @Autowired
    private FrenchToEnglishDictionary frenchToEnglishDictionary;

    public String[] parseEngToFreWord(String newEngWordToAdd) {
        return newEngWordToAdd.split("\\s");
    }

    public boolean isWordValid(String enteredWords, Set<Character> allowedLetters) {
        for (char c : enteredWords.toLowerCase().toCharArray()) {
            if (!allowedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public void addNewWord(String engWord, String frWord) {
        englishToFrenchDictionary.addEngWord(engWord, frWord);
        frenchToEnglishDictionary.addFrWord(frWord, engWord);
    }

    //Example, delete later
    @Autowired
    private TgDictionaryBot tgDictionaryBot;

    private void someMethod() {
        SendMessage sendMessage = new SendMessage();
        tgDictionaryBot.sendMessage(sendMessage);
    }
}
