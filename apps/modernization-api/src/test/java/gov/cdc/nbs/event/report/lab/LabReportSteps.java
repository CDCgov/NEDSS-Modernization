package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Transactional
public class LabReportSteps {

  private static final OrganizationIdentifier DEFAULT_ORGANIZATION = new OrganizationIdentifier(10003001L);
  private final Active<PatientIdentifier> activePatient;
  private final Active<JurisdictionIdentifier> activeJurisdiction;
  private final Active<ProgramAreaIdentifier> activeProgramArea;
  private final Active<OrganizationIdentifier> activeOrganization;
  private final Active<ProviderIdentifier> activeProvider;
  private final Active<LabReportIdentifier> activeReport;
  private final LabReportMother reportMother;

  public LabReportSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<JurisdictionIdentifier> activeJurisdiction,
      final Active<ProgramAreaIdentifier> activeProgramArea,
      final Active<OrganizationIdentifier> activeOrganization,
      final Active<ProviderIdentifier> activeProvider,
      final Active<LabReportIdentifier> activeReport,
      final LabReportMother reportMother
  ) {
    this.activePatient = activePatient;
    this.activeJurisdiction = activeJurisdiction;
    this.activeProgramArea = activeProgramArea;
    this.activeOrganization = activeOrganization;
    this.activeProvider = activeProvider;
    this.activeReport = activeReport;
    this.reportMother = reportMother;
  }

  @Before("@lab-report")
  public void clean() {
    this.reportMother.reset();
  }

  @Given("^(?i)the patient has a lab report")
  @Given("^(?i)the patient has another lab report")
  public void the_patient_has_a_lab_report() {
    activePatient.maybeActive()
        .ifPresent(
            patient -> reportMother.create(
                patient,
                this.activeOrganization.maybeActive().orElse(DEFAULT_ORGANIZATION),
                this.activeJurisdiction.active(),
                this.activeProgramArea.active()
            )
        );
  }

  @Given("the patient has a lab report reported by {organization}")
  public void patient_has_an_unprocessed_lab_report(final long organization) {
    activePatient.maybeActive()
        .ifPresent(
            patient -> reportMother.create(
                patient,
                new OrganizationIdentifier(organization),
                this.activeJurisdiction.active(),
                this.activeProgramArea.active()
            )
        );
  }

  @Given("the lab report is for {programArea} within {jurisdiction}")
  public void patient_has_a_lab_report_within(
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction
  ) {
    activeReport.maybeActive().ifPresent(
        lab -> reportMother.within(
            lab,
            programArea,
            jurisdiction
        )
    );
  }

  @Given("the lab report has not been processed")
  public void the_lab_report_has_been_processed() {
    activeReport.maybeActive().ifPresent(reportMother::unprocessed);
  }

  @Given("the lab report is electronic")
  public void the_lab_report_is_electronic() {
    activeReport.maybeActive().ifPresent(reportMother::electronic);
  }

  @Given("the lab report was entered externally")
  public void the_lab_report_was_entered_externally() {
    activeReport.maybeActive().ifPresent(reportMother::enteredExternally);
  }

  @Given("the lab report is for a pregnant patient")
  public void the_lab_report_is_for_a_pregnant_patient() {
    activeReport.maybeActive().ifPresent(reportMother::forPregnantPatient);
  }

  @Given("the lab report has an Accession number of {string}")
  @Given("the lab report was filled by {string}")
  public void the_lab_report_was_filled_by(final String filler) {
    activeReport.maybeActive().ifPresent(lab -> reportMother.filledBy(lab, filler));
  }

  @Given("the lab report was ordered by the {organization} facility")
  public void the_lab_report_was_ordered_by_the_organization(final long organization) {
    activeReport.maybeActive()
        .ifPresent(lab -> reportMother.orderedBy(lab, organization));
  }

  @Given("the lab report was ordered by the provider")
  public void the_lab_report_was_ordered_by_the_provider() {
    activeReport.maybeActive()
        .ifPresent(lab -> this.activeProvider.maybeActive()
            .ifPresent(provider -> reportMother.orderedBy(lab, provider))
        );
  }

  @Given("the lab report was created by {user} on {date}")
  public void the_lab_report_was_created_on(final ActiveUser user, final Instant date) {
    activeReport.maybeActive().ifPresent(lab -> this.reportMother.created(lab, user.id(), date));
  }

  @Given("the lab report was updated by {user} on {date}")
  public void the_lab_report_was_updated_on(final ActiveUser user, final Instant date) {
    activeReport.maybeActive().ifPresent(lab -> this.reportMother.updated(lab, user.id(), date));
  }

  @Given("the lab report was received on {date}")
  public void the_lab_report_was_received_on(final Instant date) {
    activeReport.maybeActive().ifPresent(lab -> this.reportMother.receivedOn(lab, date));
  }

}
