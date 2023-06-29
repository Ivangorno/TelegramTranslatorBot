package com.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Component
@Table(name = "ENGLISH_DICTIONARY")
public class EnglishToFrenchPair {

    @Column(name = "english")
    private String englishWord;

    @Column(name = "french")
    private  String frenchWord;

    public EnglishToFrenchPair(String englishWord, String frenchWord) {
        this.englishWord = englishWord;
        this.frenchWord = frenchWord;
    }


//    public void readFile() throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        EnglishToFrenchPair pair = new EnglishToFrenchPair(englishWord, frenchWord);
//        objectMapper.readValue(new File("src/main/resources/userFiles/words.json"), EnglishToFrenchPair.class);
//    }

//    public String[] englishFrenchPair(){
//        ObjectMapper objectMapper = new ObjectMapper();
//        String[] pair;
//        try {
//            Map<String,String> result = objectMapper.readValue(new File("src/main/resources/userFiles/words.json"), Map<String, String> );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        return pair;
//    }

}
