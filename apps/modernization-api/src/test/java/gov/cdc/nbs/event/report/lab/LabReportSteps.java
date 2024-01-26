package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LabReportSteps {

  private static final long PIEDMONT_HOSPITAL = 10003001L;
  private static final OrganizationIdentifier DEFAULT_ORGANIZATION = new OrganizationIdentifier(PIEDMONT_HOSPITAL);
  private final Active<PatientIdentifier> activePatient;
  private final Active<OrganizationIdentifier> activeOrganization;
  private final Active<ProviderIdentifier> activeProvider;

  private final Active<LabReportIdentifier> activeLabReport;
  private final LabReportMother reportMother;

  public LabReportSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<OrganizationIdentifier> activeOrganization,
      final Active<ProviderIdentifier> activeProvider,
      final Active<LabReportIdentifier> activeLabReport,
      final LabReportMother reportMother
  ) {
    this.activePatient = activePatient;
    this.activeOrganization = activeOrganization;
    this.activeProvider = activeProvider;
    this.activeLabReport = activeLabReport;
    this.reportMother = reportMother;
  }

  @Before("@lab-report")
  public void clean() {
    this.reportMother.reset();
  }

  @Given("the patient has a lab report")
  public void the_patient_has_a_lab_report() {
    activePatient.maybeActive()
        .ifPresent(
            patient -> reportMother.create(
                patient,
                this.activeOrganization.maybeActive().orElse(DEFAULT_ORGANIZATION)
            )
        );
  }

  @Given("the patient has a lab report reported by {organization}")
  public void patient_has_an_unprocessed_lab_report(final long organization) {
    activePatient.maybeActive()
        .ifPresent(
            patient -> reportMother.create(
                patient,
                new OrganizationIdentifier(organization)
            )
        );
  }

  @Given("the lab report has not been processed")
  public void the_lab_report_has_been_processed() {
    activeLabReport.maybeActive().ifPresent(reportMother::unprocessed);
  }

  @Given("the lab report is electronic")
  public void the_lab_report_is_electronic() {
    activeLabReport.maybeActive().ifPresent(reportMother::electronic);
  }

  @Given("the lab report was ordered by the provider")
  public void the_lab_report_was_ordered_by_the_provider() {
    activeLabReport.maybeActive()
        .ifPresent(lab -> this.activeProvider.maybeActive()
            .ifPresent(provider -> reportMother.orderedBy(lab, provider))
        );
  }
}
