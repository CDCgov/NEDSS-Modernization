package gov.cdc.nbs.configuration.settings;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class SettingsSteps {



  private final Active<ResultActions> response;

  SettingsSteps(
      final Active<ResultActions> response
  ) {
    this.response = response;
  }

  @ParameterType(name = "setting", value = ".*")
  public String setting(final String value) {
    return switch (value.toLowerCase()) {
      case "smarty key" -> "settings.smarty.key";
      case "analytics key" -> "settings.analytics.key";
      case "analytics host" -> "settings.analytics.host";
      case "default sizing" -> "settings.defaults.sizing";
      case "default country" -> "settings.defaults.country";
      case "session warning" -> "settings.session.warning";
      case "session expiration" -> "settings.session.expiration";
      default -> value;
    };
  }

  @Then("the settings include a(n) {setting} of {string}")
  public void the_settings_include_a_setting_of(final String path, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.%s", path).value(value));
  }

}
