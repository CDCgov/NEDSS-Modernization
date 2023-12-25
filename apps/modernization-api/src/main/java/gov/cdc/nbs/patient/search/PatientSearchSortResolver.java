package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientSearchSortResolver {

  List<SortBuilder<?>> resolve(final Pageable pageable) {
    return pageable.getSort()
        .stream()
        .<SortBuilder<?>>map(this::asSortBuilder)
        .toList();
  }

  private SortBuilder<?> asSortBuilder(final Sort.Order sorting) {
    SortOrder order = sorting.getDirection() == Sort.Direction.DESC ? SortOrder.DESC : SortOrder.ASC;
    return switch (sorting.getProperty()) {
      case "relevance" -> SortBuilders.scoreSort();
      case "lastNm" -> SortBuilders.fieldSort(ElasticsearchPerson.LAST_NM)
          .order(order);
      case "birthTime" -> SortBuilders.fieldSort(ElasticsearchPerson.BIRTH_TIME)
          .order(order);

      default -> throw new IllegalArgumentException("Invalid sort operator specified: " + sorting.getProperty());

    };
  }
}
