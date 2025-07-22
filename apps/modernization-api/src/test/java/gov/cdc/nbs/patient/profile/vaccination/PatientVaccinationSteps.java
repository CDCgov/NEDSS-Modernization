package gov.cdc.nbs.patient.profile.vaccination;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientVaccinationSteps {

  private final Active<PatientIdentifier> activePatient;

  private final PatientVaccinationResolver resolver;

  PatientVaccinationSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientVaccinationResolver resolver
  ) {
    this.activePatient = activePatient;
    this.resolver = resolver;
  }

  @Then("the profile has an associated vaccination")
  public void the_profile_has_an_associated_vaccination() {
    long patient = this.activePatient.active().id();

    Page<PatientVaccination> actual = this.resolver.find(patient, new GraphQLPage(1));
    assertThat(actual).isNotEmpty();
  }

  @Then("the profile has no associated vaccination")
  public void the_profile_has_no_associated_vaccination() {
    long patient = this.activePatient.active().id();

    Page<PatientVaccination> actual = this.resolver.find(patient, new GraphQLPage(1));
    assertThat(actual).isEmpty();
  }

  @Then("the profile vaccinations are not accessible")
  public void the_profile_vaccinations_are_not_accessible() {
    long patient = this.activePatient.active().id();

    GraphQLPage page = new GraphQLPage(1);

    assertThatThrownBy(
        () -> this.resolver.find(
            patient,
            page
        )
    )
        .isInstanceOf(AccessDeniedException.class);
  }
}
