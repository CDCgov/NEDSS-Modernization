package gov.cdc.nbs.event.vaccination;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.vaccination.VaccinationIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VaccinationSteps {


  private final Active<PatientIdentifier> activePatient;
  private final Active<ProviderIdentifier> activeProvider;
  private final Active<VaccinationIdentifier> activeVaccination;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final VaccinationMother mother;

  VaccinationSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<ProviderIdentifier> activeProvider,
      final Active<VaccinationIdentifier> activeVaccination,
      final Active<InvestigationIdentifier> activeInvestigation,
      final VaccinationMother mother
  ) {
    this.activePatient = activePatient;
    this.activeProvider = activeProvider;
    this.activeVaccination = activeVaccination;
    this.activeInvestigation = activeInvestigation;
    this.mother = mother;
  }

  @When("the patient is vaccinated")
  public void create() {
    create("3");
  }

  @When("the patient is vaccinated with {vaccine}")
  public void create(final String vaccine) {
    activePatient.maybeActive()
        .ifPresent(patient -> mother.create(patient, vaccine));
  }

  @When("the vaccination was created on {localDate} at {time}")
  public void createdOn(final LocalDate on, final LocalTime at) {
    activeVaccination.maybeActive()
        .ifPresent(
            vaccination -> mother.createdOn(
                vaccination,
                LocalDateTime.of(on, at)
            )
        );
  }

  @When("the vaccination was administered on {localDate}")
  public void administeredOn(final LocalDate on) {
    activeVaccination.maybeActive()
        .ifPresent(vaccination -> mother.administeredOn(vaccination, on));
  }

  @Given("the vaccination was performed by the provider")
  public void reportedBy() {
    activeVaccination.maybeActive()
        .ifPresent(vaccination -> this.activeProvider.maybeActive()
            .ifPresent(provider -> mother.performedBy(vaccination, provider))
        );
  }

  @Given("the vaccination was performed at {organization}")
  public void reportedAt(final OrganizationIdentifier organization) {
    activeVaccination.maybeActive()
        .ifPresent(vaccination -> mother.performedAt(vaccination, organization));
  }

  @Given("the vaccination is associated with the investigation")
  public void associatedWith() {
    activeVaccination.maybeActive()
        .ifPresent(vaccination -> mother.associated(vaccination, activeInvestigation.active()));
  }
}
