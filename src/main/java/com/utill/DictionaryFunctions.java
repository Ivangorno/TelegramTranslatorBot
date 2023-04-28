package com.utill;

import com.TgDictionaryBot;
import com.dataBase.DictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.utill.messages.DictionaryCommands.ENGLISH_DICTIONARY;
import static com.utill.messages.DictionaryCommands.FRENCH_DICTIONARY;
import static com.utill.messages.DictionaryMessages.*;

@Component
public class DictionaryFunctions {
    @Autowired
    private CheckArrayOfEnteredWords checkArrayOfEnteredWords;
    @Autowired
    private TgDictionaryBot tgDictionaryBot;
    @Autowired
    private DictionaryDao dictionaryDao;


    public void updateWord(String[] enteredText, String primaryDictionary, String translationDictionary) {
        if (checkArrayOfEnteredWords.checkArray(enteredText, 3)) {
            String wordToUpdate = enteredText[1];
            String newTranslation = enteredText[2];

            dictionaryDao.updateTranslation(newTranslation, wordToUpdate, primaryDictionary, translationDictionary);
            tgDictionaryBot.sendMessage(String.format(WORD_UPDATED_SUCCESSFULLY, wordToUpdate, newTranslation));
        } else tgDictionaryBot.sendMessage(UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void deleteWord(String[] enteredText, String primaryDictionary, String translationDictionary) {
        String wordToDelete = enteredText[1];

        if (checkArrayOfEnteredWords.checkArray(enteredText, 2)) {
            dictionaryDao.deleteWord(wordToDelete, primaryDictionary, translationDictionary);
            tgDictionaryBot.sendMessage(String.format(WORD_DELETED_SUCCESSFULLY, wordToDelete));
        } else tgDictionaryBot.sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void addWord(String[] enteredText, String primaryDictionary, String translationDictionary) {
        String frenchWordToAdd = enteredText[1];
        String englishWordToAdd = enteredText[2];

        if (checkArrayOfEnteredWords.checkArray(enteredText, 3)) {
            dictionaryDao.saveNewWord(frenchWordToAdd, englishWordToAdd, primaryDictionary, translationDictionary);

            tgDictionaryBot.sendMessage(String.format(NEW_WORD_SUCCESSFULLY_ADDED, frenchWordToAdd));
        } else tgDictionaryBot.sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public String translate(String enteredText, String languageToTranslateTo, String dictionaryType){
        return dictionaryDao.getTranslation(enteredText, languageToTranslateTo, dictionaryType);
    }

    public void changeTranslation(boolean isEnglish) {
        isEnglish = !isEnglish;
        if (isEnglish) {
            tgDictionaryBot.sendMessage(String.format(CURRENT_LANGUAGE_PAIR, ENGLISH_DICTIONARY, FRENCH_DICTIONARY));
        } else {
            tgDictionaryBot.sendMessage(String.format(CURRENT_LANGUAGE_PAIR, FRENCH_DICTIONARY, ENGLISH_DICTIONARY));
        }
    }
}
