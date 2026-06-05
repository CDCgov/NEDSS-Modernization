package gov.cdc.nbs.option.report.libraries;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReportLibraryController {
  private final ReportLibraryFinder finder;

  ReportLibraryController(final ReportLibraryFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "reportLibraries",
      summary = "Report libraries",
      description = "Provides all report libraries in NBS.",
      tags = "ReportLibraries")
  @GetMapping("/nbs/api/options/report/libraries")
  Collection<Option> libraries() {
    return finder.find();
  }
}
