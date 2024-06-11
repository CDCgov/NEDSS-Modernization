package gov.cdc.nbs.option.resultedtest.autocomplete;

import io.cucumber.java.en.Given;

public class ResultedTestOptionSteps {

  private final ResultedTestOptionMother mother;

  ResultedTestOptionSteps(final ResultedTestOptionMother mother) {
    this.mother = mother;
  }

  @Given("there is a LIONC resulted test for {string} {string}")
  public void there_is_a_lionc_resulted_test(final String code, final String value) {
    mother.createLoincResultedTest(code, value);
  }

  @Given("there is a local resulted test for {string} {string}")
  public void there_is_a_local_resulted_test(final String code, final String value) {
    mother.createLocalResultedTest(code, value);
  }
}
