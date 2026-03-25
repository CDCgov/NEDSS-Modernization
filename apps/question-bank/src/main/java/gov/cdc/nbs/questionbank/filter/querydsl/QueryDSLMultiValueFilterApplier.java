package gov.cdc.nbs.questionbank.filter.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import gov.cdc.nbs.questionbank.filter.MultiValueFilter;

class QueryDSLMultiValueFilterApplier {

  static BooleanExpression apply(final MultiValueFilter filter, final StringExpression expression) {

    return switch (filter.operator()) {
      case EQUALS -> expression.in(filter.values());
      case NOT_EQUAL_TO -> expression.notIn(filter.values()).or(expression.isNull());
      case STARTS_WITH ->
          filter.values().stream()
              .map(expression::startsWith)
              .reduce(BooleanExpression::or)
              .orElse(null);
      case CONTAINS ->
          filter.values().stream()
              .map(expression::contains)
              .reduce(BooleanExpression::or)
              .orElse(null);
    };
  }

  private QueryDSLMultiValueFilterApplier() {}
}
