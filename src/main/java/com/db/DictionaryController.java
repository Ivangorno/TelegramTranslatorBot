package com.db;

import com.config.DictionaryMapper;
import com.config.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.sql.DataSource;
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
    public String getEnglishToFrenchTranslation(String text) {
        return engToFrDictionary.get(text);
    }


}
