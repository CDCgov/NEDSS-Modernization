package gov.cdc.nbs.configuration;

import gov.cdc.nbs.configuration.features.Features;
import gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder;
import gov.cdc.nbs.configuration.settings.Settings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nbs/api/configuration")
public class ConfigurationController {

  private final Features features;
  private final Settings settings;
  private final NbsPropertiesFinder finder;

  public ConfigurationController(
      final Features features,
      final Settings settings,
      final NbsPropertiesFinder finder
  ) {
    this.features = features;
    this.settings = settings;
    this.finder = finder;
  }

  @GetMapping
  public Configuration getConfiguration() {
    return new Configuration(
        features,
        settings,
        finder.find()
    );
  }
}
