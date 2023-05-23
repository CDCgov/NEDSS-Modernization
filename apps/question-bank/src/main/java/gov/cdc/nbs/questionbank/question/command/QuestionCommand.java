package gov.cdc.nbs.questionbank.question.command;

import java.time.Instant;

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
            String placeholder) implements QuestionCommand {
    }

}
