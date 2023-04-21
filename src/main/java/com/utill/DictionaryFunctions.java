package com.utill;

import com.TgDictionaryBot;
import com.dataBase.EnglishDictionaryDao;
import com.dataBase.FrenchDictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.utill.messages.DictionaryMessages.*;

@Component
public class DictionaryFunctions {
    @Autowired
    private CheckArrayOfEnteredWords checkArrayOfEnteredWords;
    @Autowired
    private TgDictionaryBot tgDictionaryBot;
    @Autowired
    private EnglishDictionaryDao englishDictionaryDao;
    @Autowired
    private FrenchDictionaryDao frenchDictionaryDao;

    public void updateWord(String[] enteredText) {
        if (checkArrayOfEnteredWords.checkArray(enteredText, 3)) {
            String englishWord = enteredText[1];
            String frenchTranslation = enteredText[2];

             englishDictionaryDao.updateTranslation(frenchTranslation, englishWord);
            tgDictionaryBot.sendMessage(String.format(WORD_UPDATED_SUCCESSFULLY, englishWord, frenchTranslation));
        } else tgDictionaryBot.sendMessage(UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void deleteWord(String[] enteredText) {
        String wordToDelete = enteredText[1];

        if (checkArrayOfEnteredWords.checkArray(enteredText, 2)) {
            englishDictionaryDao.deleteWord(wordToDelete);
            tgDictionaryBot.sendMessage(String.format(WORD_DELETED_SUCCESSFULLY, wordToDelete));
        } else tgDictionaryBot.sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void addWord(String[] enteredText) {
        String newEnglishWord = enteredText[1];
        String translation = enteredText[2];

        if (checkArrayOfEnteredWords.checkArray(enteredText, 3)) {
             englishDictionaryDao.saveNewWord(newEnglishWord, translation);
            tgDictionaryBot.sendMessage(String.format(NEW_WORD_SUCCESSFULLY_ADDED, newEnglishWord));
        } else tgDictionaryBot.sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public String translate(String enteredText){
       return englishDictionaryDao.getEnglishToFrenchTranslation(enteredText);
    }


    public void addFrenchWord(String[] enteredText) {
        String newFrenchWord = enteredText[1];
        String translation = enteredText[2];

        if (checkArrayOfEnteredWords.checkArray(enteredText, 3)) {
            frenchDictionaryDao.saveNewWord(newFrenchWord, translation);
            tgDictionaryBot.sendMessage(String.format(NEW_WORD_SUCCESSFULLY_ADDED, newFrenchWord));
        } else tgDictionaryBot.sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
    }
    public void deleteFrenchWord(String[] enteredText) {
        String wordToDelete = enteredText[1];

        if (checkArrayOfEnteredWords.checkArray(enteredText, 2)) {
             frenchDictionaryDao.deleteWord(wordToDelete);
            tgDictionaryBot.sendMessage(String.format(WORD_DELETED_SUCCESSFULLY, wordToDelete));
        } else tgDictionaryBot.sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public String translateFrenchToEnglish(String enteredText){
        return frenchDictionaryDao.getFrenchToEnglishTranslation(enteredText);
    }

    public void updateFrenchWordTranslation(String[] enteredText) {
        if (checkArrayOfEnteredWords.checkArray(enteredText, 3)) {
            String frenchWord = enteredText[1];
            String englishTranslation = enteredText[2];

            frenchDictionaryDao.updateTranslation(englishTranslation, frenchWord);
            tgDictionaryBot.sendMessage(String.format(WORD_UPDATED_SUCCESSFULLY, frenchWord, englishTranslation));
        } else tgDictionaryBot.sendMessage(UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }


}
