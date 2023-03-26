package com.utill;

import com.EnglishToFrenchDictionary;
import com.TgDictionaryBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// Try to use one method to checkArray
// Little hit you could move logic of checking if word exist into db layer where interactions
// with words should take place.
public class CheckArrayOfEnteredWords {

    @Autowired
    private EnglishToFrenchDictionary englishToFrenchDictionary;

    @Autowired
    private TgDictionaryBot tgDictionaryBot;

//
//    public void checkArray(String[] enteredWords){
//        if(checkArrayToAddWords(enteredWords)==true){
//            sendMessageToUser = NEW_WORD_SUCCESSFULLY_ADDED + " \"" + enteredWords[1] + "\"";
//        }else sendMessageToUser = ADD_WORD_COMMAND_ENTERED_INCORRECTLY;
//    }


    public boolean checkArrayToAddWords(String[] englishAndFrenchWord) {
        if (englishAndFrenchWord.length == 3) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkArrayToDeleteWords(String[] wordToDelete) {
        if (wordToDelete.length == 2 && englishToFrenchDictionary.getEnglishToFrenchDictionary().containsKey(wordToDelete[1])) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkArrayToUpdateWords(String[] wordToUpdate) {
        if (wordToUpdate.length == 3 && englishToFrenchDictionary.getEnglishToFrenchDictionary().containsKey(wordToUpdate[1])) {
            return true;
        } else {
            return false;
        }
    }


//        public boolean checkArray(String[] enteredWords, boolean typeOfOperationWithDictionary) {
//
//        if (typeOfOperationWithDictionary == checkArrayToAddWords) {
//            if (enteredWords.length == 3){
//                sendMessageToUser = NEW_WORD_SUCCESSFULLY_ADDED + "\"" + enteredWords[1] + "\"";
//                return true;
//            } else {
//                sendMessageToUser = ADD_WORD_COMMAND_ENTERED_INCORRECTLY;
//                return false;
//            }
//
//        } else if (typeOfOperationWithDictionary == checkArrayToDeleteWords) {
//            if (enteredWords.length == 2 && englishToFrenchDictionary.getEnglishToFrenchDictionary().containsKey(enteredWords[1])) {
//                sendMessageToUser = WORD_DELETED_SUCCESSFULLY + " \"" + enteredWords[1] + "\"";
//                return true;
//            } else {
//                sendMessageToUser = DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY;
//                return false;
//            }
//        } else if (typeOfOperationWithDictionary == checkArrayToUpdateWords) {
//            if (enteredWords.length == 3 && englishToFrenchDictionary.getEnglishToFrenchDictionary().containsKey(enteredWords[1])) {
//                sendMessageToUser = WORD_UPDATED_SUCCESSFULLY + " \"" + enteredWords[1] + "\","+enteredWords[2]+"\""+ "\"";
//                return true;
//            } else {
//                sendMessageToUser = UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY;
//                return false;
//            }
//        } else
//            return false;
//    }
}


//
//
//
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

