package gov.cdc.nbs.patient.profile.address.change;

import com.github.javafaker.Faker;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
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
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileAddressChangeSteps {

    private final Faker faker = new Faker(new Locale("en-us"));

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    PatientAddressChangeController controller;

    @Autowired
    EntityManager entityManager;

    private NewPatientAddressInput newRequest;
    private UpdatePatientAddressInput updateRequest;
    private DeletePatientAddressInput deleteRequest;

    @Before("@patient-profile-address-change")
    public void reset() {
        newRequest = null;
        updateRequest = null;
        deleteRequest = null;
    }

    @When("a patient's address is added")
    public void a_patient_address_is_added() {
        long patient = patients.one().id();

        newRequest = new NewPatientAddressInput(
                patient,
                RandomUtil.getRandomDateInPast(),
                "H",
                "H",
                faker.address().streetAddress(),
                RandomUtil.getRandomString(),
                faker.address().city(),
                RandomUtil.getRandomStateCode(),
                RandomUtil.getRandomNumericString(15),
                RandomUtil.getRandomString(),
                RandomUtil.getRandomString(10),
                RandomUtil.country(),
                RandomUtil.getRandomString());

        controller.add(newRequest);
    }

    @Then("the patient has the new address")
    @Transactional
    public void the_patient_has_the_new_address() {
        PatientIdentifier patient = this.patients.one();

        Person person = this.entityManager.find(Person.class, patient.id());

        assertThat(person.addresses()).anySatisfy(
                actual -> assertThat(actual)
                        .returns(newRequest.type(), PostalEntityLocatorParticipation::getCd)
                        .returns(newRequest.use(), PostalEntityLocatorParticipation::getUseCd)
                        .returns(newRequest.asOf(), PostalEntityLocatorParticipation::getAsOfDate)
                        .returns(newRequest.comment(), PostalEntityLocatorParticipation::getLocatorDescTxt)
                        .satisfies(
                                address -> assertThat(address)
                                        .extracting(PostalEntityLocatorParticipation::getId)
                                        .returns(newRequest.patient(), EntityLocatorParticipationId::getEntityUid))
                        .satisfies(
                                address -> assertThat(address).extracting(PostalEntityLocatorParticipation::getLocator)
                                        .returns(newRequest.address1(), PostalLocator::getStreetAddr1)
                                        .returns(newRequest.address2(), PostalLocator::getStreetAddr2)
                                        .returns(newRequest.city(), PostalLocator::getCityDescTxt)
                                        .returns(newRequest.state(), PostalLocator::getStateCd)
                                        .returns(newRequest.county(), PostalLocator::getCntyCd)
                                        .returns(newRequest.zipcode(), PostalLocator::getZipCd)
                                        .returns(newRequest.country(), PostalLocator::getCntryCd)
                                        .returns(newRequest.censusTract(), PostalLocator::getCensusTract)));
    }

    @When("a patient's address is changed")
    @Transactional
    public void a_patient_address_is_changed() {
        PostalEntityLocatorParticipation existing = this.entityManager.find(Person.class, patients.one().id())
                .addresses()
                .stream()
                .findFirst()
                .orElseThrow();

        updateRequest = new UpdatePatientAddressInput(
                existing.getId().getEntityUid(),
                existing.getId().getLocatorUid(),
                RandomUtil.getRandomDateInPast(),
                "EC",
                "TMP",
                faker.address().streetAddress(),
                RandomUtil.getRandomString(),
                faker.address().city(),
                RandomUtil.getRandomStateCode(),
                RandomUtil.getRandomNumericString(15),
                RandomUtil.getRandomString(),
                RandomUtil.getRandomString(10),
                RandomUtil.country(),
                RandomUtil.getRandomString());

        controller.update(updateRequest);
    }

    @Then("the patient has the expected address")
    @Transactional
    public void the_patient_has_the_expected_address() {
        PatientIdentifier patient = this.patients.one();

        Person person = this.entityManager.find(Person.class, patient.id());

        assertThat(person.addresses()).anySatisfy(
                actual -> assertThat(actual)
                        .returns(updateRequest.type(), PostalEntityLocatorParticipation::getCd)
                        .returns(updateRequest.use(), PostalEntityLocatorParticipation::getUseCd)
                        .returns(updateRequest.asOf(), PostalEntityLocatorParticipation::getAsOfDate)
                        .returns(updateRequest.comment(), PostalEntityLocatorParticipation::getLocatorDescTxt)
                        .satisfies(
                                address -> assertThat(address)
                                        .extracting(PostalEntityLocatorParticipation::getId)
                                        .returns(updateRequest.patient(), EntityLocatorParticipationId::getEntityUid)
                                        .returns(updateRequest.id(), EntityLocatorParticipationId::getLocatorUid))
                        .satisfies(
                                address -> assertThat(address).extracting(PostalEntityLocatorParticipation::getLocator)
                                        .returns(updateRequest.address1(), PostalLocator::getStreetAddr1)
                                        .returns(updateRequest.address2(), PostalLocator::getStreetAddr2)
                                        .returns(updateRequest.city(), PostalLocator::getCityDescTxt)
                                        .returns(updateRequest.state(), PostalLocator::getStateCd)
                                        .returns(updateRequest.county(), PostalLocator::getCntyCd)
                                        .returns(updateRequest.zipcode(), PostalLocator::getZipCd)
                                        .returns(updateRequest.country(), PostalLocator::getCntryCd)
                                        .returns(updateRequest.censusTract(), PostalLocator::getCensusTract)));
    }

    @When("a patient's address is removed")
    @Transactional
    public void a_patient_address_is_removed() {

        Person patient = this.entityManager.find(Person.class, patients.one().id());

        this.deleteRequest = patient.addresses()
                .stream()
                .findFirst()
                .map(PostalEntityLocatorParticipation::getId)
                .map(id -> new DeletePatientAddressInput(id.getEntityUid(), id.getLocatorUid()))
                .orElseThrow();

        this.controller.delete(this.deleteRequest);
    }

    @Then("the patient does not have the expected address")
    @Transactional
    public void the_patient_does_not_have_the_expected_address() {
        PatientIdentifier patient = this.patients.one();

        Person actual = this.entityManager.find(Person.class, patient.id());

        assertThat(actual.addresses())
                .noneSatisfy(
                        address -> assertThat(address)
                                .extracting(PostalEntityLocatorParticipation::getId)
                                .returns(deleteRequest.patient(), EntityLocatorParticipationId::getEntityUid)
                                .returns(deleteRequest.id(), EntityLocatorParticipationId::getLocatorUid));
    }

    @Then("I am unable to add a patient's address")
    public void i_am_unable_to_add_a_patient_ethnicity() {
        assertThatThrownBy(() -> controller.add(newRequest))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Then("I am unable to change a patient's address")
    public void i_am_unable_to_change_a_patient_ethnicity() {
        assertThatThrownBy(() -> controller.update(updateRequest))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Then("I am unable to remove a patient's address")
    public void i_am_unable_to_remove_a_patient_ethnicity() {
        assertThatThrownBy(() -> controller.delete(deleteRequest))
                .isInstanceOf(AccessDeniedException.class);
    }
}
