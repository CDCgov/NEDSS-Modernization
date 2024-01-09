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
    if (filter instanceof SingleValueFilter single && expression instanceof StringExpression string) {
      return Stream.of(QueryDSLSingleValueFilterApplier.apply(single, string));
    } else if (filter instanceof MultiValueFilter multi && expression instanceof StringExpression string) {
      return Stream.of(QueryDSLMultiValueFilterApplier.apply(multi, string));
    } else if (filter instanceof DateFilter date && expression instanceof TemporalExpression<? extends Comparable> temporal) {
      return Stream.of(QueryDSLDateFilterApplier.apply(date, temporal));
    } else if (filter instanceof DateRangeFilter dateRange && expression instanceof TemporalExpression<? extends Comparable> temporal) {
      return Stream.of(QueryDSLDateRangeFilterApplier.apply(dateRange, temporal));
    } else {
      return Stream.empty();
    }
  }
}

