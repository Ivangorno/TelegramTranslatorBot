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

@Component
public class TgDictionaryBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(TgDictionaryBot.class.getName());
    @Value("${tgDictionary.BotToken}")
    private  String botToken;
    @Value("${tgDictionary.BotUserName}")
    private  String botUsername;

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

            if (message.getText().contains("/add")) {
             englishToFrenchDictionary.addNewWord(parseWord.parseEngToFreWord(message.getText()));
            }

            if (message.hasText()) {
                sendMessage(frenchToEnglishImpl.TranslateEnglishToFrench(message.getText()));
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