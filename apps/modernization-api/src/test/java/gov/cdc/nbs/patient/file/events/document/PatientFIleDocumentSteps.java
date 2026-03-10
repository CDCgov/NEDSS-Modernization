package gov.cdc.nbs.patient.file.events.document;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFIleDocumentSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFileDocumentsRequester requester;
  private final Active<ResultActions> response;

  PatientFIleDocumentSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFileDocumentsRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the documents for the patient")
  public void view() {
    this.activePatient.maybeActive().map(requester::request).ifPresent(response::active);
  }
}
