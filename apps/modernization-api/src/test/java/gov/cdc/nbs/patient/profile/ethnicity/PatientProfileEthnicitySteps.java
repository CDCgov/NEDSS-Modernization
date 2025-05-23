package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.EthnicityInput;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.demographic.PatientEthnicity;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientProfileEthnicitySteps {

  private final Active<PatientInput> input;

  private final Active<Person> activePatient;

  private final EntityManager entityManager;

  private final Available<PatientIdentifier> patients;

  private final PatientEthnicityController controller;

  private EthnicityInput updates;

  PatientProfileEthnicitySteps(
      final Active<PatientInput> input,
      final Active<Person> activePatient,
      final EntityManager entityManager,
      final Available<PatientIdentifier> patients,
      final PatientEthnicityController controller
  ) {
    this.input = input;
    this.activePatient = activePatient;
    this.entityManager = entityManager;
    this.patients = patients;
    this.controller = controller;
  }

  @Before("@patient_update")
  public void reset() {
    this.updates = null;
  }

  @When("a patient's ethnicity is changed")
  public void a_patient_ethnicity_is_changed() {

    PatientIdentifier patient = this.patients.one();

    this.updates = new EthnicityInput();
    this.updates.setPatient(patient.id());
    this.updates.setAsOf(RandomUtil.dateInPast());
    this.updates.setEthnicGroup(RandomUtil.getRandomString());
    this.updates.setUnknownReason(RandomUtil.getRandomString());

    controller.update(this.updates);
  }

  @When("a patient's {ethnicity} ethnicity is changed to specifically be {ethnicityDetail}")
  public void a_patient_ethnicity_is_changed_to_include(final String ethnicity, final String detail) {
    PatientIdentifier patient = this.patients.one();

    this.updates = new EthnicityInput();
    this.updates.setPatient(patient.id());
    this.updates.setAsOf(RandomUtil.dateInPast());
    this.updates.setEthnicGroup(ethnicity);
    this.updates.setDetailed(List.of(detail));

    controller.update(this.updates);
  }

  @Then("the patient has the changed ethnicity")
  @Transactional
  public void the_patient_has_the_changed_ethnicity() {
    PatientIdentifier patient = this.patients.one();

    Person actual = this.entityManager.find(Person.class, patient.id());

    assertThat(actual)
        .extracting(Person::getEthnicity)
        .returns(updates.getAsOf(), PatientEthnicity::asOf)
        .returns(updates.getEthnicGroup(), PatientEthnicity::ethnicGroup)
        .returns(updates.getUnknownReason(), PatientEthnicity::unknownReason)
    ;
  }

  @Then("I am unable to change a patient's ethnicity")
  public void i_am_unable_to_change_a_patient_ethnicity() {
    EthnicityInput changes = new EthnicityInput();

    assertThatThrownBy(() -> controller.update(changes))
        .isInstanceOf(AccessDeniedException.class);
  }
}
