package gov.cdc.nbs.patient.profile.mortality;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientProfileMortalitySteps {

    private final Active<PatientInput> input;

    private final Available<PatientIdentifier> patients;

    private final PersonRepository repository;

    private final PatientMortalityResolver resolver;

    PatientProfileMortalitySteps(
        final Active<PatientInput> input,
        final Available<PatientIdentifier> patients,
        final PersonRepository repository,
        final PatientMortalityResolver resolver
    ) {
        this.input = input;
        this.patients = patients;
        this.repository = repository;
        this.resolver = resolver;
    }

    @Given("the new patient's mortality is entered")
    public void the_new_patient_mortality_is_entered() {
        PatientInput active = this.input.active();

        active.setAsOf(RandomUtil.dateInPast());
        active.setDeceased(RandomUtil.deceased());
        active.setDeceasedTime(RandomUtil.dateInPast());
    }

    @Then("the new patient has the entered mortality")
    public void the_new_patient_has_the_entered_mortality() {
        Person actual = repository.findById(this.patients.one().id()).orElseThrow();
        PatientInput expected = this.input.active();

        assertThat(actual)
            .returns(expected.getAsOf(), Person::getAsOfDateMorbidity)
            .returns(expected.getDeceased(), Person::getDeceasedIndCd)
            .returns(expected.getDeceasedTime(), Person::getDeceasedTime)
        ;
    }

    @Then("the profile has no associated mortality")
    public void the_profile_has_no_associated_mortality() {
        long patient = this.patients.one().id();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

        Optional<PatientMortality> actual = this.resolver.resolve(profile);
        assertThat(actual).isEmpty();
    }

    @Then("the profile mortality is not accessible")
    public void the_profile_mortality_is_not_accessible() {
        long patient = this.patients.one().id();


        PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

        assertThatThrownBy(
            () -> this.resolver.resolve(profile)
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
