package gov.cdc.nbs.event.search.investigation;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import gov.cdc.nbs.event.search.basic.BasicInformationSearchCriteriaSortResolver;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static gov.cdc.nbs.search.SearchSorting.asSortOption;
import static gov.cdc.nbs.search.SearchSorting.asSortOrder;

@Component
class InvestigationSearchCriteriaSortResolver {

  List<SortOptions> resolve(final Pageable pageable) {
    return pageable.getSort()
        .stream()
        .flatMap(this::asOptions)
        .toList();
  }

  private Stream<SortOptions> asOptions(final Sort.Order sorting) {
    SortOrder order = asSortOrder(sorting.getDirection());

    return switch (sorting.getProperty().toLowerCase()) {
      case "startdate" -> Stream.of(asSortOption("activity_from_time", order));
      case "condition" -> Stream.of(asSortOption("cd_desc_txt", order));
      case "jurisdiction" -> Stream.of(asSortOption("jurisdiction_code_desc_txt", order));
      case "investigator" -> Stream.of(asSortOption("investigator_last_nm", order));
      case "investigationid","id" -> Stream.of(asSortOption("local_id", order));
      case "status" -> Stream.of(asSortOption("investigation_status_cd", order));
      case "notification" -> Stream.of(asSortOption("notification_record_status_cd", order));
      default -> BasicInformationSearchCriteriaSortResolver.resolve(sorting);
    };
  }

}
