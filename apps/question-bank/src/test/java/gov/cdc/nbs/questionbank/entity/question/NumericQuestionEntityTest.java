package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class NumericQuestionEntityTest {

    @Test
    void should_have_date_type() {
        NumericQuestionEntity q = new NumericQuestionEntity();
        assertEquals("NUMERIC", q.getDataType());
    }
}
