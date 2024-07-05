package gov.cdc.nbs.patient.profile.birth;

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

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientProfileBirthSteps {

    private final Active<PatientInput> input;

    private final Available<PatientIdentifier> patients;

    private final PersonRepository repository;

    private final PatientBirthResolver resolver;

    PatientProfileBirthSteps(
        final Active<PatientInput> input,
        final Available<PatientIdentifier> patients,
        final PersonRepository repository,
        final PatientBirthResolver resolver
    ) {
        this.input = input;
        this.patients = patients;
        this.repository = repository;
        this.resolver = resolver;
    }

    @Given("the new patient's birth is entered")
    public void the_new_patient_birth_is_entered() {
        this.input.active().setAsOf(RandomUtil.getRandomDateInPast());
        this.input.active().setDateOfBirth(RandomUtil.dateInPast());
        this.input.active().setBirthGender(RandomUtil.gender());
    }

    @Then("the new patient has the entered birth")
    public void the_new_patient_has_the_entered_birth() {
        Person actual = repository.findById(this.patients.one().id()).orElseThrow();
        PatientInput expected = this.input.active();

        assertThat(actual)
            .returns(expected.getAsOf(), Person::getAsOfDateSex)
            .returns(expected.getBirthGender(), Person::getBirthGenderCd)
        ;

        if (expected.getDateOfBirth() != null) {
            Instant expected_birth_time = expected.getDateOfBirth()
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();

            assertThat(actual.getBirthTime()).isEqualTo(expected_birth_time);
        } else {
            assertThat(actual.getBirthTime()).isNull();
        }
    }

    @Then("the profile has no associated birth")
    public void the_profile_has_no_associated_birth() {
        long patient = this.patients.one().id();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

        Optional<PatientBirth> actual = this.resolver.resolve(profile);
        assertThat(actual).isEmpty();
    }

    @Then("the profile birth is not accessible")
    public void the_profile_birth_is_not_accessible() {
        long patient = this.patients.one().id();


        PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

        assertThatThrownBy(
            () -> this.resolver.resolve(profile)
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
