package gov.cdc.nbs.support.concept.location;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class LocationSteps {

  private final ConceptParameterResolver resolver;
  private final CountryParameterResolver countryParameterResolver;
  private final StateParameterResolver stateParameterResolver;

  LocationSteps(
      final ConceptParameterResolver resolver,
      final CountryParameterResolver countryParameterResolver,
      final StateParameterResolver stateParameterResolver
  ) {
    this.resolver = resolver;
    this.countryParameterResolver = countryParameterResolver;
    this.stateParameterResolver = stateParameterResolver;
  }

  @ParameterType(name = "county", value = ".+")
  public String county(final String value) {
    return resolver.resolve("PHVS_COUNTY_FIPS_6-4", value)
        .orElse(null);
  }

  @ParameterType(name = "state", value = ".+")
  public String state(final String value) {
    return stateParameterResolver.resolve(value)
        .orElse(null);
  }

  @ParameterType(name = "country", value = ".+")
  public String country(final String value) {
    return countryParameterResolver.resolve(value).orElse(null);
  }
}
