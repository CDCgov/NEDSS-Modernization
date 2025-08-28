package gov.cdc.nbs.patient.file.demographics.identification;

import java.util.Collection;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.patient.demographics.identification.IdentificationDemographic;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;

public class PatientEditIdentificationEntrySteps {

  private final Active<PatientIdentifier> activePatient;
  private final Available<IdentificationDemographic> available;
  private final PatientIdentificationDemographicFinder finder;

  PatientEditIdentificationEntrySteps(
      final Active<PatientIdentifier> activePatient,
      final Available<IdentificationDemographic> available,
      final PatientIdentificationDemographicFinder finder) {
    this.activePatient = activePatient;
    this.available = available;
    this.finder = finder;
  }

  @Given("I want to change the patient's identifications")
  public void editing() {
    this.available.reset();

    this.activePatient.maybeActive()
        .map(patient -> this.finder.find(patient.id()))
        .stream().flatMap(Collection::stream).map(this::from)
        .forEach(this.available::available);
  }

  private IdentificationDemographic from(final PatientIdentificationDemographic incoming) {
    return new IdentificationDemographic(
        incoming.asOf(),
        Selectable.maybeValue(incoming.type()).orElse(null),
        Selectable.maybeValue(incoming.issuer()).orElse(null),
        incoming.value(),
        incoming.sequence());
  }
}
