package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;

class WaQuestionHistTest {

    @Test
    void should_set_numeric_fields() {
        NumericQuestionEntity actual = QuestionEntityMother.numericQuestion();
        WaQuestionHist hist = new WaQuestionHist(actual);

        assertEquals(actual.getMask(), hist.getMask());
        assertEquals(actual.getFieldSize(), hist.getFieldSize());
        assertEquals(actual.getDefaultValue(), hist.getDefaultValue());
        assertEquals(actual.getMinValue(), hist.getMinValue());
        assertEquals(actual.getMaxValue(), hist.getMaxValue());
        assertEquals(actual.getUnitTypeCd(), hist.getUnitTypeCd());
        assertEquals(actual.getUnitValue(), hist.getUnitValue());
    }

    @Test
    void should_set_date_fields() {
        DateQuestionEntity actual = QuestionEntityMother.dateQuestion();
        WaQuestionHist hist = new WaQuestionHist(actual);

        assertEquals(actual.getMask(), hist.getMask());
        assertEquals(actual.getFutureDateIndCd(), hist.getFutureDateIndCd());
    }

    @Test
    void should_set_coded_fields() {
        CodedQuestionEntity actual = QuestionEntityMother.codedQuestion();
        WaQuestionHist hist = new WaQuestionHist(actual);

        assertEquals(actual.getCodeSetGroupId(), hist.getCodeSetGroupId());
        assertEquals(actual.getDefaultValue(), hist.getDefaultValue());
    }

    @Test
    void should_throw_exception_unrecognized_type() {
        WaQuestion question = new WaQuestion() {
            @Override
            public String getDataType() {
                return "Test";
            }
        };

        assertThrows(UpdateQuestionException.class, () -> new WaQuestionHist(question));
    }
}
