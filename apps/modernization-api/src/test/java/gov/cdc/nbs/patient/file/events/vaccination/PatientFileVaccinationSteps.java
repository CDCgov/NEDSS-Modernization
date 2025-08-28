package gov.cdc.nbs.patient.file.events.vaccination;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileVaccinationSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFileVaccinationsRequester requester;
  private final Active<ResultActions> response;

  PatientFileVaccinationSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFileVaccinationsRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the vaccinations for the patient")
  public void view() {
    this.activePatient.maybeActive()
        .map(requester::request)
        .ifPresent(response::active);
  }
}
