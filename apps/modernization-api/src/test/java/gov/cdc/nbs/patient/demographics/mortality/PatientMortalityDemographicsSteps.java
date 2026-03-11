package gov.cdc.nbs.patient.demographics.mortality;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import java.time.LocalDate;

public class PatientMortalityDemographicsSteps {

  private final Active<PatientIdentifier> active;
  private final PatientMortalityDemographicApplier applier;

  PatientMortalityDemographicsSteps(
      final Active<PatientIdentifier> active, final PatientMortalityDemographicApplier applier) {
    this.active = active;
    this.applier = applier;
  }

  @Given("the patient died on {localDate}")
  public void deceasedOn(final LocalDate value) {
    this.active.maybeActive().ifPresent(patient -> applier.withDeceasedOn(patient, value));
  }

  @Given("the patient died on {localDate} as of {localDate}")
  public void deceasedOn(final LocalDate value, final LocalDate asOf) {
    this.active.maybeActive().ifPresent(patient -> applier.withDeceasedOn(patient, asOf, value));
  }

  @Given("the patient {indicator} deceased")
  public void deceased(final String value) {
    this.active.maybeActive().ifPresent(patient -> applier.withDeceased(patient, value));
  }

  @Given("the patient died in the city of {string}")
  public void city(final String city) {
    this.active
        .maybeActive()
        .ifPresent(patient -> applier.withLocation(patient, city, null, null, null));
  }

  @Given("the patient died in the county of {county}")
  public void county(final String county) {
    this.active
        .maybeActive()
        .ifPresent(patient -> applier.withLocation(patient, null, county, null, null));
  }

  @Given("the patient died in the state of {state}")
  public void state(final String state) {
    this.active
        .maybeActive()
        .ifPresent(patient -> applier.withLocation(patient, null, null, state, null));
  }

  @Given("the patient died in the country of {country}")
  public void country(final String country) {
    this.active
        .maybeActive()
        .ifPresent(patient -> applier.withLocation(patient, null, null, null, country));
  }
}
