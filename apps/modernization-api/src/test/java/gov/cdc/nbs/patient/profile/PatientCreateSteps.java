package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import net.datafaker.Faker;
import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;

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

  private AccessDeniedException accessDeniedException;
  private final Faker faker = new Faker(Locale.of("en-us"));

  @Before("@patient_profile_create")
  public void reset() {
    this.input.reset();
    this.patients.reset();
    accessDeniedException = null;
  }

  @After("@patient_profile_create")
  public void clean() {
    this.patient.maybeActive().ifPresent(repository::delete);
  }

  @Given("I am adding a new patient with comments")
  public void i_am_adding_a_new_patient() {
    NewPatient newPatient = new NewPatient(RandomUtil.getRandomDateInPast(), "abc", null, null, null);
    this.input.active(newPatient);
  }

  @Given("I am adding a new patient with names")
  public void i_am_adding_a_new_patient_with_names() {
    NewPatient newPatient = new NewPatient(null, null, Arrays.asList(new Name(
        Instant.parse("2023-05-15T10:00:00Z"),
        "L",
        "MR",
        faker.name().firstName(),
        faker.name().firstName(),
        faker.name().lastName(),
        faker.name().lastName(),
        faker.name().lastName(),
        "JR",
        "BS")), null, null);
    this.input.active(newPatient);
  }

  @Given("I am adding a new patient with addresses")
  public void i_am_adding_a_new_patient_with_addresses() {
    NewPatient newPatient = new NewPatient(null, null, null, Arrays.asList(new Address(
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
        RandomUtil.getRandomString())), null);
    this.input.active(newPatient);

  }

  @Given("I am adding a new patient with emails")
  public void i_am_adding_a_new_patient_with_emails() {
    NewPatient newPatient = new NewPatient(null, null, null, null, Arrays.asList(new Phone(
        RandomUtil.getRandomDateInPast(),
        "type-value",
        "use-value",
        "country-code",
        "number",
        "extension",
        null,
        "url",
        "comment")));
    this.input.active(newPatient);
  }

  @Given("I am adding a new patient with phones")
  public void i_am_adding_a_new_patient_with_phones() {
    NewPatient newPatient = new NewPatient(null, null, null, null, Arrays.asList(new Phone(
        RandomUtil.getRandomDateInPast(),
        "type-value",
        "use-value",
        null,
        null,
        null,
        "email",
        null,
        null)));
    this.input.active(newPatient);

  }

  @When("I send a create patient request")
  public void i_submit_the_patient() {
    try {
      PatientIdentifier created = controller.create(input.active());
      patients.available(created);

      repository.findById(created.id()).ifPresent(patient::active);

    } catch (AccessDeniedException e) {
      accessDeniedException = e;
    }
  }

  @Then("the patient created has the entered comment")
  public void the_patient_created_has_the_entered_comment() {
    Person actual = patient.active();
    NewPatient expected = this.input.active();

    assertThat(actual)
        .returns(expected.asOf(), Person::getAsOfDateAdmin)
        .returns(expected.comment(), Person::getDescription);
  }

  @Then("the patient created has the entered names")
  public void the_patient_created_has_the_entered_names() {
    Person actual = patient.active();
    NewPatient expected = this.input.active();

    assertThat(actual)
        .returns(expected.asOf(), Person::getAsOfDateAdmin);
  }

  @Then("the patient created has the entered addresses")
  public void the_patient_created_has_the_entered_addresses() {
    Person actual = patient.active();
    NewPatient expected = this.input.active();

    assertThat(actual)
        .returns(expected.comment(), Person::getDescription)
        .returns(expected.asOf(), Person::getAsOfDateAdmin);
  }

  @Then("I am unable to create a patient")
  public void i_am_unable_to_create_a_patient() {
    assertThatThrownBy(() -> controller.create(null))
        .isInstanceOf(AccessDeniedException.class);
    assertThat(accessDeniedException)
        .hasMessageContaining("Access Denied");
  }

  @Then("the patient created has the entered emails")
  public void the_patient_created_has_the_entered_emails() {
    TeleEntityLocatorParticipation actualElp = patient.active().phoneNumbers().getFirst();
    TeleLocator actualLocator = patient.active().phoneNumbers().getFirst().getLocator();
    Phone expected = this.input.active().phones().getFirst();

    assertThat(actualElp)
        .returns(expected.asOf(), TeleEntityLocatorParticipation::getAsOfDate);
    assertThat(actualLocator)
        .returns(expected.email(), TeleLocator::getEmailAddress);
  }

  @Then("the patient created has the entered phones")
  public void the_patient_created_has_the_entered_phones() {
    TeleEntityLocatorParticipation actualElp = patient.active().phoneNumbers().getFirst();
    TeleLocator actualLocator = patient.active().phoneNumbers().getFirst().getLocator();
    Phone expected = this.input.active().phones().getFirst();

    assertThat(actualElp)
        .returns(expected.asOf(), TeleEntityLocatorParticipation::getAsOfDate);
    assertThat(actualLocator)
        .returns(expected.number(), TeleLocator::getPhoneNbrTxt)
        .returns(expected.url(), TeleLocator::getUrlAddress)
        .returns(expected.extension(), TeleLocator::getExtensionTxt);
  }
}

