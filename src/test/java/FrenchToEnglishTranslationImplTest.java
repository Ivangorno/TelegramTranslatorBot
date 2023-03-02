import com.FrenchToEnglishTranslationImpl;
import com.TgDictionaryBot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrenchToEnglishTranslationImplTest {

    private TgDictionaryBot tgDictionaryBot = new TgDictionaryBot();
    FrenchToEnglishTranslationImpl frenchToEnglishTranslation = new FrenchToEnglishTranslationImpl();


    @Test
    void frToEngTranslate() {
       assertEquals("Hello",frenchToEnglishTranslation.frToEngTranslate("Bonjour"));
    }

    @Test
    void engToFrTranslate() {
    }
}