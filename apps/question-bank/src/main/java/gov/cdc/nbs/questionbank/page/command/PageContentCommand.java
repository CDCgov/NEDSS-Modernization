package gov.cdc.nbs.questionbank.page.command;

import java.time.Instant;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;

public sealed interface PageContentCommand {
    long userId();

    Instant requestedOn();

    public record AddQuestion(
            WaTemplate page,
            WaQuestion question,
            Integer orderNumber,
            long userId,
            Instant requestedOn) implements PageContentCommand {
    }

    public record AddTab(
            WaTemplate page,
            String label,
            boolean visible,
            String identifier,
            int orderNumber,
            long userId,
            Instant requestedOn) implements PageContentCommand {
    }

    public record UpdateTab(
            String label,
            boolean visible,
            long userId,
            Instant requestedOn) implements PageContentCommand {
    }
}
