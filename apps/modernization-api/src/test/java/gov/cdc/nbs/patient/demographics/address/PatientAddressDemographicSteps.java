package gov.cdc.nbs.patient.demographics.address;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import java.time.LocalDate;

public class PatientAddressDemographicSteps {

  private final PatientAddressDemographicApplier applier;

  private final Active<PatientIdentifier> activePatient;

  PatientAddressDemographicSteps(
      final PatientAddressDemographicApplier applier,
      final Active<PatientIdentifier> activePatient) {
    this.applier = applier;
    this.activePatient = activePatient;
  }

  @Given("the patient has an address")
  public void homeAddress() {
    applier.withAddress(activePatient.active());
  }

  @Given("the patient has a {string} address at {string} {string} {string}")
  public void address(final String use, final String address, final String city, final String zip) {

    PatientIdentifier identifier = activePatient.active();

    String resolvedUse = resolveUse(use);

    applier.withAddress(identifier, resolvedUse, address, city, null, null, zip);
  }

  private String resolveUse(final String use) {
    return switch (use.toLowerCase()) {
      case "birth delivery address" -> "BDL";
      case "birth place" -> "BIR";
      case "death place" -> "DTH";
      case "home" -> "H";
      case "primary business" -> "PB";
      case "secondary business" -> "SB";
      case "temporary" -> "TMP";
      default -> throw new IllegalStateException("Unexpected address use: " + use.toLowerCase());
    };
  }

  @Given("the patient has a {addressType} - {addressUse} address at {string} {string} {string}")
  public void address(
      final String type,
      final String use,
      final String address,
      final String city,
      final String zip) {

    PatientIdentifier identifier = activePatient.active();

    applier.withAddress(identifier, type, use, address, city, null, null, zip);
  }

  @Given(
      "the patient has a(n) {addressType} - {addressUse} address at {string} {string} {string} as of {localDate}")
  public void address(
      final String type,
      final String use,
      final String address,
      final String city,
      final String zip,
      final LocalDate asOf) {

    PatientIdentifier identifier = activePatient.active();

    applier.withAddress(identifier, type, use, address, city, null, null, zip, asOf);
  }

  @Given(
      "the patient has a(n) {addressType} - {addressUse} address at {string} {string} {state} {postalCode} as of {localDate}")
  public void address(
      final String type,
      final String use,
      final String address,
      final String city,
      final String state,
      final String zip,
      final LocalDate asOf) {

    PatientIdentifier identifier = activePatient.active();

    applier.withAddress(identifier, type, use, address, city, null, state, zip, asOf);
  }
}
