package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.util.ArrayList;

public class EthnicityEntrySteps {

  private final Active<EthnicityDemographic> input;

  EthnicityEntrySteps(final Active<EthnicityDemographic> input) {
    this.input = input;
  }

  @Given("I am entering the ethnicity as of date {localDate}")
  public void i_am_entering_patient_ethnicity_as_of(final LocalDate asOf) {
    this.input.active(new EthnicityDemographic(asOf, null, null, new ArrayList<>()));
  }

  @Given("I enter the ethnicity unknown reason {ethnicityUnknownReason}")
  public void i_enter_the_patient_ethnicity_unknown_reason(final String unknownReason) {
    this.input.active(current -> current.withUnknownReason(unknownReason));
  }

  @Given("I enter the ethnicity ethnic group {ethnicity}")
  public void i_enter_the_patient_ethnicity_ethnic_group(final String ethnicGroup) {
    this.input.active(current -> current.withEthnicGroup(ethnicGroup));
  }

  @Given("I enter the ethnicity detailed {ethnicityDetail}")
  public void i_enter_the_patient_ethnicity_detailed(final String detail) {
    this.input.active(current -> current.withDetail(detail));
  }
}
