package gov.cdc.nbs.patient.profile.names.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonNameId;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileNameChangeSteps {

  private final Faker faker = new Faker();

  private final Available<PatientIdentifier> patients;

  private final PatientNameChangeController controller;

  private final EntityManager entityManager;

  private NewPatientNameInput newRequest;
  private UpdatePatientNameInput updateRequest;
  private DeletePatientNameInput deleteRequest;

  PatientProfileNameChangeSteps(
      final Available<PatientIdentifier> patients,
      final PatientNameChangeController controller,
      final EntityManager entityManager
  ) {
    this.patients = patients;
    this.controller = controller;
    this.entityManager = entityManager;
  }

  @Before("@patient-profile-name-change")
  public void reset() {
    newRequest = null;
    updateRequest = null;
    deleteRequest = null;
  }

  @When("a patient's name is added")
  public void a_patient_name_is_added() {
    long patient = patients.one().id();

    newRequest = new NewPatientNameInput(
        patient,
        LocalDate.parse("2023-05-15"),
        "L",
        "MR",
        faker.name().firstName(),
        faker.name().firstName(),
        faker.name().lastName(),
        faker.name().lastName(),
        faker.name().lastName(),
        "JR",
        "BS"

    );

    controller.add(newRequest);
  }

  @Then("the patient has the new name")
  @Transactional
  public void the_patient_has_the_new_name() {
    PatientIdentifier patient = this.patients.one();

    Person person = this.entityManager.find(Person.class, patient.id());

    assertThat(person.getNames()).anySatisfy(
        actual -> assertThat(actual)
            .returns(LocalDate.parse("2023-05-15"), PersonName::getAsOfDate)
            .returns(newRequest.prefix(), PersonName::getNmPrefix)
            .returns(newRequest.first(), PersonName::getFirstNm)
            .returns(newRequest.middle(), PersonName::getMiddleNm)
            .returns(newRequest.secondMiddle(), PersonName::getMiddleNm2)
            .returns(newRequest.last(), PersonName::getLastNm)
            .returns(newRequest.secondLast(), PersonName::getLastNm2)
            .returns(Suffix.resolve(newRequest.suffix()), PersonName::getNmSuffix)
            .returns(newRequest.degree(), PersonName::getNmDegree)
            .returns(newRequest.type(), PersonName::getNmUseCd)
            .satisfies(
                name -> assertThat(name)
                    .extracting(PersonName::getId)
                    .returns(newRequest.patient(), PersonNameId::getPersonUid)
            )
    );
  }

  @When("a patient's name is changed")
  @Transactional
  public void a_patient_name_is_changed() {
    PersonName existing = this.entityManager.find(Person.class, patients.one().id())
        .getNames()
        .stream()
        .findFirst()
        .orElseThrow();

    updateRequest = new UpdatePatientNameInput(
        existing.getId().getPersonUid(),
        existing.getId().getPersonNameSeq(),
        LocalDate.parse("2023-05-15"),
        "L",
        "BRO",
        faker.name().firstName(),
        faker.name().firstName(),
        faker.name().lastName(),
        faker.name().lastName(),
        faker.name().lastName(),
        "ESQ",
        "CPA"
    );

    controller.update(updateRequest);
  }

  @Then("the patient has the expected name")
  @Transactional
  public void the_patient_has_the_expected_name() {
    PatientIdentifier patient = this.patients.one();

    Person person = this.entityManager.find(Person.class, patient.id());

    assertThat(person.getNames()).anySatisfy(
        actual -> assertThat(actual)
            .returns(LocalDate.parse("2023-05-15"), PersonName::getAsOfDate)
            .returns(updateRequest.prefix(), PersonName::getNmPrefix)
            .returns(updateRequest.first(), PersonName::getFirstNm)
            .returns(updateRequest.middle(), PersonName::getMiddleNm)
            .returns(updateRequest.secondMiddle(), PersonName::getMiddleNm2)
            .returns(updateRequest.last(), PersonName::getLastNm)
            .returns(updateRequest.secondLast(), PersonName::getLastNm2)
            .returns(Suffix.resolve(updateRequest.suffix()), PersonName::getNmSuffix)
            .returns(updateRequest.degree(), PersonName::getNmDegree)
            .returns(updateRequest.type(), PersonName::getNmUseCd)
            .satisfies(
                name -> assertThat(name)
                    .extracting(PersonName::getId)
                    .returns(updateRequest.patient(), PersonNameId::getPersonUid)
                    .returns((short) updateRequest.sequence(), PersonNameId::getPersonNameSeq)
            )
    );
  }

  @When("a patient's name is removed")
  @Transactional
  public void a_patient_name_is_removed() {

    Person patient = this.entityManager.find(Person.class, patients.one().id());

    this.deleteRequest = patient.getNames()
        .stream()
        .findFirst()
        .map(PersonName::getId)
        .map(id -> new DeletePatientNameInput(id.getPersonUid(), id.getPersonNameSeq()))
        .orElseThrow();

    this.controller.delete(this.deleteRequest);
  }

  @Then("the patient does not have the expected name")
  @Transactional
  public void the_patient_does_not_have_the_expected_name() {
    PatientIdentifier patient = this.patients.one();

    Person actual = this.entityManager.find(Person.class, patient.id());

    assertThat(actual.getNames())
        .noneSatisfy(
            name -> assertThat(name)
                .extracting(PersonName::getId)
                .returns(deleteRequest.patient(), PersonNameId::getPersonUid)
                .returns(deleteRequest.sequence(), PersonNameId::getPersonNameSeq)
        )
    ;
  }

  @Then("I am unable to add a patient's name")
  public void i_am_unable_to_add_a_patient_ethnicity() {
    assertThatThrownBy(() -> controller.add(newRequest))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Then("I am unable to change a patient's name")
  public void i_am_unable_to_change_a_patient_ethnicity() {
    assertThatThrownBy(() -> controller.update(updateRequest))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Then("I am unable to remove a patient's name")
  public void i_am_unable_to_remove_a_patient_ethnicity() {
    assertThatThrownBy(() -> controller.delete(deleteRequest))
        .isInstanceOf(AccessDeniedException.class);
  }
}
