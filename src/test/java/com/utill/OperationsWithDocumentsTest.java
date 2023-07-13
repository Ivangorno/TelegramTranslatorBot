package com.utill;

import com.TgDictionaryBot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;

import static com.utill.OperationsWithDocuments.parseJson;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = OperationsWithDocuments.class)
class OperationsWithDocumentsTest {

    @Autowired
    private OperationsWithDocuments operationsWithDocuments;
    @MockBean
    private TgDictionaryBot tgDictionaryBot;
    @MockBean
    private DictionaryFunctions dictionaryFunctions;

    @Test
    public void testSendFromFileToDictionary() {
        File mockedFile = new File("./src/test/resources/ADDwords.json");
        operationsWithDocuments.sendFromFileToDictionary(parseJson(mockedFile));

        //then
        verify(dictionaryFunctions, times(4)).addWord(any());
        verify(dictionaryFunctions, times(0)).updateWord(any());
        verify(dictionaryFunctions, times(0)).deleteWord(any());
    }
}