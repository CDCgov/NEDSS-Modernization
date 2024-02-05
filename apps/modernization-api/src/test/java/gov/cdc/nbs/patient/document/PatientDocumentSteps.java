package gov.cdc.nbs.patient.document;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Then;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientDocumentSteps {

  private final GraphQLPage page = new GraphQLPage(1);

  private final Authenticated authenticated;
  private final Available<PatientIdentifier> patients;
  private final PatientDocumentByPatientResolver resolver;

  PatientDocumentSteps(
      final Authenticated authenticated,
      final Available<PatientIdentifier> patients,
      final PatientDocumentByPatientResolver resolver
  ) {
    this.authenticated = authenticated;
    this.patients = patients;
    this.resolver = resolver;
  }

  @Then("the profile has an associated document")
  public void the_profile_has_an_associated_document() {
    long patient = this.patients.one().id();

    Page<PatientDocument> actual = resolve(patient);
    assertThat(actual).isNotEmpty();
  }

  private Page<PatientDocument> resolve(long patient) {
    return authenticated.perform(() -> this.resolver.find(patient, page));
  }

  @Then("the profile documents are not returned")
  public void the_profile_documents_are_not_returned() {
    long patient = this.patients.one().id();


    assertThatThrownBy(
        () -> resolve(patient)
    ).isInstanceOf(AccessDeniedException.class);
  }

  @Then("the profile has no associated document")
  public void the_profile_has_no_associated_document() {
    long patient = this.patients.one().id();

    Page<PatientDocument> actual = resolve(patient);
    assertThat(actual).isEmpty();
  }
}
