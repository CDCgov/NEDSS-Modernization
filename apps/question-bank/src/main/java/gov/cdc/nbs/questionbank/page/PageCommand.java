package gov.cdc.nbs.questionbank.page;

import java.time.Instant;
import java.util.Set;

public sealed interface PageCommand {
    long requester();

    Instant requestedOn();

    record UpdateDetails(
            String name,
            String messageMappingGuide,
            String dataMartName,
            String description,
            Set<String> conditionIds,
            long requester,
            Instant requestedOn) implements PageCommand {
    }
}
