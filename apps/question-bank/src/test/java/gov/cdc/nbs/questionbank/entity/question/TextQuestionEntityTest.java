package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class TextQuestionEntityTest {

    @Test
    void should_have_text_type() {
        TextQuestionEntity entity = new TextQuestionEntity();
        assertEquals("TEXT", entity.getDataType());
    }
}
