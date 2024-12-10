package gov.cdc.nbs.option.county;

import io.cucumber.java.en.Given;
import io.cucumber.java.ParameterType;

public class CountyOptionSteps {

  private final CountyMother mother;
  private final CountyStateParameterResolver stateResolver;


  CountyOptionSteps(final CountyMother mother, final CountyStateParameterResolver stateResolver) {
    this.mother = mother;
    this.stateResolver = stateResolver;
  }

  @Given("there is a {string} county for {state} state")
  public void the_county_exists_in_the_value_set_for_state(final String name, final String state) {
    mother.create(name, state);
  }

  @ParameterType(name = "state", value = ".+")
  public String state(final String value) {
    return stateResolver.resolve(value)
        .orElse(null);
  }
}

