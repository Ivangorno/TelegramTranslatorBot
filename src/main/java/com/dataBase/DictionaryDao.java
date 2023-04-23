package com.dataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
@Component
public class DictionaryDao {

    @Autowired
    @Qualifier("connection")
    private Connection connection;

    public void saveNewWord(String englishWord, String frenchWord, String primaryDictionary, String translationDictionary){
        try {
            Statement statement = connection.createStatement();

            String SQL = String.format(
                    "INSERT INTO %s_dictionary VALUES('%s','%s'); " +
                    "INSERT INTO %s_dictionary  VALUES('%s','%s')  ",
                    primaryDictionary, englishWord, frenchWord, translationDictionary, frenchWord, englishWord);
            statement.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWord(String wordToDelete, String primaryDictionary, String translationDictionary){
        try {
            Statement statement = connection.createStatement();
            String SQL = String.format(
                    "DELETE FROM %s_dictionary WHERE %s= '%s'; " +
                    "DELETE FROM %s_dictionary  WHERE %s= '%s'",
                    primaryDictionary, primaryDictionary, wordToDelete, translationDictionary,primaryDictionary,wordToDelete);
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
