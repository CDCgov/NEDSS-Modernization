package gov.cdc.nbs.questionbank.filter.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import gov.cdc.nbs.questionbank.filter.MultiValueFilter;

class QueryDSLMultiValueFilterApplier {

  static BooleanExpression apply(
      final MultiValueFilter filter,
      final Expression<?> expression
  ) {

    if (expression instanceof StringExpression string) {
      return switch (filter.operator()) {
        case EQUALS -> string.in(filter.values());
        case NOT_EQUAL_TO -> string.notIn(filter.values());
        case STARTS_WITH -> filter.values().stream().map(string::startsWith).reduce(BooleanExpression::or).orElse(null);
        case CONTAINS -> filter.values().stream().map(string::contains).reduce(BooleanExpression::or).orElse(null);
      };
    }
    return null;
  }

  private QueryDSLMultiValueFilterApplier() {
  }

}
