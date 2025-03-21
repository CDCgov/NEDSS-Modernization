package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static gov.cdc.nbs.search.SearchSorting.asSortOption;
import static gov.cdc.nbs.search.SearchSorting.asHandlingNullSortOption;
import static gov.cdc.nbs.search.SearchSorting.asSortOrder;

@Component
class PatientSearchCriteriaSortResolver {
  private static final String ADDRESS = "address";
  private static final String NAME = "name";

  List<SortOptions> resolve(final Pageable pageable) {
    return pageable.getSort()
        .stream()
        .flatMap(this::asOption)
        .toList();
  }

  private Stream<SortOptions> asOption(final Sort.Order sorting) {
    SortOrder order = asSortOrder(sorting.getDirection());

    return switch (sorting.getProperty().toLowerCase()) {
      case "patientid" -> Stream.of(asSortOption("local_id", order));
      case "patientname" -> Stream.of(
          asSortOption(NAME, "name.lastNm.keyword", order),
          asSortOption(NAME, "name.firstNm.keyword", order),
          asSortOption(NAME, "name.middleNm.keyword", order),
          asSortOption(NAME, "name.nmSuffix.keyword", order),
          asHandlingNullSortOption("birth_time", order),
          asHandlingNullSortOption("local_id", order));
      case "lastnm" -> Stream.of(asSortOption(NAME, "name.lastNm.keyword", order));
      case "firstnm" -> Stream.of(asSortOption(NAME, "name.firstNm.keyword", order));
      case ADDRESS -> Stream.of(asHandlingNullSortOption("sort.address", order));
      case "birthtime", "birthday" -> Stream.of(asSortOption("birth_time", order));
      case "city" -> Stream.of(asSortOption(ADDRESS, "address.city.keyword", order));
      case "county" -> Stream.of(asSortOption(ADDRESS, "address.cntyText.keyword", order));
      case "country" -> Stream.of(asSortOption(ADDRESS, "address.cntryText.keyword", order));
      case "email" -> Stream.of(asHandlingNullSortOption("sort.email", order));
      case "id" -> Stream.of(asSortOption("patient", order));
      case "identification" -> Stream.of(asHandlingNullSortOption("sort.identification", order));
      case NAME -> Stream.of(asHandlingNullSortOption("sort.name", order));
      case "phonenumber" -> Stream.of(asHandlingNullSortOption("sort.phone", order));
      case "state" -> Stream.of(asSortOption(ADDRESS, "address.stateText.keyword", order));
      case "sex" -> Stream.of(asSortOption("curr_sex_cd", order));
      case "zip" -> Stream.of(asSortOption(ADDRESS, "address.zip.keyword", order));
      default -> Stream.of(asSortOption("_score", order));
    };
  }
}
