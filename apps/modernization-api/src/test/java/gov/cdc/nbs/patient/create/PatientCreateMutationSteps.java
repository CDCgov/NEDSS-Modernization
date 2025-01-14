package gov.cdc.nbs.patient.create;

import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.entity.enums.RecordStatus;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PatientCreateMutationSteps {

  @Autowired
  BasicPatientCreateController controller;

  @Autowired
  PersonRepository repository;

  @Autowired
  Active<ActiveUser> activeUser;

  @Autowired
  Active<Person> patient;

  @Autowired
  Active<PatientInput> input;

  @Autowired
  Available<PatientIdentifier> patients;

  private AccessDeniedException accessDeniedException;


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
    newPatient.setAsOf(RandomUtil.getRandomDateInPast());
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

    assertThat(actual)
        .returns(activeUser.active().id(), Person::getAddUserId)
        .extracting(Person::getAddTime).isNotNull();

    assertThat(actual)
        .returns(activeUser.active().id(), Person::getLastChgUserId)
        .extracting(Person::getLastChgTime).isNotNull();

    assertThat(actual)
        .returns(RecordStatus.ACTIVE, Person::getRecordStatusCd)
        .extracting(Person::getRecordStatusTime).isNotNull();

  }

  @Then("I am not allowed to create a patient")
  public void i_am_not_allowed_to_create_a_patient() {
    assertNotNull(accessDeniedException);
  }


}
