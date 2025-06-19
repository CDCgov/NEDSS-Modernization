package gov.cdc.nbs.patient.demographics.gender;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class GenderDemographicEntrySteps {

  private final Active<GenderDemographic> input;

  GenderDemographicEntrySteps(final Active<GenderDemographic> input) {
    this.input = input;
  }

  @Given("I enter the gender demographics as of date {localDate}")
  public void i_enter_patient_gender_as_of(final LocalDate value) {
    this.input.active(new GenderDemographic(value));
  }

  @Given("I enter the gender demographics with the current gender of {sex}")
  public void i_enter_patient_current_gender(final String value) {
    this.input.active(current -> current.withCurrent(value));
  }

  @Given("I enter the gender demographics with the unknown reason of {sexUnknown}")
  public void i_enter_patient_gender_unknown_reason(final String value) {
    this.input.active(current -> current.withUnknownReason(value));
  }

  @Given("I enter the gender demographics with preferred gender of {transgender}")
  public void i_enter_patient_gender_transgender_information(final String value) {
    this.input.active(current -> current.withTransgenderInformation(value));
  }

  @Given("I enter the gender demographics with the additional gender {string}")
  public void i_enter_patient_gender_additional_gender(final String value) {
    this.input.active(current -> current.withAdditionalGender(value));
  }
}
