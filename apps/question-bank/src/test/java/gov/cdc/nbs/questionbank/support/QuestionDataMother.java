package gov.cdc.nbs.questionbank.support;

import java.util.UUID;
import gov.cdc.nbs.questionbank.entities.enums.CodeSet;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.DateQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.DropdownQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.NumericQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.TextQuestionData;

public class QuestionDataMother {
    public static TextQuestionData textQuestionData() {
        return new TextQuestionData(
                "test label",
                "test tooltip",
                13,
                "test placeholder",
                "some default value",
                CodeSet.LOCAL);
    }

    public static DateQuestionData dateQuestionData() {
        return new DateQuestionData(
                "test label",
                "test tooltip",
                true,
                CodeSet.LOCAL);
    }

    public static NumericQuestionData numericQuestionData() {
        return QuestionDataMother.numericQuestionData(UUID.randomUUID());
    }

    public static NumericQuestionData numericQuestionData(UUID unitsValueSet) {
        return new NumericQuestionData(
                "test label",
                "test tooltip",
                -3,
                543,
                -2,
                unitsValueSet,
                CodeSet.LOCAL);
    }


    public static DropdownQuestionData dropdownQuestionData() {
        return QuestionDataMother.dropdownQuestionData(UUID.randomUUID(), UUID.randomUUID());
    }

    public static DropdownQuestionData dropdownQuestionData(UUID valueSet, UUID defaultValue) {
        return new DropdownQuestionData(
                "test label",
                "test tooltip",
                valueSet,
                defaultValue,
                true,
                CodeSet.LOCAL);
    }
}
