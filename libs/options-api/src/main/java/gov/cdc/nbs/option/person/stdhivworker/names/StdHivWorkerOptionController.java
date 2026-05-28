package gov.cdc.nbs.option.person.stdhivworker.names;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StdHivWorkerOptionController {
  private final StdHivWorkerOptionFinder finder;

  StdHivWorkerOptionController(final StdHivWorkerOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "stdHivWorkerNames",
      summary = "STD HIV Worker Name Option",
      description = "Provides all STD HIV program area worker names.",
      tags = "STDWorkerNameOptions")
  @GetMapping("nbs/api/options/person/std-hiv-worker/names")
  Collection<Option> workers() {
    return finder.find();
  }
}
