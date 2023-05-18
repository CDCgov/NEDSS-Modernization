package gov.cdc.nbs.questionbank.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DisplayGroupRefTest {
    @Test
    void should_return_numeric() {
        var q = new DisplayGroupRef();
        assertEquals("display_group", q.getReferenceType());
    }
}
