package com.utill;

import com.EnglishDictionary;
import com.FrenchDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;


@Component
public class WordUtils {
    @Autowired
    private EnglishDictionary englishDictionary;
    @Autowired
    private FrenchDictionary frenchToEnglishDictionary;

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

    public void addNewWord(String enteredWord, String translation,Map<String, String> primaryDictionary, Map<String, String> secondaryDictionary) {
        primaryDictionary.put(enteredWord, translation);
        secondaryDictionary.put(translation, enteredWord);
    }

    public void deleteWord(String wordToDelete, Map<String, String> dictionary) {
        dictionary.remove(wordToDelete);
    }

    public void updateWord(String[] englishAndFrenchWord, Map<String, String> dictionary) {
        dictionary.computeIfPresent(
                englishAndFrenchWord[1], (engWord, frWord) -> engWord.replace(engWord, englishAndFrenchWord[2]));
    }



    //Example, delete later
//    @Autowired
//    private TgDictionaryBot tgDictionaryBot;
//
//    private void someMethod() {
//        SendMessage sendMessage = new SendMessage();
//        tgDictionaryBot.sendMessage(sendMessage);
//    }
}
