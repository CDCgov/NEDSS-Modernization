package gov.cdc.nbs.option.countries.list;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class CountriesListController {
  private final CountriesListFinder finder;

  public CountriesListController(final CountriesListFinder finder) {
    this.finder = finder;
  }

  @Operation(operationId = "countries", summary = "Countries Option", description = "Provides all Country options.",
      tags = "CountryOptions")
  @GetMapping("/nbs/api/options/countries")
  public Collection<Option> addressTypes() {
    return finder.find();
  }
}
