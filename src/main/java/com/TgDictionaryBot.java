package com;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Logger;

import static com.utill.DictionaryCommands.ADD_NEW_WORD;
import static com.utill.ErrorMessages.*;


@Component
public class TgDictionaryBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(TgDictionaryBot.class.getName());

    @Value("${tgDictionary.BotToken}")
    private String botToken;
    @Value("${tgDictionary.BotUserName}")
    private String botUsername;

    private Message message;

    @Autowired
    private FrenchToEnglishTranslationImpl frenchToEnglishImpl;

    @Autowired
    private EnglishToFrenchDictionary englishToFrenchDictionary;

    @Autowired
    private ParseWordImpl parseWord;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                String[] words = parseWord.parseEngToFreWord(text);
                if (text.contains(ADD_NEW_WORD)) {
                    if (checkArray(words)) {
                        englishToFrenchDictionary.addNewWord(words);
                        sendMessage(NEW_WORD_SUCCESSFULLY_ADDED);
                    }
                }
                else if (englishToFrenchDictionary.isEnglish(text)) {
                    sendMessage(frenchToEnglishImpl.TranslateEnglishToFrench(text));
                } else {
                    sendMessage(ENTERED_NOT_CORRECT_ENGLISH_WORD);
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

    private boolean checkArray(String[] englishAndFrenchWord) {
        if (englishAndFrenchWord.length == 3) {
            return true;
        } else {
            sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
            return false;
        }
    }
}