package gov.cdc.nbs.patient.documentsrequiringreview;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DocumentsRequiringReviewSteps {

  private final Active<PatientIdentifier> activePatient;
  private final Active<LabReportIdentifier> activeLabReport;
  private final Active<Pageable> activePageable;
  private final PatientProfileDocumentsRequiringReviewRequester requester;
  private final Active<ResultActions> response;

  DocumentsRequiringReviewSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<LabReportIdentifier> activeLabReport,
      final Active<Pageable> activePageable,
      final PatientProfileDocumentsRequiringReviewRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.activeLabReport = activeLabReport;
    this.activePageable = activePageable;
    this.requester = requester;
    this.response = response;
  }

  @When("I search for documents requiring review for the patient")
  @When("I view the Patient Profile Documents Requiring Review")
  public void i_view_the_patient_profile_documents_requiring_review() {
    this.activePatient.maybeActive()
        .map(this::request)
        .ifPresent(this.response::active);
  }

  private ResultActions request(final PatientIdentifier patient) {
    return
        this.activePageable.maybeActive().isPresent()
            ? this.requester.documentsRequiringReview(patient, this.activePageable.active())
            : this.requester.documentsRequiringReview(patient);
  }

  @Then("the patient has no documents requiring review")
  public void the_patient_has_no_documents_requiring_review() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findDocumentsRequiringReviewForPatient.total").value(equalTo(0)))
        .andExpect(jsonPath("$.data.findDocumentsRequiringReviewForPatient.content").isEmpty());
  }

  @Then("the {documentType} requiring review is returned")
  public void the_correct_document_requiring_review_is_returned(final String documentType) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findDocumentsRequiringReviewForPatient.content[*].type")
            .value(hasItem(equalTo(documentType))));
  }

  @Then("no {documentType}(s) are returned")
  public void no_lab_reports_are_returned(final String documentType) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findDocumentsRequiringReviewForPatient.content[*]").exists())
        .andExpect(
            jsonPath(
                "$.data.findDocumentsRequiringReviewForPatient.content[?(@.type=='%s')]",
                documentType
            )
                .doesNotExist()
        );
  }

  @Then("the patient {documentType} requiring review was reported by the {string} facility")
  public void the_patient_document_requiring_review_was_reported_by(
      final String documentType,
      final String facility
  )
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findDocumentsRequiringReviewForPatient.content[?(@.type=='%s')].facilityProviders.reportingFacility.name",
                documentType
            )
                .value(hasItem(equalTo(facility)))
        );

  }

  @Then("the patient {documentType} requiring review was ordered by {string}")
  public void the_patient_document_requiring_review_was_ordered_by(
      final String documentType,
      final String provider
  )
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findDocumentsRequiringReviewForPatient.content[?(@.type=='%s')].facilityProviders.orderingProvider.name",
                documentType
            )
                .value(hasItem(equalTo(provider)))
        );

  }

  @Then("the patient {documentType} requiring review is electronic")
  public void the_patient_document_requiring_review_is_electronic(final String documentType)
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findDocumentsRequiringReviewForPatient.content[?(@.type=='%s')].isElectronic",
                documentType
            )
                .value(hasItem(equalTo(true)))
        );

  }

  @Then("the patient {documentType} requiring review is not electronic")
  public void the_patient_document_requiring_review_is_not_electronic(final String documentType)
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findDocumentsRequiringReviewForPatient.content[?(@.type=='%s')].isElectronic",
                documentType
            )
                .value(hasItem(equalTo(false)))
        );
  }

  @Then("the patient {documentType} requiring review has the event of the Lab Report")
  public void the_patient_document_requiring_review_has_the_event_of_the_lab_report(final String documentType)
      throws Exception {
    String expected = this.activeLabReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    checkDocumentEvent(documentType, expected);
  }

  private void checkDocumentEvent(final String documentType, final String event)
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findDocumentsRequiringReviewForPatient.content[?(@.type=='%s')].localId",
                documentType
            )
                .value(hasItem(equalTo(event)))
        );
  }
}
