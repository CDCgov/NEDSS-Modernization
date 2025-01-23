package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.address.AddressDemographic;
import gov.cdc.nbs.patient.profile.create.CreatedPatient;
import gov.cdc.nbs.patient.profile.create.NewPatient;
import gov.cdc.nbs.patient.profile.create.PatientCreateController;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Arrays;
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
  Active<PatientIdentifier> activePatient;

  private final Faker faker = new Faker(Locale.of("en-us"));

  @Before("@patient_profile_create")
  public void reset() {
    this.input.reset();
  }

  @After("@patient_profile_create")
  public void clean() {
    this.patient.maybeActive().ifPresent(repository::delete);
  }

  @Given("I am adding a new patient with addresses")
  public void i_am_adding_a_new_patient_with_addresses() {
    this.input.active(
        current -> current.withAddress(
            new AddressDemographic(
                RandomUtil.dateInPast(),
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
                RandomUtil.getRandomString())));
  }

  @Given("I am adding a new patient with emails")
  public void i_am_adding_a_new_patient_with_emails() {
    this.input.active(
        current -> current.withPhoneEmail(
            new NewPatient.Phone(
                RandomUtil.dateInPast(),
                "type-value",
                "use-value",
                null,
                null,
                null,
                "email",
                "url",
                "comment")));
  }

  @Given("I am adding a new patient with phones")
  public void i_am_adding_a_new_patient_with_phones() {
    this.input.active(
        current -> current.withPhoneEmail(
            new NewPatient.Phone(
                RandomUtil.dateInPast(),
                "type-value",
                "use-value",
                "country-code",
                "number",
                "extension",
                null,
                "url",
                "comment")));
  }

  @Given("I am adding a new patient with races")
  public void i_am_adding_a_new_patient_with_races() {
    this.input.active(
        current -> current.withRace(
            new NewPatient.Race(
                RandomUtil.dateInPast(),
                "category-value",
                Arrays.asList("detail1", "detail2"))));
  }

  @Given("I am adding a new patient with identifications")
  public void i_am_adding_a_new_patient_with_identifications() {
    this.input.active(
        current -> current.withIdentification(
            new NewPatient.Identification(
                RandomUtil.dateInPast(),
                "DL",
                "TX",
                "value")));
  }

  @When("I send a create patient request")
  public void i_submit_the_patient() {
    CreatedPatient created = controller.create(input.active());

    activePatient.active(new PatientIdentifier(created.id(), created.shortId(), created.local()));

    repository.findById(created.id()).ifPresent(patient::active);
  }

  @Then("the patient created has the entered addresses")
  public void the_patient_created_has_the_entered_addresses() {
    PostalLocator actual = patient.active().addresses().getFirst().getLocator();
    AddressDemographic expected = this.input.active().addresses().getFirst();

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

  @Then("the patient created has the entered emails")
  public void the_patient_created_has_the_entered_emails() {
    TeleEntityLocatorParticipation actualElp = patient.active().phoneNumbers().getFirst();
    TeleLocator actualLocator = patient.active().phoneNumbers().getFirst().getLocator();
    NewPatient.Phone expected = this.input.active().phoneEmails().getFirst();

    assertThat(actualElp)
        .returns(expected.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant(),
            TeleEntityLocatorParticipation::getAsOfDate);
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
        .returns(expected.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant(),
            TeleEntityLocatorParticipation::getAsOfDate);
    assertThat(actualLocator)
        .returns(expected.phoneNumber(), TeleLocator::getPhoneNbrTxt)
        .returns(expected.countryCode(), TeleLocator::getCntryCd)
        .returns(expected.url(), TeleLocator::getUrlAddress)
        .returns(expected.extension(), TeleLocator::getExtensionTxt);
  }

  @Then("the patient created has the entered races")
  public void the_patient_created_has_the_entered_races() {
    PersonRace actual = patient.active().getRaces().getFirst();
    NewPatient.Race expected = this.input.active().races().getFirst();

    assertThat(actual)
        .returns(expected.asOf(), PersonRace::getAsOfDate)
        .returns(expected.race(), PersonRace::getRaceCategoryCd);
  }

  @Then("the patient created has the entered identifications")
  public void the_patient_created_has_the_entered_identifications() {
    EntityId actual = patient.active().identifications().getFirst();
    NewPatient.Identification expected = this.input.active().identifications().getFirst();

    assertThat(actual)
        .returns(expected.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant(), EntityId::getAsOfDate)
        .returns(expected.type(), EntityId::getTypeCd)
        .returns(expected.issuer(), EntityId::getAssigningAuthorityCd)
        .returns(expected.id(), EntityId::getRootExtensionTxt);
  }
}
