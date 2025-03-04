package gov.cdc.nbs.patient.profile.address;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientAddressDemographicsSteps {

  private final PatientMother mother;
  private final Active<PatientIdentifier> patient;

  PatientAddressDemographicsSteps(
      final PatientMother mother,
      final Active<PatientIdentifier> patient
  ) {
    this.mother = mother;
    this.patient = patient;
  }

  //  @Given("the patient has a {addressType} - {addressUse} address at {string} {string} {string} as of {localDate}")
  //  public void the_patient_has_an_address(String type, String use, String address, String city, String zip,
  //      LocalDate date) {
  //    PatientIdentifier identifier = patient.active();
  //
  //    mother.withAddress(
  //        identifier,
  //        type,
  //        use,
  //        address,
  //        city,
  //        null,
  //        null,
  //        zip,
  //        date
  //
  //    );
  //  }
}
