package gov.cdc.nbs.questionbank.question;

import java.util.UUID;
import gov.cdc.nbs.questionbank.entities.enums.CodeSet;

public sealed interface QuestionRequest {

    record CreateTextQuestion(
            String label,
            String tooltip,
            Integer maxLength,
            String placeholder,
            String defaultValue,
            CodeSet codeSet) implements QuestionRequest {
    }

    record CreateDateQuestion(
            String label,
            String tooltip,
            boolean allowFutureDates,
            CodeSet codeSet) implements QuestionRequest {
    }

    record CreateDropdownQuestion(
            String label,
            String tooltip,
            UUID valueSet,
            UUID defaultValue,
            boolean isMultiSelect,
            CodeSet codeSet) implements QuestionRequest {
    }

    record CreateNumericQuestion(
            String label,
            String tooltip,
            Integer minValue,
            Integer maxValue,
            Integer defaultValue,
            UUID unitValueSet,
            CodeSet codeSet) implements QuestionRequest {
    }

    record UpdateTextQuestion(
            String requestId,
            long questionId,
            long userId,
            String label,
            String tooltip,
            Integer maxLength,
            String placeholder) implements QuestionRequest {
    }

}
