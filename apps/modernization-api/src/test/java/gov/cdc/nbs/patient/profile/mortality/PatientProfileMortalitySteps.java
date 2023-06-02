package gov.cdc.nbs.patient.profile.mortality;

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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientProfileMortalitySteps {

    @Autowired
    TestActive<PatientInput> input;

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    PersonRepository repository;

    @Autowired
    PatientMortalityResolver resolver;

    @Given("the new patient's mortality is entered")
    public void the_new_patient_mortality_is_entered() {
        PatientInput active = this.input.active();

        active.setAsOf(RandomUtil.getRandomDateInPast());
        active.setDeceased(RandomUtil.deceased());
        active.setDeceasedTime(RandomUtil.getRandomDateInPast());
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

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        Optional<PatientMortality> actual = this.resolver.resolve(profile);
        assertThat(actual).isEmpty();
    }

    @Then("the profile mortality is not accessible")
    public void the_profile_mortality_is_not_accessible() {
        long patient = this.patients.one().id();


        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        assertThatThrownBy(
            () -> this.resolver.resolve(profile)
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
