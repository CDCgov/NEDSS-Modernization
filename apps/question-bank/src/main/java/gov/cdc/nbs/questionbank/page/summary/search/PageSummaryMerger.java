package gov.cdc.nbs.questionbank.page.summary.search;

import gov.cdc.nbs.accumulation.CollectionMerge;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;
import java.util.Collection;

class PageSummaryMerger {

  PageSummary merge(final PageSummary left, final PageSummary right) {
    Collection<ConditionSummary> conditions =
        CollectionMerge.merged(left.conditions(), right.conditions());

    return new PageSummary(
        left.id(),
        left.eventType(),
        left.name(),
        left.status(),
        conditions,
        left.lastUpdate(),
        left.lastUpdateBy());
  }
}
