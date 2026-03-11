package gov.cdc.nbs.support.concept.race;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class RaceConceptSteps {

  private final ConceptParameterResolver categoryResolver;
  private final DetailedRaceParameterResolver detailedResolver;

  RaceConceptSteps(
      final ConceptParameterResolver categoryResolver,
      final DetailedRaceParameterResolver detailedResolver) {
    this.categoryResolver = categoryResolver;
    this.detailedResolver = detailedResolver;
  }

  @ParameterType(name = "raceCategory", value = ".*")
  public String raceCategory(final String value) {
    return categoryResolver.resolve("RACE_CALCULATED", value).orElse(null);
  }

  @ParameterType(name = "raceDetail", value = ".*")
  public String raceDetail(final String value) {
    return detailedResolver.resolve(value).orElse(null);
  }
}
