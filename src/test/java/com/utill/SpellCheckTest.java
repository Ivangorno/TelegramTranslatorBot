package com.utill;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckTest {

    @Test
    void capitalizeAllLowerCase() {

        SpellCheck spellCheck = new SpellCheck();
                assertEquals("City", spellCheck.capitalizeText("city"));
    }

    @Test
    void capitalizeAllUpperCase() {

        SpellCheck spellCheck = new SpellCheck();
        assertEquals("City", spellCheck.toCorrectCapitalization("cITy"));
    }

    @Test
    void capitalizeRandomCapitalizationCase() {

        SpellCheck spellCheck = new SpellCheck();
        assertEquals("Longword", spellCheck.toCorrectCapitalization("LonGWOrd"));
    }
}