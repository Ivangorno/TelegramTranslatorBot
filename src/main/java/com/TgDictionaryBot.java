package com;


import com.utill.DictionaryFunctions;
import com.utill.SpellCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static com.utill.messages.DictionaryCommands.*;
import static com.utill.messages.DictionaryMessages.*;
import static com.utill.AllowedLetters.*;

@Component
public class TgDictionaryBot extends TelegramLongPollingBot {

//    private boolean isEnglish = true;

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
    private SpellCheck spellCheck;
    @Autowired
    private DictionaryFunctions dictionaryFunctions;
    private Message message;

    private boolean isEnglish = true;


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                String[] enteredText = spellCheck.parseToSeparateWords(text);
                String dictionaryCommand = enteredText[0];

                if (text.contentEquals(CHANGE_LANGUAGE)) {
                    isEnglish = !isEnglish;
                    if (isEnglish) {
                        sendMessage(String.format(CURRENT_LANGUAGE_PAIR, ENGLISH_DICTIONARY, FRENCH_DICTIONARY));
                    } else {
                        sendMessage(String.format(CURRENT_LANGUAGE_PAIR, FRENCH_DICTIONARY, ENGLISH_DICTIONARY));
                    }

                    return;
//                    dictionaryFunctions.changeTranslation(isEnglish);
                }

                if (isEnglish) {
                    if (dictionaryCommand.contentEquals(ADD_NEW_WORD)) {
                        dictionaryFunctions.addWord(enteredText, ENGLISH_DICTIONARY);
                    } else if (dictionaryCommand.contentEquals(DELETE_WORD)) {
                        dictionaryFunctions.deleteWord(enteredText, ENGLISH_DICTIONARY);
                    } else if (dictionaryCommand.contentEquals(UPDATE_WORD)) {
                        dictionaryFunctions.updateWord(enteredText, ENGLISH_DICTIONARY, FRENCH_DICTIONARY);
                    } else if (spellCheck.isWordValid(text, ENGLISH_LETTERS)) {
                        sendMessage(dictionaryFunctions.translate(text, FRENCH_DICTIONARY, ENGLISH_DICTIONARY));
                    } else {
                        sendMessage(ENTERED_NOT_CORRECT_ENGLISH_WORD);
                    }
                } else {
                    if (dictionaryCommand.contentEquals(ADD_NEW_WORD)) {
                        dictionaryFunctions.addWord(enteredText,FRENCH_DICTIONARY);
                    } else if (dictionaryCommand.contentEquals(DELETE_WORD)) {
                        dictionaryFunctions.deleteWord(enteredText, FRENCH_DICTIONARY);
                    } else if (dictionaryCommand.contentEquals(UPDATE_WORD)) {
                        dictionaryFunctions.updateWord(enteredText, FRENCH_DICTIONARY, ENGLISH_DICTIONARY);
                    } else if (spellCheck.isWordValid(text, FRENCH_LETTERS)) {
                        sendMessage(dictionaryFunctions.translate(text, ENGLISH_DICTIONARY, FRENCH_DICTIONARY));
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
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        setButtons(sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/change language"));
        keyboardFirstRow.add(new KeyboardButton("/add"));
//        keyboardFirstRow.add(new KeyboardButton());  If you wanna add button

        keyboardRowList.add(keyboardFirstRow);
//        keyboardRowList.add(keyboardSecondRow); If you wanna add new row of buttons

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
}
