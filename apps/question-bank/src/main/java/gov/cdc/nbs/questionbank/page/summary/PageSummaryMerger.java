package gov.cdc.nbs.questionbank.page.summary;

import gov.cdc.nbs.accumulation.CollectionMerge;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;

import java.util.Collection;

public class PageSummaryMerger {

  public PageSummary merge(final PageSummary left, final PageSummary right) {
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

}
