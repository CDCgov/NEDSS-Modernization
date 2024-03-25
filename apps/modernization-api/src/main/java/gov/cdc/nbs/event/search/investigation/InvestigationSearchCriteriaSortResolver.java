package gov.cdc.nbs.event.search.investigation;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.util.ObjectBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

import static gov.cdc.nbs.search.SearchSorting.*;

@Component
class InvestigationSearchCriteriaSortResolver {

  List<SortOptions> resolve(final Pageable pageable) {
    return pageable.getSort()
        .stream()
        .map(this::asOption)
        .toList();
  }

  private SortOptions asOption(final Sort.Order sorting) {
    SortOrder order = asSortOrder(sorting.getDirection());

    return switch (sorting.getProperty()) {
      case "lastNm" -> asFilteredSortOption(
          "person_participations",
          "person_participations.last_name.keyword",
          order,
          onlyPatients()
      );
      case "birthTime" -> asFilteredSortOption(
          "person_participations",
          "person_participations.birth_time",
          order,
          onlyPatients()
      );
      default -> asSortOption("_score", order);
    };
  }

  private static Function<Query.Builder, ObjectBuilder<Query>> onlyPatients() {
    return filter -> filter.term(
        term -> term.field("person_participations.person_cd")
            .value("PAT")
    );
  }

}
