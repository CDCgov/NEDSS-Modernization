package gov.cdc.nbs.questionbank.page.model;

import java.time.Instant;
import java.util.List;
import gov.cdc.nbs.questionbank.question.model.Condition;

public record PageSummary(
        long id,
        EventType eventType,
        String name,
        String description,
        String status,
        MessageMappingGuide messageMappingGuide,
        List<Condition> conditions,
        Instant lastUpdate,
        String lastUpdateBy) {

    public record EventType(String value, String name) {
    }

    public record MessageMappingGuide(String value, String name) {
    }
}


