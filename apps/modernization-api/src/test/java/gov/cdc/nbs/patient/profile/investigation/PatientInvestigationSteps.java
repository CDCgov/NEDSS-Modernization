package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.TestPatients;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientInvestigationSteps {

    @Autowired
    TestPatients patients;

    @Autowired
    PatientInvestigationResolver resolver;

    @Then("the profile has an associated investigation")
    public void the_profile_has_an_associated_investigation() {
        long patient = this.patients.one();

        Page<PatientInvestigation> actual = this.resolver.find(
            patient,
            false,
            new GraphQLPage(1)
        );

        assertThat(actual).isNotEmpty();
    }

    @Then("the profile has no associated investigation")
    public void the_profile_has_no_associated_investigation() {
        long patient = this.patients.one();

        Page<PatientInvestigation> actual = this.resolver.find(
            patient,
            false,
            new GraphQLPage(1)
        );

        assertThat(actual).isEmpty();
    }

    @Then("the profile has an associated open investigation")
    public void the_profile_has_an_associated_open_investigation() {
        long patient = this.patients.one();

        Page<PatientInvestigation> actual = this.resolver.find(
            patient,
            true,
            new GraphQLPage(1)
        );

        assertThat(actual).isNotEmpty();
    }

    @Then("the profile has no associated open investigation")
    public void the_profile_has_no_associated_open_investigation() {
        long patient = this.patients.one();

        Page<PatientInvestigation> actual = this.resolver.find(
            patient,
            true,
            new GraphQLPage(1)
        );

        assertThat(actual).isEmpty();
    }

    @Then("the profile investigations are not accessible")
    public void the_profile_investigations_are_not_accessible() {
        long patient = this.patients.one();

        GraphQLPage page = new GraphQLPage(1);

        assertThatThrownBy(
            () -> this.resolver.find(
                patient,
                false,
                page
            )
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
