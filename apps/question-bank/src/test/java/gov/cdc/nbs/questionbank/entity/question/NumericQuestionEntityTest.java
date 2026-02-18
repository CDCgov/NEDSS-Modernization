package gov.cdc.nbs.questionbank.entity.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.request.create.NumericMask;
import gov.cdc.nbs.questionbank.support.QuestionCommandMother;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class NumericQuestionEntityTest {

  @Test
  void should_have_date_type() {
    NumericQuestionEntity q = new NumericQuestionEntity();
    assertEquals("NUMERIC", q.getDataType());
  }

  @Test
  void should_set_related_unit_value_set() {
    QuestionCommand.AddNumericQuestion command =
        new QuestionCommand.AddNumericQuestion(
            NumericMask.NUM_DD,
            1,
            0l,
            1l,
            100l,
            "literal units",
            123l,
            new QuestionCommand.QuestionData(
                CodeSet.LOCAL,
                "localId",
                "name",
                "subgrp",
                "descrip",
                "label",
                "tooltip",
                1009l,
                null,
                null),
            new QuestionCommand.ReportingData("lbl", "RDBNM", "COLNM", "DMRT"),
            new QuestionCommand.MessagingData(false, null, null, null, false, null),
            1l,
            Instant.now());
    NumericQuestionEntity q = new NumericQuestionEntity(command);

    assertEquals(UnitType.CODED.toString(), q.getUnitTypeCd());
    assertEquals("123", q.getUnitValue());
  }

  @Test
  void should_set_related_unit_literal_value() {
    QuestionCommand.AddNumericQuestion command =
        new QuestionCommand.AddNumericQuestion(
            NumericMask.NUM_EXT,
            1,
            0l,
            1l,
            100l,
            "literal units",
            null,
            new QuestionCommand.QuestionData(
                CodeSet.LOCAL,
                "localId",
                "name",
                "subgrp",
                "descrip",
                "label",
                "tooltip",
                1009l,
                null,
                null),
            new QuestionCommand.ReportingData("lbl", "RDBNM", "COLNM", "DMRT"),
            new QuestionCommand.MessagingData(false, null, null, null, false, null),
            1l,
            Instant.now());
    NumericQuestionEntity q = new NumericQuestionEntity(command);

    assertEquals(UnitType.LITERAL.toString(), q.getUnitTypeCd());
    assertEquals("literal units", q.getUnitValue());
  }

  @Test
  void should_do_update() {
    NumericQuestionEntity entity = QuestionEntityMother.numericQuestion();
    var command = QuestionCommandMother.update();

    entity.update(command);

    assertEquals(command.mask(), entity.getMask());
    assertEquals(command.fieldLength(), entity.getFieldSize());
    assertEquals(command.defaultValue(), entity.getDefaultValue());
    assertEquals(command.minValue(), entity.getMinValue());
    assertEquals(command.maxValue(), entity.getMaxValue());
    assertEquals(command.relatedUnitsValueSet().toString(), entity.getUnitValue());
  }

  @Test
  void unitvalue_required_if_unit_type_is_provided() {
    NumericQuestionEntity entity = QuestionEntityMother.numericQuestion();
    var command =
        new QuestionCommand.Update(
            new QuestionCommand.UpdatableQuestionData(
                true, "uniqueName", "descrip", "label", "tt", 123l, null, null),
            null,
            "mask",
            "10",
            false,
            null,
            null,
            null,
            null,
            1000l,
            new QuestionCommand.ReportingData("asdf", "ASD", "FDSA", "DSSF"),
            new QuestionCommand.MessagingData(false, null, null, null, false, null),
            0,
            null);

    entity.update(command);
    assertEquals("CODED", entity.getUnitTypeCd());
    assertEquals("1000", entity.getUnitValue());
  }

  @Test
  void should_not_set_reporting_messaging_readonly() {
    var command =
        new QuestionCommand.AddNumericQuestion(
            NumericMask.NUM,
            3,
            1l,
            0l,
            100l,
            "CCs",
            null,
            new QuestionCommand.QuestionData(
                CodeSet.LOCAL,
                "TEST9900003",
                "Numeric Question Unique Name",
                "ADMN",
                "Numeric question description",
                "Numeric question label",
                "Numeric question tooltip",
                1026l, // readonly
                "Numeric question admin comments",
                new QuestionCommand.QuestionOid("oid", "oid system")),
            null,
            null,
            9999000L,
            Instant.now());
    assertThat(new NumericQuestionEntity(command)).isNotNull();
  }
}
