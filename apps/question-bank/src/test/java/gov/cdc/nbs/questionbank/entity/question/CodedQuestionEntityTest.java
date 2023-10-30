package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.support.QuestionCommandMother;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;

class CodedQuestionEntityTest {

    @Test
    void should_return_coded() {
        CodedQuestionEntity entity = new CodedQuestionEntity();

        assertEquals("CODED", entity.getDataType());
    }

    @Test
    void should_do_update() {
        var command = QuestionCommandMother.update();
        CodedQuestionEntity q = QuestionEntityMother.codedQuestion();
        q.update(command);

        assertEquals(command.defaultValue(), q.getDefaultValue());
        assertEquals(command.valueSet(), q.getCodeSetGroupId());
    }
}
