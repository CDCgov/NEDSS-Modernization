package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.patient.profile.administrative.Administrative;
import gov.cdc.nbs.patient.profile.birth.BirthDemographic;
import gov.cdc.nbs.patient.profile.names.NameDemographic;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class PatientCreateEntrySteps {

  private final Active<Administrative> activeAdministrative;
  private final Active<BirthDemographic> activeBirthDemographic;
  private final Active<NameDemographic> activeName;
  private final Active<NewPatient> input;

  public PatientCreateEntrySteps(
      final Active<Administrative> activeAdministrative,
      final Active<BirthDemographic> activeBirthDemographic,
      final Active<NameDemographic> activeName,
      final Active<NewPatient> input
  ) {
    this.activeAdministrative = activeAdministrative;
    this.activeBirthDemographic = activeBirthDemographic;
    this.activeName = activeName;
    this.input = input;
  }

  @Given("the administrative is included in the extended patient data")
  public void the_administrative_is_included_in_the_extended_patient_data() {
    this.activeAdministrative.maybeActive()
        .ifPresent(administrative -> this.input.active(current -> current.withAdministrative(administrative)));
  }

  @Given("the name is included with the extended patient data")
  public void i_add_the_current_name() {
    this.activeName.maybeActive().ifPresent(
        name -> this.input.active(current -> current.withName(name))
    );
  }

  @Given("the birth demographics are included in the extended patient data")
  public void the_birth_demographics_are_included_in_the_extended_patient_data() {
    this.activeBirthDemographic.maybeActive()
        .ifPresent(demographic -> this.input.active(current -> current.withBirth(demographic)));
  }
}
