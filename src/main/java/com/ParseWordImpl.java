package com;

import org.springframework.stereotype.Component;

import java.util.StringTokenizer;
@Component
public class ParseWordImpl {


    private String newEngWordToAdd;

    private StringTokenizer stringTokenizer;

    public String[] parseEngToFreWord(String newEngWordToAdd) {
        String[] engAndFrWord = newEngWordToAdd.split("\\s");

        return engAndFrWord;
    }
}

