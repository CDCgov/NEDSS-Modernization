package gov.cdc.nbs.event.search.labreport;

import static gov.cdc.nbs.search.SearchSorting.*;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.util.ObjectBuilder;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
class LabReportSearchCriteriaSortResolver {
  private static final String PERSON_PARTICIPATIONS = "person_participations";

  List<SortOptions> resolve(final Pageable pageable) {
    return pageable.getSort().stream().map(this::asOption).toList();
  }

  private SortOptions asOption(final Sort.Order sorting) {
    SortOrder order = asSortOrder(sorting.getDirection());

    return switch (sorting.getProperty()) {
      case "lastNm" ->
          asFilteredSortOption(
              PERSON_PARTICIPATIONS,
              "person_participations.last_name.keyword",
              order,
              onlyPatients());
      case "firstNm" ->
          asFilteredSortOption(
              PERSON_PARTICIPATIONS,
              "person_participations.first_name.keyword",
              order,
              onlyPatients());
      case "birthTime" ->
          asFilteredSortOption(
              PERSON_PARTICIPATIONS, "person_participations.birth_time", order, onlyPatients());
      case "sex" ->
          asFilteredSortOption(
              PERSON_PARTICIPATIONS, "person_participations.curr_sex_cd", order, onlyPatients());
      case "local_id" ->
          asFilteredSortOption(
              PERSON_PARTICIPATIONS, "person_participations.local_id", order, onlyPatients());
      default -> asSortOption("_score", order);
    };
  }

  private static Function<Query.Builder, ObjectBuilder<Query>> onlyPatients() {
    return filter ->
        filter.term(term -> term.field("person_participations.person_cd").value("PAT"));
  }
}
