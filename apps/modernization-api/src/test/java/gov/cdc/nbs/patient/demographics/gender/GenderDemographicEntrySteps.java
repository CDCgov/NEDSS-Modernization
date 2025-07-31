package gov.cdc.nbs.patient.demographics.gender;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.Clock;
import java.time.LocalDate;
import java.util.function.Supplier;

public class GenderDemographicEntrySteps {

  private final Clock clock;
  private final Active<GenderDemographic> input;

  GenderDemographicEntrySteps(final Clock clock, final Active<GenderDemographic> input) {
    this.clock = clock;
    this.input = input;
  }

  @Given("I enter the gender demographics as of date {localDate}")
  public void i_enter_patient_gender_as_of(final LocalDate value) {
    this.input.active(current -> current.withAsOf(value), initial());
  }

  private Supplier<GenderDemographic> initial() {
    return () -> new GenderDemographic(LocalDate.now(clock));
  }

  @Given("I enter the gender demographics with the current gender of {sex}")
  public void i_enter_patient_current_gender(final String value) {
    this.input.active(current -> current.withCurrent(value), initial());
  }

  @Given("I enter the gender demographics with the unknown reason of {sexUnknown}")
  public void i_enter_patient_gender_unknown_reason(final String value) {
    this.input.active(current -> current.withCurrent("U").withUnknownReason(value), initial());
  }

  @Given("I enter the gender demographics with the transgender of {transgender}")
  @Given("I enter the gender demographics with a preferred gender of {transgender}")
  public void i_enter_patient_gender_transgender_information(final String value) {
    this.input.active(current -> current.withTransgenderInformation(value), initial());
  }

  @Given("I enter the gender demographics with the additional gender {string}")
  public void i_enter_patient_gender_additional_gender(final String value) {
    this.input.active(current -> current.withAdditionalGender(value), initial());
  }
}
