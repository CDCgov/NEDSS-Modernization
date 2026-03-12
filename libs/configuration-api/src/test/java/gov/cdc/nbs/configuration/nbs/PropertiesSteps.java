package gov.cdc.nbs.configuration.nbs;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PropertiesSteps {

  private final Active<ResultActions> response;

  public PropertiesSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @ParameterType(name = "property", value = ".*")
  public String property(final String value) {
    return switch (value.toLowerCase()) {
      case "code base" -> "properties.entries.CODE_BASE";
      case "std program areas", "stdprogramareas" -> "properties.stdProgramAreas";
      case "hiv program areas", "hivprogramareas" -> "properties.hivProgramAreas";
      default -> value;
    };
  }

  @Then("the properties include a(n) {property} of {string}")
  public void the_properties_include_a_property_of(final String path, final String value)
      throws Exception {
    this.response.active().andExpect(jsonPath("$.%s", path).value(value));
  }

  @Then("the properties include a(n) {property} with {string}")
  public void the_properties_include_a_property_with(final String path, final String value)
      throws Exception {
    this.response.active().andExpect(jsonPath("$.%s", path).value(hasItem(equalTo(value))));
  }
}
