import com.dataBase.DictionaryController;
import com.EnglishDictionary;
import com.TgDictionaryBot;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = EnglishDictionary.class)
@MockBean({
        TgDictionaryBot.class
})
class FrenchToEnglishTranslationImplTest {

    @Autowired
    private EnglishDictionary englishDictionary;

    @Autowired
    private DictionaryController dictionaryController;

    @Test
    void frToEngTranslate() {
       assertEquals("Bonjour", dictionaryController.getEnglishToFrenchTranslation("Hello"));
    }
}