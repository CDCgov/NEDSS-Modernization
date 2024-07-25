package gov.cdc.nbs.configuration.features;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class FeaturesSteps {

  private final Active<ResultActions> response;

  public FeaturesSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @ParameterType(name = "feature", value = ".*")
  public String feature(final String value) {
    return switch (value.toLowerCase()) {
      case "search view" -> "features.search.view.enabled";
      case "search table view" -> "features.search.view.table.enabled";
      default -> value;
    };
  }

  @Then("the {feature} feature is {toggle}")
  public void the_feature_is_enabled(final String path, final boolean toggle) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.%s", path).value(toggle));
  }


}
