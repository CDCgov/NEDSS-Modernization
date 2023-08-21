package gov.cdc.nbs.questionbank.question.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.exception.NullObjectException;
import gov.cdc.nbs.questionbank.util.Util;

class QuestionUtilTest {

    @Test
    void should_throw_null_object() {
        assertThrows(NullObjectException.class, () -> Util.requireNonNull(null, "Some message"));
    }

    @Test
    void should_set_message() {
        final String msg = "Some message";
        try {
            Util.requireNonNull(null, msg);
        } catch (NullObjectException ex) {
            assertEquals(msg, ex.getMessage());
        }
    }

    @Test
    void should_not_throw_null_object() {
        String value = Util.requireNonNull("Not null", "Some message");
        assertEquals("Not null", value);
    }
}
