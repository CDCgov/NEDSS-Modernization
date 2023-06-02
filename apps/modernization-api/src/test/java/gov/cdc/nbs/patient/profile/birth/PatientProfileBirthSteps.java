package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.TestAvailable;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientProfileBirthSteps {

    @Autowired
    TestActive<PatientInput> input;

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    PersonRepository repository;

    @Autowired
    PatientBirthResolver resolver;

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
            .returns(expected.getAsOf(), Person::getAsOfDateGeneral)
            .returns(expected.getBirthGender(), Person::getBirthGenderCd)
        ;

        if(expected.getDateOfBirth() != null) {
            Instant expected_birth_time = expected.getDateOfBirth()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

            assertThat(actual.getBirthTime()).isEqualTo(expected_birth_time);
        }else {
            assertThat(actual.getBirthTime()).isNull();
        }
    }

    @Then("the profile has no associated birth")
    public void the_profile_has_no_associated_birth() {
        long patient = this.patients.one().id();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        Optional<PatientBirth> actual = this.resolver.resolve(profile);
        assertThat(actual).isEmpty();
    }

    @Then("the profile birth is not accessible")
    public void the_profile_birth_is_not_accessible() {
        long patient = this.patients.one().id();


        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        assertThatThrownBy(
            () -> this.resolver.resolve(profile)
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
