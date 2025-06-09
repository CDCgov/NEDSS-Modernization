package gov.cdc.nbs.patient.investigation;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileInvestigationsSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFileInvestigationsRequester requester;
  private final Active<ResultActions> response;

  PatientFileInvestigationsSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFileInvestigationsRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the investigations for a patient")
  public void all() {
    this.activePatient.maybeActive()
        .map(this.requester::all)
        .ifPresent(this.response::active);
  }

  @When("I view the open investigations for a patient")
  public void open() {
    this.activePatient.maybeActive()
        .map(this.requester::open)
        .ifPresent(this.response::active);
  }

}
