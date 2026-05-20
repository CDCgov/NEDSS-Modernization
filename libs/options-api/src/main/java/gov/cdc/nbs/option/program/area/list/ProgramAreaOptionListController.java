package gov.cdc.nbs.option.program.area.list;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nbs/api/options/program-areas")
class ProgramAreaOptionListController {

  private final ProgramAreaOptionFinder resolver;

  ProgramAreaOptionListController(final ProgramAreaOptionFinder finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "program-area-list",
      summary = "NBS Program Area Options",
      description = "Provides options for all program areas.",
      tags = "ProgramAreaOptions")
  @GetMapping
  Collection<Option> complete() {
    return this.resolver.find();
  }
}
