package gov.cdc.nbs.patient.demographics.phone;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientPhoneDemographicSteps {

  private final PatientMother mother;
  private final Active<PatientIdentifier> patient;

  PatientPhoneDemographicSteps(
	  final PatientMother mother,
	  final Active<PatientIdentifier> patient
  ) {
	this.mother = mother;
	this.patient = patient;
  }

	@Given("the patient has a phone")
	public void the_patient_has_a_phone() {
		patient.maybeActive().ifPresent(mother::withPhone);

	}

	@Given("the patient has the {phoneType} - {phoneUse} number of {string}")
	public void the_patient_has_the_phone(final String type, final String use, final String number) {
		this.patient.maybeActive()
				.ifPresent(found -> mother.withPhone(found, type, use, null, number, null));
	}

	@Given("the patient has the {phoneType} - {phoneUse} number of {string} as of {localDate}")
	public void the_patient_has_the_phone_as_of(final String type, final String use, final String number,
			LocalDate asOf) {
		this.patient.maybeActive()
				.ifPresent(found -> mother.withPhone(found, type, use, null, number, null, asOf));
	}

  @Given("the patient has the {phoneType} - {phoneUse} phone number of {string} {string} - {string} as of {localDate}")
  public void the_patient_has_phone_number(
	  final String type,
	  final String use,
	  final String countryCode,
	  final String phone,
	  final String extension,
	  final LocalDate localDate
  ) {
	this.patient.maybeActive().ifPresent(
		identifier -> this.mother.withPhone(
			identifier,
			type,
			use,
			countryCode,
			phone,
			extension,
			localDate
		)
	);
  }
}
