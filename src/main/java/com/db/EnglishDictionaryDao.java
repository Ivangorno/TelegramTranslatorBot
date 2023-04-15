package com.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
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
}
