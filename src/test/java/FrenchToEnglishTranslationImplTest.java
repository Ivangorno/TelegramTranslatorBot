import com.TgDictionaryBot;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest( classes = EnglishDictionary.class)
@MockBean({
        TgDictionaryBot.class
})
class FrenchToEnglishTranslationImplTest {



    @Autowired
    private DictionaryController dictionaryController;

    }