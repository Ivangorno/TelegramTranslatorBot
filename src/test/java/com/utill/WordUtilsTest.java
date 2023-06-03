package com.utill;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static com.utill.AllowedLetters.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
@SpringBootTest(classes = SpellCheck.class)
@ActiveProfiles("test")
class WordUtilsTest {
    @MockBean
    private ModeSelector modeSelector;
    @Autowired
    private SpellCheck spellCheck;
    @Test
    void enteredLettersAreCorrect(){
        when(modeSelector.isEnglish()).thenReturn(true);

        Assertions.assertTrue(spellCheck.isWordValid("Cat"));
    }

    @Test
    void enteredWrongLetters(){
        SpellCheck wordUtils = new SpellCheck();
         assertEquals(false, wordUtils.isWordValid("Кіт"));
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