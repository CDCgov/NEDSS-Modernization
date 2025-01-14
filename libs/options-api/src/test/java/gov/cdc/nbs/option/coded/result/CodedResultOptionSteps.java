package gov.cdc.nbs.option.coded.result;

import io.cucumber.java.en.Given;

public class CodedResultOptionSteps {

  private final CodedResultOptionMother mother;

  CodedResultOptionSteps(final CodedResultOptionMother mother) {
    this.mother = mother;
  }

  @Given("there is a SNOMED coded result for {string} {string}")
  public void there_is_a_SNOMED_coded_result(final String code, final String value) {
    mother.createSNOMED(code, value);
  }

  @Given("there is a local coded result for {string} {string}")
  public void there_is_a_local_coded_result(final String code, final String value) {
    mother.createLocal(code, value);
  }
}
