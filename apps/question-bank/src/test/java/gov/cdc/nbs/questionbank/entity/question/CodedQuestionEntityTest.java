package gov.cdc.nbs.questionbank.entity.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.support.QuestionCommandMother;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import java.time.Instant;
import org.junit.jupiter.api.Test;

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

  @Test
  void should_not_set_reporting_messaging_readonly() {
    var command =
        new QuestionCommand.AddCodedQuestion(
            900l,
            "123",
            new QuestionCommand.QuestionData(
                CodeSet.LOCAL,
                "TEST9900004",
                "Coded Question Unique Name",
                "ADMN",
                "Coded question description",
                "Coded question label",
                "Coded question tooltip",
                1026l, // readonly
                "Coded question admin comments",
                new QuestionCommand.QuestionOid("oid", "oid system")),
            null,
            null,
            9999000L,
            Instant.now());
    assertThat(new CodedQuestionEntity(command)).isNotNull();
  }
}
