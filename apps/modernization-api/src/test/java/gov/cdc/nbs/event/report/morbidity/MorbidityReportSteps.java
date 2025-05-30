package gov.cdc.nbs.event.report.morbidity;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Transactional
public class MorbidityReportSteps {

  private final Active<JurisdictionIdentifier> activeJurisdiction;
  private final Active<ProgramAreaIdentifier> activeProgramArea;

  private final Active<PatientIdentifier> activePatient;
  private final Active<OrganizationIdentifier> activeOrganization;
  private final Active<ProviderIdentifier> activeProvider;
  private final Active<MorbidityReportIdentifier> activeReport;
  private final MorbidityReportMother reportMother;

  public MorbidityReportSteps(
      final Active<JurisdictionIdentifier> activeJurisdiction,
      final Active<ProgramAreaIdentifier> activeProgramArea,
      final Active<PatientIdentifier> activePatient,
      final Active<OrganizationIdentifier> activeOrganization,
      final Active<ProviderIdentifier> activeProvider,
      final Active<MorbidityReportIdentifier> activeReport,
      final MorbidityReportMother reportMother
  ) {
    this.activeJurisdiction = activeJurisdiction;
    this.activeProgramArea = activeProgramArea;
    this.activePatient = activePatient;
    this.activeOrganization = activeOrganization;
    this.activeProvider = activeProvider;
    this.activeReport = activeReport;
    this.reportMother = reportMother;
  }

  @Given("^(?i)the patient has a morbidity report")
  @Given("^(?i)the patient has another morbidity report")
  public void create() {
    activePatient.maybeActive()
        .ifPresent(
            patient -> reportMother.create(
                patient,
                this.activeProgramArea.active(),
                this.activeJurisdiction.active(),
                this.activeOrganization.active()
            )
        );
  }

  @Given("(?i)the patient has a morbidity report reported by {organization}")
  public void create(final OrganizationIdentifier organization) {
    activePatient.maybeActive()
        .ifPresent(
            patient -> reportMother.create(
                patient,
                this.activeProgramArea.active(),
                this.activeJurisdiction.active(),
                organization
            )
        );
  }

  @Given("the morbidity report is for {programArea} within {jurisdiction}")
  public void within(
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction
  ) {
    this.activeReport.maybeActive().ifPresent(
        report -> reportMother.within(
            report,
            programArea,
            jurisdiction
        )
    );
  }

  @Given("the morbidity report is electronic")
  public void electronic() {
    activeReport.maybeActive()
        .ifPresent(reportMother::electronic);
  }

  @Given("^(?i)the morbidity report has not been processed")
  public void unprocessed() {
    activeReport.maybeActive().ifPresent(reportMother::unprocessed);
  }

  @Given("the morbidity report is for the condition {condition}")
  public void condition(final String condition) {
    activeReport.maybeActive()
        .ifPresent(report -> reportMother.withCondition(report, condition));
  }

  @Given("the morbidity report was received on {localDate} at {time}")
  public void receivedOn(final LocalDate on, final LocalTime at) {
    activeReport.maybeActive()
        .ifPresent(report -> reportMother.receivedOn(report, LocalDateTime.of(on, at)));
  }

  @Given("the morbidity report was reported on {localDate}")
  public void reportedOn(final LocalDate on) {
    activeReport.maybeActive()
        .ifPresent(report -> reportMother.reportedOn(report, on));
  }

  @Given("the morbidity report was ordered by the provider")
  public void orderedBy() {
    activeReport.maybeActive()
        .ifPresent(report -> this.activeProvider.maybeActive()
            .ifPresent(provider -> reportMother.orderedBy(report, provider))
        );
  }
}
