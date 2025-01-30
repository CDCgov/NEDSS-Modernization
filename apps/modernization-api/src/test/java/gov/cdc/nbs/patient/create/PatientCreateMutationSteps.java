package gov.cdc.nbs.patient.create;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PatientCreateMutationSteps {

  private final BasicPatientCreateController controller;

  private final PersonRepository repository;

  private final Active<Person> patient;

  private final Active<PatientInput> input;

  private final Available<PatientIdentifier> patients;

  private AccessDeniedException accessDeniedException;

  PatientCreateMutationSteps(
      final BasicPatientCreateController controller,
      final PersonRepository repository,
      final Active<Person> patient,
      final Active<PatientInput> input,
      final Available<PatientIdentifier> patients
  ) {
    this.controller = controller;
    this.repository = repository;
    this.patient = patient;
    this.input = input;
    this.patients = patients;
  }

  @Before("@patient_create")
  public void reset() {
    this.input.reset();
    this.patients.reset();
  }

  @After("@patient_create")
  public void clean() {
    //  patient creator uses the IdGeneratorService to resolve patient ids, these id's are not cleaned by
    //  the TestPatientCleaner.  Ideally, the created patient would stick around, however the search tests can fail
    //  when extra patients exist in the database.
    this.patient.maybeActive().ifPresent(repository::delete);
  }

  @Given("I am adding a new patient")
  public void i_am_adding_a_new_patient() {
    PatientInput newPatient = new PatientInput();
    newPatient.setAsOf(RandomUtil.dateInPast());
    this.input.active(newPatient);
  }


  @When("I submit the patient")
  public void i_submit_the_patient() {
    try {
      PatientIdentifier created = controller.create(input.active());
      patients.available(created);

      repository.findById(created.id()).ifPresent(patient::active);

    } catch (AccessDeniedException e) {
      accessDeniedException = e;
    }
  }

  @Then("the patient is created")
  public void the_patient_is_created() {

    Person actual = patient.active();

    assertThat(actual).isNotNull();

  }

  @Then("I am not allowed to create a patient")
  public void i_am_not_allowed_to_create_a_patient() {
    assertNotNull(accessDeniedException);
  }


}
