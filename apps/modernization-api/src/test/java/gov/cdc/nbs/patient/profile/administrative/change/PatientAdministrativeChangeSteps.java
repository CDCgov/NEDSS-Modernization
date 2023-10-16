package gov.cdc.nbs.patient.profile.administrative.change;

import com.github.javafaker.Faker;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Available;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientAdministrativeChangeSteps {

    private final Faker faker = new Faker();

    @Autowired
    Available<PatientIdentifier> patients;

    @Autowired
    PatientAdministrativeController controller;

    @Autowired
    EntityManager entityManager;

    private UpdatePatientAdministrative changes;

    @Before("@patient-profile-administrative-change")
    public void reset() {
        this.changes = null;
    }

    @When("a patient's administrative is changed")
    public void a_patient_administrative_is_changed() {
        PatientIdentifier patient = this.patients.one();

        this.changes = new UpdatePatientAdministrative(
            patient.id(),
            RandomUtil.getRandomDateInPast(),
            faker.lorem().paragraph()
        );

        controller.update(changes);
    }


    @Then("the patient has the changed administrative")
    @Transactional
    public void the_patient_has_the_changed_administrative() {
        PatientIdentifier patient = this.patients.one();

        Person actual = this.entityManager.find(Person.class, patient.id());

        assertThat(actual)
            .returns(changes.asOf(), Person::getAsOfDateAdmin)
            .returns(changes.comment(), Person::getDescription)
        ;
    }

    @Then("I am unable to change a patient's administrative")
    public void i_am_unable_to_change_a_patient_administrative() {
        assertThatThrownBy(() -> controller.update(changes))
            .isInstanceOf(AccessDeniedException.class);
    }
}
