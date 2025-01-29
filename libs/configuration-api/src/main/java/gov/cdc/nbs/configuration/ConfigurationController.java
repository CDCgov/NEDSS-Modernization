package gov.cdc.nbs.configuration;

import gov.cdc.nbs.configuration.features.Features;
import gov.cdc.nbs.configuration.features.FeaturesResolver;
import gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder;
import gov.cdc.nbs.configuration.nbs.Properties;
import gov.cdc.nbs.configuration.settings.Settings;
import gov.cdc.nbs.configuration.settings.SettingsResolver;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/nbs/api/configuration")
class ConfigurationController {

  private final FeaturesResolver featuresResolver;
  private final SettingsResolver settingsResolver;
  private final NbsPropertiesFinder finder;

  ConfigurationController(
      final FeaturesResolver featuresResolver,
      final SettingsResolver settingsResolver,
      final NbsPropertiesFinder finder
  ) {
    this.featuresResolver = featuresResolver;
    this.settingsResolver = settingsResolver;
    this.finder = finder;
  }

  @GetMapping
  Configuration getConfiguration() {
    Features features = featuresResolver.resolve();
    Settings settings = settingsResolver.resolve();
    Properties properties = finder.find();
    return new Configuration(
        features,
        settings,
        properties
    );
  }
}
