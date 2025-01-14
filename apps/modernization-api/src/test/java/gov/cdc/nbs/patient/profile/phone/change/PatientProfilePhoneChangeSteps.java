package gov.cdc.nbs.patient.profile.phone.change;

import net.datafaker.Faker;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Available;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfilePhoneChangeSteps {

  private final Faker faker = new Faker(Locale.of("en-us"));

  @Autowired
  Available<PatientIdentifier> patients;

  @Autowired
  PatientPhoneChangeController controller;

  @Autowired
  EntityManager entityManager;

  private NewPatientPhoneInput newRequest;
  private UpdatePatientPhoneInput updateRequest;
  private DeletePatientPhoneInput deleteRequest;

  @Before("@patient-profile-phone-change")
  public void reset() {
    newRequest = null;
    updateRequest = null;
    deleteRequest = null;
  }

  @When("a patient's phone is added")
  public void a_patient_phone_is_added() {
    long patient = patients.one().id();

    newRequest = new NewPatientPhoneInput(
        patient,
        RandomUtil.getRandomDateInPast(),
        RandomUtil.oneFrom("AN", "BP", "CP", "NET", "FAX", "PH"),
        RandomUtil.oneFrom("SB", "EC", "H", "MC", "WP", "TMP"),
        RandomUtil.getRandomString(19),
        faker.phoneNumber().cellPhone(),
        faker.phoneNumber().extension(),
        faker.internet().emailAddress(),
        faker.internet().url(),
        RandomUtil.getRandomString(19));

    controller.add(newRequest);
  }

  @Then("the patient has the new phone")
  @Transactional
  public void the_patient_has_the_new_phone() {
    PatientIdentifier patient = this.patients.one();

    Person person = this.entityManager.find(Person.class, patient.id());

    assertThat(person.phones()).anySatisfy(
        actual -> assertThat(actual)
            .returns(newRequest.type(), TeleEntityLocatorParticipation::getCd)
            .returns(newRequest.use(), TeleEntityLocatorParticipation::getUseCd)
            .returns(newRequest.asOf(), TeleEntityLocatorParticipation::getAsOfDate)
            .returns(newRequest.comment(), TeleEntityLocatorParticipation::getLocatorDescTxt)
            .satisfies(
                phone -> assertThat(phone)
                    .extracting(TeleEntityLocatorParticipation::getId)
                    .returns(newRequest.patient(), EntityLocatorParticipationId::getEntityUid))
            .satisfies(
                phone -> assertThat(phone).extracting(TeleEntityLocatorParticipation::getLocator)
                    .returns(newRequest.countryCode(), TeleLocator::getCntryCd)
                    .returns(newRequest.number(), TeleLocator::getPhoneNbrTxt)
                    .returns(newRequest.extension(), TeleLocator::getExtensionTxt)
                    .returns(newRequest.email(), TeleLocator::getEmailAddress)
                    .returns(newRequest.url(), TeleLocator::getUrlAddress)));
  }

  @When("a patient's phone is changed")
  @Transactional
  public void a_patient_phone_is_changed() {
    TeleEntityLocatorParticipation existing = this.entityManager.find(Person.class, patients.one().id())
        .phones()
        .stream()
        .findFirst()
        .orElseThrow();

    updateRequest = new UpdatePatientPhoneInput(
        existing.getId().getEntityUid(),
        existing.getId().getLocatorUid(),
        RandomUtil.getRandomDateInPast(),
        RandomUtil.oneFrom("AN", "BP", "CP", "NET", "FAX", "PH"),
        RandomUtil.oneFrom("SB", "EC", "H", "MC", "WP", "TMP"),
        RandomUtil.getRandomString(15),
        faker.phoneNumber().cellPhone(),
        faker.phoneNumber().extension(),
        faker.internet().emailAddress(),
        faker.internet().url(),
        RandomUtil.getRandomString(15));

    controller.update(updateRequest);
  }

  @Then("the patient has the expected phone")
  @Transactional
  public void the_patient_has_the_expected_phone() {
    PatientIdentifier patient = this.patients.one();

    Person person = this.entityManager.find(Person.class, patient.id());

    assertThat(person.phones()).anySatisfy(
        actual -> assertThat(actual)
            .returns(updateRequest.type(), TeleEntityLocatorParticipation::getCd)
            .returns(updateRequest.use(), TeleEntityLocatorParticipation::getUseCd)
            .returns(updateRequest.asOf(), TeleEntityLocatorParticipation::getAsOfDate)
            .returns(updateRequest.comment(), TeleEntityLocatorParticipation::getLocatorDescTxt)
            .satisfies(
                phone -> assertThat(phone)
                    .extracting(TeleEntityLocatorParticipation::getId)
                    .returns(updateRequest.patient(), EntityLocatorParticipationId::getEntityUid))
            .satisfies(
                phone -> assertThat(phone).extracting(TeleEntityLocatorParticipation::getLocator)
                    .returns(updateRequest.countryCode(), TeleLocator::getCntryCd)
                    .returns(updateRequest.number(), TeleLocator::getPhoneNbrTxt)
                    .returns(updateRequest.extension(), TeleLocator::getExtensionTxt)
                    .returns(updateRequest.email(), TeleLocator::getEmailAddress)
                    .returns(updateRequest.url(), TeleLocator::getUrlAddress)));
  }

  @When("a patient's phone is removed")
  @Transactional
  public void a_patient_phone_is_removed() {

    Person patient = this.entityManager.find(Person.class, patients.one().id());

    this.deleteRequest = patient.phones()
        .stream()
        .findFirst()
        .map(TeleEntityLocatorParticipation::getId)
        .map(id -> new DeletePatientPhoneInput(id.getEntityUid(), id.getLocatorUid()))
        .orElseThrow();

    this.controller.delete(this.deleteRequest);
  }

  @Then("the patient does not have the expected phone")
  @Transactional
  public void the_patient_does_not_have_the_expected_phone() {
    PatientIdentifier patient = this.patients.one();

    Person actual = this.entityManager.find(Person.class, patient.id());

    assertThat(actual.phones())
        .noneSatisfy(
            phone -> assertThat(phone)
                .extracting(TeleEntityLocatorParticipation::getId)
                .returns(deleteRequest.patient(), EntityLocatorParticipationId::getEntityUid)
                .returns(deleteRequest.id(), EntityLocatorParticipationId::getLocatorUid));
  }

  @Then("I am unable to add a patient's phone")
  public void i_am_unable_to_add_a_patient_ethnicity() {
    assertThatThrownBy(() -> controller.add(newRequest))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Then("I am unable to change a patient's phone")
  public void i_am_unable_to_change_a_patient_ethnicity() {
    assertThatThrownBy(() -> controller.update(updateRequest))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Then("I am unable to remove a patient's phone")
  public void i_am_unable_to_remove_a_patient_ethnicity() {
    assertThatThrownBy(() -> controller.delete(deleteRequest))
        .isInstanceOf(AccessDeniedException.class);
  }
}
