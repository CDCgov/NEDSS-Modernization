package gov.cdc.nbs.questionbank.support;

import io.cucumber.java.ParameterType;
import org.springframework.data.domain.Sort;

public class DirectionSteps {

  @ParameterType(name = "direction", value = "(?i)(asc|ascending|desc|descending)")
  public Sort.Direction direction(final String value) {
    return switch (value) {
      case "asc", "ascending" -> Sort.Direction.ASC;
      default -> Sort.Direction.DESC;
    };
  }
}
