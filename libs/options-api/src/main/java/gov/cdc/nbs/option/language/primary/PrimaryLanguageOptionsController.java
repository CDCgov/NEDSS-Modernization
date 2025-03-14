package gov.cdc.nbs.option.language.primary;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class PrimaryLanguageOptionsController {

  private final PrimaryLanguageOptionFinder finder;

  PrimaryLanguageOptionsController(final PrimaryLanguageOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "primaryLanguages",
      summary = "Primary language Option",
      description = "Provides all Primary language options.",
      tags = "PrimaryLanguageOptions"
  )
  @GetMapping("nbs/api/options/languages/primary")
  Collection<Option> all() {
    return this.finder.find();
  }

}
