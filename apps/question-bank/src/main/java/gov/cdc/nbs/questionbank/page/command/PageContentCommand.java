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

    public record AddLineSeparator(
            WaTemplate page,
            Long componentId,
            Integer orderNumber,
            long userId,
            String adminComments,
            Instant requestedOn) implements PageContentCommand {
    }

    public record AddHyperLink(
            WaTemplate page,
            Long componentId,
            Integer orderNumber,
            long userId,
            String adminComments,
            String label,
            String linkUrl,
            Instant requestedOn) implements PageContentCommand {
    }
}
