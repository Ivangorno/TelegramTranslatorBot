package com;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class WordUtils {

    //How can we simplify this method ?
    public String[] parseEngToFreWord(String newEngWordToAdd) {
        String[] englishAndFrenchWordsPair = newEngWordToAdd.split("\\s");

        return englishAndFrenchWordsPair;
    }

    private static Set<Character> englishLetters = new HashSet<>(
            Arrays.asList('a', 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't',
                    'v', 'w', 'x', 'z', 'e', 'i', 'o', 'u', 'y'));

    // It's better to separate this method from EnglishToFrenchDictionary class as it's part of bussines logic
    // move here FrenchToEnglishDictionary logic as well.
    public static boolean isEnglish(String enteredWords) {
        for (char c : enteredWords.toLowerCase().toCharArray()) {
            if (!englishLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }
}

