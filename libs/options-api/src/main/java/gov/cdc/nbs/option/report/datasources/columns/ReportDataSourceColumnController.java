package gov.cdc.nbs.option.report.datasources.columns;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReportDataSourceColumnController {
  private final ReportDataSourceFilterableColumnFinder finder;

  ReportDataSourceColumnController(final ReportDataSourceFilterableColumnFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "reportDataSourceColumns",
      summary = "Filterable report columns for a data source",
      description = "Provides all filterable report columns in a data source in NBS.",
      tags = "ReportDataSourceFilterableColumns")
  @GetMapping("/nbs/api/options/report/datasource/{dataSourceUid}/columns/filterable")
  Collection<Option> dataSourceColumns(@PathVariable final String dataSourceUid) {
    return finder.find(dataSourceUid);
  }
}
