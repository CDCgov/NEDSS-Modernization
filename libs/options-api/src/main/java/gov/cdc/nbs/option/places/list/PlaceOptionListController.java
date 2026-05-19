package gov.cdc.nbs.option.places.list;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nbs/api/options/places")
class PlaceOptionListController {

  private final PlaceOptionFinder resolver;

  PlaceOptionListController(final PlaceOptionFinder finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "place-list",
      summary = "Place Options",
      description = "Provides options for all places.",
      tags = "PlaceOptions")
  @GetMapping
  Collection<Option> complete() {
    return this.resolver.find();
  }
}
