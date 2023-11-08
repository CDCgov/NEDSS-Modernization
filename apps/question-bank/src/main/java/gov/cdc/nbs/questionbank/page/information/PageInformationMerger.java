package gov.cdc.nbs.questionbank.page.information;

import gov.cdc.nbs.accumulation.CollectionMerge;
import gov.cdc.nbs.questionbank.page.Condition;

import java.util.Collection;

class PageInformationMerger {

  PageInformation merge(final PageInformation left, final PageInformation right) {
    Collection<Condition> associations = CollectionMerge.merged(left.associated(), right.associated());

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
