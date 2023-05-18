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
    private String sqlCommand1;
    private String sqlCommand2;

    public void saveNewWord(String englishWord, String frenchWord) {
        try {
            Statement statement = connection.createStatement();

            if (languageOfDictionary.isEnglish()) {
                sqlCommand1 = String.format(INSERT_NEW_ENGLISH_WORD_AND_TRANSLATION_INTO_DICTIONARY, englishWord, frenchWord);
                sqlCommand2 = String.format(INSERT_NEW_FRENCH_WORD_AND_TRANSLATION_INTO_DICTIONARY, frenchWord, englishWord);
            } else {
                sqlCommand1 = String.format(INSERT_NEW_FRENCH_WORD_AND_TRANSLATION_INTO_DICTIONARY, frenchWord, englishWord);
                sqlCommand2 = String.format(INSERT_NEW_ENGLISH_WORD_AND_TRANSLATION_INTO_DICTIONARY, englishWord, frenchWord);
            }
            statement.execute(sqlCommand1);
            statement.execute(sqlCommand2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWord(String wordToDelete) {
        try {
            Statement statement = connection.createStatement();


            if (languageOfDictionary.isEnglish()) {
                sqlCommand1 = String.format(DELETE_ENGLISH_TO_FRENCH_TRANSLATION, wordToDelete);
                sqlCommand2 = String.format(DELETE_FRENCH_TO_ENGLISH_TRANSLATION, wordToDelete);

            } else {
                sqlCommand1 = String.format(DELETE_FRENCH_TO_ENGLISH_TRANSLATION, wordToDelete);
                sqlCommand2 = String.format(DELETE_ENGLISH_TO_FRENCH_TRANSLATION, wordToDelete);
            }
            statement.execute(sqlCommand1);
            statement.execute(sqlCommand2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTranslation(String newTranslation, String wordToUpdate) {

        try {
            Statement statement = connection.createStatement();
            sqlCommand1 = String.format(UPDATE_FRENCH_TRANSLATION_TO_ENGLISH_WORD, newTranslation, wordToUpdate);
            sqlCommand2 = String.format(UPDATE_ENGLISH_TRANSLATION_TO_FRENCH_WORD, newTranslation, wordToUpdate);

            statement.execute(sqlCommand1);
            statement.execute(sqlCommand2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTranslation(String enteredText) {
        String translation;
        String sqlCommand;
        String frenchColumn = "french";
        String englishColumn = "english";

        try {
            Statement statement = connection.createStatement();
            if (languageOfDictionary.isEnglish()) {
                sqlCommand = String.format(TRANSLATE_ENGLISH_TO_FRENCH_COMMAND, enteredText);
                ResultSet resultSet = statement.executeQuery(sqlCommand);
                resultSet.next();
                translation = resultSet.getString(frenchColumn);

            } else {
                sqlCommand = String.format(TRANSLATE_FRENCH_TO_ENGLISH_COMMAND, enteredText);
                ResultSet resultSet = statement.executeQuery(sqlCommand);
                resultSet.next();
                translation = resultSet.getString(englishColumn);
            }


            return translation;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
