package gov.cdc.nbs.patient.profile.identification;

import com.github.javafaker.Faker;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.TestPatient;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.TestAvailable;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileIdentificationSteps {

    private final Faker faker = new Faker();

    @Autowired
    PatientMother mother;

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    PatientIdentificationResolver resolver;

    @Autowired
    TestActive<PatientInput> input;

    @Autowired
    TestPatient patient;

    @Given("the patient has identification")
    public void the_patient_has_identification() {
        mother.withIdentification(patients.one());
    }

    @Given("the new patient's Social Security Number is entered")
    public void the_new_patient_ssn_is_entered() {
        PatientInput.Identification identification = new PatientInput.Identification(
            faker.idNumber().ssnValid(),
            "SS",
            "ANY"
        );


        this.input.active().getIdentifications().add(identification);
    }

    @Given("the new patient's identification is entered")
    public void the_new_patient_identification_is_entered() {
        PatientInput.Identification identification = new PatientInput.Identification(
            RandomUtil.getRandomString(),
            RandomUtil.getRandomString(),
            RandomUtil.getRandomString()
        );


        this.input.active().getIdentifications().add(identification);
    }

    @Then("the new patient has the entered identification")
    @Transactional
    public void the_new_patient_has_the_entered_identification() {
        Person actual = patient.managed();

        Collection<EntityId> identifications = actual.identifications();

        if (!identifications.isEmpty()) {

            assertThat(identifications)
                .satisfiesExactlyInAnyOrder(
                    PatientCreateAssertions.containsIdentifications(input.active().getIdentifications()));
        }

    }

    @Then("the profile has associated identifications")
    public void the_profile_has_associated_identifications() {
        long patient = this.patients.one().id();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

        GraphQLPage page = new GraphQLPage(1);

        Page<PatientIdentification> actual = this.resolver.resolve(profile, page);
        assertThat(actual).isNotEmpty();
    }

    @Then("the profile has no associated identifications")
    public void the_profile_has_no_associated_identifications() {
        long patient = this.patients.one().id();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

        GraphQLPage page = new GraphQLPage(1);

        Page<PatientIdentification> actual = this.resolver.resolve(profile, page);
        assertThat(actual).isEmpty();
    }

    @Then("the profile identifications are not accessible")
    public void the_profile_identification_is_not_accessible() {
        long patient = this.patients.one().id();


        PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

        GraphQLPage page = new GraphQLPage(1);

        assertThatThrownBy(
            () -> this.resolver.resolve(profile, page)
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
