package com.dataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Statement;
@Component
public class EnglishDictionaryDao {

    @Autowired
    private ConnectionCreator connectionCreator;

    public void saveNewWord(String englishWord, String frenchWord){
        try {
            Statement statement = connectionCreator.getConnection().createStatement();
            String SQL = String.format("INSERT INTO ENGLISH_DICTIONARY VALUES('%s','%s')", englishWord, frenchWord);
            statement.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWord(String englishWord){
        try {
            Statement statement = connectionCreator.getConnection().createStatement();
            String SQL = String.format("DELETE FROM ENGLISH_DICTIONARY WHERE english= '%s'", englishWord);
            statement.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
