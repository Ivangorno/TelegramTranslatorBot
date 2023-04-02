package com;


import com.utill.DictionaryUtils;
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
    private DictionaryUtils dictionaryUtils;
    private Message message;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                String[] words = wordUtils.parseEngToFreWord(text);
                Map<String, String> engToFrDictionary = englishDictionary.getEngToFrDictionary();
                Map<String, String> frToEngDictionary = frenchToEnglishDictionary.getFrToEngDictionary();

                if (isEnglish) { //change this boolean in the future
                    if (text.contains(ADD_NEW_WORD)) {
                        dictionaryUtils.addWord(words, engToFrDictionary, frToEngDictionary);
                    } else if (text.contains(DELETE_WORD)) {
                        dictionaryUtils.deleteWord(words, engToFrDictionary);
                    } else if (text.contains(UPDATE_WORD)) {
                        dictionaryUtils.updateWord(words, engToFrDictionary);
                    } else if (wordUtils.isWordValid(text, ENGLISH_LETTERS)) {
                        sendMessage(translation.translateEnglishToFrench(text));
                    } else {
                        sendMessage(ENTERED_NOT_CORRECT_ENGLISH_WORD);
                    }
                } else {
                    if (text.contains(ADD_NEW_WORD)) {
                        dictionaryUtils.addWord(words, frToEngDictionary, engToFrDictionary);
                    } else if (text.contains(DELETE_WORD)) {
                        dictionaryUtils.deleteWord(words, frToEngDictionary);
                    } else if (text.contains(UPDATE_WORD)) {
                        dictionaryUtils.updateWord(words, frToEngDictionary);
                    } else if (wordUtils.isWordValid(text, FRENCH_LETTERS)) {
                        sendMessage(translation.translateFrenchToEnglish(text));
                    } else {
                        sendMessage(ENTERED_NOT_CORRECT_ENGLISH_WORD);
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
