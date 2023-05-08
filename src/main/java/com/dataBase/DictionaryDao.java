package com.dataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.utill.messages.DictionaryCommands.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
@Component
public class DictionaryDao {

    @Autowired
    @Qualifier("connection")
    private Connection connection;

    public void saveNewWord(String englishWord, String frenchWord, String primaryDictionary){
        try {
            Statement statement = connection.createStatement();
             String SQL;

            if(primaryDictionary.equals( ENGLISH_DICTIONARY) ){
                 SQL = String.format(INSERT_NEW_WORDS_INTO_DICTIONARY, englishWord,frenchWord, frenchWord,englishWord);
            } else {
                SQL = String.format(INSERT_NEW_WORDS_INTO_DICTIONARY, frenchWord,englishWord, englishWord,frenchWord);
            }
            statement.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWord(String wordToDelete, String primaryDictionary){
        try {
            Statement statement = connection.createStatement();
            String SQL;
            if(primaryDictionary.equals( ENGLISH_DICTIONARY) ) {
                SQL = String.format(DELETE_ENGLISH_TO_FRENCH_TRANSLATION,
                        primaryDictionary, wordToDelete, primaryDictionary, wordToDelete);
            }else {
                SQL = String.format(DELETE_FRENCH_TO_ENGLISH_TRANSLATION,
                        primaryDictionary, wordToDelete, primaryDictionary, wordToDelete);
            }
            statement.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTranslation(String newTranslation, String wordToUpdate,  String primaryDictionary, String translationDictionary){
        String primaryLanguageColumn =  primaryDictionary;
        String translationLanguageColumn =translationDictionary;

        try {
            Statement statement = connection.createStatement();
            String SQL = String.format(
                    "UPDATE %s_dictionary SET %s= '%s' WHERE %s= '%s'; " +
                    "UPDATE %s_dictionary SET %s= '%s' WHERE %s= '%s' ",

            primaryDictionary, translationDictionary,newTranslation,primaryLanguageColumn,wordToUpdate,
            translationDictionary,translationLanguageColumn, newTranslation, translationLanguageColumn, newTranslation );

            statement.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTranslation(String enteredText, String translationLanguage, String dictionaryType) {
        String columnLabel = dictionaryType;

        try {
            Statement statement = connection.createStatement();
            String SQL = String.format(
                    "SELECT %s FROM %s_dictionary WHERE LOWER(%s)= LOWER('%s')",
                    translationLanguage, dictionaryType, columnLabel, enteredText);

            ResultSet resultSet = statement.executeQuery(SQL);
            resultSet.next();


            return resultSet.getString(translationLanguage);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
