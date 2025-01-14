package gov.cdc.nbs.option.race;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class RaceOptionsController {

  private final RaceOptionFinder finder;

  RaceOptionsController(final RaceOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "races",
      summary = "Race Option",
      description = "Provides all Race options.",
      tags = "RaceOptions"
  )
  @GetMapping("nbs/api/options/races")
  Collection<Option> all() {
    return SpecificRaceSorter.sorted(this.finder.find());
  }

}
