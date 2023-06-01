package gov.cdc.nbs.questionbank.question.command;

import java.time.Instant;
import gov.cdc.nbs.questionbank.entities.ValueEntity;
import gov.cdc.nbs.questionbank.entities.ValueSet;
import gov.cdc.nbs.questionbank.entities.enums.CodeSet;

public sealed interface QuestionCommand {
    Long question();

    long userId();

    Instant requestedOn();

    record AddTextQuestion(
            Long question,
            long userId,
            Instant requestedOn,
            String label,
            String tooltip,
            Integer maxLength,
            String placeholder,
            String defaultValue,
            CodeSet codeSet) implements QuestionCommand {
    }

    record AddDateQuestion(
            Long question,
            long userId,
            Instant requestedOn,
            String label,
            String tooltip,
            boolean allowFutureDates,
            CodeSet codeSet) implements QuestionCommand {

    }

    record AddDropDownQuestion(
            Long question,
            long userId,
            Instant requestedOn,
            String label,
            String tooltip,
            ValueSet valueSet,
            ValueEntity defaultValue,
            boolean isMultiSelect,
            CodeSet codeSet) implements QuestionCommand {

    }

    record AddNumericQuestion(
            Long question,
            long userId,
            Instant requestedOn,
            String label,
            String tooltip,
            Integer minValue,
            Integer maxValue,
            Integer defaultValue,
            ValueSet unitsOptions,
            CodeSet codeSet) implements QuestionCommand {
    }


    record UpdateTextQuestion(
            Long question,
            long userId,
            Instant requestedOn,
            String label,
            String tooltip,
            Integer maxLength,
            String placeholder,
            String defaultValue) implements QuestionCommand {
    }

}
