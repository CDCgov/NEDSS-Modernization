package gov.cdc.nbs.questionbank.page.information;

import gov.cdc.nbs.accumulation.CollectionMerge;
import gov.cdc.nbs.questionbank.page.SelectableCondition;

import java.util.Collection;

class PageInformationMerger {

  PageInformation merge(final PageInformation left, final PageInformation right) {
    Collection<SelectableCondition> associations = CollectionMerge.merged(left.conditions(), right.conditions());

    return new PageInformation(
        left.page(),
        left.eventType(),
        left.messageMappingGuide(),
        left.name(),
        left.datamart(),
        left.description(),
        associations
    );
  }

}
