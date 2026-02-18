package gov.cdc.nbs.patient.demographics.ethnicity;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import java.time.Clock;
import java.time.LocalDate;
import java.util.function.Supplier;

public class EthnicityDemographicEntrySteps {

  private final Clock clock;
  private final Active<EthnicityDemographic> active;

  EthnicityDemographicEntrySteps(final Clock clock, final Active<EthnicityDemographic> active) {
    this.clock = clock;
    this.active = active;
  }

  private Supplier<EthnicityDemographic> initial() {
    return () -> new EthnicityDemographic(LocalDate.now(clock));
  }

  @Given("I am entering the {ethnicity} ethnicity as of {localDate}")
  public void entering(final String ethnicity, final LocalDate asOf) {
    this.active.active(current -> current.withAsOf(asOf).withEthnicGroup(ethnicity), initial());
  }

  @Given("I am entering the Spanish origin {ethnicityDetail}")
  @Given("I am entering the detailed ethnicity {ethnicityDetail}")
  public void enteringDetail(final String detail) {
    this.active.active(current -> current.withDetail(detail), initial());
  }

  @Given("I am entering the ethnicity as of date {localDate}")
  public void i_am_entering_patient_ethnicity_as_of(final LocalDate asOf) {
    this.active.active(current -> current.withAsOf(asOf), initial());
  }

  @Given("I enter the ethnicity unknown reason {ethnicityUnknownReason}")
  public void i_enter_the_patient_ethnicity_unknown_reason(final String unknownReason) {
    this.active.active(current -> current.withUnknownReason(unknownReason), initial());
  }

  @Given("I enter the ethnicity ethnic group {ethnicity}")
  public void i_enter_the_patient_ethnicity_ethnic_group(final String ethnicGroup) {
    this.active.active(current -> current.withEthnicGroup(ethnicGroup), initial());
  }

  @Given("I enter the ethnicity detailed {ethnicityDetail}")
  public void i_enter_the_patient_ethnicity_detailed(final String detail) {
    this.active.active(current -> current.withDetail(detail), initial());
  }
}
