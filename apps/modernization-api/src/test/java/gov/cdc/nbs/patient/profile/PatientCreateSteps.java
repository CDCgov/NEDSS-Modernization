package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.profile.administrative.PatientAdministrative;
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
  Active<PatientAdministrative> input;

  @Autowired
  Available<PatientIdentifier> patients;

  @Before("@patient_create")
  public void reset() {
    this.input.reset();
    this.patients.reset();
  }

  @After("@patient_create")
  public void clean() {
    this.patient.maybeActive().ifPresent(repository::delete);
  }

  @Given("I am adding a new patient with comments")
  public void i_am_adding_a_new_patient() {
    PatientAdministrative newPatient =
        new PatientAdministrative(1L, 2L, (short) 3, RandomUtil.getRandomDateInPast(), "abc");
    this.input.active(newPatient);
  }

  @When("I send a create patient request")
  public void i_submit_the_patient() {
    try {
      PatientIdentifier created = controller.create(input.active());
      patients.available(created);

      repository.findById(created.id()).ifPresent(patient::active);

    } catch (AccessDeniedException e) {
    }
  }

  @Then("the patient created has the entered comment")
  public void the_patient_created_has_the_entered_comment() {
    Person actual = patient.active();
    PatientAdministrative expected = this.input.active();

    assertThat(actual)
        .returns(expected.asOf(), Person::getAsOfDateAdmin)
        .returns(expected.comment(), Person::getDescription);


  }

  @Then("I am unable to create a patient")
  public void i_am_unable_to_create_a_patient() {
    assertThatThrownBy(() -> controller.create(null))
        .isInstanceOf(AccessDeniedException.class);
  }
}
