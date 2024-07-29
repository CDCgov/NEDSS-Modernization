package gov.cdc.nbs.configuration;

import io.cucumber.java.ParameterType;

import java.util.Objects;

public class ToggleSteps {

  @ParameterType(name = "toggle", value = "enabled|disabled")
  public boolean setting(final String value) {
    return Objects.equals(value.toLowerCase(), "enabled");
  }

}
