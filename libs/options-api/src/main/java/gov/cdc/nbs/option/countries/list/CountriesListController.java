package gov.cdc.nbs.option.countries.list;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.option.Option;

@RestController
public class CountriesListController {
  private final CountriesListFinder finder;

  public CountriesListController(final CountriesListFinder finder) {
    this.finder = finder;
  }

  @GetMapping("/nbs/api/options/countries")
  public Collection<Option> addressTypes() {
    return finder.find();
  }
}
