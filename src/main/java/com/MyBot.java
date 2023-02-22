package com;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class MyBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(MyBot.class.getName());

    private static final String BOT_TOKEN = " ";
    private static final String BOT_USERNAME = " ";

    private Message message;

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
                String text = message.getText();
                SendMessage sendEchoText = new SendMessage();
                sendEchoText.setText("Ви відправили: " + text);
                sendEchoText.setChatId(String.valueOf(message.getChatId()));
                try {
                    execute(sendEchoText);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
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
