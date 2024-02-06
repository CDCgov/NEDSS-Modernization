package gov.cdc.nbs.event.report.morbidity;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MorbidityReportSteps {

  private static final OrganizationIdentifier DEFAULT_ORGANIZATION = new OrganizationIdentifier(10003001L);

  private final Active<PatientIdentifier> activePatient;
  private final Active<OrganizationIdentifier> activeOrganization;
  private final Active<ProviderIdentifier> activeProvider;
  private final Active<MorbidityReportIdentifier> activeReport;
  private final MorbidityReportMother reportMother;

  public MorbidityReportSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<OrganizationIdentifier> activeOrganization,
      final Active<ProviderIdentifier> activeProvider,
      final Active<MorbidityReportIdentifier> activeReport,
      final MorbidityReportMother reportMother
  ) {
    this.activePatient = activePatient;
    this.activeOrganization = activeOrganization;
    this.activeProvider = activeProvider;
    this.activeReport = activeReport;
    this.reportMother = reportMother;
  }

  @Before("@morbidity-report")
  public void clean() {
    reportMother.reset();
  }

  @Given("^(?i)the patient has a morbidity report")
  public void patient_has_an_unprocessed_morbidity_report() {
    activePatient.maybeActive()
        .ifPresent(
            patient -> reportMother.create(
                patient,
                this.activeOrganization.maybeActive().orElse(DEFAULT_ORGANIZATION)
            )
        );
  }

  @Given("^(?i)the morbidity report has not been processed")
  public void the_morbidity_report_has_been_processed() {
    activeReport.maybeActive().ifPresent(reportMother::unprocessed);
  }

  @Given("^(?i)the morbidity report was ordered by the provider")
  public void the_morbidity_report_was_ordered_by_the_provider() {
    activeReport.maybeActive()
        .ifPresent(lab -> this.activeProvider.maybeActive()
            .ifPresent(provider -> reportMother.orderedBy(lab, provider))
        );
  }
}
