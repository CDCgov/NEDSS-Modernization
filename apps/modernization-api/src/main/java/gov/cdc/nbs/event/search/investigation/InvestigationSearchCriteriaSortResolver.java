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
  private static final String PERSON_PARTICIPATIONS = "person_participations";

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
          PERSON_PARTICIPATIONS,
          "person_participations.last_name.keyword",
          order,
          onlyPatients());
      case "firstNm" -> asFilteredSortOption(
          PERSON_PARTICIPATIONS,
          "person_participations.first_name.keyword",
          order,
          onlyPatients());
      case "birthTime" -> asFilteredSortOption(
          PERSON_PARTICIPATIONS,
          "person_participations.birth_time",
          order,
          onlyPatients());
      case "sex" -> asFilteredSortOption(
          PERSON_PARTICIPATIONS,
          "person_participations.curr_sex_cd",
          order,
          onlyPatients());
      case "local_id" -> asFilteredSortOption(
          PERSON_PARTICIPATIONS,
          "person_participations.local_id",
          order,
          onlyPatients());
      case "startDate" -> asSortOption("activity_from_time", order);
      case "condition" -> asSortOption("cd_desc_txt", order);
      case "jurisdiction" -> asSortOption("jurisdiction_code_desc_txt", order);
      case "investigator" -> asSortOption("investigator_last_nm", order);
      case "investigationId" -> asSortOption("local_id", order);
      case "status" -> asSortOption("investigation_status_cd", order);
      case "notification" -> asSortOption("notification_record_status_cd", order);
      default -> asSortOption("_score", order);
    };
  }

  private static Function<Query.Builder, ObjectBuilder<Query>> onlyPatients() {
    return filter -> filter.term(
        term -> term.field("person_participations.person_cd")
            .value("PAT"));
  }

}
