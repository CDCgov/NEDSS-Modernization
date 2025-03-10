package gov.cdc.nbs.option.name.type;

import gov.cdc.nbs.option.general.GeneralCodedFinder;
import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class NameTypeOptionsController {

  private final GeneralCodedFinder finder;
  private static final String CODE = "P_NM_USE";

  NameTypeOptionsController(final GeneralCodedFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "nameTypes",
      summary = "Name Type Option",
      description = "Provides all name type options.",
      tags = "NameTypeOptions"
  )
  @GetMapping("nbs/api/options/name/types")
  Collection<Option> all() {
    return this.finder.find(CODE);
  }

}
