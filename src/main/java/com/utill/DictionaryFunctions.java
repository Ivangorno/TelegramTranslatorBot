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

    @Autowired
    private SpellCheck editString;

    public void updateWord(String[] enteredText) {
        if (enteredText.length == 2) {
            String wordToUpdate = editString.toCorrectCapitalization(enteredText[0]);
            String newTranslation = editString.toCorrectCapitalization(enteredText[1]);

            dictionaryDao.updateTranslation(newTranslation, wordToUpdate);

        } else tgDictionaryBot.sendMessage(UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void deleteWord(String[] enteredText) {

        if (enteredText.length == 1) {
            String wordToDelete = editString.toCorrectCapitalization(enteredText[0]);

            dictionaryDao.deleteWord(wordToDelete);

        } else tgDictionaryBot.sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void addWord(String[] enteredText)  {

        if (enteredText.length == 2) {
            String baseWord = editString.toCorrectCapitalization(enteredText[0]);
            String translationWord = editString.toCorrectCapitalization(enteredText[1]);
            dictionaryDao.saveNewWord(baseWord, translationWord);

        } else tgDictionaryBot.sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public String translate(String enteredText) {
             return dictionaryDao.getTranslation(enteredText);
    }
}
