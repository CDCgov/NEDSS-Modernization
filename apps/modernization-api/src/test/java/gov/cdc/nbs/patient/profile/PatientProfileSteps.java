package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import gov.cdc.nbs.repository.PersonRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class PatientProfileSteps {

    @Autowired
    TestPatients patients;

    @Autowired
    PersonRepository repository;

    @Autowired
    PatientShortIdentifierResolver shortIdentifierResolver;

    @Autowired
    PatientProfileResolver resolver;

    PatientProfile profile;

    @Before
    public void clearProfile() {
        this.profile = null;
    }

    @When("a profile is requested by patient identifier")
    public void a_profile_is_loaded_by_patient_identifier() {
        this.profile = resolver.find(patients.one(), null).orElse(null);
    }

    @When("a profile is requested by short identifier")
    public void a_profile_is_loaded_by_short_identifier() {
        Person person = repository.findById(patients.one()).orElseThrow();

        long shortId = shortIdentifierResolver.resolve(person.getLocalId()).orElseThrow();

        this.profile = resolver.find(null, shortId).orElse(null);

    }

    @Then("the profile is found")
    public void the_profile_is_found() {

        assertThat(this.profile).isNotNull();
    }

    @Then("the profile is not found")
    public void the_profile_has_no_associated_document() {

        assertThat(this.profile).isNull();
    }
}
