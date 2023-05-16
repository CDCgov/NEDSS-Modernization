package gov.cdc.nbs.questionbank.entities;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class TextQuestionEntityTest {

    @Test
    void should_return_text() {
        var q = new TextQuestionEntity();
        assertEquals("text_question", q.getDisplayType());
    }
}
