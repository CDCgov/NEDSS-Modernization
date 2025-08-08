package gov.cdc.nbs.patient.file.demographics.name;

import java.util.Collection;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.patient.demographics.name.NameDemographic;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;

public class PatientEditNameEntrySteps {
  private final Active<PatientIdentifier> activePatient;
  private final Available<NameDemographic> available;
  private final PatientNameDemographicFinder finder;

  PatientEditNameEntrySteps(
      final Active<PatientIdentifier> activePatient,
      final Available<NameDemographic> available,
      final PatientNameDemographicFinder finder) {
    this.activePatient = activePatient;
    this.available = available;
    this.finder = finder;
  }

  @Given("I want to change the patient's names")
  public void editing() {
    this.available.reset();

    this.activePatient.maybeActive()
        .map(patient -> this.finder.find(patient.id()))
        .stream().flatMap(Collection::stream).map(this::from)
        .forEach(this.available::available);
  }

  private NameDemographic from(final PatientNameDemographic incoming) {
    return new NameDemographic(
        incoming.asOf(),
        Selectable.maybeValue(incoming.type()).orElse(null),
        Selectable.maybeValue(incoming.prefix()).orElse(null),
        incoming.first(),
        incoming.middle(),
        incoming.secondMiddle(),
        incoming.last(),
        incoming.secondLast(),
        Selectable.maybeValue(incoming.suffix()).orElse(null),
        Selectable.maybeValue(incoming.degree()).orElse(null),
        incoming.sequence());
  }
}
