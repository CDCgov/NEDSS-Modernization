package gov.cdc.nbs.patient.profile.general.change;

import com.github.javafaker.Faker;
import gov.cdc.nbs.entity.odse.Person;
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
public class PatientGeneralInformationChangeSteps {

    private final Faker faker = new Faker();

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    PatientGeneralInformationController controller;

    @Autowired
    EntityManager entityManager;

    private UpdateGeneralInformation changes;

    @Before("@patient-profile-general-information-change")
    public void reset() {
        this.changes = null;
    }

    @When("a patient's general information is changed")
    public void a_patient_general_information_is_changed() {
        PatientIdentifier patient = this.patients.one();

        this.changes = new UpdateGeneralInformation(
                patient.id(),
                RandomUtil.getRandomDateInPast(),
                RandomUtil.maybeOneFrom("A", "B", "C", "D", "E", "F"),
                faker.name().lastName(),
                RandomUtil.getRandomInt(25),
                RandomUtil.getRandomInt(25),
                RandomUtil.maybeOneFrom("11", "21", "22", "23", "24", "42"),
                RandomUtil.maybeOneFrom("0", "1", "10", "11", "12", "13"),
                RandomUtil.maybeOneFrom("AAR", "ABK", "ACE", "ACH", "ADA", "ady"),
                RandomUtil.maybeOneFrom("Y", "N", "UNK"),
                RandomUtil.getRandomString());

        controller.update(changes);
    }


    @Then("the patient has the changed general information")
    @Transactional
    public void the_patient_has_the_changed_general_information() {
        PatientIdentifier patient = this.patients.one();

        Person actual = this.entityManager.find(Person.class, patient.id());

        assertThat(actual)
                .returns(changes.asOf(), Person::getAsOfDateGeneral)
                .returns(changes.maritalStatus(), Person::getMaritalStatusCd)
                .returns(changes.maternalMaidenName(), Person::getMothersMaidenNm)
                .returns(changes.adultsInHouse().shortValue(), Person::getAdultsInHouseNbr)
                .returns(changes.childrenInHouse().shortValue(), Person::getChildrenInHouseNbr)
                .returns(changes.occupation(), Person::getOccupationCd)
                .returns(changes.educationLevel(), Person::getEducationLevelCd)
                .returns(changes.primaryLanguage(), Person::getPrimLangCd)
                .returns(changes.speaksEnglish(), Person::getSpeaksEnglishCd)
                .returns(changes.stateHIVCase(), Person::getEharsId);
    }

    @Then("I am unable to change a patient's general information")
    public void i_am_unable_to_change_a_patient_general_information() {
        assertThatThrownBy(() -> controller.update(changes))
                .isInstanceOf(AccessDeniedException.class);
    }
}
