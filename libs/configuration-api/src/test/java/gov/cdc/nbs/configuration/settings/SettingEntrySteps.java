package gov.cdc.nbs.configuration.settings;

import io.cucumber.java.en.Given;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PostConstruct;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@ScenarioScope
public class SettingEntrySteps {
  private static final String SETTINGS_PROPERTY_SOURCE = "settings-test-properties";

  private final ConfigurableEnvironment environment;

  SettingEntrySteps(final ConfigurableEnvironment environment) {
    this.environment = environment;
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

  @PostConstruct
  void reset() {
    Logger.getAnonymousLogger().info(">>>>>>>>>Resetting settings");
    environment.getPropertySources().remove(SETTINGS_PROPERTY_SOURCE);
  }
}
