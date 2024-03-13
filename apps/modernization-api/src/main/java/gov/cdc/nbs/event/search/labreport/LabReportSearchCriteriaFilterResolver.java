package gov.cdc.nbs.event.search.labreport;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.event.search.LabReportFilter;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class LabReportSearchCriteriaFilterResolver {

  Query resolve(final LabReportFilter criteria, final PermissionScope scope) {
    return Stream.of(
            withPermission(scope)
        ).flatMap(Optional::stream)
        .map(QueryVariant::_toQuery)
        .reduce(
            new BoolQuery.Builder(),
            BoolQuery.Builder::filter,
            (one, two) -> one.filter(two.build().filter())
        ).build()._toQuery();
  }

  private Optional<QueryVariant> withPermission(final PermissionScope scope) {
    TermsQuery statuses = scope.any()
        .stream()
        .map(FieldValue::of)
        .collect(
            Collectors.collectingAndThen(
                Collectors.toList(),
                collected -> TermsQuery.of(
                    query -> query.field("program_jurisdiction_oid")
                        .terms(terms -> terms.value(collected))
                )
            )
        );

    return Optional.of(statuses);
  }

}
