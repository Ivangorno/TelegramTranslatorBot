package com;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrenchDictionary {

     private String frenchWord;
     private String englishWord;
     private TgDictionaryBot tgDictionaryBot;
     private Map<String, String> frenchToEnglishDictionary = new HashMap<>();

    public FrenchDictionary (TgDictionaryBot tgDictionaryBot){
        this.tgDictionaryBot = tgDictionaryBot;
    }

    public Map<String, String> getFrenchToEnglishDictionary() {
        return frenchToEnglishDictionary;
    }

    public void addNewWord(String frenchWord, String englishWord){
        frenchToEnglishDictionary.put(frenchWord,englishWord);
    }

    public void addNewWord(Message message){
        if (message.getText().equals("/add")) {
          tgDictionaryBot.sendMessage("Enter a NEW WORD to add" );
        }

    }



}
