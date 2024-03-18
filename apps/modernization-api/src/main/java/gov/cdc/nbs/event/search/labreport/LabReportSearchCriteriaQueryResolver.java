package gov.cdc.nbs.event.search.labreport;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import gov.cdc.nbs.event.search.LabReportFilter;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
class LabReportSearchCriteriaQueryResolver {

  Query resolve(final LabReportFilter criteria) {
    return Stream.of(
            withAccessionNumber(criteria),
            withLabIdentifier(criteria),
            withResultedTest(criteria),
            withCodedTest(criteria)
        ).flatMap(Optional::stream)
        .map(QueryVariant::_toQuery)
        .reduce(
            new BoolQuery.Builder(),
            BoolQuery.Builder::filter,
            (one, two) -> one.filter(two.build().filter())
        ).build()._toQuery();
  }

  private Optional<QueryVariant> withAccessionNumber(final LabReportFilter criteria) {
    return criteria.accession().map(
        accession -> NestedQuery.of(
            nested -> nested.path("act_ids")
                .scoreMode(ChildScoreMode.Avg)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field("act_ids.type_cd")
                                    .value("FN")
                            )
                        ).must(
                            must -> must.prefix(
                                prefix -> prefix.field("act_ids.root_extension_txt")
                                    .caseInsensitive(true)
                                    .value(accession.getLabEventId())
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withLabIdentifier(final LabReportFilter criteria) {
    return criteria.labId().map(
        identifier -> MatchQuery.of(
            match -> match.field("local_id").query(identifier.getLabEventId())
        )
    );
  }

  private Optional<QueryVariant> withResultedTest(final LabReportFilter criteria) {
    String result = criteria.getResultedTest();

    if (result == null || result.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(
        NestedQuery.of(
            nested -> nested.path("observations")
                .scoreMode(ChildScoreMode.None)
                .query(
                    query -> query.match(
                        match -> match.field("observations.cd_desc_txt")
                            .query(result)
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withCodedTest(final LabReportFilter criteria) {
    String result = criteria.getCodedResult();

    if (result == null || result.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(
        NestedQuery.of(
            nested -> nested.path("observations")
                .scoreMode(ChildScoreMode.None)
                .query(
                    query -> query.match(
                        match -> match.field("observations.display_name")
                            .query(result)
                    )
                )
        )
    );
  }

}
