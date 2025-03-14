package gov.cdc.nbs.patient.profile.phone;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientEmailDemographicSteps {

  private final PatientMother mother;
  private final Active<PatientIdentifier> patient;

  PatientEmailDemographicSteps(
	  final PatientMother mother,
	  final Active<PatientIdentifier> patient
  ) {
	this.mother = mother;
	this.patient = patient;
  }

  @Given("the patient has an email address of {string}")
  public void the_patient_has_the_email_address(final String emailAddress) {
	this.patient.maybeActive().ifPresent(identifier -> this.mother.withEmail(identifier, emailAddress));
  }

  @Given("the patient has the {phoneType} - {phoneUse} email address of {string}")
  public void the_patient_has_email_address(
	  final String type,
	  final String use,
	  final String email
  ) {
	this.patient.maybeActive().ifPresent(
		identifier -> this.mother.withEmail(
			identifier,
			type,
			use,
			email
		)
	);
  }

  @Given("the patient has the {phoneType} - {phoneUse} email address of {string} as of {localDate}")
  public void the_patient_has_email_address(
	  final String type,
	  final String use,
	  final String email,
	  final LocalDate date
  ) {
	this.patient.maybeActive().ifPresent(
		identifier -> this.mother.withEmail(
			identifier,
			type,
			use,
			email,
			date
		)
	);
  }
}
