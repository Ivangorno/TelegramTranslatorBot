package com.utill;

import org.springframework.stereotype.Component;

@Component
public class CheckArrayOfEnteredWords {

    public boolean checkArray(String[] enteredWords, int correctArraySize) {
        return enteredWords.length == correctArraySize;
    }
}



