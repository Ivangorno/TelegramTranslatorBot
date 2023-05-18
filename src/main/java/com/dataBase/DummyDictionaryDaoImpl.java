package com.dataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Profile("test")
@Component
public class DummyDictionaryDaoImpl implements DictionaryDao {

    private Map<String, String> dummyDB = new HashMap<>();

    {
        dummyDB.put("Mother", "Mère");
        dummyDB.put("Father", "Père");
        dummyDB.put("Cat", "Chatte");
    }


    public void saveNewWord(String englishWord, String frenchWord) {
        dummyDB.put(englishWord, frenchWord);
    }

    public void deleteWord(String wordToDelete) {
        dummyDB.remove(wordToDelete);
    }

    public void updateTranslation(String newTranslation, String wordToUpdate) {
        dummyDB.put(wordToUpdate, newTranslation);
    }

    public String getTranslation(String enteredText) {
        return dummyDB.get(enteredText);
    }
}
