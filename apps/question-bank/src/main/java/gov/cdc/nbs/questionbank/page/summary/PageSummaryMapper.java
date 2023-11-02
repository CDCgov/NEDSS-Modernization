package gov.cdc.nbs.questionbank.page.summary;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.accumulation.CollectionMerge;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryTables;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageSummaryMapper {

  private final PageSummaryTables tables;

  public PageSummaryMapper() {
    this(new PageSummaryTables());
  }

  public PageSummaryMapper(final PageSummaryTables tables) {
    this.tables = tables;
  }

  public List<PageSummary> map(final List<Tuple> tuples) {
    // preserves ordering
    return tuples.stream()
        .map(this::map)
        .collect(Collectors.toMap(PageSummary::id, Function.identity(), this::merge, LinkedHashMap::new))
        .values()
        .stream()
        .toList();
  }

  /**
   * The query contains a join on the page_cond_mapping table that causes each condition associated with a page to
   * generate an extra row. The merge function simply combines the condition lists and returns the 'left' entry
   */
  private PageSummary merge(final PageSummary left, final PageSummary right) {
    Collection<ConditionSummary> conditions = CollectionMerge.merged(left.conditions(), right.conditions());

    return new PageSummary(
        left.id(),
        left.eventType(),
        left.name(),
        left.description(),
        left.status(),
        left.messageMappingGuide(),
        conditions,
        left.lastUpdate(),
        left.lastUpdateBy()
    );
  }

  public PageSummary map(final Tuple tuple) {
    Long identifier = tuple.get(this.tables.page().id);
    List<ConditionSummary> conditions = List.of(asCondition(tuple));
    PageSummary.EventType eventType = getEventType(tuple);

    String lastUpdateBy = tuple.get(this.tables.lastUpdatedBy());
    Instant lastUpdate = tuple.get(this.tables.page().lastChgTime);
    String name = tuple.get(this.tables.page().templateNm);
    String description = tuple.get(this.tables.page().descTxt);
    return new PageSummary(
        identifier,
        eventType,
        name,
        description,
        getStatus(tuple),
        new PageSummary.MessageMappingGuide(
            tuple.get(this.tables.mappingGuide().id.code),
            tuple.get(this.tables.mappingGuide().codeShortDescTxt)
        ),
        conditions,
        lastUpdate,
        lastUpdateBy
    );
  }

  private ConditionSummary asCondition(final Tuple tuple) {
    return new ConditionSummary(
        tuple.get(this.tables.condition().id),
        tuple.get(this.tables.condition().conditionShortNm)
    );
  }

  /**
   * Sets the page status as defined in legacy code PageManagementActionUtil line #1965-1970
   * <p>
   * If a page has status "Draft" and no publish version exists, then set to "Initial Draft", if a published version
   * does exist, then set to "Published with Draft"
   */
  private String getStatus(final Tuple tuple) {
    String templateType = tuple.get(this.tables.page().templateType);
    Integer publishVersion = tuple.get(this.tables.page().publishVersionNbr);
    if ("Draft".equalsIgnoreCase(templateType)) {
      if (publishVersion == null) {
        return "Initial Draft";
      } else {
        return "Published with Draft";
      }
    }
    return templateType;
  }

  private PageSummary.EventType getEventType(final Tuple tuple) {
    String type = tuple.get(this.tables.eventType().id.code);
    String display = tuple.get(this.tables.eventType().codeShortDescTxt);
    return new PageSummary.EventType(type, display);
  }
}
