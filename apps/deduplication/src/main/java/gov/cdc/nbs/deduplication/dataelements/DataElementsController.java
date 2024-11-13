package gov.cdc.nbs.deduplication.dataelements;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.deduplication.dataelements.model.DataElementConfiguration;
import gov.cdc.nbs.deduplication.dataelements.model.DataElementConfigurationResponse;

@RestController
@RequestMapping("/api/deduplication/data-elements")
public class DataElementsController {
  private final DataElementsResolver resolver;
  private final DataElementCreator creator;

  public DataElementsController(
      final DataElementsResolver resolver,
      final DataElementCreator creator) {
    this.resolver = resolver;
    this.creator = creator;
  }

  @GetMapping
  public DataElementConfigurationResponse getDataElements() {
    return resolver.resolveCurrent();
  }

  @PostMapping
  public DataElementConfigurationResponse save(@RequestBody DataElementConfiguration configuration) {
    creator.create(configuration);
    return resolver.resolveCurrent();
  }

}
