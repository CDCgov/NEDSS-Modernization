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
      case ADDRESS -> asSortOption(ADDRESS, "address.streetAddr1.keyword", order);
      case "birthTime" -> asSortOption("birth_time", order);
      case "city" -> asSortOption(ADDRESS, "address.city.keyword", order);
      case "county" -> asSortOption(ADDRESS, "address.cntyCd.keyword", order);
      case "country" -> asSortOption(ADDRESS, "address.cntryCd.keyword", order);
      case "email" -> asSortOption("email", "email.emailAddress.keyword", order);
      case "firstNm" -> asSortOption("name", "name.firstNm.keyword", order);
      case "id" -> asSortOption("id", order);
      case "local_id" -> asSortOption("local_id", order);
      case "identification" -> asSortOption("entity_id", "entity_id.rootExtensionTxt.keyword", order);
      case "lastNm" -> asSortOption("name", "name.lastNm.keyword", order);
      case "phoneNumber" -> asSortOption("phone", "phone.telephoneNbr.keyword", order);
      case "relevance" -> asSortOption("_score", order);
      case "sex" -> asSortOption("cur_sex_cd", order);
      case "state" -> asSortOption(ADDRESS, "address.state.keyword", order);
      case "zip" -> asSortOption(ADDRESS, "address.zip.keyword", order);
      default -> asSortOption(sorting.getProperty(), order);
    };
  }
}
