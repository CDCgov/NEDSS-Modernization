package gov.cdc.nbs.questionbank.question.command;

import java.time.Instant;

public interface QuestionCommand {
    long userId();

    Instant requestedOn();

}
