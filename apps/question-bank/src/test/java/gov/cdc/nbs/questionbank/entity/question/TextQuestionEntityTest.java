package gov.cdc.nbs.questionbank.entity.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.request.create.TextMask;
import gov.cdc.nbs.questionbank.support.QuestionCommandMother;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import java.time.Instant;
import org.junit.jupiter.api.Test;

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

  @Test
  void should_not_set_reporting_messaging_readonly() {
    var command =
        new QuestionCommand.AddTextQuestion(
            TextMask.TXT,
            50,
            "default value",
            new QuestionCommand.QuestionData(
                CodeSet.LOCAL,
                "TEST9900001",
                "Text Question Unique Name",
                "ADMN",
                "Text question description",
                "Text question label",
                "Text question tooltip",
                1026l, // readonly
                "Text question admin comments",
                new QuestionCommand.QuestionOid("oid", "oid system")),
            null,
            null,
            9999000L,
            Instant.now());
    assertThat(new TextQuestionEntity(command)).isNotNull();
  }
}
