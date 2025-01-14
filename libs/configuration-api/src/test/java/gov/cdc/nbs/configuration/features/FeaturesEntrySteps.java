package gov.cdc.nbs.configuration.features;

import io.cucumber.java.en.Given;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

public class FeaturesEntrySteps {

  private static final String FEATURE_PROPERTY_SOURCE = "feature-test-properties";

  private final ConfigurableEnvironment environment;

  FeaturesEntrySteps(final ConfigurableEnvironment environment) {
    this.environment = environment;
  }

  @Given("I {toggle} the {feature} feature")
  public void i_toggle_a_feature(final boolean toggle, final String feature) {
    properties().put("nbs.ui." + feature, toggle);
  }

  private Map<String, Object> properties() {
    MutablePropertySources sources = environment.getPropertySources();

    if (sources.get(FEATURE_PROPERTY_SOURCE) instanceof MapPropertySource source) {
      return source.getSource();
    } else {
      MapPropertySource properties = new MapPropertySource(FEATURE_PROPERTY_SOURCE, new HashMap<>());
      sources.addFirst(properties);
      return properties.getSource();
    }
  }

}
