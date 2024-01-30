package gov.cdc.nbs.patient.morbidity;

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
public class PatientMorbidityReportSteps {

  private final Active<PatientIdentifier> patients;
  private final PatientMorbidityResolver resolver;

  PatientMorbidityReportSteps(
      final Active<PatientIdentifier> patients,
      final PatientMorbidityResolver resolver
  ) {
    this.patients = patients;
    this.resolver = resolver;
  }

  @Then("the profile has an associated morbidity report")
  public void the_profile_has_an_associated_morbidity_report() {
    long patient = this.patients.active().id();

    Page<PatientMorbidity> actual = this.resolver.find(patient, new GraphQLPage(5));
    assertThat(actual).isNotEmpty();
  }


  @Then("the profile has no associated morbidity report")
  public void the_profile_has_no_associated_morbidity_report() {
    long patient = this.patients.active().id();

    Page<PatientMorbidity> actual = this.resolver.find(patient, new GraphQLPage(5));
    assertThat(actual).isEmpty();
  }


  @Then("the profile morbidity reports are not returned")
  public void the_profile_morbidity_reports_are_not_returned() {
    long patient = this.patients.active().id();

    GraphQLPage page = new GraphQLPage(5);

    assertThatThrownBy(() -> {
      this.resolver.find(patient, page);
    })
        .isInstanceOf(AccessDeniedException.class);
  }
}
