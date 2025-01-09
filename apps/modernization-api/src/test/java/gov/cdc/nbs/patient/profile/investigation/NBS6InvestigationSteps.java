package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class NBS6InvestigationSteps {

  private final Active<NBS6InvestigationRequest> activeContext;

  NBS6InvestigationSteps(final Active<NBS6InvestigationRequest> activeContext) {
    this.activeContext = activeContext;
  }

  @Given("I view an investigation from the patient profile")
  public void i_view_an_investigation_from_the_patient_profile() {
    this.activeContext.active(new NBS6InvestigationRequest("PageAction.do", "ReturnToFileEvents"));
  }

  @Given("I view an open investigation from the patient profile")
  public void i_view_an_open_investigation_from_the_patient_profile() {
    this.activeContext.active(new NBS6InvestigationRequest("PageAction.do", "ReturnToFileSummary"));
  }

  @Given("I view an investigation from a queue")
  public void i_view_an_investigation_a_queue() {
    this.activeContext.active(new NBS6InvestigationRequest("PageAction.do", "FileSummary"));
  }

  @Given("I view a legacy investigation from the patient profile")
  public void i_view_a_legacy_investigation_from_the_patient_profile() {
    this.activeContext.active(new NBS6InvestigationRequest("ViewInvestigation3.do", "ReturnToFileEvents"));
  }

  @Given("I view an open legacy investigation from the patient profile")
  public void i_view_an_open_legacy_investigation_from_the_patient_profile() {
    this.activeContext.active(new NBS6InvestigationRequest("ViewInvestigation1.do", "ReturnToFileSummary"));
  }

  @Given("I view a legacy investigation from a queue")
  public void i_view_a_legacy_investigation_a_queue() {
    this.activeContext.active(new NBS6InvestigationRequest("ViewInvestigation2.do", "FileSummary"));
  }
}
