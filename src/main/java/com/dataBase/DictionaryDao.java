package com.dataBase;

import org.springframework.stereotype.Component;


public interface DictionaryDao {

    void saveNewWord(String englishWord, String frenchWord);

    void deleteWord(String wordToDelete);

    void updateTranslation(String newTranslation, String wordToUpdate);

    String getTranslation(String enteredText);
}
