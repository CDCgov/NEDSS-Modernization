package gov.cdc.nbs.questionbank.page.summary.search;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.questionbank.page.PageStatusResolver;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class PageSummaryMapper {

  private final PageSummaryTables tables;

  PageSummaryMapper(final PageSummaryTables tables) {
    this.tables = tables;
  }

  PageSummary map(final Tuple tuple) {
    long identifier =
        Objects.requireNonNull(tuple.get(this.tables.page().id), "A Page Summary ID is required.");
    List<ConditionSummary> conditions =
        Arrays.asList(asCondition(tuple)).stream().filter(Objects::nonNull).toList();
    PageSummary.EventType eventType = getEventType(tuple);

    String lastUpdateBy = tuple.get(this.tables.lastUpdatedBy());
    Instant lastUpdate = tuple.get(this.tables.page().lastChgTime);
    LocalDate lastUpdateDate =
        (lastUpdate != null) ? LocalDate.ofInstant(lastUpdate, ZoneId.systemDefault()) : null;
    String name = tuple.get(this.tables.page().templateNm);
    return new PageSummary(
        identifier, eventType, name, getStatus(tuple), conditions, lastUpdateDate, lastUpdateBy);
  }

  private ConditionSummary asCondition(final Tuple tuple) {
    if (tuple.get(this.tables.condition().id) == null) {
      return null;
    }
    return new ConditionSummary(
        tuple.get(this.tables.condition().id), tuple.get(this.tables.condition().conditionShortNm));
  }

  /**
   * Sets the page status as defined in legacy code PageManagementActionUtil line #1965-1970
   *
   * <p>If a page has status "Draft" and no publish version exists, then set to "Initial Draft", if
   * a published version does exist, then set to "Published with Draft"
   */
  private String getStatus(final Tuple tuple) {
    String templateType = tuple.get(this.tables.page().templateType);
    Integer publishVersion = tuple.get(this.tables.page().publishVersionNbr);
    return PageStatusResolver.resolve(templateType, publishVersion);
  }

  private PageSummary.EventType getEventType(final Tuple tuple) {
    String type = tuple.get(this.tables.eventType().id.code);
    String display = tuple.get(this.tables.eventType().codeShortDescTxt);
    return new PageSummary.EventType(type, display);
  }
}
