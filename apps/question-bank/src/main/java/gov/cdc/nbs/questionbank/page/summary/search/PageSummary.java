package gov.cdc.nbs.questionbank.page.summary.search;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;

public record PageSummary(
        long id,
        EventType eventType,
        String name,
        String description,
        String status,
        MessageMappingGuide messageMappingGuide,
        Collection<ConditionSummary> conditions,
        Instant lastUpdate,
        String lastUpdateBy) {

    public record EventType(String value, String name) {
    }

    public record MessageMappingGuide(String value, String name) {
    }
}


