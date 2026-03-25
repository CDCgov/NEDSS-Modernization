package gov.cdc.nbs.support.concept;

import io.cucumber.java.ParameterType;

public class IndicatorSteps {

  @ParameterType(
      name = "indicator",
      value = "unknown|Unknown|is not known to be|no|No|is not|does not|yes|Yes|is|does")
  public String indicator(final String value) {
    return switch (value.toLowerCase()) {
      case "yes", "is", "does" -> "Y";
      case "no", "is not", "does not", "doesn't" -> "N";
      case "unknown", "is not known to be" -> "U";
      default -> value;
    };
  }
}
