package gov.cdc.nbs.questionbank.entity.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.MessagingData;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.ReportingData;
import gov.cdc.nbs.questionbank.question.request.create.DateMask;
import gov.cdc.nbs.questionbank.support.QuestionCommandMother;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class DateQuestionEntityTest {

  @Test
  void should_have_date_type() {
    DateQuestionEntity q = new DateQuestionEntity();
    assertEquals("DATE", q.getDataType());
  }

  @Test
  void should_set_future_date_to_F() {
    QuestionCommand.AddDateQuestion command = addCommand(DateMask.DATE, false);

    DateQuestionEntity q = new DateQuestionEntity(command);
    assertEquals('F', q.getFutureDateIndCd().charValue());
    assertEquals("DATE", q.getMask());
  }

  @Test
  void should_set_future_date_to_T() {
    QuestionCommand.AddDateQuestion command = addCommand(DateMask.DATE, true);

    DateQuestionEntity q = new DateQuestionEntity(command);
    assertEquals('T', q.getFutureDateIndCd().charValue());
    assertEquals("DATE", q.getMask());
  }

  private QuestionCommand.AddDateQuestion addCommand(DateMask mask, boolean allowFutureDates) {
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
        new ReportingData("report label", "RDB_TABLE_NAME", "RDB_COLUMN_NAME", "DATA_MART_COL"),
        new MessagingData(false, null, null, null, false, null),
        0,
        null);
  }

  @Test
  void should_do_update() {
    DateQuestionEntity entity = QuestionEntityMother.dateQuestion();
    var command = QuestionCommandMother.update();

    entity.update(command);

    assertEquals(command.mask(), entity.getMask());
    assertEquals(command.allowFutureDates() ? 'T' : 'F', entity.getFutureDateIndCd().charValue());
  }

  @Test
  void should_not_set_reporting_messaging_readonly() {
    var command =
        new QuestionCommand.AddDateQuestion(
            DateMask.DATE,
            true,
            new QuestionCommand.QuestionData(
                CodeSet.PHIN,
                "TEST9900002",
                "Date Question Unique Name",
                "INV",
                "Date question description",
                "Date question label",
                "Date question tooltip",
                1026l, // readonly
                "Date question admin comments",
                new QuestionCommand.QuestionOid("oid", "oid system")),
            null,
            null,
            9999000L,
            Instant.now());
    assertThat(new DateQuestionEntity(command)).isNotNull();
  }
}
