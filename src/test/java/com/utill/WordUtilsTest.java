package com.utill;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.utill.AllowedLetters.*;
import static org.junit.Assert.assertEquals;

class WordUtilsTest {

    @Test
    void enteredLettersAreCorrect(){
        WordUtils wordUtils = new WordUtils();
        Assertions.assertTrue(wordUtils.isWordValid("Cat", ENGLISH_LETTERS));
    }

    @Test
    void enteredWrongLetters(){
        WordUtils wordUtils = new WordUtils();
         assertEquals(false, wordUtils.isWordValid("Кот", ENGLISH_LETTERS));
    }
    @Test
    void capsLockLettersShouldBeValid(){
        WordUtils wordUtils = new WordUtils();
        assertEquals(true, wordUtils.isWordValid("DOG", ENGLISH_LETTERS));
    }

    @Test
    void exclamationPointSymbolIsNotValid(){
        WordUtils wordUtils = new WordUtils();
        assertEquals(false, wordUtils.isWordValid("Cat!", ENGLISH_LETTERS));
    }
}