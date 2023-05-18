package gov.cdc.nbs.questionbank.entities;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class DisplayElementRefTest {

    @Test
    void should_return_numeric() {
        var q = new DisplayElementRef();
        assertEquals("display_element", q.getReferenceType());
    }
}
