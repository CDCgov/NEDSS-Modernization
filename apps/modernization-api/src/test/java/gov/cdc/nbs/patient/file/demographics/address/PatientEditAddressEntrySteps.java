package gov.cdc.nbs.patient.file.demographics.address;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.patient.demographics.address.AddressDemographic;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;
import java.util.Collection;

public class PatientEditAddressEntrySteps {

  private final Active<PatientIdentifier> activePatient;
  private final Available<AddressDemographic> available;
  private final PatientAddressDemographicFinder finder;

  PatientEditAddressEntrySteps(
      final Active<PatientIdentifier> activePatient,
      final Available<AddressDemographic> available,
      final PatientAddressDemographicFinder finder) {
    this.activePatient = activePatient;
    this.available = available;
    this.finder = finder;
  }

  @Given("I want to change the patient's addresses")
  public void editing() {
    this.activePatient.maybeActive().map(patient -> this.finder.find(patient.id())).stream()
        .flatMap(Collection::stream)
        .map(this::from)
        .forEach(this.available::available);
  }

  private AddressDemographic from(final PatientAddressDemographic incoming) {
    return new AddressDemographic(
        incoming.identifier(),
        incoming.asOf(),
        incoming.type().value(),
        incoming.use().value(),
        incoming.address1(),
        incoming.address2(),
        incoming.city(),
        Selectable.maybeValue(incoming.state()).orElse(null),
        incoming.zipcode(),
        Selectable.maybeValue(incoming.county()).orElse(null),
        incoming.censusTract(),
        Selectable.maybeValue(incoming.country()).orElse(null),
        incoming.comment());
  }
}
