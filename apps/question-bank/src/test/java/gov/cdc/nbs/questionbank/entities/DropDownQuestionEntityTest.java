package gov.cdc.nbs.questionbank.entities;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class DropDownQuestionEntityTest {

    @Test
    void should_return_dropdown() {
        var question = new DropdownQuestionEntity();
        assertEquals("dropdown_question", question.getDisplayType());
    }
}
