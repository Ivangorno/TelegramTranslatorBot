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

    public final static String INSERT_NEW_WORDS_INTO_DICTIONARY = "INSERT INTO english_dictionary VALUES('%s','%s');" +
                                                             "INSERT INTO french_dictionary  VALUES('%s','%s') ";

    public final static String DELETE_ENGLISH_TO_FRENCH_TRANSLATION = "DELETE FROM english_dictionary WHERE %s= '%s'; DELETE FROM french_dictionary  WHERE %s= '%s'";
    public final static String DELETE_FRENCH_TO_ENGLISH_TRANSLATION = "DELETE FROM french_dictionary WHERE %s= '%s'; DELETE FROM english_dictionary  WHERE %s= '%s'";

    public final static Set<String> ALL_COMMANDS = Set.of(ADD_WORD_MODE, DELETE_WORD_MODE, UPDATE_WORD_MODE, EXIT_TO_TRANSLATION_MODE, CHANGE_LANGUAGE);
}
