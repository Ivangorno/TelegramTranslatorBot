package com.utill;

import com.TgDictionaryBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.utill.messages.DictionaryMessages.*;

@Component
public class DictionaryFunctions {
    @Autowired
    private CheckArrayOfEnteredWords checkArrayOfEnteredWords;
    @Autowired
    private WordUtils wordUtils;
    @Autowired
    private TgDictionaryBot tgDictionaryBot;

    public void updateWord(String[] words, Map<String, String> dictionary) {
        if (checkArrayOfEnteredWords.checkArray(words, 3)) {
            wordUtils.updateWord(words, dictionary);
            tgDictionaryBot.sendMessage(String.format(WORD_UPDATED_SUCCESSFULLY, words[1], words[2]));
        } else tgDictionaryBot.sendMessage(UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void deleteWord(String[] enteredWords) {
        String englishWord = enteredWords[1];

        if (checkArrayOfEnteredWords.checkArray(enteredWords, 2)) {
            wordUtils.deleteWord(englishWord);
            tgDictionaryBot.sendMessage(String.format(WORD_DELETED_SUCCESSFULLY, englishWord));
        } else tgDictionaryBot.sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void addWord(String[] words) {
        if (checkArrayOfEnteredWords.checkArray(words, 3)) {
            wordUtils.addNewWord(words[1], words[2]);
            tgDictionaryBot.sendMessage(String.format(NEW_WORD_SUCCESSFULLY_ADDED, words[1]));
        } else tgDictionaryBot.sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
    }
}
