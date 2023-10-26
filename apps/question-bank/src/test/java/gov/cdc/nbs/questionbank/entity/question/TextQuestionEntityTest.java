package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.support.QuestionCommandMother;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;

class TextQuestionEntityTest {

    @Test
    void should_have_text_type() {
        TextQuestionEntity entity = new TextQuestionEntity();
        assertEquals("TEXT", entity.getDataType());
    }

    @Test
    void should_do_update() {
        TextQuestionEntity entity = QuestionEntityMother.textQuestion();
        var command = QuestionCommandMother.update();

        entity.update(command);

        assertEquals(command.mask(), entity.getMask());
        assertEquals(command.fieldLength(), entity.getFieldSize());
        assertEquals(command.defaultValue(), entity.getDefaultValue());
    }
}
