package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.MessagingData;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.ReportingData;

class DateQuestionTest {

    @Test
    void should_have_date_type() {
        DateQuestion q = new DateQuestion();
        assertEquals("DATE", q.getDataType());
    }

    @Test
    void should_set_future_date_to_F() {
        QuestionCommand.AddDateQuestion command = addCommand("mask", false);

        DateQuestion q = new DateQuestion(command);
        assertEquals('F', q.getFutureDateIndCd().charValue());
        assertEquals("mask", q.getMask());
    }

    @Test
    void should_set_future_date_to_T() {
        QuestionCommand.AddDateQuestion command = addCommand("mask1", true);

        DateQuestion q = new DateQuestion(command);
        assertEquals('T', q.getFutureDateIndCd().charValue());
        assertEquals("mask1", q.getMask());
    }

    private QuestionCommand.AddDateQuestion addCommand(String mask, boolean allowFutureDates) {
        return new QuestionCommand.AddDateQuestion(
                mask,
                allowFutureDates,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                new ReportingData(
                        null,
                        null,
                        null,
                        null),
                new MessagingData(
                        false,
                        null,
                        null,
                        null,
                        false,
                        null),
                0,
                null);
    }
}
