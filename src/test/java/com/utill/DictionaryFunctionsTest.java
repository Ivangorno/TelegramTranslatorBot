package com.utill;

import com.TgDictionaryBot;
import com.dataBase.DictionaryDao;
import com.dataBase.DummyDictionaryDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.utill.messages.DictionaryCommands.*;
import static com.utill.messages.DictionaryMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes =
        DictionaryFunctions.class)
@ActiveProfiles("test")
public class DictionaryFunctionsTest {

    @Autowired
    private DictionaryFunctions dictionaryFunctions;

    @MockBean
    private TgDictionaryBot tgDictionaryBot;

    @MockBean
    private DictionaryDao dictionaryDao;


    @Test
    public void testUpdateWordWithTooManyWords() {
        //given
        String[] testArrayOfWords = new String[3];
        testArrayOfWords[0] = "Mother";
        testArrayOfWords[1] = "Mère";
        testArrayOfWords[2] = "Test";

        //when
        dictionaryFunctions.updateWord(testArrayOfWords);

        //then
        verify(tgDictionaryBot, times(1)).sendMessage(any());
        verify(dictionaryDao, times(0)).updateTranslation(any(), any());
    }
    @Test
    public void testUpdateWordWithCorrectNumberOfWords() {
        //given
        String[] testArrayOfWords = new String[2];
        testArrayOfWords[0] = "Mother";
        testArrayOfWords[1] = "Mère";

        //when
        dictionaryFunctions.updateWord(testArrayOfWords);

        //then
        verify(tgDictionaryBot, times(2)).sendMessage(any());
        verify(dictionaryDao, times(1)).updateTranslation(any(), any());
    }
    @Test
    public void testUpdateWordWithTooFewWords() {
        //given
        String[] testArrayOfWords = new String[1];
        testArrayOfWords[0] = "Mother";

        //when
        dictionaryFunctions.updateWord(testArrayOfWords);

        //then
        verify(tgDictionaryBot, times(1)).sendMessage(any());
        verifyNoMoreInteractions(dictionaryDao, tgDictionaryBot);
    }

    @Test
    void testDeleteWordMultipleWordsEntered() {
        String[] enteredText = { "WordToDelete", "ExtraWord" };

        dictionaryFunctions.deleteWord(enteredText);

        verify(tgDictionaryBot).sendMessage(DELETE_A_WORD_COMMAND_ENTERED_INCORRECTLY);
        verifyNoMoreInteractions(dictionaryDao, tgDictionaryBot);
    }

    @Test
    void testDeleteWordWithOneWordEntered() {
        String[] enteredText = { "WordToDelete" };

        dictionaryFunctions.deleteWord(enteredText);

        verify(dictionaryDao).deleteWord("WordToDelete");
        verify(tgDictionaryBot).sendMessage(String.format(WORD_DELETED_SUCCESSFULLY, enteredText));
        verify(tgDictionaryBot).sendMessage(ENTER_ANOTHER_WORD_TO_DELETE);
        verifyNoMoreInteractions(dictionaryDao, tgDictionaryBot);
    }

    @Test
    public void testAddWordCorrectInput() {
        String[] enteredText = new String[]{"baseWord", "translation"};

        dictionaryFunctions.addWord(enteredText);

        verify(dictionaryDao).saveNewWord("baseWord", "translation");
        verify(tgDictionaryBot).sendMessage(String.format(NEW_WORD_SUCCESSFULLY_ADDED, "translation"));
        verify(tgDictionaryBot).sendMessage(ENTER_NEW_WORD_TO_ADD);
        verifyNoMoreInteractions(tgDictionaryBot, dictionaryDao);
    }

    @Test
    public void testAddWordOneWord() {
        String[] enteredText = new String[]{"baseWord"};

        dictionaryFunctions.addWord(enteredText);

        verify(tgDictionaryBot).sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
        verifyNoMoreInteractions(tgDictionaryBot, dictionaryDao);
    }

    @Test
    public void testAddWordToManyWords() {
        String[] enteredText = new String[]{"baseWord", "translation", "extraWord"};

        dictionaryFunctions.addWord(enteredText);

        verify(tgDictionaryBot).sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);
        verifyNoMoreInteractions(tgDictionaryBot, dictionaryDao);
    }

    @Test
    public void testGetTranslationExistingWord() {

        String enteredText = "Mother";

        //when
        dictionaryFunctions.translate(enteredText);

        //then
        verify(dictionaryDao, times(1)).getTranslation(any());
        verifyNoInteractions(tgDictionaryBot);
    }

    @Test
    public void testGetTranslationNonExistingWord() {

        String enteredText = "Мати";

        //when
        dictionaryFunctions.translate(enteredText);

        //then
         verify(dictionaryDao, times(1)).getTranslation(enteredText);
         assertNull(dictionaryDao.getTranslation(enteredText));
         verifyNoInteractions(tgDictionaryBot);
    }


}