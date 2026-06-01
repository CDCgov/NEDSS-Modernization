package gov.cdc.nbs.option.report.datasources;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReportDataSourceController {
  private final ReportDataSourceFinder finder;

  ReportDataSourceController(final ReportDataSourceFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "reportDataSources",
      summary = "Report data sources",
      description = "Provides all report data sources in NBS.",
      tags = "ReportDataSuorces")
  @GetMapping("/nbs/api/options/report/datasources")
  Collection<Option> dataSources() {
    return finder.find();
  }
}
