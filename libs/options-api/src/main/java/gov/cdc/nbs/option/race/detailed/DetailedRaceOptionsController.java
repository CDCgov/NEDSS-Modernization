package gov.cdc.nbs.option.race.detailed;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DetailedRaceOptionsController {

  private final DetailedRaceOptionFinder finder;

  DetailedRaceOptionsController(final DetailedRaceOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "detailedRaces",
      summary = "Detailed Race Option",
      description = "Provides all Detailed Race options for the given category.",
      tags = "RaceOptions")
  @GetMapping("nbs/api/options/races/{category}")
  Collection<Option> all(@PathVariable final String category) {
    return this.finder.find(category);
  }
}
