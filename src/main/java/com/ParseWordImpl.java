package com;

import org.springframework.stereotype.Component;

@Component
public class ParseWordImpl {

    public String[] parseEngToFreWord(String newEngWordToAdd) {
        String[] englishAndFrenchWordsPair = newEngWordToAdd.split("\\s");

        return englishAndFrenchWordsPair;
    }
}

