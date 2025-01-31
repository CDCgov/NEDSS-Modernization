package gov.cdc.nbs.patient.profile.administrative.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientAdministrativeChangeSteps {


  private final Active<PatientIdentifier> patients;
  private final PatientAdministrativeController controller;
  private final EntityManager entityManager;
  private final Active<UpdatePatientAdministrative> activeChange;
  private final Faker faker;

  PatientAdministrativeChangeSteps(
      final Active<PatientIdentifier> patients,
      final PatientAdministrativeController controller,
      final EntityManager entityManager
  ) {
    this.patients = patients;
    this.controller = controller;
    this.entityManager = entityManager;
    this.activeChange = new Active<>();
    this.faker = new Faker();
  }

  @Before("@patient-profile-administrative-change")
  public void reset() {
    this.activeChange.reset();
  }

  @When("a patient's administrative is changed")
  public void a_patient_administrative_is_changed() {
    PatientIdentifier patient = this.patients.active();

    this.activeChange.active(
        new UpdatePatientAdministrative(
            patient.id(),
            RandomUtil.dateInPast(),
            faker.lorem().paragraph()
        )
    );


    this.activeChange.maybeActive().ifPresent(controller::update);
  }

  @Then("the patient has the changed administrative")
  @Transactional
  public void the_patient_has_the_changed_administrative() {
    PatientIdentifier patient = this.patients.active();

    UpdatePatientAdministrative changes = this.activeChange.active();

    Person actual = this.entityManager.find(Person.class, patient.id());

    assertThat(actual)
        .returns(changes.asOf(), Person::getAsOfDateAdmin)
        .returns(changes.comment(), Person::getDescription);
  }

  @Then("I am unable to change a patient's administrative")
  public void i_am_unable_to_change_a_patient_administrative() {
    assertThatThrownBy(() -> controller.update(null))
        .isInstanceOf(AccessDeniedException.class);
  }
}
