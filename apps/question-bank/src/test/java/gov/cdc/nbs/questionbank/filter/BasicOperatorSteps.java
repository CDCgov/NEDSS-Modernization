package gov.cdc.nbs.questionbank.filter;

import io.cucumber.java.ParameterType;

public class BasicOperatorSteps {

  @ParameterType(name = "operator", value = "(?i)(equals|not equal to|starts with|contains)")
  public SingleValueFilter.Operator operator(final String value) {
    return switch (value.toLowerCase()) {
      case "equals" -> SingleValueFilter.Operator.EQUALS;
      case "not equal to" -> SingleValueFilter.Operator.NOT_EQUAL_TO;
      case "starts with" -> SingleValueFilter.Operator.STARTS_WITH;
      case "contains" -> SingleValueFilter.Operator.CONTAINS;
      default -> throw new IllegalStateException("Unexpected Operator value: " + value.toLowerCase());
    };
  }

}
