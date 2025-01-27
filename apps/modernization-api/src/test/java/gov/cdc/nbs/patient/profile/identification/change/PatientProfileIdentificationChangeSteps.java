package gov.cdc.nbs.patient.profile.identification.change;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.EntityIdId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileIdentificationChangeSteps {

    private final Available<PatientIdentifier> patients;

    private final PatientIdentificationChangeController controller;

    private final EntityManager entityManager;

    PatientProfileIdentificationChangeSteps(
        final Available<PatientIdentifier> patients,
        final PatientIdentificationChangeController controller,
        final EntityManager entityManager
    ) {
        this.patients = patients;
        this.controller = controller;
        this.entityManager = entityManager;
    }

    private NewPatientIdentificationInput newRequest;
    private UpdatePatientIdentificationInput updateRequest;
    private DeletePatientIdentificationInput deleteRequest;

    @Before("@patient-profile-identification-change")
    public void reset() {
        newRequest = null;
        updateRequest = null;
        deleteRequest = null;
    }

    @When("a patient's identification is added")
    public void a_patient_identification_is_added() {
        long patient = patients.one().id();

        newRequest = new NewPatientIdentificationInput(
            patient,
            RandomUtil.dateInPast(),
            RandomUtil.oneFrom("AN", "APT", "CI", "DL"),
            RandomUtil.oneFrom("OTH", "SC", "SD", "TN", "TX"),
            RandomUtil.getRandomNumericString(15)
        );

        controller.add(newRequest);
    }

    @Then("the patient has the new identification")
    @Transactional
    public void the_patient_has_the_new_identification() {
        PatientIdentifier patient = this.patients.one();

        Person person = this.entityManager.find(Person.class, patient.id());

        assertThat(person.identifications()).anySatisfy(
            actual -> assertThat(actual)
                .returns(newRequest.type(), EntityId::getTypeCd)
                .returns(newRequest.asOf(), EntityId::getAsOfDate)
                .returns(newRequest.authority(), EntityId::getAssigningAuthorityCd)
                .returns(newRequest.value(), EntityId::getRootExtensionTxt)
                .satisfies(
                    identification -> assertThat(identification)
                        .extracting(EntityId::getId)
                        .returns((short) 1, EntityIdId::getEntityIdSeq)
                )
        );
    }

    @When("a patient's identification is changed")
    @Transactional
    public void a_patient_identification_is_changed() {
        EntityId existing = this.entityManager.find(Person.class, patients.one().id())
            .identifications()
            .stream()
            .findFirst()
            .orElseThrow();

        updateRequest = new UpdatePatientIdentificationInput(
            existing.getId().getEntityUid(),
            existing.getId().getEntityIdSeq(),
            RandomUtil.dateInPast(),
            RandomUtil.oneFrom("AN", "APT", "CI", "DL"),
            RandomUtil.oneFrom("OTH", "SC", "SD", "TN", "TX"),
            RandomUtil.getRandomNumericString(15)
        );

        controller.update(updateRequest);
    }

    @Then("the patient has the expected identification")
    @Transactional
    public void the_patient_has_the_expected_identification() {
        PatientIdentifier patient = this.patients.one();

        Person person = this.entityManager.find(Person.class, patient.id());

        assertThat(person.identifications()).anySatisfy(
            actual -> assertThat(actual)
                .returns(updateRequest.type(), EntityId::getTypeCd)
                .returns(updateRequest.asOf(), EntityId::getAsOfDate)
                .returns(updateRequest.authority(), EntityId::getAssigningAuthorityCd)
                .returns(updateRequest.value(), EntityId::getRootExtensionTxt)
                .satisfies(
                    identification -> assertThat(identification)
                        .extracting(EntityId::getId)
                        .returns((short) updateRequest.sequence(), EntityIdId::getEntityIdSeq)
                )
        );
    }

    @When("a patient's identification is removed")
    @Transactional
    public void a_patient_identification_is_removed() {

        Person patient = this.entityManager.find(Person.class, patients.one().id());

        this.deleteRequest = patient.identifications()
            .stream()
            .findFirst()
            .map(EntityId::getId)
            .map(id -> new DeletePatientIdentificationInput(id.getEntityUid(), id.getEntityIdSeq()))
            .orElseThrow();

        this.controller.delete(this.deleteRequest);
    }

    @Then("the patient does not have the expected identification")
    @Transactional
    public void the_patient_does_not_have_the_expected_identification() {
        PatientIdentifier patient = this.patients.one();

        Person actual = this.entityManager.find(Person.class, patient.id());

        assertThat(actual.identifications())
            .noneSatisfy(
                identification -> assertThat(identification)
                    .extracting(EntityId::getId)
                    .returns(deleteRequest.patient(), EntityIdId::getEntityUid)
                    .returns(deleteRequest.sequence(), EntityIdId::getEntityIdSeq)
            )
        ;
    }

    @Then("I am unable to add a patient's identification")
    public void i_am_unable_to_add_a_patient_ethnicity() {
        assertThatThrownBy(() -> controller.add(newRequest))
            .isInstanceOf(AccessDeniedException.class);
    }

    @Then("I am unable to change a patient's identification")
    public void i_am_unable_to_change_a_patient_ethnicity() {
        assertThatThrownBy(() -> controller.update(updateRequest))
            .isInstanceOf(AccessDeniedException.class);
    }

    @Then("I am unable to remove a patient's identification")
    public void i_am_unable_to_remove_a_patient_ethnicity() {
        assertThatThrownBy(() -> controller.delete(deleteRequest))
            .isInstanceOf(AccessDeniedException.class);
    }
}
