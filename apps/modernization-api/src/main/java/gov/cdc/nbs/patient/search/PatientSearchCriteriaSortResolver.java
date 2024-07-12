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
  private static final String ADDRESS = "address";

  List<SortOptions> resolve(final Pageable pageable) {
    return pageable.getSort()
        .stream()
        .map(this::asOption)
        .toList();
  }

  private SortOptions asOption(final Sort.Order sorting) {
    SortOrder order = asSortOrder(sorting.getDirection());

    return switch (sorting.getProperty()) {
      case "address" -> asSortOption(ADDRESS, "address.streetAddr1", order);
      case "birthTime" -> asSortOption("birth_time", order);
      case "city" -> asSortOption(ADDRESS, "address.city", order);
      case "county" -> asSortOption(ADDRESS, "address.cntyCd", order);
      case "country" -> asSortOption(ADDRESS, "address.cntryCd", order);
      case "email" -> asSortOption("email", "email.emailAddress", order);
      case "firstNm" -> asSortOption("name", "name.firstNm.keyword", order);
      case "id" -> asSortOption("id", order);
      case "local_id" -> asSortOption("local_id", order);
      case "identification" -> asSortOption("entity_id", "entity_id.rootExtensionTxt", order);
      case "lastNm" -> asSortOption("name", "name.lastNm.keyword", order);
      case "phoneNumber" -> asSortOption("phone", "phone.telephoneNbr", order);
      case "relevance" -> asSortOption("_score", order);
      case "sex" -> asSortOption("cur_sex_cd", order);
      case "state" -> asSortOption(ADDRESS, "address.state", order);
      case "zip" -> asSortOption(ADDRESS, "address.zip", order);
      default -> asSortOption(sorting.getProperty(), order);
    };
  }
}
