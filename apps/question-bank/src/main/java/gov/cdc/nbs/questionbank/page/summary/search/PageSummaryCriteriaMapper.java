package gov.cdc.nbs.questionbank.page.summary.search;

import gov.cdc.nbs.questionbank.filter.Filter;
import gov.cdc.nbs.questionbank.filter.json.FilterJSON;
import java.util.List;
import java.util.Objects;

public class PageSummaryCriteriaMapper {

  public static PageSummaryCriteria asCriteria(final PageSummaryRequest request) {
    String search = request.search();
    List<Filter> filters =
        request.filters().stream().filter(Objects::nonNull).map(FilterJSON::asFilter).toList();

    return new PageSummaryCriteria(search, filters);
  }

  private PageSummaryCriteriaMapper() {}
}
