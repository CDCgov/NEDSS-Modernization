package gov.cdc.nbs.option.report.sections;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReportSectionController {
  private final ReportSectionFinder finder;

  ReportSectionController(final ReportSectionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "reportSections",
      summary = "Report sections",
      description = "Provides all report sections in NBS.",
      tags = "ReportSections")
  @GetMapping("/nbs/api/options/report/sections")
  Collection<Option> sections() {
    return finder.find();
  }
}
