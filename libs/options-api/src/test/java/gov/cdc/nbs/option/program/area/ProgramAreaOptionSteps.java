package gov.cdc.nbs.option.program.area;

import io.cucumber.java.en.Given;

public class ProgramAreaOptionSteps {

  private final ProgramAreaMother mother;


  ProgramAreaOptionSteps(final ProgramAreaMother mother) {
    this.mother = mother;
  }

  @Given("there is a {string} program area")
  public void the_program_area_exists_in_the_value_set(final String name) {
    mother.create(name);
  }

}
