package gov.cdc.nbs.patient.file.demographics.phone;

import java.util.Collection;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.patient.demographics.phone.PhoneDemographic;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;

public class PatientEditPhoneEntrySteps {

  private final Active<PatientIdentifier> activePatient;
  private final Available<PhoneDemographic> available;
  private final PatientPhoneDemographicFinder finder;

  public PatientEditPhoneEntrySteps(
      final Active<PatientIdentifier> activePatient,
      final Available<PhoneDemographic> available,
      final PatientPhoneDemographicFinder finder) {
    this.activePatient = activePatient;
    this.available = available;
    this.finder = finder;

  }

  @Given("I want to change the patient's phone numbers")
  public void editing() {
    this.available.reset();

    this.activePatient.maybeActive()
        .map(patient -> this.finder.find(patient.id()))
        .stream().flatMap(Collection::stream).map(this::from)
        .forEach(this.available::available);
  }

  private PhoneDemographic from(final PatientPhoneDemographic incoming) {

    return new PhoneDemographic(
        incoming.identifier(),
        incoming.asOf(),
        Selectable.maybeValue(incoming.type()).orElse(null),
        Selectable.maybeValue(incoming.use()).orElse(null),
        incoming.countryCode(),
        incoming.phoneNumber(),
        incoming.extension(),
        incoming.email(),
        incoming.url(),
        incoming.comment());

  }
}
