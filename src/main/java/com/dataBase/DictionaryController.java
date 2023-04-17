package com.dataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.Map;
import java.util.TreeMap;


@Component
public class DictionaryController {

    @Autowired
    private ConnectionCreator connectionCreator;

    private Map<String, String> engToFrDictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    @PostConstruct
    public void getDictionary() {
        try {
            Statement statement = connectionCreator.getConnection().createStatement();
            String SQL = "SELECT * FROM ENGLISH_DICTIONARY";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                engToFrDictionary.put(resultSet.getString("english"), resultSet.getString("french"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public Map<String, String> getEngToFrDictionary() {
        return engToFrDictionary;
    }

    public String getEnglishToFrenchTranslation(String enteredText) {
        try {
            Statement statement = connectionCreator.getConnection().createStatement();
            String SQL = String.format("SELECT french FROM ENGLISH_DICTIONARY WHERE english ='%s'", enteredText);

            ResultSet resultSet = statement.executeQuery(SQL);
            resultSet.next();

            return resultSet.getString("french");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
