package gov.cdc.nbs.patient.profile.identification;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.patient.profile.PatientProfile;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileIdentificationSteps {

    @Autowired
    TestPatients patients;

    @Autowired
    PatientIdentificationResolver resolver;

    @Then("the profile has associated identifications")
    public void the_profile_has_associated_identificationes() {
        long patient = this.patients.one();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        GraphQLPage page = new GraphQLPage(1);

        Page<PatientIdentification> actual = this.resolver.resolve(profile, page);
        assertThat(actual).isNotEmpty();
    }

    @Then("the profile has associated no identifications")
    public void the_profile_has_no_associated_identificationes() {
        long patient = this.patients.one();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        GraphQLPage page = new GraphQLPage(1);

        Page<PatientIdentification> actual = this.resolver.resolve(profile, page);
        assertThat(actual).isEmpty();
    }

    @Then("the profile identifications are not accessible")
    public void the_profile_identification_is_not_accessible() {
        long patient = this.patients.one();


        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        GraphQLPage page = new GraphQLPage(1);

        assertThatThrownBy(
            () -> this.resolver.resolve(profile, page)
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
