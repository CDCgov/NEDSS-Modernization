package gov.cdc.nbs.questionbank.page.model;

import gov.cdc.nbs.questionbank.question.model.ConditionSummary;
import java.util.List;

public record PageInfo(
        EventType eventType,
        String name,
        String description,
        MessageMappingGuide messageMappingGuide,
        List<ConditionSummary> conditions,
        String dataMartName

) {

    public record EventType(String value, String name) {
    }

    public record MessageMappingGuide(String value, String name) {
    }
}


