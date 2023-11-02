package gov.cdc.nbs.questionbank.filter.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import gov.cdc.nbs.questionbank.filter.SingleValueFilter;

class QueryDSLSingleValueFilterApplier {

  static BooleanExpression apply(
      final SingleValueFilter filter,
      final Expression<?> expression
  ) {

    if (expression instanceof StringExpression string) {
      return switch (filter.operator()) {
        case EQUALS -> string.eq(filter.value());
        case NOT_EQUAL_TO -> string.ne(filter.value());
        case STARTS_WITH -> string.startsWith(filter.value());
        case CONTAINS -> string.contains(filter.value());
      };
    }

    return null;
  }

  private QueryDSLSingleValueFilterApplier() {
  }

}
