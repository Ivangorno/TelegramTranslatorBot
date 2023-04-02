package com.utill;

import com.TgDictionaryBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.utill.messages.DictionaryMessages.*;
import static com.utill.messages.DictionaryMessages.ADD_WORD_COMMAND_ENTERED_INCORRECTLY;
@Component
public class DictionaryUtils {
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

    public void deleteWord(String[] words, Map<String, String> dictionary) {
        if (checkArrayOfEnteredWords.checkArray(words, 2)) {
            wordUtils.deleteWord(words[1], dictionary);
            tgDictionaryBot.sendMessage(String.format(WORD_DELETED_SUCCESSFULLY, words[1]));
        } else tgDictionaryBot.sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void addWord(String[] words, Map<String, String> primaryDictionary,Map<String, String> secondaryDictionary ) {
        if (checkArrayOfEnteredWords.checkArray(words, 3)) {
            wordUtils.addNewWord(words[1], words[2],primaryDictionary,secondaryDictionary );
            tgDictionaryBot.sendMessage(String.format(NEW_WORD_SUCCESSFULLY_ADDED, words[1]));
        } else tgDictionaryBot.sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
    }


}
