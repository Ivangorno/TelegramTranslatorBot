package com.utill;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CheckArrayOfEnteredWords.class)
class CheckArrayOfEnteredWordsTest {

    @Autowired
    private CheckArrayOfEnteredWords checkArrayOfEnteredWords;

    @Test
    public void testCheckArray() {
        String[] dummyArray = new String[3];
        dummyArray[0] = "/add";
        dummyArray[1] = "Hello";
        dummyArray[2] = "Bonjour";

        boolean result = checkArrayOfEnteredWords.checkArray(dummyArray, 3);

        assertEquals(true, result);
    }

    @Test
    public void testCheckArrayWithWrongValue() {
        String[] dummyArray = new String[3];
        dummyArray[0] = "/add";
        dummyArray[1] = "Hello";
        dummyArray[2] = "Bonjour";

        boolean result = checkArrayOfEnteredWords.checkArray(dummyArray, 2);

        assertEquals(false, result);
    }

}