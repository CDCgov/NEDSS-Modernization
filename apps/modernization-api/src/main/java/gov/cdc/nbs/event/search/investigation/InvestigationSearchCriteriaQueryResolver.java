package gov.cdc.nbs.event.search.investigation;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import gov.cdc.nbs.event.search.InvestigationFilter;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class InvestigationSearchCriteriaQueryResolver {

  Query resolve(final InvestigationFilter criteria) {
    return Stream.of(
            withABCSCase(criteria),
            withStateCase(criteria),
            withCountyCase(criteria),
            withCaseIdentifier(criteria),
            withNotification(criteria))
        .flatMap(Optional::stream)
        .map(QueryVariant::_toQuery)
        .reduce(
            new BoolQuery.Builder(),
            BoolQuery.Builder::filter,
            (one, two) -> one.filter(two.build().filter()))
        .build()
        ._toQuery();
  }

  private Optional<QueryVariant> withABCSCase(final InvestigationFilter criteria) {
    return criteria
        .abcsCase()
        .map(
            number ->
                NestedQuery.of(
                    nested ->
                        nested
                            .path(SearchableInvestigation.IDENTIFICATION)
                            .scoreMode(ChildScoreMode.Avg)
                            .query(
                                query ->
                                    query.bool(
                                        bool ->
                                            bool.filter(
                                                    filter ->
                                                        filter.term(
                                                            term ->
                                                                term.field(
                                                                        SearchableInvestigation
                                                                            .IDENTIFICATION_TYPE)
                                                                    .value("STATE")))
                                                .filter(
                                                    filter ->
                                                        filter.term(
                                                            term ->
                                                                term.field(
                                                                        SearchableInvestigation
                                                                            .IDENTIFICATION_SEQUENCE)
                                                                    .value(2)))
                                                .must(
                                                    must ->
                                                        must.prefix(
                                                            prefix ->
                                                                prefix
                                                                    .field(
                                                                        SearchableInvestigation
                                                                            .IDENTIFICATION_VALUE)
                                                                    .caseInsensitive(true)
                                                                    .value(number.getId())))))));
  }

  private Optional<QueryVariant> withStateCase(final InvestigationFilter criteria) {
    return criteria
        .stateCase()
        .map(
            number ->
                NestedQuery.of(
                    nested ->
                        nested
                            .path(SearchableInvestigation.IDENTIFICATION)
                            .scoreMode(ChildScoreMode.Avg)
                            .query(
                                query ->
                                    query.bool(
                                        bool ->
                                            bool.filter(
                                                    filter ->
                                                        filter.term(
                                                            term ->
                                                                term.field(
                                                                        SearchableInvestigation
                                                                            .IDENTIFICATION_TYPE)
                                                                    .value("STATE")))
                                                .must(
                                                    must ->
                                                        must.prefix(
                                                            prefix ->
                                                                prefix
                                                                    .field(
                                                                        SearchableInvestigation
                                                                            .IDENTIFICATION_VALUE)
                                                                    .caseInsensitive(true)
                                                                    .value(number.getId())))))));
  }

  private Optional<QueryVariant> withCountyCase(final InvestigationFilter criteria) {
    return criteria
        .countyCase()
        .map(
            number ->
                NestedQuery.of(
                    nested ->
                        nested
                            .path(SearchableInvestigation.IDENTIFICATION)
                            .scoreMode(ChildScoreMode.Avg)
                            .query(
                                query ->
                                    query.bool(
                                        bool ->
                                            bool.filter(
                                                    filter ->
                                                        filter.term(
                                                            term ->
                                                                term.field(
                                                                        SearchableInvestigation
                                                                            .IDENTIFICATION_TYPE)
                                                                    .value("CITY")))
                                                .must(
                                                    must ->
                                                        must.prefix(
                                                            prefix ->
                                                                prefix
                                                                    .field(
                                                                        SearchableInvestigation
                                                                            .IDENTIFICATION_VALUE)
                                                                    .caseInsensitive(true)
                                                                    .value(number.getId())))))));
  }

  private Optional<QueryVariant> withCaseIdentifier(final InvestigationFilter criteria) {
    return criteria
        .caseId()
        .map(
            identifier ->
                MatchQuery.of(match -> match.field("local_id").query(identifier.getId())));
  }

  private Optional<QueryVariant> withNotification(InvestigationFilter criteria) {
    return criteria
        .notification()
        .map(
            identifier ->
                MatchQuery.of(
                    match -> match.field("notification_local_id").query(identifier.getId())));
  }
}
