package gov.cdc.nbs.patient.demographics.gender;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientGenderDemographicSteps {

  private final Active<PatientIdentifier> active;
  private final PatientGenderApplier applier;

  PatientGenderDemographicSteps(
      final Active<PatientIdentifier> active,
      final PatientGenderApplier applier
  ) {
    this.active = active;
    this.applier = applier;
  }

  @Given("the patient's gender as of {localDate} is {sex}")
  public void hasGender(final LocalDate asOf, final String gender) {
    this.active.maybeActive().ifPresent(patient -> applier.withGender(patient, asOf, gender));
  }

  @Given("the patient has the gender {sex}")
  @Given("the patient's gender is {sex}")
  public void hasGender(final String gender) {
    this.active.maybeActive().ifPresent(patient -> applier.withGender(patient, gender));
  }

  @Given("the patient's current gender is unknown with the reason being {sexUnknown} as of {localDate}")
  public void unknown(final String reason, final LocalDate asOf) {
    this.active.maybeActive().ifPresent(patient -> applier.withUnknown(patient, reason, asOf));
  }

  @Given("the patient's current gender is unknown with the reason being {sexUnknown}")
  public void unknown(final String reason) {
    this.active.maybeActive().ifPresent(patient -> applier.withUnknown(patient, reason, LocalDate.now()));
  }

  @Given("the patient has the transgender of {transgender}")
  @Given("the patient has a preferred gender of {transgender}")
  public void hasTransgender(final String gender) {
    this.active.maybeActive().ifPresent(patient -> applier.withPreferredGender(patient, gender));
  }

  @Given("the patient has the {string} additional gender")
  public void hasAdditional(final String gender) {
    this.active.maybeActive().ifPresent(patient -> applier.withAdditionalGender(patient, gender));
  }
}
