package gov.cdc.nbs.questionbank.filter;

import io.cucumber.java.ParameterType;

public class ValueOperatorSteps {

  @ParameterType(value = "(?i)(equals|not equal to|starts with|contains)")
  public ValueFilter.Operator valueOperator(final String value) {
    return switch (value.toLowerCase()) {
      case "equals" -> ValueFilter.Operator.EQUALS;
      case "not equal to" -> ValueFilter.Operator.NOT_EQUAL_TO;
      case "starts with" -> ValueFilter.Operator.STARTS_WITH;
      case "contains" -> ValueFilter.Operator.CONTAINS;
      default ->
          throw new IllegalStateException(
              "Unexpected Value Operator value: " + value.toLowerCase());
    };
  }
}
