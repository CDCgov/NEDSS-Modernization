package gov.cdc.nbs.configuration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder;
import io.swagger.annotations.ApiImplicitParam;



@RestController
@RequestMapping("/nbs/api/configuration")
public class ConfigurationController {

  private final Features features;
  private final NbsPropertiesFinder finder;

  public ConfigurationController(
      final Features features,
      final NbsPropertiesFinder finder) {
    this.features = features;
    this.finder = finder;
  }

  @ApiImplicitParam(
      name = "Authorization",
      required = true,
      paramType = "header",
      dataTypeClass = String.class)
  @GetMapping
  public Configuration getConfiguration() {
    return new Configuration(features, finder.find());
  }
}
