package com.dataBase;

import com.utill.ModeSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.utill.messages.DictionaryCommands.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@Profile("dev")
public class DictionaryDaoImpl implements DictionaryDao {

    @Autowired
    @Qualifier("connection")
    private Connection connection;

    @Autowired
    ModeSelector languageOfDictionary;

    public void saveNewWord(String englishWord, String frenchWord){
        try {
                Statement statement = connection.createStatement();
                String SqlCommand1;
                String SqlCommand2;

                if(languageOfDictionary.isEnglish()){
                    SqlCommand1 = String.format(INSERT_NEW_ENGLISH_WORD_AND_TRANSLATION_INTO_DICTIONARY, englishWord,frenchWord);
                    SqlCommand2 = String.format(INSERT_NEW_FRENCH_WORD_AND_TRANSLATION_INTO_DICTIONARY, frenchWord,englishWord);
                } else {
                    SqlCommand1 = String.format(INSERT_NEW_FRENCH_WORD_AND_TRANSLATION_INTO_DICTIONARY, frenchWord,englishWord);
                    SqlCommand2 = String.format(INSERT_NEW_ENGLISH_WORD_AND_TRANSLATION_INTO_DICTIONARY, englishWord,frenchWord);
                }
                statement.execute(SqlCommand1);
                statement.execute(SqlCommand2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWord(String wordToDelete, String primaryDictionary){
        try {
            Statement statement = connection.createStatement();
            String SqlCommand1;
            String SqlCommand2;


            if(languageOfDictionary.isEnglish() ) {
                SqlCommand1 = String.format(DELETE_ENGLISH_TO_FRENCH_TRANSLATION,wordToDelete);
                SqlCommand2 = String.format(DELETE_FRENCH_TO_ENGLISH_TRANSLATION,wordToDelete);

            }else {
                SqlCommand1 = String.format(DELETE_FRENCH_TO_ENGLISH_TRANSLATION,wordToDelete);
                SqlCommand2 = String.format(DELETE_ENGLISH_TO_FRENCH_TRANSLATION,wordToDelete);
            }
            statement.execute(SqlCommand1);
            statement.execute(SqlCommand2);
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
                    translationDictionary,translationLanguageColumn, wordToUpdate, translationLanguageColumn, newTranslation );

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
