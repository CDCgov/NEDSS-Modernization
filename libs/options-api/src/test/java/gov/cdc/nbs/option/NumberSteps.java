package gov.cdc.nbs.option;

import io.cucumber.java.ParameterType;

public class NumberSteps {

  @ParameterType(name = "nth", value="(\\d+)(?:st|nd|rd|th)")
  public int nth(final String value) {
    return Integer.parseInt(value);
  }

}
