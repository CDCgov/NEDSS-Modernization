package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class CodedQuestionEntityTest {

    @Test
    void should_return_coded() {
        CodedQuestionEntity entity = new CodedQuestionEntity();

        assertEquals("CODED", entity.getDataType());
    }
}
