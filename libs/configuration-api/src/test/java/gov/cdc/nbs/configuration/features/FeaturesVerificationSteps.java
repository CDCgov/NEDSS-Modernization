package gov.cdc.nbs.configuration.features;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class FeaturesVerificationSteps {

  private final Active<ResultActions> response;

  public FeaturesVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("the {feature} feature is {toggle}")
  public void the_feature_is_enabled(final String path, final boolean toggle) throws Exception {
    this.response.active().andExpect(jsonPath("$.%s", path).value(toggle));
  }
}
