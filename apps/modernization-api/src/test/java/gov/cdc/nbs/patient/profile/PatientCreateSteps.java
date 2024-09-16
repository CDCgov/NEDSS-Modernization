package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.create.NewPatient;
import gov.cdc.nbs.patient.profile.create.PatientCreateController;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class PatientCreateSteps {
  @Autowired
  PatientCreateController controller;

  @Autowired
  PersonRepository repository;

  @Autowired
  Active<ActiveUser> activeUser;

  @Autowired
  Active<Person> patient;

  @Autowired
  Active<NewPatient> input;

  @Autowired
  Available<PatientIdentifier> patients;

  private final Faker faker = new Faker(Locale.of("en-us"));

  @Before("@patient_profile_create")
  public void reset() {
    this.input.reset();
    this.patients.reset();
  }

  @After("@patient_profile_create")
  public void clean() {
    this.patient.maybeActive().ifPresent(repository::delete);
  }

  @Given("I am adding a new patient with addresses")
  public void i_am_adding_a_new_patient_with_addresses() {
    NewPatient newPatient = new NewPatient(
        null,
        Collections.emptyList(),
        List.of(
            new NewPatient.Address(
                RandomUtil.getRandomDateInPast(),
                "H",
                "H",
                faker.address().streetAddress(),
                RandomUtil.getRandomString(),
                faker.address().city(),
                RandomUtil.getRandomStateCode(),
                RandomUtil.getRandomNumericString(15),
                RandomUtil.getRandomString(),
                RandomUtil.getRandomString(10),
                RandomUtil.country(),
                RandomUtil.getRandomString()
            )
        ), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    this.input.active(newPatient);

  }

  @Given("I am adding a new patient with emails")
  public void i_am_adding_a_new_patient_with_emails() {
    NewPatient newPatient = new NewPatient(
        null,
        Collections.emptyList(),
        Collections.emptyList(),
        List.of(
            new NewPatient.Phone(
                RandomUtil.getRandomDateInPast(),
                "type-value",
                "use-value",
                null,
                null,
                null,
                "email",
                "url",
                "comment"
            )
        ),
        Collections.emptyList(),
        Collections.emptyList()
    );
    this.input.active(newPatient);
  }

  @Given("I am adding a new patient with phones")
  public void i_am_adding_a_new_patient_with_phones() {
    NewPatient newPatient = new NewPatient(
        null,
        Collections.emptyList(),
        Collections.emptyList(),
        List.of(
            new NewPatient.Phone(
                RandomUtil.getRandomDateInPast(),
                "type-value",
                "use-value",
                "country-code",
                "number",
                "extension",
                null,
                "url",
                "comment"
            )
        ),
        Collections.emptyList(),
        Collections.emptyList()
    );
    this.input.active(newPatient);

  }

  @When("I send a create patient request")
  public void i_submit_the_patient() {
    PatientIdentifier created = controller.create(input.active());
    patients.available(created);

    repository.findById(created.id()).ifPresent(patient::active);
  }

  @Then("the patient created has the entered emails")
  public void the_patient_created_has_the_entered_emails() {
    TeleEntityLocatorParticipation actualElp = patient.active().phoneNumbers().getFirst();
    TeleLocator actualLocator = patient.active().phoneNumbers().getFirst().getLocator();
    NewPatient.Phone expected = this.input.active().phoneEmails().getFirst();

    assertThat(actualElp)
        .returns(expected.asOf(), TeleEntityLocatorParticipation::getAsOfDate);
    assertThat(actualLocator)
        .returns(expected.url(), TeleLocator::getUrlAddress)
        .returns(expected.email(), TeleLocator::getEmailAddress);
  }

  @Then("the patient created has the entered phones")
  public void the_patient_created_has_the_entered_phones() {
    TeleEntityLocatorParticipation actualElp = patient.active().phoneNumbers().getFirst();
    TeleLocator actualLocator = patient.active().phoneNumbers().getFirst().getLocator();
    NewPatient.Phone expected = this.input.active().phoneEmails().getFirst();

    assertThat(actualElp)
        .returns(expected.asOf(), TeleEntityLocatorParticipation::getAsOfDate);
    assertThat(actualLocator)
        .returns(expected.number(), TeleLocator::getPhoneNbrTxt)
        .returns(expected.countryCode(), TeleLocator::getCntryCd)
        .returns(expected.url(), TeleLocator::getUrlAddress)
        .returns(expected.extension(), TeleLocator::getExtensionTxt);
  }
}

