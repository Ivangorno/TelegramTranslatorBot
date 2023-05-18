package com.utill;

import com.TgDictionaryBot;
import com.dataBase.DictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.utill.messages.DictionaryCommands.*;
import static com.utill.messages.DictionaryMessages.*;

@Component
public class DictionaryFunctions {

    @Autowired
    private TgDictionaryBot tgDictionaryBot;
    @Autowired
    private DictionaryDao dictionaryDao;

    public void updateWord(String[] enteredText) {
        if (enteredText.length == 2) {
            String wordToUpdate = enteredText[0];
            String newTranslation = enteredText[1];

            dictionaryDao.updateTranslation(newTranslation, wordToUpdate);
            tgDictionaryBot.sendMessage(String.format(WORD_UPDATED_SUCCESSFULLY, wordToUpdate, newTranslation));
            tgDictionaryBot.sendMessage(ENTER_NEW_WORD_TO_UPDATE);

        } else tgDictionaryBot.sendMessage(UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void deleteWord(String[] enteredText) {
        String wordToDelete = enteredText[0];

        if (enteredText.length == 1) {
            dictionaryDao.deleteWord(wordToDelete);
            tgDictionaryBot.sendMessage(String.format(WORD_DELETED_SUCCESSFULLY, wordToDelete));
            tgDictionaryBot.sendMessage(ENTER_ANOTHER_WORD_TO_DELETE);

        } else tgDictionaryBot.sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void addWord(String[] enteredText) {
        String baseWord = enteredText[0];
        String translationWord = enteredText[1];

        if (enteredText.length == 2) {
            dictionaryDao.saveNewWord(baseWord, translationWord);

            tgDictionaryBot.sendMessage(String.format(NEW_WORD_SUCCESSFULLY_ADDED, translationWord));
            tgDictionaryBot.sendMessage(ENTER_NEW_WORD_TO_ADD);
        } else tgDictionaryBot.sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public String translate(String enteredText) {
        return dictionaryDao.getTranslation(enteredText);
    }
}
