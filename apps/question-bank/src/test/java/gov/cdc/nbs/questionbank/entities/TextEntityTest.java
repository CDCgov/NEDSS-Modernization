package gov.cdc.nbs.questionbank.entities;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class TextEntityTest {

    @Test
    void should_return_numeric() {
        var q = new TextEntity();
        assertEquals("text", q.getDisplayType());
    }
}
