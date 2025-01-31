package gov.cdc.nbs.configuration.settings;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class SettingsSteps {

  private static final String SETTINGS_PROPERTY_SOURCE = "settings-test-properties";

  private final ConfigurableEnvironment environment;
  private final Active<ResultActions> response;

  public SettingsSteps(
      final ConfigurableEnvironment environment,
      final Active<ResultActions> response
  ) {
    this.environment = environment;
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
      default -> value;
    };
  }

  @Given("I set the {setting} setting to {string}")
  public void i_set_the_setting(final String setting, String value) {
    properties().put("nbs.ui." + setting, value);
  }

  private Map<String, Object> properties() {
    MutablePropertySources sources = environment.getPropertySources();

    if (sources.get(SETTINGS_PROPERTY_SOURCE) instanceof MapPropertySource source) {
      return source.getSource();
    } else {
      MapPropertySource properties = new MapPropertySource(SETTINGS_PROPERTY_SOURCE, new HashMap<>());
      sources.addFirst(properties);
      return properties.getSource();
    }
  }

  @Then("the settings include a(n) {setting} of {string}")
  public void the_settings_include_a_setting_of(final String path, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.%s", path).value(value));
  }

}
