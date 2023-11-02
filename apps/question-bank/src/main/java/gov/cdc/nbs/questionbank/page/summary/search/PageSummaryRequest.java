package gov.cdc.nbs.questionbank.page.summary.search;

import gov.cdc.nbs.questionbank.filter.Filter;

import java.util.List;
import java.util.stream.Stream;

record PageSummaryRequest(
    String search,
    List<Filter> filters

) {

  PageSummaryRequest(
      String search,
      List<Filter> filters

  ) {
    this.search = search;
    this.filters = filters == null
        ? List.of()
        : List.copyOf(filters);
  }

  PageSummaryRequest(final String search) {
    this(search, List.of());
  }

  PageSummaryRequest withSearch(final String search) {
    return new PageSummaryRequest(search, filters());
  }


  PageSummaryRequest withFilter(final Filter filter) {

    List<Filter> appended = Stream.concat(filters.stream(), Stream.of(filter)).toList();

    return new PageSummaryRequest(
        search(),
        appended
    );
  }
}
