package gov.cdc.nbs.questionbank.filter;

import java.util.Collection;
import java.util.Objects;

public record MultiValueFilter(String property, Operator operator, Collection<String> values)
    implements ValueFilter {

  public MultiValueFilter {
    Objects.requireNonNull(property, "a property is required");
    Objects.requireNonNull(operator, "an operator is required");
    Objects.requireNonNull(values, "a values is required");
  }
}
