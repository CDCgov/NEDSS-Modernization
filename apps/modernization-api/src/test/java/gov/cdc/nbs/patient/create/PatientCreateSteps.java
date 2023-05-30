package gov.cdc.nbs.patient.create;

import gov.cdc.nbs.authorization.TestActiveUser;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.TestAvailable;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PatientCreateSteps {

    @Autowired
    PatientCreationController controller;

    @Autowired
    PersonRepository repository;

    @Autowired
    TestActiveUser activeUser;

    @Autowired
    TestActive<Person> patient;

    @Autowired
    TestActive<PatientInput> input;

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    private AccessDeniedException accessDeniedException;


    @Before
    public void reset() {
        this.input.reset();
        this.patients.reset();
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


        Optional<Person> created = repository.findById(this.patients.one().id());

        Person actual = created.orElseThrow();

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
