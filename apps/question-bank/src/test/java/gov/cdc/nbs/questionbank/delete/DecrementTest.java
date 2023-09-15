package gov.cdc.nbs.questionbank.delete;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class DecrementTest {

    private Decrement decrement = new Decrement();

    @Test
    void should_show_coverage() {
        Integer output = decrement.decrement(2);
        assertEquals(1, output.intValue());

    }
}
