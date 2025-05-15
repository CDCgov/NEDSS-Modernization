package gov.cdc.nbs.testing.authorization.programarea;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;

public class ProgramAreaSteps {

  private final ProgramAreaMother mother;
  private final ProgramAreaParameterResolver resolver;

  ProgramAreaSteps(
      final ProgramAreaMother mother,
      final ProgramAreaParameterResolver resolver
  ) {
    this.mother = mother;
    this.resolver = resolver;
  }

  @Given("there is a program area named {string}")
  public void there_is_a_program_area_named(final String name) {
    this.mother.create(name);
  }

  @ParameterType(name = "programArea", value = "\"?([\\w ]*)\"?")
  public ProgramAreaIdentifier program_area(final String value) {
    return resolver.resolve(value).orElse(null);
  }

}
