package gov.cdc.nbs.event.document;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.patient.RevisionMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Transactional
public class CaseReportSteps {

  private final Active<JurisdictionIdentifier> activeJurisdiction;
  private final Active<ProgramAreaIdentifier> activeProgramArea;
  private final Active<InvestigationIdentifier> activeInvestigation;

  private final Active<PatientIdentifier> activePatient;
  private final Active<CaseReportIdentifier> activeReport;
  private final CaseReportMother reportMother;
  private final RevisionMother revisionMother;

  public CaseReportSteps(
      final Active<JurisdictionIdentifier> activeJurisdiction,
      final Active<ProgramAreaIdentifier> activeProgramArea,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<PatientIdentifier> activePatient,
      final Active<CaseReportIdentifier> activeReport,
      final CaseReportMother reportMother,
      final RevisionMother revisionMother
  ) {
    this.activeJurisdiction = activeJurisdiction;
    this.activeProgramArea = activeProgramArea;
    this.activeInvestigation = activeInvestigation;
    this.activePatient = activePatient;
    this.activeReport = activeReport;
    this.reportMother = reportMother;
    this.revisionMother = revisionMother;
  }

  @Before("@documents")
  public void clean() {
    reportMother.reset();
  }

  @Given("^(?i)the patient has a Case Report")
  public void the_patient_has_a_Case_Report() {
    ProgramAreaIdentifier programArea = activeProgramArea.active();
    JurisdictionIdentifier jurisdiction = activeJurisdiction.active();
    activePatient.maybeActive()
        .map(revisionMother::revise)
        .ifPresent(patient -> reportMother.create(patient, programArea, jurisdiction));
  }

  @Given("the patient has a Case Report for {condition}")
  public void the_patient_has_a_Case_Report_for(final String condition) {
    ProgramAreaIdentifier programArea = activeProgramArea.active();
    JurisdictionIdentifier jurisdiction = activeJurisdiction.active();
    activePatient.maybeActive()
        .map(revisionMother::revise)
        .ifPresent(patient -> reportMother.create(patient, programArea, jurisdiction, condition));
  }

  @Given("the case report is for {programArea} within {jurisdiction}")
  public void the_case_report_is_within(
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction
  ) {
    this.activeReport.maybeActive().ifPresent(report -> reportMother.within(report, programArea, jurisdiction));
  }

  @When("the case report requires security assignment")
  public void the_patient_only_has_a_report_with_no_program_area_or_jurisdiction() {
    this.activeReport.maybeActive().ifPresent(reportMother::requiresSecurityAssignment);
  }

  @Given("the case report has not been processed")
  public void the_case_report_has_not_been_processed() {
    this.activeReport.maybeActive().ifPresent(reportMother::unprocessed);
  }

  @Given("the case report was sent by (the ){string}")
  public void the_case_report_was_sent_by(final String facility) {
    this.activeReport.maybeActive().ifPresent(report -> reportMother.sentBy(report, facility));
  }

  @Given("the case report was received on {localDate}")
  public void the_case_report_was_received_on(final LocalDate on) {
    this.activeReport.maybeActive().ifPresent(report -> reportMother.addedOn(report, on.atStartOfDay()));
  }

  @Given("the case report was received on {localDate} at {time}")
  public void receivedOn(final LocalDate on, final LocalTime at) {
    this.activeReport.maybeActive().ifPresent(report -> reportMother.addedOn(report, LocalDateTime.of(on, at)));
  }

  @Given("the case report is for the condition {condition}")
  @Given("the case report is for the {condition} condition")
  public void the_case_report_is_for_the_condition(final String condition) {
    this.activeReport.maybeActive().ifPresent(report -> reportMother.withCondition(report, condition));
  }

  @Given("the case report has been updated")
  public void this_case_report_has_been_updated() {
    this.activeReport.maybeActive().ifPresent(reportMother::updated);
  }

  @Given("the case report is associated with the investigation")
  public void associated() {
    this.activeReport.maybeActive()
        .ifPresent(report -> reportMother.associated(report, this.activeInvestigation.active()));
  }
}
