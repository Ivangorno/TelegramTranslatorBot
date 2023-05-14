package com;


import com.utill.DictionaryFunctions;
import com.utill.ModeSelector;
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

    @Autowired
    private ModeSelector modeSelector;

    private Message message;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            message = update.getMessage();
            String text = message.getText();

            if (ALL_COMMANDS.contains(text)) {
                modeSelector.changeMode(text);
                return;
            }

            String[] enteredText = spellCheck.parseToSeparateWords(text);

            boolean isBot = !message.getFrom().getIsBot();

            if (modeSelector.isEnglish()) {
                if (modeSelector.isAddWordMode() && isBot) {
                    dictionaryFunctions.addWord(enteredText, ENGLISH_DICTIONARY);
                } else if (modeSelector.isDeleteWordMode() && isBot) {
                    dictionaryFunctions.deleteWord(enteredText, ENGLISH_DICTIONARY);
                } else if (modeSelector.isUpdateWordMode() && isBot) {
                    dictionaryFunctions.updateWord(enteredText, ENGLISH_DICTIONARY, FRENCH_DICTIONARY);
                } else if (spellCheck.isWordValid(text, ENGLISH_LETTERS)) {
                    sendMessage(dictionaryFunctions.translate(text, FRENCH_DICTIONARY, ENGLISH_DICTIONARY));
                } else {
                    sendMessage(ENTERED_NOT_CORRECT_ENGLISH_WORD);
                }
            } else {
                if (modeSelector.isAddWordMode() && isBot) {
                    dictionaryFunctions.addWord(enteredText, FRENCH_DICTIONARY);
                } else if (modeSelector.isDeleteWordMode() && isBot) {
                    dictionaryFunctions.deleteWord(enteredText, FRENCH_DICTIONARY);
                } else if (modeSelector.isUpdateWordMode() && isBot) {
                    dictionaryFunctions.updateWord(enteredText, FRENCH_DICTIONARY, ENGLISH_DICTIONARY);
                } else if (spellCheck.isWordValid(text, FRENCH_LETTERS)) {
                    sendMessage(dictionaryFunctions.translate(text, ENGLISH_DICTIONARY, FRENCH_DICTIONARY));
                } else {
                    sendMessage(ENTERED_NOT_CORRECT_FRENCH_WORD);
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
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton(CHANGE_LANGUAGE));
        keyboardFirstRow.add(new KeyboardButton(EXIT_TO_TRANSLATION_MODE));
        keyboardSecondRow.add(new KeyboardButton(DELETE_WORD_MODE));
        keyboardSecondRow.add(new KeyboardButton(UPDATE_WORD_MODE));
        keyboardSecondRow.add(new KeyboardButton(EXIT_TO_TRANSLATION_MODE));


//        keyboardFirstRow.add(new KeyboardButton());  If you wanna add button

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
//        keyboardRowList.add(keyboardSecondRow); If you wanna add new row of buttons

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    public  TgDictionaryBot() {
//        List<BotCommand> listOfCommands = new ArrayList<>();
//        listOfCommands.add(new BotCommand("/add_mode", "add new word to the dictionary"));
//        listOfCommands.add(new BotCommand("/delete_mode", "delete a word from the dictionary"));
//        listOfCommands.add(new BotCommand("/update_mode", "update translation of a word in the dictionary"));
//
//        try {
//            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }
}
