package gov.cdc.nbs.questionbank.page.summary.search;

import gov.cdc.nbs.questionbank.question.model.ConditionSummary;
import java.time.LocalDate;
import java.util.Collection;

public record PageSummary(
    long id,
    EventType eventType,
    String name,
    String status,
    Collection<ConditionSummary> conditions,
    LocalDate lastUpdate,
    String lastUpdateBy) {

  public record EventType(String value, String name) {}
}
