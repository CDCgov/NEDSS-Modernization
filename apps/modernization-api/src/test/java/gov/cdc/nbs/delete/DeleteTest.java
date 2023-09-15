package gov.cdc.nbs.delete;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class DeleteTest {

    private Increment increment = new Increment();

    @Test
    void should_show_coverage() {
        Integer output = increment.increment(1);
        assertEquals(2, output.intValue());
    }

}
