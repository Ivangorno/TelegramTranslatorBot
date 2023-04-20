package com.utill;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.utill.AllowedLetters.*;
import static org.junit.Assert.assertEquals;

class WordUtilsTest {

    @Test
    void enteredLettersAreCorrect(){
        SpellCheck wordUtils = new SpellCheck();
        Assertions.assertTrue(wordUtils.isWordValid("Cat", ENGLISH_LETTERS));
    }

    @Test
    void enteredWrongLetters(){
        SpellCheck wordUtils = new SpellCheck();
         assertEquals(false, wordUtils.isWordValid("Кот", ENGLISH_LETTERS));
    }
    @Test
    void capsLockLettersShouldBeValid(){
        SpellCheck wordUtils = new SpellCheck();
        assertEquals(true, wordUtils.isWordValid("DOG", ENGLISH_LETTERS));
    }

    @Test
    void exclamationPointSymbolIsNotValid(){
        SpellCheck wordUtils = new SpellCheck();
        assertEquals(false, wordUtils.isWordValid("Cat!", ENGLISH_LETTERS));
    }
}