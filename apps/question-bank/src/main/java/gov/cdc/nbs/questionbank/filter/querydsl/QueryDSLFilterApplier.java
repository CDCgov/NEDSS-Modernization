package gov.cdc.nbs.questionbank.filter.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import gov.cdc.nbs.questionbank.filter.Filter;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class QueryDSLFilterApplier {

  public interface ExpressionResolver {
    @SuppressWarnings("java:S1452")
    Stream<Expression<?>> resolve(final String property);
  }

  public interface CriteriaResolver {
    Stream<BooleanExpression> resolve(final Filter filter, final Expression<?> expression);
  }

  public static Stream<BooleanExpression> apply(
      final ExpressionResolver expressionResolver,
      final CriteriaResolver criteriaResolver,
      final Collection<Filter> filters) {
    return filters.stream()
        .flatMap(applyFilter(expressionResolver, criteriaResolver))
        .reduce(BooleanExpression::and)
        .stream();
  }

  private static Function<Filter, Stream<BooleanExpression>> applyFilter(
      final ExpressionResolver expressionResolver, final CriteriaResolver criteriaResolver) {
    return filter ->
        expressionResolver
            .resolve(filter.property())
            .flatMap(expression -> criteriaResolver.resolve(filter, expression));
  }

  private QueryDSLFilterApplier() {}
}
