package com;


import com.utill.CheckArrayOfEnteredWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import static com.utill.messages.DictionaryCommands.*;
import static com.utill.messages.DictionaryMessages.*;


@Component
public class TgDictionaryBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(TgDictionaryBot.class.getName());

    private boolean isEnglish = true;

    @Value("${tgDictionary.BotToken}")
    private String botToken;
    @Value("${tgDictionary.BotUserName}")
    private String botUsername;

    @Autowired
    private CheckArrayOfEnteredWords checkArrayOfEnteredWords;
    @Autowired
    private TranslationImpl translation;
    @Autowired
    private EnglishToFrenchDictionary englishToFrenchDictionary;
    @Autowired
    private FrenchToEnglishDictionary frenchToEnglishDictionary;
    @Autowired
    private WordUtils wordUtils;

    private Message message;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                String[] words = wordUtils.parseEngToFreWord(text);
//                  change this boolean in future
                if(isEnglish) {
                    if (text.contains(ADD_NEW_WORD)) {
                        addWordEnglishTranslation(words);
                    } else if (text.contains(DELETE_WORD)) {
                        deleteWord(words, englishToFrenchDictionary.getEngToFrDictionary());
                    } else if (text.contains(UPDATE_WORD)) {
                        updateWord(words, englishToFrenchDictionary.getEngToFrDictionary());
                    } else if (wordUtils.isWordValid(text, ENGLISH_LETTERS)) {
                        sendMessage(translation.translateEnglishToFrench(text));
                    } else {
                        sendMessage(ENTERED_NOT_CORRECT_ENGLISH_WORD);
                    }
                } else {
                    if (text.contains(ADD_NEW_WORD)) {
                        addWordFrenchTranslation(words);
                    } else if (text.contains(DELETE_WORD)) {
                        deleteWord(words, frenchToEnglishDictionary.getFrToEngDictionary());
                    } else if (text.contains(UPDATE_WORD)) {
                        updateWord(words, frenchToEnglishDictionary.getFrToEngDictionary());
                    } else if (wordUtils.isWordValid(text, FRENCH_LETTERS)) {
                        sendMessage(translation.translateFrenchToEnglish(text));
                    } else {
                        sendMessage(ENTERED_NOT_CORRECT_ENGLISH_WORD);
                    }
                }
            }
        }
    }

    private void updateWord(String[] words, Map<String, String> dictionary) {
        if (checkArrayOfEnteredWords.checkArray(words, 3)) {
            wordUtils.updateWord(words, dictionary);
            sendMessage(String.format(WORD_UPDATED_SUCCESSFULLY, words[1], words[2]));
        } else sendMessage(UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    private void deleteWord(String[] words, Map<String, String> dictionary) {
        if (checkArrayOfEnteredWords.checkArray(words, 2)) {
            wordUtils.deleteWord(words[1], dictionary);
            sendMessage(String.format(WORD_DELETED_SUCCESSFULLY, words[1]));
        } else sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    private void addWordEnglishTranslation(String[] words) {
        if (checkArrayOfEnteredWords.checkArray(words, 3)) {
            wordUtils.addNewWord(words[1], words[2]);
            sendMessage(String.format(NEW_WORD_SUCCESSFULLY_ADDED, words[1]));
        } else sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    private void addWordFrenchTranslation(String[] words) {
        if (checkArrayOfEnteredWords.checkArray(words, 3)) {
            //need to update addWord method for multiple Dictionaries
            wordUtils.addNewWord(words[2], words[1]);
            sendMessage( String.format(NEW_WORD_SUCCESSFULLY_ADDED, words[1]));
        } else sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
    }

    public void sendMessage(String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(message.getChatId());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(SendMessage sendMessage) {
        sendMessage.setChatId(message.getChatId());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
