package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
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
    NewPatient newPatient =
        new NewPatient(new Administrative(RandomUtil.getRandomDateInPast(), "abc"), null, null, null, null, null);
    this.input.active(newPatient);
  }

  @Given("I am adding a new patient with names")
  public void i_am_adding_a_new_patient_with_names() {
    NewPatient newPatient = new NewPatient(null, Arrays.asList(new Name(
        Instant.parse("2023-05-15T10:00:00Z"),
        "L",
        "MR",
        faker.name().firstName(),
        faker.name().firstName(),
        faker.name().lastName(),
        faker.name().lastName(),
        faker.name().lastName(),
        "JR",
        "BS")), null, null, null, null);
    this.input.active(newPatient);
  }

  @Given("I am adding a new patient with addresses")
  public void i_am_adding_a_new_patient_with_addresses() {
    NewPatient newPatient = new NewPatient(null, null, Arrays.asList(new Address(
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
        RandomUtil.getRandomString())), null, null, null);
    this.input.active(newPatient);

  }

  @Given("I am adding a new patient with emails")
  public void i_am_adding_a_new_patient_with_emails() {
    NewPatient newPatient = new NewPatient(null, null, null, Arrays.asList(new Phone(
        RandomUtil.getRandomDateInPast(),
        "type-value",
        "use-value",
        null,
        null,
        null,
        "email",
        "url",
        "comment")), null, null);
    this.input.active(newPatient);
  }

  @Given("I am adding a new patient with phones")
  public void i_am_adding_a_new_patient_with_phones() {
    NewPatient newPatient = new NewPatient(null, null, null, Arrays.asList(new Phone(
        RandomUtil.getRandomDateInPast(),
        "type-value",
        "use-value",
        "country-code",
        "number",
        "extension",
        null,
        "url",
        "comment")), null, null);
    this.input.active(newPatient);

  }

  @Given("I am adding a new patient with races")
  public void i_am_adding_a_new_patient_with_races() {
    NewPatient newPatient = new NewPatient(null, null, null, null, Arrays.asList(new Race(
        RandomUtil.getRandomDateInPast(),
        "category-value",
        Arrays.asList("detail1", "detail2"))), null);
    this.input.active(newPatient);
  }

  @Given("I am adding a new patient with identifications")
  public void i_am_adding_a_new_patient_with_identifications() {
    NewPatient newPatient = new NewPatient(null, null, null, null, null, Arrays.asList(new Identification(
        RandomUtil.getRandomDateInPast(),
        "DL",
        "TX",
        "value")));
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
    Administrative expected = this.input.active().administrative();

    assertThat(actual)
        .returns(expected.asOf(), Person::getAsOfDateAdmin)
        .returns(expected.comment(), Person::getDescription);
  }

  @Then("the patient created has the entered names")
  public void the_patient_created_has_the_entered_names() {
    PersonName actual = patient.active().getNames().getFirst();
    Name expected = this.input.active().names().getFirst();

    assertThat(actual)
        .returns(expected.first(), PersonName::getFirstNm)
        .returns(expected.last(), PersonName::getLastNm);
  }

  @Then("the patient created has the entered addresses")
  public void the_patient_created_has_the_entered_addresses() {
    PostalLocator actual = patient.active().addresses().getFirst().getLocator();
    Address expected = this.input.active().addresses().getFirst();

    assertThat(actual)
        .returns(expected.city(), PostalLocator::getCityDescTxt)
        .returns(expected.state(), PostalLocator::getStateCd)
        .returns(expected.county(), PostalLocator::getCntyCd)
        .returns(expected.country(), PostalLocator::getCntryCd)
        .returns(expected.zipcode(), PostalLocator::getZipCd)
        .returns(expected.address1(), PostalLocator::getStreetAddr1)
        .returns(expected.address2(), PostalLocator::getStreetAddr2)
        .returns(expected.censusTract(), PostalLocator::getCensusTract);
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
    Phone expected = this.input.active().phoneEmails().getFirst();

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
    Phone expected = this.input.active().phoneEmails().getFirst();

    assertThat(actualElp)
        .returns(expected.asOf(), TeleEntityLocatorParticipation::getAsOfDate);
    assertThat(actualLocator)
        .returns(expected.phoneNumber(), TeleLocator::getPhoneNbrTxt)
        .returns(expected.countryCode(), TeleLocator::getCntryCd)
        .returns(expected.url(), TeleLocator::getUrlAddress)
        .returns(expected.extension(), TeleLocator::getExtensionTxt);
  }

  @Then("the patient created has the entered races")
  public void the_patient_created_has_the_entered_races() {
    PersonRace actual = patient.active().getRaces().getFirst();
    Race expected = this.input.active().races().getFirst();

    assertThat(actual)
        .returns(expected.asOf(), PersonRace::getAsOfDate)
        .returns(expected.race(), PersonRace::getRaceCategoryCd);
  }

  @Then("the patient created has the entered identifications")
  public void the_patient_created_has_the_entered_identifications() {
    EntityId actual = patient.active().identifications().getFirst();
    Identification expected = this.input.active().identifications().getFirst();

    assertThat(actual)
        .returns(expected.asOf(), EntityId::getAsOfDate)
        .returns(expected.type(), EntityId::getTypeCd)
        .returns(expected.authority(), EntityId::getAssigningAuthorityCd)
        .returns(expected.value(), EntityId::getRootExtensionTxt);
  }
}

