package gov.cdc.nbs.patient.demographics.ethnicity;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.util.ArrayList;

public class EthnicityDemographicEntrySteps {

  private final Active<EthnicityDemographic> active;

  EthnicityDemographicEntrySteps(final Active<EthnicityDemographic> active) {
    this.active = active;
  }

  @Given("I am entering the {ethnicity} ethnicity as of {localDate}")
  public void entering(
      final String ethnicity,
      final LocalDate asOf
  ) {
    this.active.active(new EthnicityDemographic(asOf, ethnicity, null, null));
  }

  @Given("I am entering the Spanish origin {ethnicityDetail}")
  @Given("I am entering the detailed ethnicity {ethnicityDetail}")
  public void enteringDetail(final String detail) {
    this.active.active(current -> current.withDetail(detail));
  }

  //

  @Given("I am entering the ethnicity as of date {localDate}")
  public void i_am_entering_patient_ethnicity_as_of(final LocalDate asOf) {
    this.active.active(new EthnicityDemographic(asOf, null, null, new ArrayList<>()));
  }

  @Given("I enter the ethnicity unknown reason {ethnicityUnknownReason}")
  public void i_enter_the_patient_ethnicity_unknown_reason(final String unknownReason) {
    this.active.active(current -> current.withUnknownReason(unknownReason));
  }

  @Given("I enter the ethnicity ethnic group {ethnicity}")
  public void i_enter_the_patient_ethnicity_ethnic_group(final String ethnicGroup) {
    this.active.active(current -> current.withEthnicGroup(ethnicGroup));
  }

  @Given("I enter the ethnicity detailed {ethnicityDetail}")
  public void i_enter_the_patient_ethnicity_detailed(final String detail) {
    this.active.active(current -> current.withDetail(detail));
  }
}
