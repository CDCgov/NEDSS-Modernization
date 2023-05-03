package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.patient.profile.PatientProfile;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientProfileBirthSteps {

    @Autowired
    TestPatients patients;

    @Autowired
    PatientBirthResolver resolver;

    @Then("the profile has no associated birth")
    public void the_profile_has_no_associated_birth() {
        long patient = this.patients.one();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        Optional<PatientBirth> actual = this.resolver.resolve(profile);
        assertThat(actual).isEmpty();
    }

    @Then("the profile birth is not accessible")
    public void the_profile_birth_is_not_accessible() {
        long patient = this.patients.one();


        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        assertThatThrownBy(
            () -> this.resolver.resolve(profile)
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
