package gov.cdc.nbs.questionbank.page.information;

import gov.cdc.nbs.questionbank.page.SelectableCondition;

class SelectableConditionMerger {

  SelectableCondition merge(final SelectableCondition current, final SelectableCondition next) {

    return new SelectableCondition(
        current.value(), current.name(), current.published() || next.published());
  }
}
