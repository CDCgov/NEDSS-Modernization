package gov.cdc.nbs.patient.file.demographics.race;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.patient.demographics.race.RaceDemographic;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;
import java.util.Collection;
import java.util.List;

public class PatientEditRaceEntrySteps {

  private final Active<PatientIdentifier> activePatient;
  private final Available<RaceDemographic> available;
  private final PatientRaceDemographicFinder finder;

  PatientEditRaceEntrySteps(
      final Active<PatientIdentifier> activePatient,
      final Available<RaceDemographic> available,
      final PatientRaceDemographicFinder finder) {
    this.activePatient = activePatient;
    this.available = available;
    this.finder = finder;
  }

  @Given("I want to change the patient's race")
  public void editing() {
    this.activePatient.maybeActive().map(patient -> this.finder.find(patient.id())).stream()
        .flatMap(Collection::stream)
        .map(this::from)
        .forEach(this.available::available);
  }

  private RaceDemographic from(final PatientRaceDemographic incoming) {

    List<String> detailed = incoming.detailed().stream().map(Selectable::value).toList();

    return new RaceDemographic(incoming.asOf(), incoming.race().value(), detailed);
  }
}
