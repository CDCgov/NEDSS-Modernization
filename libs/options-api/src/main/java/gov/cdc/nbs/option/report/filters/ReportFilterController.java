package gov.cdc.nbs.option.report.filters;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReportFilterController {
  private final ReportFilterFinder finder;

  ReportFilterController(final ReportFilterFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "reportFilters",
      summary = "Report filters",
      description = "Provides all report filters in NBS.",
      tags = "ReportFilters")
  @GetMapping("/nbs/api/options/report/filters")
  Collection<Option> filters() {
    return finder.find();
  }
}
