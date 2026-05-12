package gov.cdc.nbs.option.jurisdictions.list;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nbs/api/options/jurisdictions")
class JurisdictionOptionListController {

  private final JurisdictionOptionListResolver resolver;

  JurisdictionOptionListController(final JurisdictionOptionListResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "jurisdiction-list",
      summary = "NBS Jurisdiction Options",
      description = "Provides options for all jurisdictions.",
      tags = "JurisdictionOptions")
  @GetMapping
  Collection<Option> jurisdicions() {
    return this.resolver.find();
  }
}
