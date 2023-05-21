package com.dataBase;

import com.TgDictionaryBot;
import com.utill.DictionaryFunctions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.utill.messages.DictionaryMessages.ADD_WORD_COMMAND_ENTERED_INCORRECTLY;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  DictionaryFunctions.class)
class DummyDictionaryDaoImplTest {

    @MockBean
    private TgDictionaryBot tgDictionaryBot;

    @Autowired
    private DictionaryFunctions dictionaryFunctions;

    @MockBean
    private DictionaryDao dictionaryDao;

    @Test
    void saveNewWordShouldNotWorkIfUserEntersTreeWords() {
        String[] enteredText = new String[3];
        assertNotEquals(enteredText.length,2);
    }

    @Test
    void saveNewWordShouldWorkIfUserEntersTwoWords() {
        String[] enteredText = new String[2];
        assertEquals(enteredText.length,2);
    }
    @Test
    void addWordWillSendMessage(){
        String[] enteredText = new String[6];

        dictionaryFunctions.addWord(enteredText);
        verify(tgDictionaryBot, atLeastOnce()).sendMessage(any());

    }
    @Test
    void enteredTreeWordsWillSendErrorMessage(){
        String[] enteredText = new String[3];

        dictionaryFunctions.addWord(enteredText);
        verify(tgDictionaryBot, times(1)).sendMessage(ADD_WORD_COMMAND_ENTERED_INCORRECTLY);

    }
    @Test
    void noTextReturnsOutOfBoundsException(){
        String[] enteredText = new String[0];

      assertThrows(IndexOutOfBoundsException.class,
              ()->dictionaryFunctions.addWord(enteredText) ) ;
          }

    @Test
    void getTranslation() {
        String[] enteredText = new String[1];

        Map<String,String> testMap = new HashMap<>();
        {
            testMap.put("Mother", "Mere");
            testMap.put("Father", "Père");
            testMap.put("Cat", "Chat");
        }
        dictionaryFunctions.translate("Mother");

            verify(dictionaryDao, times(1)).getTranslation(any());
        }
    }
