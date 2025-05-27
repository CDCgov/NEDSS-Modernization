package gov.cdc.nbs.patient.treatment;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.TestPatientIdentifier;
import io.cucumber.java.en.Then;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientTreatmentSteps {

  private final TestPatientIdentifier patients;

  private final PatientTreatmentByPatientResolver resolver;

  PatientTreatmentSteps(
      final TestPatientIdentifier patients,
      final PatientTreatmentByPatientResolver resolver
  ) {
    this.patients = patients;
    this.resolver = resolver;
  }


  @Then("the profile has an associated Treatment")
  public void the_profile_has_an_associated_treatment() {
    long patient = this.patients.one().id();

    Page<PatientTreatment> actual = this.resolver.find(patient, new GraphQLPage(5));
    assertThat(actual).isNotEmpty();
  }

  @Then("the profile has no associated Treatments")
  public void the_profile_has_no_associated_treatments() {
    long patient = this.patients.one().id();

    Page<PatientTreatment> actual = this.resolver.find(patient, new GraphQLPage(5));
    assertThat(actual).isEmpty();
  }

  @Then("the profile Treatments are not returned")
  public void the_profile_treatments_are_not_returned() {
    long patient = this.patients.one().id();

    GraphQLPage page = new GraphQLPage(5);

    assertThatThrownBy(() -> {
      this.resolver.find(patient, page);
    })
        .isInstanceOf(AccessDeniedException.class);
  }
}
