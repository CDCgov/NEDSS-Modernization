package gov.cdc.nbs.option.language.primary;

import io.cucumber.java.en.Given;

public class PrimaryLanguageSteps {

  private final PrimaryLanguageMother mother;


  PrimaryLanguageSteps(final PrimaryLanguageMother mother) {
    this.mother = mother;
  }

  @Given("there is a {string} primary language")
  public void the_language_exists_in_the_value_set(final String name) {
    mother.create(name);
  }



}
