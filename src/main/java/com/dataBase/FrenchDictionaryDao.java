package com.dataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class FrenchDictionaryDao {

    @Autowired
    private ConnectionCreator connectionCreator;

    public void saveNewWord(String englishWord, String frenchWord){
        try {
            Statement statement = connectionCreator.getConnection().createStatement();
            String SQL = String.format("INSERT INTO french_dictionary VALUES('%s','%s')", frenchWord, englishWord);
            statement.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWord(String frenchWord){
        try {
            Statement statement = connectionCreator.getConnection().createStatement();
            String SQL = String.format("DELETE FROM french_dictionary WHERE french = '%s'", frenchWord);
            statement.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTranslation(String englishTranslation, String frenchWord){
        try {
            Statement statement = connectionCreator.getConnection().createStatement();
            String SQL = String.format("UPDATE french_dictionary SET english= '%s' WHERE french= '%s'", englishTranslation, frenchWord);
            statement.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFrenchToEnglishTranslation(String enteredText) {
        try {
            Statement statement = connectionCreator.getConnection().createStatement();
            String SQL = String.format("SELECT english FROM french_dictionary WHERE LOWER(french)= LOWER('%s')", enteredText);

            ResultSet resultSet = statement.executeQuery(SQL);
            resultSet.next();

            return resultSet.getString("english");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
