package com.utill;

import com.TgDictionaryBot;
import com.dataBase.DictionaryDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.utill.messages.DictionaryCommands.ENGLISH_DICTIONARY;
import static com.utill.messages.DictionaryCommands.FRENCH_DICTIONARY;
import static com.utill.messages.DictionaryMessages.UPDATE_A_WORD_COMMAND_ENTERED_INCORRECTLY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    public void testUpdateWordWithWordsOutOfArray() {
        //given
        String[] testArrayOfWords = new String[2];
        testArrayOfWords[0] = "Mother";
        testArrayOfWords[1] = "Mère";
//        testArrayOfWords[2] = "Test";

        //when
        dictionaryFunctions.updateWord(testArrayOfWords);

        //then
        verify(tgDictionaryBot, times(2)).sendMessage(any());
        verify(dictionaryDao, times(1)).updateTranslation(any(), any());
    }

}