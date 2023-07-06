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

    public record EventType(String type, String display) {
    }

    public record MessageMappingGuide(String id, String display) {
    }
}


