package gov.cdc.nbs.patient.profile.address;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.patient.profile.PatientProfile;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileAddressSteps {

    @Autowired
    TestPatients patients;

    @Autowired
    PatientAddressResolver resolver;

    @Then("the profile has associated addresses")
    public void the_profile_has_associated_addresses() {
        long patient = this.patients.one();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        GraphQLPage page = new GraphQLPage(1);

        Page<PatientAddress> actual = this.resolver.resolve(profile, page);
        assertThat(actual).isNotEmpty();
    }

    @Then("the profile has associated no addresses")
    public void the_profile_has_no_associated_addresses() {
        long patient = this.patients.one();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        GraphQLPage page = new GraphQLPage(1);

        Page<PatientAddress> actual = this.resolver.resolve(profile, page);
        assertThat(actual).isEmpty();
    }

    @Then("the profile addresses are not accessible")
    public void the_profile_address_is_not_accessible() {
        long patient = this.patients.one();


        PatientProfile profile = new PatientProfile(patient, "local", (short) 1);

        GraphQLPage page = new GraphQLPage(1);

        assertThatThrownBy(
            () -> this.resolver.resolve(profile, page)
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
