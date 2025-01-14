package gov.cdc.nbs.event.search.basic;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.util.ObjectBuilder;
import org.springframework.data.domain.Sort;

import java.util.function.Function;
import java.util.stream.Stream;

import static gov.cdc.nbs.search.SearchSorting.*;

public class BasicInformationSearchCriteriaSortResolver {

  private static final String PERSON_PARTICIPATIONS = "person_participations";

  public static Stream<SortOptions> resolve(final Sort.Order sorting) {
    SortOrder order = asSortOrder(sorting.getDirection());

    return switch (sorting.getProperty().toLowerCase()) {
      case "lastnm", "lastname" -> Stream.of(
          asFilteredSortOption(
              PERSON_PARTICIPATIONS,
              "person_participations.last_name.keyword",
              order,
              onlyPatients()
          )
      );
      case "firstnm", "firstname" -> Stream.of(
          asFilteredSortOption(
              PERSON_PARTICIPATIONS,
              "person_participations.first_name.keyword",
              order,
              onlyPatients()
          )
      );
      case "legalname" -> Stream.of(
          asFilteredSortOption(
              PERSON_PARTICIPATIONS,
              "person_participations.last_name.keyword",
              order,
              onlyPatients()
          ),
          asFilteredSortOption(
              PERSON_PARTICIPATIONS,
              "person_participations.first_name.keyword",
              order,
              onlyPatients()
          )
      );
      case "birthtime", "birthday" -> Stream.of(
          asFilteredSortOption(
              PERSON_PARTICIPATIONS,
              "person_participations.birth_time",
              order,
              onlyPatients()
          )
      );
      case "sex", "gender" -> Stream.of(
          asFilteredSortOption(
              PERSON_PARTICIPATIONS,
              "person_participations.curr_sex_cd",
              order,
              onlyPatients()
          )
      );
      case "patientid", "shortid","patient" -> Stream.of(
          asFilteredSortOption(
              PERSON_PARTICIPATIONS,
              "person_participations.local_id",
              order,
              onlyPatients()
          )
      );
      default -> Stream.of(asSortOption("_score", order));
    };
  }

  private static Function<Query.Builder, ObjectBuilder<Query>> onlyPatients() {
    return filter -> filter.term(
        term -> term.field("person_participations.person_cd")
            .value("PAT"));
  }

  private BasicInformationSearchCriteriaSortResolver() {

  }
}
