package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.support.QuestionCommandMother;

class TextQuestionEntityTest {

    @Test
    void should_have_text_type() {
        TextQuestionEntity entity = new TextQuestionEntity();
        assertEquals("TEXT", entity.getDataType());
    }

    @Test
    void should_do_update() {
        var command = QuestionCommandMother.update();
        TextQuestionEntity q = new TextQuestionEntity();
        q.setQuestionType("LOCAL");
        q.setVersionCtrlNbr(1);

        q.update(command);

        assertEquals(command.mask(), q.getMask());
        assertEquals(command.fieldLength(), q.getFieldSize());
        assertEquals(command.defaultValue(), q.getDefaultValue());
    }
}
