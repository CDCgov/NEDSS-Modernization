package gov.cdc.nbs.option.person.names.list;

import gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder;
import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PersonNamesListController {
  private final PersonNamesListFinder finder;
  private final NbsPropertiesFinder nbsPropertiesFinder;

  PersonNamesListController(
      final PersonNamesListFinder finder, final NbsPropertiesFinder nbsPropertiesFinder) {
    this.nbsPropertiesFinder = nbsPropertiesFinder;
    this.finder = finder;
  }

  @Operation(
      operationId = "personNames",
      summary = "STD HIV Workers",
      description = "Provides all STD HIV Workers.",
      tags = "WorkerOptions")
  @GetMapping("nbs/api/options/person/names")
  Collection<Option> workers() {
    return finder.find();
  }
}
