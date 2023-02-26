package com;

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

    private static final String BOT_TOKEN = "5674632523:AAG2xGaEhs52-EMXeOVcPqfOk3WLTxeky_o";
    private static final String BOT_USERNAME = "ivan_tutorial_java_bot";


    private FrToEngDictionary frenchDictionary = new FrToEngDictionary( this );
    private Message message;

    private FrenchToEnglishTranslationImpl frToEngImpl = new FrenchToEnglishTranslationImpl();

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
