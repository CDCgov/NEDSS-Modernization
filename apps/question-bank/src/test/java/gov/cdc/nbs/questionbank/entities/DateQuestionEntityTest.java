package gov.cdc.nbs.questionbank.entities;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class DateQuestionEntityTest {

    @Test
    void should_return_date() {
        var q = new DateQuestionEntity();
        assertEquals("date_question", q.getDisplayType());
    }
}
