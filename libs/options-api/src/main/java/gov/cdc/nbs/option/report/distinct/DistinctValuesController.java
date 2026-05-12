package gov.cdc.nbs.option.report.distinct;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DistinctValuesController {
  private final DistinctValuesFinder finder;

  DistinctValuesController(final DistinctValuesFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "distinct-values",
      summary = "Distinct values in a report column",
      description = "Provides all distinct options for a specific report column.",
      tags = "DistinctValues")
  @GetMapping("nbs/api/options/report/distinct/{columnUid}")
  Collection<Option> distinctValues(@PathVariable final String columnUid) {
    return finder.find(columnUid);
  }
}
