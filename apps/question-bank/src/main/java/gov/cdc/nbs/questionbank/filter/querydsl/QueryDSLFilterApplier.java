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

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class QueryDSLFilterApplier {

  public interface ExpressionResolver {
    Stream<Expression<?>> resolve(final String property);
  }

  public static Stream<BooleanExpression> apply(
      final ExpressionResolver expressionResolver,
      final Collection<Filter> filters
  ) {
    return filters.stream()
        .flatMap(applyFilter(expressionResolver))
        .reduce(BooleanExpression::and)
        .stream();
  }

  private static Function<Filter, Stream<BooleanExpression>> applyFilter(
      final ExpressionResolver expressionResolver) {
    return filter -> expressionResolver.resolve(filter.property())
        .flatMap(asBooleanExpression(filter));
  }

  @SuppressWarnings("java:S1452")
  private static Function<Expression<?>, Stream<BooleanExpression>> asBooleanExpression(final Filter filter) {
    return expression -> {
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
    };
  }



  private QueryDSLFilterApplier() {
  }

}
