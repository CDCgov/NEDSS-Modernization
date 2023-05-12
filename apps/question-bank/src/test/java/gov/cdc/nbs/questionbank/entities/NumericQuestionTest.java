package gov.cdc.nbs.questionbank.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class NumericQuestionTest {

    @Test
    void should_return_numeric() {
        var q = new NumericQuestion();
        assertEquals("numeric_question", q.getDisplayType());
    }
}
