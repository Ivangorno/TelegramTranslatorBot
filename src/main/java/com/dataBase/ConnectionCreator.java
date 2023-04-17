package com.dataBase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionCreator {

    @Value("${dictionaryController.EngDictionaryUrl}")
    private String englishDictionaryUrl ="jdbc:postgresql://localhost:5432/English_Dictionary";

    @Value("${dictionaryController.EngDictionaryUserName}")
    private String userName ="postgres";

    @Value("${dictionaryController.EngDictionaryPassword}")
    private String password ="postgres";


    private Connection connection;
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(englishDictionaryUrl, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
