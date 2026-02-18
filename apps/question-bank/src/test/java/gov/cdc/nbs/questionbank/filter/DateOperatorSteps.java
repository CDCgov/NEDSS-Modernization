package gov.cdc.nbs.questionbank.filter;

import io.cucumber.java.ParameterType;

public class DateOperatorSteps {

  @ParameterType(value = "(?i)(today|last 7 days|last 14 days|last 30 days|more than 30 days)")
  public DateFilter.Operator dateOperator(final String value) {
    return switch (value.toLowerCase()) {
      case "today" -> DateFilter.Operator.TODAY;
      case "last 7 days" -> DateFilter.Operator.LAST_7_DAYS;
      case "last 14 days" -> DateFilter.Operator.LAST_14_DAYS;
      case "last 30 days" -> DateFilter.Operator.LAST_30_DAYS;
      case "more than 30 days" -> DateFilter.Operator.MORE_THAN_30_DAYS;
      default ->
          throw new IllegalStateException("Unexpected Date Operator value: " + value.toLowerCase());
    };
  }
}
