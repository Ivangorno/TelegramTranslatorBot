package com;

import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String BOT_TOKEN = "";
    private static final String BOT_USERNAME = "";

    private Message message;

    @Autowired
    private FrenchToEnglishTranslationImpl frToEngImpl;

    @Autowired
    private EngToFrenchDictionary engToFrenchDictionary;

    @Autowired
    private ParseWordImpl parseWord;

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage()) {
            message = update.getMessage();

            if (message.getText().contains("/add")) {
             engToFrenchDictionary.addNewWord(parseWord.parseEngToFreWord(message.getText()));
            }

            if (message.hasText()) {
                sendMessage(frToEngImpl.engToFrTranslate(message.getText()));
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