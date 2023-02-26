package com;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class FrToEngDictionary {
    private boolean addNewWordCheck = false;
     private String frenchWord;
     private String englishWord;
     private TgDictionaryBot tgDictionaryBot;
     private  Map<String, String> frenchToEnglishDictionary = new HashMap<>();
    {
        frenchToEnglishDictionary.put("Bonjour", "Hello");
        frenchToEnglishDictionary.put("Chat","Cat");
    }

    public FrToEngDictionary(TgDictionaryBot tgDictionaryBot){
                this.tgDictionaryBot = tgDictionaryBot;
    }

    public Map<String, String> getFrenchToEnglishDictionary() {
        return frenchToEnglishDictionary;
    }

    public void addNewWord(String frenchWord, String englishWord){
        frenchToEnglishDictionary.put(frenchWord,englishWord);
    }

    public void addNewWord(Message message){

                if(addNewWordCheck == true) {
                    frenchWord = message.getText();
                    tgDictionaryBot.sendMessage("Enter a TRANSLATION for new french word");

                    tgDictionaryBot.onUpdateReceived(new Update());
                    englishWord = message.getText();
                    frenchToEnglishDictionary.put(frenchWord, englishWord);
                    tgDictionaryBot.sendMessage("New Word added to the dictionary");
                }

        if (addNewWordCheck == false) {
            if (message.getText().equals("/add")) {
              tgDictionaryBot.sendMessage("Enter a NEW FRENCH word to add");
            }
            addNewWordCheck = true;
        }

        }


        }






