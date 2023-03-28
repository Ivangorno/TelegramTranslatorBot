package com.utill;

import org.springframework.stereotype.Component;

@Component
public class CheckArrayOfEnteredWords {

    public boolean checkArray(String[] englishAndFrenchWord, int desiredSize) {
        return englishAndFrenchWord.length == desiredSize;
    }
}



