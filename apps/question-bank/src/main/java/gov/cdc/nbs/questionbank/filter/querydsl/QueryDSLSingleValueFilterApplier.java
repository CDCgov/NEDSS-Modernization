package gov.cdc.nbs.questionbank.filter.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import gov.cdc.nbs.questionbank.filter.SingleValueFilter;

class QueryDSLSingleValueFilterApplier {

  static BooleanExpression apply(
      final SingleValueFilter filter, final StringExpression expression) {
    return switch (filter.operator()) {
      case EQUALS -> expression.eq(filter.value());
      case NOT_EQUAL_TO -> expression.ne(filter.value());
      case STARTS_WITH -> expression.startsWith(filter.value());
      case CONTAINS -> expression.contains(filter.value());
    };
  }

  private QueryDSLSingleValueFilterApplier() {}
}
