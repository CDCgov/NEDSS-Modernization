package gov.cdc.nbs.questionbank.page.model;

import java.time.Instant;
import java.util.List;
import gov.cdc.nbs.questionbank.question.model.Condition;

public record PageSummary(
        long id,
        String eventType,
        String name,
        String state,
        String messageMappingGuide,
        List<Condition> conditions,
        Instant lastUpdate,
        String lastUpdateB) {
}
