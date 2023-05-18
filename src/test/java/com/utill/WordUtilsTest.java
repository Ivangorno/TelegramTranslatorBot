package com.utill;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.utill.AllowedLetters.*;
import static org.junit.Assert.assertEquals;

class WordUtilsTest {

    @Test
    void enteredLettersAreCorrect(){
        SpellCheck wordUtils = new SpellCheck();
        Assertions.assertTrue(wordUtils.isWordValid("Cat"));
    }

    @Test
    void enteredWrongLetters(){
        SpellCheck wordUtils = new SpellCheck();
         assertEquals(false, wordUtils.isWordValid("Кот"));
    }
    @Test
    void capsLockLettersShouldBeValid(){
        SpellCheck wordUtils = new SpellCheck();
        assertEquals(true, wordUtils.isWordValid("DOG"));
    }

    @Test
    void exclamationPointSymbolIsNotValid(){
        SpellCheck wordUtils = new SpellCheck();
        assertEquals(false, wordUtils.isWordValid("Cat!"));
    }
}