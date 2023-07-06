package gov.cdc.nbs.questionbank.page.model;

import java.time.Instant;
import java.util.List;
import gov.cdc.nbs.questionbank.question.model.Condition;

public record PageSummary(
        long id,
        EventType eventType,
        String name,
        String status,
        MessageMappingGuide messageMappingGuide,
        List<Condition> conditions,
        Instant lastUpdate,
        String lastUpdateBy) {

    public record EventType(String type, String display) {
        public EventType(String type) {
            this(type, switch (type) {
                case "INV" -> "Investigation";
                case "CON" -> "Contact";
                case "VAC" -> "Vaccination";
                case "IXS" -> "Interview";
                case "SUS" -> "Lab Susceptibility";
                case "LAB" -> "Lab Report";
                case "ISO" -> "Lab Isolate Tracking";
                default -> type;
            });
        }
    }

    public record MessageMappingGuide(String id, String display) {
    }
}


