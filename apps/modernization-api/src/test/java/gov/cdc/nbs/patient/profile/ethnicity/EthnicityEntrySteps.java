package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import java.util.List;
import java.time.Instant;

public class EthnicityEntrySteps {

  private final Active<EthnicityDemographic> input;

  EthnicityEntrySteps(final Active<EthnicityDemographic> input) {
    this.input = input;
  }

  @Given("I am entering the ethnicity as of date {date}")
  public void i_am_entering_patient_ethnicity_as_of(final Instant asOf) {
    this.input.active(new EthnicityDemographic(asOf, null, null, null));
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
  public void i_enter_the_patient_ethnicity_detailed(final List<String> detailed) {
    this.input.active(current -> current.withDetailed(detailed));
  }
}
