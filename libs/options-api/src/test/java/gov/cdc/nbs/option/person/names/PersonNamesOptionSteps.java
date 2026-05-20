package gov.cdc.nbs.option.person.names;

import io.cucumber.java.en.Given;

public class PersonNamesOptionSteps {

  private final PersonNamesMother mother;

  PersonNamesOptionSteps(final PersonNamesMother mother) {
    this.mother = mother;
  }

  @Given("there is a {string} {string} person name")
  public void the_person_name_exists_in_the_value_set(
      final String firstName, final String lastName) {
    mother.create(firstName, lastName);
  }
}
