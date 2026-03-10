package gov.cdc.nbs.questionbank.filter.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.TemporalExpression;
import gov.cdc.nbs.questionbank.filter.DateFilter;
import gov.cdc.nbs.questionbank.filter.DateRangeFilter;
import gov.cdc.nbs.questionbank.filter.Filter;
import gov.cdc.nbs.questionbank.filter.MultiValueFilter;
import gov.cdc.nbs.questionbank.filter.SingleValueFilter;
import java.util.stream.Stream;

public class DefaultQueryDSLCriteriaResolver implements QueryDSLFilterApplier.CriteriaResolver {

  @Override
  public Stream<BooleanExpression> resolve(final Filter filter, final Expression<?> expression) {
    return switch (filter) {
      case SingleValueFilter single when expression instanceof StringExpression string ->
          Stream.of(QueryDSLSingleValueFilterApplier.apply(single, string));
      case MultiValueFilter multi when expression instanceof StringExpression string ->
          Stream.of(QueryDSLMultiValueFilterApplier.apply(multi, string));
      case DateFilter date when expression instanceof TemporalExpression<?> temporal ->
          Stream.of(QueryDSLDateFilterApplier.apply(date, temporal));
      case DateRangeFilter dateRange when expression instanceof TemporalExpression<?> temporal ->
          Stream.of(QueryDSLDateRangeFilterApplier.apply(dateRange, temporal));
      case null, default -> Stream.empty();
    };
  }
}
