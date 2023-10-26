package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.MessagingData;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.ReportingData;
import gov.cdc.nbs.questionbank.support.QuestionCommandMother;

class DateQuestionEntityTest {

    @Test
    void should_have_date_type() {
        DateQuestionEntity q = new DateQuestionEntity();
        assertEquals("DATE", q.getDataType());
    }

    @Test
    void should_set_future_date_to_F() {
        QuestionCommand.AddDateQuestion command = addCommand("mask", false);

        DateQuestionEntity q = new DateQuestionEntity(command);
        assertEquals('F', q.getFutureDateIndCd().charValue());
        assertEquals("mask", q.getMask());
    }

    @Test
    void should_set_future_date_to_T() {
        QuestionCommand.AddDateQuestion command = addCommand("mask1", true);

        DateQuestionEntity q = new DateQuestionEntity(command);
        assertEquals('T', q.getFutureDateIndCd().charValue());
        assertEquals("mask1", q.getMask());
    }

    private QuestionCommand.AddDateQuestion addCommand(String mask, boolean allowFutureDates) {
        return new QuestionCommand.AddDateQuestion(
                mask,
                allowFutureDates,
                new QuestionCommand.QuestionData(
                        CodeSet.LOCAL,
                        "localId",
                        "uniqueName",
                        "subgroup",
                        "description",
                        "label",
                        "tooltip",
                        1234L,
                        "comments",
                        null),
                new ReportingData(
                        "report label",
                        "RDB_TABLE_NAME",
                        "RDB_COLUMN_NAME",
                        "DATA_MART_COL"),
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

    @Test
    void should_do_update() {
        var command = QuestionCommandMother.update();
        DateQuestionEntity q = new DateQuestionEntity();
        q.setQuestionType("LOCAL");
        q.setVersionCtrlNbr(1);

        q.update(command);

        assertEquals(command.mask(), q.getMask());
        assertEquals(command.allowFutureDates() ? 'T' : 'F', q.getFutureDateIndCd().charValue());

    }
}
