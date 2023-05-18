package com.utill.messages;

import java.util.Set;

public class DictionaryCommands {
    public final static String ADD_WORD_MODE = "/add_mode";
    public final static String DELETE_WORD_MODE = "/delete_mode";
    public final static String UPDATE_WORD_MODE = "/update_mode";
    public final static String CHANGE_LANGUAGE = "Change language";
    public final static String EXIT_TO_TRANSLATION_MODE = "Exit To Translation";
    public final static String ENGLISH_DICTIONARY = "english";
    public final static String FRENCH_DICTIONARY = "french";


    public final static String ENTER_NEW_WORD_TO_UPDATE = "Enter another word to UPDATE it's translation";
    public final static String ENTER_ANOTHER_WORD_TO_DELETE = "Enter another word to DELETE from the dictionary";
    public final static String ENTER_NEW_WORD_TO_ADD = "Enter another word to ADD to the dictionary";


    public final static String TRANSLATE_ENGLISH_TO_FRENCH_COMMAND = "SELECT french FROM english_dictionary WHERE LOWER(english)= LOWER('%s')";
    public final static String TRANSLATE_FRENCH_TO_ENGLISH_COMMAND = "SELECT english FROM french_dictionary WHERE LOWER(french)= LOWER('%s')";
    public final static String INSERT_NEW_ENGLISH_WORD_AND_TRANSLATION_INTO_DICTIONARY = "INSERT INTO english_dictionary VALUES('%s','%s');";
    public final static String INSERT_NEW_FRENCH_WORD_AND_TRANSLATION_INTO_DICTIONARY = "INSERT INTO french_dictionary VALUES('%s','%s');";
    public final static String DELETE_ENGLISH_TO_FRENCH_TRANSLATION = "DELETE FROM english_dictionary WHERE LOWER(english)= '%1$s'; DELETE FROM french_dictionary  WHERE LOWER(english)= '%1$s'";
    public final static String DELETE_FRENCH_TO_ENGLISH_TRANSLATION = "DELETE FROM french_dictionary WHERE LOWER(french)= '%1$s'; DELETE FROM english_dictionary  WHERE LOWER(french)= '%1$s'";

    public final static String UPDATE_FRENCH_TRANSLATION_TO_ENGLISH_WORD =
            "UPDATE english_dictionary SET french= '%1$s' WHERE english= '%2$s'; " +
                    "UPDATE french_dictionary SET french= '%1$s' WHERE english= '%2$s' ";

    public final static String UPDATE_ENGLISH_TRANSLATION_TO_FRENCH_WORD =
            "UPDATE french_dictionary SET english= '%1$s' WHERE french= '%2$s'; " +
                    "UPDATE english_dictionary SET english= '%1$s' WHERE french= '%2$s' ";

    public final static Set<String> ALL_COMMANDS = Set.of(ADD_WORD_MODE, DELETE_WORD_MODE, UPDATE_WORD_MODE, EXIT_TO_TRANSLATION_MODE, CHANGE_LANGUAGE);
}
