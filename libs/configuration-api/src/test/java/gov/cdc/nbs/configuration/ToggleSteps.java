package gov.cdc.nbs.configuration;

import io.cucumber.java.ParameterType;

public class ToggleSteps {

  private static final String PREFIX = "enable";

  @ParameterType(name = "toggle", value = "enabled|enable|disabled|disable")
  public boolean setting(final String value) {
    return value.regionMatches(true, 0, PREFIX, 0, PREFIX.length());
  }
}
