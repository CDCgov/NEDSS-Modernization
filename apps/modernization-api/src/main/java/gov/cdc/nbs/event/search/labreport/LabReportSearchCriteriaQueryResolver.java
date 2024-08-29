package gov.cdc.nbs.event.search.labreport;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.search.WildCards;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
class LabReportSearchCriteriaQueryResolver {

  private static final String OBSERVATIONS = "observations";

  Query resolve(final LabReportFilter criteria) {
    return Stream.of(
            withAccessionNumber(criteria),
            withLabIdentifier(criteria),
            withResultedTest(criteria),
            withCodedResult(criteria)
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
    return criteria.withResultedTest().map(
        value -> NestedQuery.of(
            nested -> nested.path(OBSERVATIONS)
                .scoreMode(ChildScoreMode.Avg)
                .query(
                    query -> query.simpleQueryString(
                        simple -> simple.fields("observations.cd_desc_txt")
                            .query(WildCards.startsWith(value))
                            .defaultOperator(Operator.And)
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withCodedResult(final LabReportFilter criteria) {
    return criteria.withCodedResult().map(
        value -> NestedQuery.of(
            nested -> nested.path(OBSERVATIONS)
                .scoreMode(ChildScoreMode.Avg)
                .query(
                    query -> query.simpleQueryString(
                        simple -> simple.fields("observations.display_name")
                            .query(WildCards.startsWith(value))
                            .defaultOperator(Operator.And)
                    )
                )
        )
    );
  }

}
