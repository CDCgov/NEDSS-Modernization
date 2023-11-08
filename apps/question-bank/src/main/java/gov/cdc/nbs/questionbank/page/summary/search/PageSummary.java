package gov.cdc.nbs.questionbank.page.summary.search;

import gov.cdc.nbs.questionbank.question.model.ConditionSummary;

import java.time.Instant;
import java.util.Collection;

public record PageSummary(
    long id,
    EventType eventType,
    String name,
    String status,
    Collection<ConditionSummary> conditions,
    Instant lastUpdate,
    String lastUpdateBy
) {

  public record EventType(String value, String name) {
  }


  public record MessageMappingGuide(String value, String name) {
  }
}


