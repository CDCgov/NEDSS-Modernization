package gov.cdc.nbs.questionbank.support;

import java.util.UUID;
import gov.cdc.nbs.questionbank.entities.enums.CodeSet;
import gov.cdc.nbs.questionbank.question.QuestionRequest.CreateDateQuestion;
import gov.cdc.nbs.questionbank.question.QuestionRequest.CreateDropdownQuestion;
import gov.cdc.nbs.questionbank.question.QuestionRequest.CreateNumericQuestion;
import gov.cdc.nbs.questionbank.question.QuestionRequest.CreateTextQuestion;

public class QuestionDataMother {
    public static CreateTextQuestion textQuestionData() {
        return new CreateTextQuestion(
                "test label",
                "test tooltip",
                13,
                "test placeholder",
                "some default value",
                CodeSet.LOCAL);
    }

    public static CreateDateQuestion dateQuestionData() {
        return new CreateDateQuestion(
                "test label",
                "test tooltip",
                true,
                CodeSet.LOCAL);
    }

    public static CreateNumericQuestion numericQuestionData() {
        return QuestionDataMother.numericQuestionData(UUID.randomUUID());
    }

    public static CreateNumericQuestion numericQuestionData(UUID unitsValueSet) {
        return new CreateNumericQuestion(
                "test label",
                "test tooltip",
                -3,
                543,
                -2,
                unitsValueSet,
                CodeSet.LOCAL);
    }


    public static CreateDropdownQuestion dropdownQuestionData() {
        return QuestionDataMother.dropdownQuestionData(UUID.randomUUID(), UUID.randomUUID());
    }

    public static CreateDropdownQuestion dropdownQuestionData(UUID valueSet, UUID defaultValue) {
        return new CreateDropdownQuestion(
                "test label",
                "test tooltip",
                valueSet,
                defaultValue,
                true,
                CodeSet.LOCAL);
    }
}
