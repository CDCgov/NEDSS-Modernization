package gov.cdc.nbs.questionbank.filter.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.TemporalExpression;
import gov.cdc.nbs.questionbank.filter.DateFilter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static gov.cdc.nbs.questionbank.filter.querydsl.LocalDateConverter.asInstant;

class QueryDSLDateFilterApplier {

  @SuppressWarnings({"unchecked"})
  static BooleanExpression apply(
      final DateFilter filter,
      final TemporalExpression<? extends Comparable> expression
  ) {

    if (Instant.class.isAssignableFrom(expression.getType())) {

      TemporalExpression<Instant> instant = (TemporalExpression<Instant>) expression;

      return switch (filter.operator()) {
        case TODAY -> instant.after(asInstant(filter.from()));
        case LAST_7_DAYS -> instant.after(asInstant(filter.from().minusDays(7L)));
        case LAST_14_DAYS -> instant.after(asInstant(filter.from().minusDays(14L)));
        case LAST_30_DAYS -> instant.after(asInstant(filter.from().minusDays(30L)));
        case MORE_THAN_30_DAYS -> instant.before(asInstant(filter.from().minusDays(30L)));
      };

    }

    return null;
  }


  private QueryDSLDateFilterApplier() {
  }

}
