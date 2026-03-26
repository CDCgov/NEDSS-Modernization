package gov.cdc.nbs.questionbank.filter.querydsl;

import static gov.cdc.nbs.questionbank.filter.querydsl.LocalDateConverter.asInstant;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.TemporalExpression;
import gov.cdc.nbs.questionbank.filter.DateRangeFilter;
import java.time.Instant;
import java.time.LocalDate;

class QueryDSLDateRangeFilterApplier {

  @SuppressWarnings("unchecked")
  static BooleanExpression apply(
      final DateRangeFilter filter, final TemporalExpression<?> expression) {

    if (Instant.class.isAssignableFrom(expression.getType())) {
      TemporalExpression<Instant> instant = (TemporalExpression<Instant>) expression;

      if (filter.after() != null && filter.before() != null) {
        return instant.between(after(filter.after()), before(filter.before()));
      } else if (filter.after() != null) {
        return instant.goe(after(filter.after()));
      } else if (filter.before() != null) {
        return instant.before(before(filter.before()));
      }
    }

    return null;
  }

  private static Instant after(final LocalDate localDate) {
    return asInstant(localDate);
  }

  private static Instant before(final LocalDate localDate) {
    return asInstant(localDate.plusDays(1));
  }

  private QueryDSLDateRangeFilterApplier() {}
}
