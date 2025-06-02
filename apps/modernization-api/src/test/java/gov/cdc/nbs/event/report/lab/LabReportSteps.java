package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Transactional
public class LabReportSteps {

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

  @Given("the patient has a(nother) Lab(oratory) Report")
  @Given("the patient has a(nother) lab(oratory) Report")
  @Given("the patient has a(nother) lab(oratory) report")
  public void the_patient_has_a_lab_report() {
    activePatient.maybeActive()
        .ifPresent(
            patient -> reportMother.create(
                patient,
                this.activeOrganization.active(),
                this.activeJurisdiction.active(),
                this.activeProgramArea.active()
            )
        );
  }

  @Given("the patient has a lab(oratory) report reported by {organization}")
  public void patient_has_an_unprocessed_lab_report(final OrganizationIdentifier organization) {
    activePatient.maybeActive()
        .ifPresent(
            patient -> reportMother.create(
                patient,
                organization,
                this.activeJurisdiction.active(),
                this.activeProgramArea.active()
            )
        );
  }

  @Given("the lab(oratory) report is for {programArea} within {jurisdiction}")
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

  @Given("the lab(oratory) report has not been processed")
  public void the_lab_report_has_been_processed() {
    activeReport.maybeActive().ifPresent(reportMother::unprocessed);
  }

  @Given("the lab(oratory) report is electronic")
  public void the_lab_report_is_electronic() {
    activeReport.maybeActive().ifPresent(reportMother::electronic);
  }

  @Given("the lab(oratory) report was entered externally")
  public void the_lab_report_was_entered_externally() {
    activeReport.maybeActive().ifPresent(reportMother::enteredExternally);
  }

  @Given("the lab(oratory) report is for a pregnant patient")
  public void the_lab_report_is_for_a_pregnant_patient() {
    activeReport.maybeActive().ifPresent(reportMother::forPregnantPatient);
  }

  @Given("the lab(oratory) report has an Accession number of {string}")
  @Given("the lab(oratory) report was filled by {string}")
  public void the_lab_report_was_filled_by(final String filler) {
    activeReport.maybeActive().ifPresent(lab -> reportMother.filledBy(lab, filler));
  }

  @Given("the lab(oratory) report was ordered by the {organization} facility")
  public void the_lab_report_was_ordered_by_the_organization(final OrganizationIdentifier organization) {
    activeReport.maybeActive()
        .ifPresent(lab -> reportMother.orderedBy(lab, organization));
  }

  @Given("the lab(oratory) report was ordered by the provider")
  public void the_lab_report_was_ordered_by_the_provider() {
    activeReport.maybeActive()
        .ifPresent(lab -> this.activeProvider.maybeActive()
            .ifPresent(provider -> reportMother.orderedBy(lab, provider))
        );
  }

  @Given("the lab(oratory) report was created by {user} on {localDate}")
  public void the_lab_report_was_created_on(final ActiveUser user, final LocalDate on) {
    activeReport.maybeActive().ifPresent(lab -> this.reportMother.created(lab, user.id(), on));
  }

  @Given("the lab(oratory) report was updated by {user} on {localDate}")
  public void the_lab_report_was_updated_on(final ActiveUser user, final LocalDate on) {
    activeReport.maybeActive().ifPresent(lab -> this.reportMother.updated(lab, user.id(), on));
  }

  @Given("the lab(oratory) report was received on {localDate}")
  public void receivedOn(final LocalDate on) {
    activeReport.maybeActive().ifPresent(lab -> this.reportMother.receivedOn(lab, on.atStartOfDay()));
  }

  @Given("the lab(oratory) report was received on {localDate} at {time}")
  public void receivedOn(final LocalDate on, final LocalTime at) {
    activeReport.maybeActive().ifPresent(lab -> this.reportMother.receivedOn(lab, LocalDateTime.of(on, at)));
  }

  @Given("the lab(oratory) report was reported on {localDate}")
  public void reportedOn(final LocalDate on) {
    activeReport.maybeActive()
        .ifPresent(report -> reportMother.reportedOn(report, on));
  }

  @Given("the lab(oratory) report was collected on {localDate}")
  public void collectedOn(final LocalDate on) {
    activeReport.maybeActive()
        .ifPresent(report -> reportMother.collectedOn(report, on));
  }

}
