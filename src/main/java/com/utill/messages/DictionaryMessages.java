package com.utill.messages;

import java.util.Set;

public class DictionaryMessages {
        public final static String ENTERED_NOT_CORRECT_ENGLISH_WORD = "ERROR: Incorrect english letter entered. Please enter an english word to translate";
        public final static String WORD_UPDATED_SUCCESSFULLY = "Word successfully updated: \"%s\" translates to \"%s\""; //reuse this logic to other messages.
        public final static String NEW_WORD_SUCCESSFULLY_ADDED = "New word added: \"%s\"";
        public final static String WORD_DELETED_SUCCESSFULLY = "Word successfully deleted: \"%s\"";
        public final static String DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY = "ERROR: Enter \"/del\" command and ONE WORD to delete that word from the dictionary";
        public final static String UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY = "ERROR: Enter \"/upd\" command and TWO WORDS to update a word in the dictionary";
        public final static String ADD_WORD_COMMAND_ENTERED_INCORRECTLY = "ERROR: Enter \"/add\" command and TWO WORDS to add a new word to the dictionary";

        public final static Set<Character> FRENCH_LETTERS = Set.of('a','b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t',
                'v','w','x','z','e','i','o','u','y', 'â', 'à', 'ç', 'é', 'ê', 'ë', 'è', 'ï', 'î', 'ô', 'û', 'ù');

        public final static Set<Character> ENGLISH_LETTERS = Set.of('a', 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't',
                'v', 'w', 'x', 'z', 'e', 'i', 'o', 'u', 'y');

}