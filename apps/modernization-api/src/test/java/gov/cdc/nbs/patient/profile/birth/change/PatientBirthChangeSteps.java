package gov.cdc.nbs.patient.profile.birth.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientBirthChangeSteps {

    private final Faker faker = new Faker();

    @Autowired
    Available<PatientIdentifier> patients;

    @Autowired
    PatientBirthAndGenderController controller;

    @Autowired
    EntityManager entityManager;

    private UpdateBirthAndGender changes;

    @Before("@patient-profile-birth-change")
    public void reset() {
        this.changes = null;
    }

    @When("a patient's birth is changed")
    public void a_patient_birth_is_changed() {
        PatientIdentifier patient = this.patients.one();

        this.changes = new UpdateBirthAndGender(
                patient.id(),
                RandomUtil.getRandomDateInPast(),
                new UpdateBirthAndGender.Birth(
                        RandomUtil.dateInPast(),
                        RandomUtil.maybeGender(),
                        RandomUtil.maybeIndicator(),
                        RandomUtil.getRandomInt(19),
                        faker.address().city(),
                        RandomUtil.getRandomStateCode(),
                        RandomUtil.maybeOneFrom("25009", "34013", "36031", "50009", "51057"),
                        RandomUtil.country()),
                null);

        controller.update(changes);
    }

    @Then("the patient has the changed birth")
    @Transactional
    public void the_patient_has_the_changed_birth() {
        PatientIdentifier patient = this.patients.one();

        Person actual = this.entityManager.find(Person.class, patient.id());

        assertThat(actual)
                .returns(changes.asOf(), Person::getAsOfDateSex)
                .returns(changes.birth().bornOn().atStartOfDay(), Person::getBirthTime)
                .returns(Gender.resolve(changes.birth().gender()), Person::getBirthGenderCd)
                .returns(changes.birth().birthOrder().shortValue(), Person::getBirthOrderNbr)
                .satisfies(
                        addresses -> assertThat(addresses.addresses())
                                .satisfiesExactly(
                                        address -> assertThat(address)
                                                .returns("F", PostalEntityLocatorParticipation::getCd)
                                                .returns("BIR", PostalEntityLocatorParticipation::getUseCd)
                                                .extracting(PostalEntityLocatorParticipation::getLocator)
                                                .returns(changes.birth().city(), PostalLocator::getCityDescTxt)
                                                .returns(changes.birth().state(), PostalLocator::getStateCd)
                                                .returns(changes.birth().county(), PostalLocator::getCntyCd)
                                                .returns(changes.birth().country(), PostalLocator::getCntryCd)));
    }

    @Then("I am unable to change a patient's birth")
    public void i_am_unable_to_change_a_patient_birth() {
        assertThatThrownBy(() -> controller.update(changes))
                .isInstanceOf(AccessDeniedException.class);
    }
}
