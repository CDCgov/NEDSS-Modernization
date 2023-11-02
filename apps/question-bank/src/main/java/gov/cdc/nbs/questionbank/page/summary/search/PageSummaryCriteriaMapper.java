package gov.cdc.nbs.questionbank.page.summary.search;

import gov.cdc.nbs.questionbank.filter.Filter;

import java.util.List;
import java.util.Objects;

class PageSummaryCriteriaMapper {

  static PageSummaryCriteria asCriteria(final PageSummaryRequest request) {
    String search = request.search();
    List<Filter> filters = request.filters().stream().filter(Objects::nonNull).toList();
    return new PageSummaryCriteria(search, filters);
  }

  private PageSummaryCriteriaMapper() {
  }

}
