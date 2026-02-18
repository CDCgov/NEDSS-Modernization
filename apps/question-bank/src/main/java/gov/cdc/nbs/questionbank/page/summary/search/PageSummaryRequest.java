package gov.cdc.nbs.questionbank.page.summary.search;

import gov.cdc.nbs.questionbank.filter.json.FilterJSON;
import java.util.List;
import java.util.stream.Stream;

public record PageSummaryRequest(String search, List<FilterJSON> filters) {

  public PageSummaryRequest(String search, List<FilterJSON> filters) {

    this.search = search;
    this.filters = filters == null ? List.of() : List.copyOf(filters);
  }

  PageSummaryRequest(final String search) {
    this(search, List.of());
  }

  PageSummaryRequest withSearch(final String search) {
    return new PageSummaryRequest(search, filters());
  }

  PageSummaryRequest withFilter(final FilterJSON filter) {

    List<FilterJSON> appended = Stream.concat(filters.stream(), Stream.of(filter)).toList();

    return new PageSummaryRequest(search(), appended);
  }
}
