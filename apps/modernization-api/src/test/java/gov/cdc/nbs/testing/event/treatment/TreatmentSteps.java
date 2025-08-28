package gov.cdc.nbs.testing.event.treatment;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TreatmentSteps {

  private final TreatmentMother mother;
  private final Active<PatientIdentifier> activePatient;
  private final Active<ProviderIdentifier> activeProvider;
  private final Active<ProgramAreaIdentifier> activeProgramArea;
  private final Active<MorbidityReportIdentifier> activeMorbidity;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<TreatmentIdentifier> activeTreatment;

  TreatmentSteps(
      final TreatmentMother mother,
      final Active<PatientIdentifier> activePatient,
      final Active<ProviderIdentifier> activeProvider,
      final Active<ProgramAreaIdentifier> activeProgramArea,
      final Active<MorbidityReportIdentifier> activeMorbidity,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<TreatmentIdentifier> activeTreatment
  ) {
    this.mother = mother;
    this.activePatient = activePatient;
    this.activeProvider = activeProvider;
    this.activeProgramArea = activeProgramArea;
    this.activeMorbidity = activeMorbidity;
    this.activeInvestigation = activeInvestigation;
    this.activeTreatment = activeTreatment;
  }

  @Given("the patient is a subject of a Treatment")
  public void subjectOf() {
    this.activeInvestigation.maybeActive().ifPresent(mother::create);
    this.activePatient.maybeActive()
        .ifPresent(patient -> mother.create(activeProgramArea.active(), patient, "181"));
  }

  @Given("the patient is a subject of a(n) {treatment} treatment")
  public void treated(final String treatment) {
    this.activePatient.maybeActive()
        .ifPresent(patient -> mother.create(activeProgramArea.active(), patient, treatment));
  }

  @Given("the patient is a subject of the custom {string} treatment")
  public void custom(final String treatment) {
    this.activePatient.maybeActive()
        .ifPresent(patient -> mother.createCustom(patient, activeProgramArea.active(), treatment));
  }

  @Given("the morbidity report includes a {treatment} treatment")
  public void treatedOnMorbidity(final String treatment) {
    this.activeMorbidity.maybeActive().ifPresent(report -> mother.create(report, treatment));
  }

  @Given("the morbidity report includes the custom {string} treatment")
  public void customTreatedOnMorbidity(final String treatment) {
    this.activeMorbidity.maybeActive().ifPresent(report -> mother.create(report, "OTH", treatment));
  }

  @Given("the treatment was created on {localDate} at {time}")
  public void createdOn(final LocalDate on, final LocalTime at) {
    this.activeTreatment.maybeActive().ifPresent(treatment -> mother.createdOn(treatment, LocalDateTime.of(on, at)));
  }

  @Given("the treatment was treated on {localDate}")
  public void treatedOn(final LocalDate on) {
    this.activeTreatment.maybeActive().ifPresent(treatment -> mother.treatedOn(treatment, on));
  }

  @Given("the treatment was provided by the provider")
  public void reportedBy() {
    activeTreatment.maybeActive()
        .ifPresent(treatment -> this.activeProvider.maybeActive()
            .ifPresent(provider -> mother.providedBy(treatment, provider))
        );
  }

  @Given("the treatment was reported at {organization}")
  public void reportedAt(final OrganizationIdentifier organization) {
    activeTreatment.maybeActive()
        .ifPresent(treatment -> mother.reportedAt(treatment, organization));
  }

  @Given("the treatment is associated with the investigation")
  public void associatedWith() {
    activeTreatment.maybeActive()
        .ifPresent(treatment -> mother.associated(treatment, activeInvestigation.active()));
  }
}
