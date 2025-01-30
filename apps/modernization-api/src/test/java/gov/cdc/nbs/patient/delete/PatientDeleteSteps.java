package gov.cdc.nbs.patient.delete;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientDeleteSteps {

  private final PersonRepository repository;
  private final Available<PatientIdentifier> patients;
  private final PatientDeleteController controller;

  private PatientDeleteResult result;

  private AccessDeniedException accessDeniedException;

  PatientDeleteSteps(
      final PersonRepository repository,
      final Available<PatientIdentifier> patients,
      final PatientDeleteController controller
  ) {
    this.repository = repository;
    this.patients = patients;
    this.controller = controller;
  }

  @Before
  public void reset() {
    result = null;
    accessDeniedException = null;
  }

  @When("I delete the patient")
  public void i_delete_the_patient() {
    long identifier = this.patients.one().id();

    try {

      this.result = controller.delete(identifier);
    } catch (AccessDeniedException exception) {
      this.accessDeniedException = exception;
    }
  }

  @Then("the patient is deleted")
  public void the_patient_is_deleted() {
    assertThat(this.result)
        .isInstanceOf(PatientDeleteResult.PatientDeleteSuccessful.class);

    Person actual = repository.findById(this.result.patient())
        .orElseThrow();

    assertThat(actual)
        .extracting(Person::recordStatus)
        .returns("LOG_DEL", gov.cdc.nbs.audit.RecordStatus::status);


  }

  @Then("the patient is not deleted because of an association with an event")
  public void the_patient_is_not_deleted_because_of_an_association_with_an_event() {
    assertThat(this.result)
        .isInstanceOf(PatientDeleteResult.PatientDeleteFailed.class)
        .asInstanceOf(InstanceOfAssertFactories.type(PatientDeleteResult.PatientDeleteFailed.class))
        .satisfies(
            actual -> assertThat(actual.reason())
                .containsIgnoringCase("Cannot delete patient with Active Revisions"));

    Person actual = repository.findById(this.result.patient())
        .orElseThrow();

    assertThat(actual)
        .extracting(Person::recordStatus)
        .returns("ACTIVE", gov.cdc.nbs.audit.RecordStatus::status);
  }

  @Then("I am not allowed to delete the patient")
  public void i_am_not_allowed_to_delete_the_patient() {
    assertThat(accessDeniedException)
        .hasMessageContaining("Access Denied");
  }

  @When("I delete an unknown patient")
  public void i_delete_an_unknown_patient() {
    try {
      this.result = controller.delete(Long.MIN_VALUE);
    } catch (AccessDeniedException exception) {
      this.accessDeniedException = exception;
    }
  }

  @Then("there is no patient to delete")
  public void there_is_no_patient_to_delete() {
    assertThat(this.result)
        .isInstanceOf(PatientDeleteResult.PatientDeleteFailed.class)
        .asInstanceOf(InstanceOfAssertFactories.type(PatientDeleteResult.PatientDeleteFailed.class))
        .satisfies(
            actual -> assertThat(actual.reason())
                .containsIgnoringCase("Unable to find patient")
                .contains(String.valueOf(this.result.patient())));
  }

}
