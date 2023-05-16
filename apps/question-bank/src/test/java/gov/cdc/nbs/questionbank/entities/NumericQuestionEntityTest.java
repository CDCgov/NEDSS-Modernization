package gov.cdc.nbs.questionbank.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class NumericQuestionEntityTest {

    @Test
    void should_return_numeric() {
        var q = new NumericQuestionEntity();
        assertEquals("numeric_question", q.getDisplayType());
    }
}
