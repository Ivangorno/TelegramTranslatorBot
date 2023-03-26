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

import java.util.logging.Logger;

import static com.utill.DictionaryCommands.*;
import static com.utill.DictionaryMessages.*;


@Component
public class TgDictionaryBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(TgDictionaryBot.class.getName());

    @Value("${tgDictionary.BotToken}")
    private String botToken;
    @Value("${tgDictionary.BotUserName}")
    private String botUsername;

    @Autowired
    private CheckArrayOfEnteredWords checkArrayOfEnteredWords;
    @Autowired
    private TranslationImpl frenchToEnglishImpl;

    private Message message;

    public Message getMessage() {
        return message;
    }

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
                    if (checkArrayOfEnteredWords.checkArrayToAddWords(words)) {
                        englishToFrenchDictionary.addNewWord(words);
                        sendMessage(NEW_WORD_SUCCESSFULLY_ADDED + " \"" + words[1] + "\"");
                    } else sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);

                } else if (text.contains(DELETE_WORD)) {
                    if (checkArrayOfEnteredWords.checkArrayToDeleteWords(words)) {
                        englishToFrenchDictionary.deleteWord(words[1]);
                        sendMessage(WORD_DELETED_SUCCESSFULLY + " \"" + words[1] + "\"");
                    } else sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);

                } else if (text.contains(UPDATE_WORD)) {
                    if (checkArrayOfEnteredWords.checkArrayToUpdateWords(words)) {
                        englishToFrenchDictionary.updateWord(words);
                        sendMessage(WORD_UPDATED_SUCCESSFULLY + " \"" + words[1] + "\" translates to: \""+words[2]+"\"");
                    } else sendMessage(UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY);

                } else if (englishToFrenchDictionary.isEnglish(text)) {
                    sendMessage(frenchToEnglishImpl.translateEnglishToFrench(text));
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
}

//    private boolean checkArrayToAddWords(String[] englishAndFrenchWord) {
//        if (englishAndFrenchWord.length == 3) {
//            return true;
//        } else {
//            sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
//            return false;
//        }
//    }
//
//    private boolean checkArrayToDeleteWords(String[] wordToDelete) {
//        if (wordToDelete.length == 2 && englishToFrenchDictionary.getEnglishToFrenchDictionary().containsKey(wordToDelete[1])) {
//            return true;
//        } else {
//            sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
//            return false;
//        }
//    }
//    private boolean checkArrayToUpdateWords(String[] wordToUpdate) {
//        if (wordToUpdate.length == 3 && englishToFrenchDictionary.getEnglishToFrenchDictionary().containsKey(wordToUpdate[1])) {
//            return true;
//        } else {
//            sendMessage(UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
//            return false;
//        }
//    }
