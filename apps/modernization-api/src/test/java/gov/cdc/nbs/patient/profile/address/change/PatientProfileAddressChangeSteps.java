package gov.cdc.nbs.patient.profile.address.change;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileAddressChangeSteps {

  private final Faker faker = new Faker(Locale.of("en-us"));

  private final Available<PatientIdentifier> patients;

  private final PatientAddressChangeController controller;

  private final EntityManager entityManager;

  private final Active<NewPatientAddressInput> newRequest;

  private final Active<UpdatePatientAddressInput> updateRequest;

  private final Active<DeletePatientAddressInput> deleteRequest;

  PatientProfileAddressChangeSteps(
      final Available<PatientIdentifier> patients,
      final PatientAddressChangeController controller,
      final EntityManager entityManager,
      final Active<NewPatientAddressInput> newRequest,
      final Active<UpdatePatientAddressInput> updateRequest,
      final Active<DeletePatientAddressInput> deleteRequest
  ) {
    this.patients = patients;
    this.controller = controller;
    this.entityManager = entityManager;
    this.newRequest = newRequest;
    this.updateRequest = updateRequest;
    this.deleteRequest = deleteRequest;
  }

  @Before("@patient-profile-address-change")
  public void reset() {
    newRequest.reset();
    updateRequest.reset();
    deleteRequest.reset();
  }

  @When("a patient's address is added")
  public void a_patient_address_is_added() {
    long patient = patients.one().id();

    newRequest.active(
        new NewPatientAddressInput(
            patient,
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
            RandomUtil.getRandomString()));

    controller.add(newRequest.active());
  }

  @When("a patient's address is changed")
  @Transactional
  public void a_patient_address_is_changed() {
    PostalEntityLocatorParticipation existing = this.entityManager.find(Person.class, patients.one().id())
        .addresses()
        .stream()
        .findFirst()
        .orElseThrow();

    updateRequest.active(
        new UpdatePatientAddressInput(
            existing.getId().getEntityUid(),
            existing.getId().getLocatorUid(),
            RandomUtil.dateInPast(),
            "EC",
            "TMP",
            faker.address().streetAddress(),
            RandomUtil.getRandomString(),
            faker.address().city(),
            RandomUtil.getRandomStateCode(),
            RandomUtil.getRandomNumericString(15),
            RandomUtil.getRandomString(),
            RandomUtil.getRandomString(10),
            RandomUtil.country(),
            RandomUtil.getRandomString()));

    controller.update(updateRequest.active());
  }

  @When("a patient's address is removed")
  @Transactional
  public void a_patient_address_is_removed() {

    Person patient = this.entityManager.find(Person.class, patients.one().id());

    this.deleteRequest.active(
        patient.addresses()
            .stream()
            .findFirst()
            .map(PostalEntityLocatorParticipation::getId)
            .map(id -> new DeletePatientAddressInput(id.getEntityUid(), id.getLocatorUid()))
            .orElseThrow());

    this.controller.delete(this.deleteRequest.active());
  }

  @Then("the patient does not have the expected address")
  @Transactional
  public void the_patient_does_not_have_the_expected_address() {
    PatientIdentifier patient = this.patients.one();

    Person actual = this.entityManager.find(Person.class, patient.id());

    DeletePatientAddressInput request = deleteRequest.active();

    assertThat(actual.addresses())
        .noneSatisfy(
            address -> assertThat(address)
                .extracting(PostalEntityLocatorParticipation::getId)
                .returns(request.patient(), EntityLocatorParticipationId::getEntityUid)
                .returns(request.id(), EntityLocatorParticipationId::getLocatorUid));
  }

  @Then("I am unable to add a patient's address")
  public void i_am_unable_to_add_a_patient_ethnicity() {
    NewPatientAddressInput input = newRequest.maybeActive().orElse(null);
    assertThatThrownBy(() -> controller.add(input))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Then("I am unable to change a patient's address")
  public void i_am_unable_to_change_a_patient_ethnicity() {
    UpdatePatientAddressInput input = updateRequest.maybeActive().orElse(null);
    assertThatThrownBy(() -> controller.update(input))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Then("I am unable to remove a patient's address")
  public void i_am_unable_to_remove_a_patient_ethnicity() {
    DeletePatientAddressInput input = deleteRequest.maybeActive().orElse(null);

    assertThatThrownBy(() -> controller.delete(input))
        .isInstanceOf(AccessDeniedException.class);
  }
}
