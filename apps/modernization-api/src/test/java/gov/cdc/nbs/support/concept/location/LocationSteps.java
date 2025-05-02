package gov.cdc.nbs.support.concept.location;

import io.cucumber.java.ParameterType;

public class LocationSteps {

  private final CountyParameterResolver countyResolver;
  private final CountryParameterResolver countryResolver;
  private final StateParameterResolver stateResolver;

  LocationSteps(
      final CountyParameterResolver countyResolver,
      final CountryParameterResolver countryResolver,
      final StateParameterResolver stateResolver
  ) {
    this.countyResolver = countyResolver;
    this.countryResolver = countryResolver;
    this.stateResolver = stateResolver;
  }

  @ParameterType(name = "county", value = ".+")
  public String county(final String value) {
    return countyResolver.resolve(value)
        .orElse(null);
  }

  @ParameterType(name = "state", value = ".+")
  public String state(final String value) {
    return stateResolver.resolve(value)
        .orElse(null);
  }

  @ParameterType(name = "postalCode", value = "\\d{5}([ \\-]\\d{4})?")
  public String postalCode(final String value) { return value; }

  @ParameterType(name = "country", value = ".+")
  public String country(final String value) {
    return countryResolver.resolve(value).orElse(null);
  }
}
