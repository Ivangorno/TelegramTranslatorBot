package com.utill.messages;

public class DictionaryCommands {
    public final static String ADD_NEW_WORD = "/add";
    public final static String DELETE_WORD = "/del";
    public final static String UPDATE_WORD = "/upd";
    public final static String ENGLISH_DICTIONARY = "english";
    public final static String FRENCH_DICTIONARY = "french";
    public final static String CHANGE_LANGUAGE = "/change language";

    public final static String INSERT_NEW_WORDS_INTO_DICTIONARY = "INSERT INTO english_dictionary VALUES('%s','%s');" +
                                                             "INSERT INTO french_dictionary  VALUES('%s','%s') ";

    public final static String DELETE_ENGLISH_TO_FRENCH_TRANSLATION = "DELETE FROM english_dictionary WHERE %s= '%s'; DELETE FROM french_dictionary  WHERE %s= '%s'";
    public final static String DELETE_FRENCH_TO_ENGLISH_TRANSLATION = "DELETE FROM french_dictionary WHERE %s= '%s'; DELETE FROM english_dictionary  WHERE %s= '%s'";
}