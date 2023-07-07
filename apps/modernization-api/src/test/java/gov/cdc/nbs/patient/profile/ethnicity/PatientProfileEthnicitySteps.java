package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.EthnicityInput;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.demographic.PatientEthnicity;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.TestAvailable;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.EntityManager;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientProfileEthnicitySteps {

    @Autowired
    TestActive<PatientInput> input;

    @Autowired
    TestActive<Person> patient;

    @Autowired
    EntityManager entityManager;

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    PatientEthnicityController controller;

    private EthnicityInput updates;

    @Before("@patient_update")
    public void reset() {
        this.updates = null;
    }

    @Given("the new patient's ethnicity is entered")
    public void the_new_patient_ethnicity_is_entered() {
        PatientInput active = this.input.active();

        active.setEthnicity(RandomUtil.getRandomString());
    }

    @Then("the new patient has the entered ethnicity")
    public void the_new_patient_has_the_entered_ethnicity() {
        Person actual = this.patient.active();
        PatientInput expected = this.input.active();

        assertThat(actual)
            .extracting(Person::getEthnicity)
            .returns(expected.getAsOf(), PatientEthnicity::asOf)
            .returns(expected.getEthnicity(), PatientEthnicity::ethnicGroup);
    }

    @When("a patient's ethnicity is changed")
    public void a_patient_ethnicity_is_changed() {

        PatientIdentifier patient = this.patients.one();

        this.updates = new EthnicityInput();
        this.updates.setPatient(patient.id());
        this.updates.setAsOf(RandomUtil.getRandomDateInPast());
        this.updates.setEthnicGroup(RandomUtil.getRandomString());
        this.updates.setUnknownReason(RandomUtil.getRandomString());

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
