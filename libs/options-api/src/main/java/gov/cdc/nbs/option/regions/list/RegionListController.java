package gov.cdc.nbs.option.regions.list;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegionListController {

  private final RegionListFinder finder;

  public RegionListController(final RegionListFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "regions",
      summary = "Regions Option",
      description = "Provides all Regions options.",
      tags = "RegionsOptions")
  @GetMapping("/nbs/api/options/regions")
  public Collection<Option> findAllRegions() {
    return finder.all();
  }
}
