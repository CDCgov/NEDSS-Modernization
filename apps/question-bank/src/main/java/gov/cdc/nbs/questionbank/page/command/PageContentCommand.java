package gov.cdc.nbs.questionbank.page.command;

import java.time.Instant;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;

public sealed interface PageContentCommand {
    long userId();

    Instant requestedOn();

    public record AddQuestion(
            Long page,
            WaQuestion question,
            Integer orderNumber,
            long userId,
            Instant requestedOn) implements PageContentCommand {
    }

    public record AddTab(
            Long page,
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

    public record AddSection(
            String label,
            boolean visible,
            String identifier,
            long tab,
            long userId,
            Instant requestedOn) implements PageContentCommand {
    }

    public record UpdateSection(
            String label,
            boolean visible,
            long userId,
            Instant requestedOn) implements PageContentCommand {
    }

    public record DeleteSection(long setionId, long userId, Instant requestedOn) implements PageContentCommand {
    }

    public record AddSubsection(
            String label,
            boolean visible,
            String identifier,
            long section,
            long userId,
            Instant requestedOn) implements PageContentCommand {
    }

    public record UpdateSubsection(
            String label,
            boolean visible,
            long userId,
            Instant requestedOn) implements PageContentCommand {
    }

    public record DeleteSubsection(long subsectionId, long userId, Instant requestedOn) implements PageContentCommand {

    }
}
