package gov.cdc.nbs.patient.demographics.birth;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientBirthDemographicsSteps {

  private final Active<PatientIdentifier> active;
  private final PatientBirthDemographicApplier applier;


  PatientBirthDemographicsSteps(
      final Active<PatientIdentifier> active,
      final PatientBirthDemographicApplier applier
  ) {
    this.active = active;
    this.applier = applier;
  }

  @Given("the patient was born on {localDate}")
  public void bornOn(final LocalDate value) {
    this.active.maybeActive().ifPresent(
        patient -> applier.withBirthday(
            patient,
            value
        )
    );
  }

  @Given("the patient was born on {localDate} as of {localDate}")
  public void bornOn(final LocalDate value, final LocalDate asOf) {
    this.active.maybeActive().ifPresent(
        patient -> applier.withBirthday(
            patient,
            asOf,
            value
        )
    );
  }

  @Given("the patient was born {int} years ago")
  public void bornOn(final int value) {
    bornOn(value, LocalDate.now().minusDays(1));
  }

  @Given("the patient was born {int} years from {localDate}")
  public void bornOn(final int value, final LocalDate from) {
    this.active.maybeActive().ifPresent(
        patient -> applier.withBirthday(
            patient,
            from.minusYears(value)
        )
    );
  }

  @Given("the patient was born a {sex}")
  public void bornAs(final String value) {
    this.active.maybeActive().ifPresent(
        patient -> applier.withBornAs(
            patient,
            value
        )
    );
  }

  @Given("the patient was the {nth} to be born")
  public void bornNth(final int order) {
    this.active.maybeActive().ifPresent(
        patient -> applier.withBornNth(
            patient,
            order
        )
    );
  }

  @Given("the patient was a single birth")
  public void singleBirth() {
    this.active.maybeActive().ifPresent(
        applier::withSingleBirth
    );
  }

  @Given("the patient was born in the city of {string}")
  public void city(final String city) {
    this.active.maybeActive().ifPresent(
        patient -> applier.withBirthLocation(
            patient,
            city,
            null,
            null,
            null
        )
    );
  }

  @Given("the patient was born in the county of {county}")
  public void county(final String county) {
    this.active.maybeActive().ifPresent(
        patient -> applier.withBirthLocation(
            patient,
            null,
            county,
            null,
            null
        )
    );
  }

  @Given("the patient was born in the state of {state}")
  public void state(final String state) {
    this.active.maybeActive().ifPresent(
        patient -> applier.withBirthLocation(
            patient,
            null,
            null,
            state,
            null
        )
    );
  }

  @Given("the patient was born in the country of {country}")
  public void country(final String country) {
    this.active.maybeActive().ifPresent(
        patient -> applier.withBirthLocation(
            patient,
            null,
            null,
            null,
            country
        )
    );
  }
}
