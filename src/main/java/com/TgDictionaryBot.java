package com;


import com.dataBase.DictionaryController;
import com.utill.DictionaryFunctions;
import com.utill.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.logging.Logger;

import static com.utill.messages.DictionaryCommands.*;
import static com.utill.messages.DictionaryMessages.*;
import static com.utill.AllowedLetters.*;

@Component
public class TgDictionaryBot extends TelegramLongPollingBot {

    private boolean isEnglish = true; //delete this boolean in the future

    private static final Logger LOGGER = Logger.getLogger(TgDictionaryBot.class.getName());

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Autowired
    private DictionaryController dictionaryController;
    @Value("${tgDictionary.BotToken}")
    private String botToken;
    @Value("${tgDictionary.BotUserName}")
    private String botUsername;
    @Autowired
    private TranslationImpl translation;
    @Autowired
    private EnglishDictionary englishDictionary;
    @Autowired
    private FrenchDictionary frenchToEnglishDictionary;
    @Autowired
    private WordUtils wordUtils;
    @Autowired
    private DictionaryFunctions dictionaryUtils;
    private Message message;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                String[] enteredText = wordUtils.parseEngToFreWord(text);
                String dictionaryCommand = enteredText[0];

                if (isEnglish) { //change this boolean in the future
                    if (dictionaryCommand.contentEquals(ADD_NEW_WORD)) {
                        dictionaryUtils.addWord(enteredText);
                    } else if (dictionaryCommand.contentEquals(DELETE_WORD)) {
                        dictionaryUtils.deleteWord(enteredText);
                    } else if (dictionaryCommand.contentEquals(UPDATE_WORD)) {
                        dictionaryUtils.updateWord(enteredText);
                    } else if (wordUtils.isWordValid(text, ENGLISH_LETTERS)) {
                        sendMessage(translation.translateEnglishToFrench(text));
                    } else {
                        sendMessage(ENTERED_NOT_CORRECT_ENGLISH_WORD);
                    }
                } else {
                    if (dictionaryCommand.contentEquals(ADD_NEW_WORD)) {
                        dictionaryUtils.addWord(enteredText);
                    } else if (dictionaryCommand.contentEquals(DELETE_WORD)) {
                        dictionaryUtils.deleteWord(enteredText);
                    } else if (dictionaryCommand.contentEquals(UPDATE_WORD)) {
                        dictionaryUtils.updateWord(enteredText);
                    } else if (wordUtils.isWordValid(text, FRENCH_LETTERS)) {
                        sendMessage(translation.translateFrenchToEnglish(text));
                    } else {
                        sendMessage(ENTERED_NOT_CORRECT_FRENCH_WORD);
                    }
                }
            }
        }
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
}
