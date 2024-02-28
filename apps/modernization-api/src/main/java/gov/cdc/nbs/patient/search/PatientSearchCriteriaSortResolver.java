package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

import static gov.cdc.nbs.search.SearchSorting.asSortOption;
import static gov.cdc.nbs.search.SearchSorting.asSortOrder;

@Component
class PatientSearchCriteriaSortResolver {

  List<SortOptions> resolve(final Pageable pageable) {
    return pageable.getSort()
        .stream()
        .map(this::asOption)
        .toList();
  }

  private SortOptions asOption(final Sort.Order sorting) {
    SortOrder order = asSortOrder(sorting.getDirection());

    return switch (sorting.getProperty()) {
      case "relevance" -> asSortOption("_score", order);
      case "lastNm" -> asSortOption("name", "name.lastNm.keyword", order);
      case "birthTime" -> asSortOption("birth_time", order);
      default -> asSortOption(sorting.getProperty(), order);
    };
  }

}
