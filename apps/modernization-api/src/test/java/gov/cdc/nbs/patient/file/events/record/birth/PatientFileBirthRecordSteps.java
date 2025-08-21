package gov.cdc.nbs.patient.file.events.record.birth;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileBirthRecordSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFileBirthRecordRequester requester;
  private final Active<ResultActions> response;

  PatientFileBirthRecordSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFileBirthRecordRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the birth records for the patient")
  public void view() {
    this.activePatient.maybeActive()
        .map(requester::request)
        .ifPresent(response::active);
  }
}
