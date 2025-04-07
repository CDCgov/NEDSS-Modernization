package gov.cdc.nbs.patient.profile.phone;

import gov.cdc.nbs.data.LimitString;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.patient.TestPatient;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.datafaker.Faker;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientProfileEmailSteps {

  private final Faker faker = new Faker();

  private final Active<PatientInput> input;

  private final TestPatient patient;

  PatientProfileEmailSteps(
	  final Active<PatientInput> input,
	  final TestPatient patient
  ) {
	this.input = input;
	this.patient = patient;
  }

  @Given("the new patient's email address is entered")
  public void the_new_patient_email_address_is_entered() {
	this.input.active().getEmailAddresses().add(LimitString.toMaxLength(faker.internet().emailAddress(), 100));
  }

  @Then("the new patient has the entered email address")
  @Transactional
  public void the_new_patient_has_the_entered_email_address() {
	Person actual = patient.managed();

	Collection<TeleEntityLocatorParticipation> emails = actual.emailAddresses();

	if (!emails.isEmpty()) {

	  assertThat(emails)
		  .satisfiesExactlyInAnyOrder(
			  PatientCreateAssertions.containsEmailAddresses(input.active().getEmailAddresses()));

	}

  }
}
