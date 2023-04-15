package com.config;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class DictionaryMapper implements RowMapper<Map<String,String>> {


    @Override
    public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {

        Map<String, String> dictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        while (rs.next()) {
            dictionary.put(rs.getString("english"), rs.getString("french"));
        }

            return dictionary;
        }
    }

