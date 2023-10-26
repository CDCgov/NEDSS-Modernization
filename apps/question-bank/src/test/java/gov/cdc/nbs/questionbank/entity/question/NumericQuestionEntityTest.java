package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.exception.NullObjectException;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.request.CreateNumericQuestionRequest.NumericMask;
import gov.cdc.nbs.questionbank.support.QuestionCommandMother;

class NumericQuestionEntityTest {

    @Test
    void should_have_date_type() {
        NumericQuestionEntity q = new NumericQuestionEntity();
        assertEquals("NUMERIC", q.getDataType());
    }

    @Test
    void should_set_related_unit_value_set() {
        QuestionCommand.AddNumericQuestion command = new QuestionCommand.AddNumericQuestion(
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
                new QuestionCommand.ReportingData(
                        "lbl",
                        "RDBNM",
                        "COLNM",
                        "DMRT"),
                new QuestionCommand.MessagingData(
                        false,
                        null,
                        null,
                        null,
                        false,
                        null),
                1l,
                Instant.now());
        NumericQuestionEntity q = new NumericQuestionEntity(command);

        assertEquals(UnitType.CODED.toString(), q.getUnitTypeCd());
        assertEquals("123", q.getUnitValue());
    }

    @Test
    void should_set_related_unit_literal_value() {
        QuestionCommand.AddNumericQuestion command = new QuestionCommand.AddNumericQuestion(
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
                new QuestionCommand.ReportingData(
                        "lbl",
                        "RDBNM",
                        "COLNM",
                        "DMRT"),
                new QuestionCommand.MessagingData(
                        false,
                        null,
                        null,
                        null,
                        false,
                        null),
                1l,
                Instant.now());
        NumericQuestionEntity q = new NumericQuestionEntity(command);

        assertEquals(UnitType.LITERAL.toString(), q.getUnitTypeCd());
        assertEquals("literal units", q.getUnitValue());
    }

    @Test
    void should_do_update() {
        var command = QuestionCommandMother.update();
        NumericQuestionEntity q = new NumericQuestionEntity();
        q.setQuestionType("LOCAL");
        q.setVersionCtrlNbr(1);

        q.update(command);

        assertEquals(command.mask(), q.getMask());
        assertEquals(command.fieldLength(), q.getFieldSize());
        assertEquals(command.defaultValue(), q.getDefaultValue());
        assertEquals(command.minValue(), q.getMinValue());
        assertEquals(command.maxValue(), q.getMaxValue());
        assertEquals(command.unitType().toString(), q.getUnitTypeCd());
        assertEquals(command.unitValue(), q.getUnitValue());
    }

    @Test
    void unitvalue_required_if_unit_type_is_provided() {
        var command = new QuestionCommand.Update(
                new QuestionCommand.UpdatableQuestionData(
                        true,
                        "uniqueName",
                        "descrip",
                        "label",
                        "tt",
                        123l,
                        null,
                        null),
                null,
                "mask",
                "10",
                false,
                null,
                null,
                null,
                UnitType.LITERAL,
                null,
                new QuestionCommand.ReportingData("asdf", "ASD", "FDSA", "DSSF"),
                new QuestionCommand.MessagingData(false, null, null, null, false, null),
                0, null);
        NumericQuestionEntity q = new NumericQuestionEntity();
        q.setQuestionType("LOCAL");
        q.setVersionCtrlNbr(1);

        assertThrows("If specifying UnitType, UnitValue must not be null",
                NullObjectException.class,
                () -> q.update(command));
    }

}
