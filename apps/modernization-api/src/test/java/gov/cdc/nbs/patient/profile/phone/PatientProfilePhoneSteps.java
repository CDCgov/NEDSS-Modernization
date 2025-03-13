package gov.cdc.nbs.patient.profile.phone;

import net.datafaker.Faker;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.TestPatient;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientProfilePhoneSteps {

  private final Faker faker = new Faker();

  final PatientMother mother;

  final Active<PatientIdentifier> activePatient;

  final Active<PatientInput> input;

  final TestPatient patient;

  PatientProfilePhoneSteps(
	  final PatientMother mother,
	  final Active<PatientIdentifier> activePatient,
	  final Active<PatientInput> input,
	  final TestPatient patient
  ) {
	this.mother = mother;
	this.activePatient = activePatient;
	this.input = input;
	this.patient = patient;
  }

  @Given("the patient has a phone")
  public void the_patient_has_a_phone() {
	activePatient.maybeActive().ifPresent(mother::withPhone);

  }

  @Given("the new patient's phone number is entered")
  public void the_new_patient_phone_number_is_entered() {

	PatientInput.PhoneNumber phoneNumber = new PatientInput.PhoneNumber();
	phoneNumber.setType("PH");
	phoneNumber.setUse("H");
	phoneNumber.setNumber(faker.phoneNumber().cellPhone());
	phoneNumber.setExtension(faker.phoneNumber().extension());

	this.input.active().getPhoneNumbers().add(phoneNumber);

  }

  @Then("the new patient has the entered phone number")
  @Transactional
  public void the_new_patient_has_the_entered_phone_number() {
	Person actual = patient.managed();

	Collection<TeleEntityLocatorParticipation> phoneNumbers = actual.phoneNumbers();

	if (!phoneNumbers.isEmpty()) {

	  assertThat(phoneNumbers)
		  .satisfiesExactlyInAnyOrder(
			  PatientCreateAssertions.containsPhoneNumbers(input.active().getPhoneNumbers()));

	}

  }

  @Given("the patient has the {phoneType} - {phoneUse} number of {string}")
  public void the_patient_has_the_phone(final String type, final String use, final String number) {
	this.activePatient.maybeActive()
		.ifPresent(found -> mother.withPhone(found, type, use, null, number, null));
  }

  @Given("the patient has the {phoneType} - {phoneUse} number of {string} as of {localDate}")
  public void the_patient_has_the_phone_as_of(final String type, final String use, final String number,
	  LocalDate asOf) {
	this.activePatient.maybeActive()
		.ifPresent(found -> mother.withPhone(found, type, use, null, number, null, asOf));
  }
}
