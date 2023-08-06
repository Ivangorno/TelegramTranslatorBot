package com.utill;

import com.TgDictionaryBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.utill.messages.DictionaryCommands.*;
import static com.utill.messages.DictionaryMessages.CURRENT_LANGUAGE_PAIR;

@Component
public class ModeSelector {

    @Autowired
    private TgDictionaryBot tgDictionaryBot;

    @Autowired
    private OperationsWithDocuments operationsWithDocuments;

    private boolean isEnglish = true;
    private boolean isAddWordMode = false;
    private boolean isUpdateWordMode = false;
    private boolean isDeleteWordMode = false;

    public void changeMode(String text) {
        if (text.contentEquals(CHANGE_LANGUAGE)) {
            isEnglish = changeTranslation(isEnglish);
        } else if(text.contentEquals("Save DB To Jason")){
            exitAllModes();
            operationsWithDocuments.saveDbFileLocally(operationsWithDocuments.saveDbToJson());
            tgDictionaryBot.SendDocument(operationsWithDocuments.getFile(operationsWithDocuments.saveDbToJson()));
            operationsWithDocuments.deleteDocument();

        } else if (text.contentEquals(ADD_WORD_MODE)) {
            exitAllModes();
            tgDictionaryBot.sendMessage("Enter TEXT in format <base word> and <it's translation>");
            isAddWordMode = true;
        } else if (text.contentEquals(DELETE_WORD_MODE)) {
            exitAllModes();
            tgDictionaryBot.sendMessage("Enter a <word> to DELETE from translation library");
            isDeleteWordMode = true;
        } else if (text.contentEquals(UPDATE_WORD_MODE)) {
            exitAllModes();
            tgDictionaryBot.sendMessage("Enter TEXT in format <base word> and <it's new translation> ");
            isUpdateWordMode = true;
        } else if (text.contentEquals(EXIT_TO_TRANSLATION_MODE)) {
            exitAllModes();
            tgDictionaryBot.sendMessage("You now in Translation Mode");
        }
    }

    private void exitAllModes() {
        isAddWordMode = false;
        isUpdateWordMode = false;
        isDeleteWordMode = false;
    }

    public boolean changeTranslation(boolean isEnglish) {
        isEnglish = !isEnglish;
        if (isEnglish) {
            tgDictionaryBot.sendMessage(String.format(CURRENT_LANGUAGE_PAIR, ENGLISH_DICTIONARY, FRENCH_DICTIONARY));
        } else {
            tgDictionaryBot.sendMessage(String.format(CURRENT_LANGUAGE_PAIR, FRENCH_DICTIONARY, ENGLISH_DICTIONARY));
        }
        return isEnglish;
    }

    public boolean isEnglish() {
        return isEnglish;
    }

    public void setEnglish(boolean english) {
        isEnglish = english;
    }

    public boolean isAddWordMode() {
        return isAddWordMode;
    }

    public void setAddWordMode(boolean addWordMode) {
        isAddWordMode = addWordMode;
    }

    public boolean isUpdateWordMode() {
        return isUpdateWordMode;
    }

    public void setUpdateWordMode(boolean updateWordMode) {
        isUpdateWordMode = updateWordMode;
    }

    public boolean isDeleteWordMode() {
        return isDeleteWordMode;
    }

    public void setDeleteWordMode(boolean deleteWordMode) {
        isDeleteWordMode = deleteWordMode;
    }
}
