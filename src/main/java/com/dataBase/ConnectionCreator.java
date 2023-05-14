package com.dataBase;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@Profile("dev")
public class ConnectionCreator {

    @Value("${connectionCreator.EnglishDictionaryUrl}")
    private String englishDictionaryUrl;
    @Value("${connectionCreator.EngDictionaryUserName}")
    private String userName;
    @Value("${connectionCreator.EngDictionaryPassword}")
    private String password;

    @Bean(name="connection")
    public Connection createConnection()
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return  DriverManager.getConnection(englishDictionaryUrl, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
