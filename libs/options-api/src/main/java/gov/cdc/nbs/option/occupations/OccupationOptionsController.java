package gov.cdc.nbs.option.occupations;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OccupationOptionsController {

  private final OccupationOptionFinder finder;

  OccupationOptionsController(final OccupationOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "occupations",
      summary = "Occupation Option",
      description = "Provides all Occupations options.",
      tags = "OccupationOptions")
  @GetMapping("nbs/api/options/occupations")
  Collection<Option> all() {
    return this.finder.find();
  }
}
