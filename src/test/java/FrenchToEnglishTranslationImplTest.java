import com.EnglishToFrenchDictionary;
import com.TgDictionaryBot;
import com.UTF8Validation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = EnglishToFrenchDictionary.class)
@MockBean({
        UTF8Validation.class,
        TgDictionaryBot.class
})
class FrenchToEnglishTranslationImplTest {

    @Autowired
    private EnglishToFrenchDictionary englishToFrenchDictionary;

    @Test
    void frToEngTranslate() {
       assertEquals("Bonjour",englishToFrenchDictionary.getEnglishToFrenchTranslation("Hello"));
    }
}