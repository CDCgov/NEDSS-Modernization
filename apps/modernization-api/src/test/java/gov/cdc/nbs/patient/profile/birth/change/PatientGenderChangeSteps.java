package gov.cdc.nbs.patient.profile.birth.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.TestAvailable;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientGenderChangeSteps {

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    PatientBirthAndGenderController controller;

    @Autowired
    EntityManager entityManager;

    private UpdateBirthAndGender changes;

    @Before("@patient-profile-gender-change")
    public void reset() {
        this.changes = null;
    }

    @When("a patient's gender is changed")
    public void a_patient_gender_is_changed() {
        PatientIdentifier patient = this.patients.one();

        this.changes = new UpdateBirthAndGender(
                patient.id(),
                RandomUtil.getRandomDateInPast(),
                null,
                new UpdateBirthAndGender.Gender(
                        RandomUtil.maybeGender(),
                        RandomUtil.getRandomString(),
                        RandomUtil.getRandomString(),
                        RandomUtil.getRandomString()));

        controller.update(changes);
    }


    @Then("the patient has the changed gender")
    @Transactional
    public void the_patient_has_the_changed_gender() {
        PatientIdentifier patient = this.patients.one();

        Person actual = this.entityManager.find(Person.class, patient.id());

        assertThat(actual)
                .returns(changes.asOf(), Person::getAsOfDateSex)
                .returns(Gender.resolve(changes.gender().current()), Person::getCurrSexCd)
                .returns(changes.gender().unknownReason(), Person::getSexUnkReasonCd)
                .returns(changes.gender().preferred(), Person::getPreferredGenderCd)
                .returns(changes.gender().additional(), Person::getAdditionalGenderCd);
    }

    @Then("I am unable to change a patient's gender")
    public void i_am_unable_to_change_a_patient_gender() {
        assertThatThrownBy(() -> controller.update(changes))
                .isInstanceOf(AccessDeniedException.class);
    }
}
