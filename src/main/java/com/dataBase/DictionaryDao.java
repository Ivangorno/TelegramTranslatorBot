package com.dataBase;

public interface DictionaryDao {

    void saveNewWord(String englishWord, String frenchWord, String primaryDictionary);

    void deleteWord(String wordToDelete, String primaryDictionary);

    void updateTranslation(String newTranslation, String wordToUpdate,  String primaryDictionary, String translationDictionary);

    String getTranslation(String enteredText, String translationLanguage, String dictionaryType);
}
