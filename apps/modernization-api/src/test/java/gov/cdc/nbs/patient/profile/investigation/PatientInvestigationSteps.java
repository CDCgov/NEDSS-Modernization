package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
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

  @Autowired
  Authenticated authenticated;

  @Then("the profile has an associated investigation")
  public void the_profile_has_an_associated_investigation() {
    Page<PatientInvestigation> actual = investigations();

    assertThat(actual).isNotEmpty();
  }

  private Page<PatientInvestigation> investigations() {
    long patient = this.patients.one();
    return authenticated.perform(
        () -> this.resolver.find(
            patient,
            false,
            new GraphQLPage(1)
        )
    );
  }

  private Page<PatientInvestigation> openInvestigations() {
    long patient = this.patients.one();
    return authenticated.perform(
        () -> this.resolver.find(
            patient,
            true,
            new GraphQLPage(1)
        )
    );
  }

  @Then("the profile has no associated investigation")
  public void the_profile_has_no_associated_investigation() {
    Page<PatientInvestigation> actual = investigations();

    assertThat(actual).isEmpty();
  }

  @Then("the profile has an associated open investigation")
  public void the_profile_has_an_associated_open_investigation() {
    Page<PatientInvestigation> actual = openInvestigations();

    assertThat(actual).isNotEmpty();
  }

  @Then("the profile has no associated open investigation")
  public void the_profile_has_no_associated_open_investigation() {
    Page<PatientInvestigation> actual = openInvestigations();

    assertThat(actual).isEmpty();
  }

  @Then("the profile investigations are not accessible")
  public void the_profile_investigations_are_not_accessible() {
    assertThatThrownBy(this::investigations).isInstanceOf(AccessDeniedException.class);
  }
}
