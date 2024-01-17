package gov.cdc.nbs.patient.documentsrequiringreview;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DocumentsRequiringReviewSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientProfileDocumentsRequiringReviewRequester requester;
  private final Active<ResultActions> response;

  DocumentsRequiringReviewSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientProfileDocumentsRequiringReviewRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I search for documents requiring review for the patient")
  @When("I view the Patient Profile Documents Requiring Review")
  public void i_view_the_patient_profile_documents_requiring_review() {
    this.activePatient.maybeActive()
        .map(this.requester::documentsRequiringReview)
        .ifPresent(this.response::active);
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
                .doesNotExist());
  }



}
