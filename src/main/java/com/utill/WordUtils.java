package com.utill;

import com.db.EnglishDictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;


@Component
public class WordUtils {
    @Autowired
    private EnglishDictionaryDao englishDictionaryDao;
    public String[] parseEngToFreWord(String newEngWordToAdd) {
        return newEngWordToAdd.split("\\s");
    }

    public boolean isWordValid(String enteredWord, Set<Character> allowedLetters) {
        for (char c : enteredWord.toLowerCase().toCharArray()) {
            if (!allowedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public void addNewWord(String enteredWord, String translation, Map<String, String> primaryDictionary, Map<String, String> secondaryDictionary) {
       englishDictionaryDao.saveNewWord(enteredWord, translation);
    }

    public void deleteWord(String wordToDelete, Map<String, String> dictionary) {
        dictionary.remove(wordToDelete);
    }

    public void updateWord(String[] enteredWords, Map<String, String> dictionary) {
        dictionary.computeIfPresent(
                enteredWords[1], (wordToUpdate, newTranslation) -> wordToUpdate.replace(wordToUpdate, enteredWords[2]));
    }
}
