package gov.cdc.nbs.questionbank.order;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class QueryDSLOrderResolver {

  public interface ExpressionResolver {
    @SuppressWarnings("java:S1452")
    Stream<Expression<?>> resolve(final String property);
  }

  @SuppressWarnings("java:S1452")
  public static Stream<OrderSpecifier<?>> resolve(
      final ExpressionResolver resolver, final Pageable pageable) {
    return pageable.getSort().stream().flatMap(withOrdering(resolver)).filter(Objects::nonNull);
  }

  private static Function<Sort.Order, Stream<OrderSpecifier<?>>> withOrdering(
      final ExpressionResolver resolver) {
    return order ->
        resolver
            .resolve(order.getProperty())
            .flatMap(onlyComparable())
            .map(withDirection(order.getDirection()));
  }

  private static Function<Expression<?>, Stream<ComparableExpressionBase<?>>> onlyComparable() {
    return expression ->
        (expression instanceof ComparableExpressionBase<?> comparable)
            ? Stream.of(comparable)
            : Stream.empty();
  }

  private static Function<ComparableExpressionBase<?>, OrderSpecifier<?>> withDirection(
      final Sort.Direction direction) {
    return comparable -> direction.isAscending() ? comparable.asc() : comparable.desc();
  }

  private QueryDSLOrderResolver() {}
}
