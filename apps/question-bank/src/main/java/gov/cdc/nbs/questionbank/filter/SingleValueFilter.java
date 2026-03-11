package gov.cdc.nbs.questionbank.filter;

import java.util.Objects;

public record SingleValueFilter(String property, Operator operator, String value)
    implements ValueFilter {

  public SingleValueFilter {
    Objects.requireNonNull(property, "a property is required");
    Objects.requireNonNull(operator, "an operator is required");
    Objects.requireNonNull(value, "a value is required");
  }
}
